package bagPrograms;

import java.util.Arrays;

public class ResizableArrayBag<T> implements BagInterface<T> {
	private T[] bag;
	private int numberOfEntries;
	private boolean integrityOK = false;
	private static final int DEFAULT_CAPACITY = 25;
	private static final int MAX_CAPACITY = 10000;

	public ResizableArrayBag() {
		this(DEFAULT_CAPACITY);
	}

	public ResizableArrayBag(int initialCapacity) {
		checkCapacity(initialCapacity);

		@SuppressWarnings("unchecked")
		T[] tempBag = (T[]) new Object[initialCapacity];
		bag = tempBag;
		numberOfEntries = 0;
		integrityOK = true;
	}

	public ResizableArrayBag(T[] contents) {
		checkCapacity(contents.length);
		bag = Arrays.copyOf(contents, contents.length);
		numberOfEntries = contents.length;
		integrityOK = true;
	}

	public boolean add(T newEntry) {
		checkintegrity();
		if (isArrayFull()) {
			doubleCapacity();
		}

		bag[numberOfEntries] = newEntry;
		numberOfEntries++;

		return true;
	}

	public T[] toArray() {
		checkintegrity();

		@SuppressWarnings("unchecked")
		T[] result = (T[]) new Object[numberOfEntries];
		for (int index = 0; index < numberOfEntries; index++) {
			result[index] = bag[index];
		}

		return result;
	}

	public boolean isEmpty() {
		return numberOfEntries == 0;
	}

	public int getCurrentSize() {
		return numberOfEntries;
	}

	public int getFrequencyOf(T anEntry) {
		checkintegrity();
		int counter = 0;

		for (int index = 0; index < numberOfEntries; index++) {
			if (anEntry.equals(bag[index])) {
				counter++;
			}
		}

		return counter;
	}

	public boolean contains(T anEntry) {
		checkintegrity();
		return getIndexOf(anEntry) > -1;
	}

	public void clear() {
		while (!isEmpty())
			remove();
	}

	public T remove() {
		checkintegrity();
		T result = removeEntry(numberOfEntries - 1);
		return result;
	}

	public boolean remove(T anEntry) {
		checkintegrity();
		int index = getIndexOf(anEntry);
		T result = removeEntry(index);
		return anEntry.equals(result);
	}

	private int getIndexOf(T anEntry) {
		int where = -1;
		boolean found = false;
		int index = 0;

		while (!found && (index < numberOfEntries)) {
			if (anEntry.equals(bag[index])) {
				found = true;
				where = index;
			} 
			index++;
		}

		return where;
	}

	private T removeEntry(int givenIndex) {
		T result = null;

		if (!isEmpty() && (givenIndex >= 0)) {
			result = bag[givenIndex];
			int lastIndex = numberOfEntries - 1;
			bag[givenIndex] = bag[lastIndex];
			bag[lastIndex] = null;
			numberOfEntries--;
		}

		return result;
	}

	private boolean isArrayFull() {
		return numberOfEntries >= bag.length;
	}

	private void doubleCapacity() {
		int newLength = 2 * bag.length;
		checkCapacity(newLength);
		bag = Arrays.copyOf(bag, newLength);
	}

	private void checkCapacity(int capacity) {
		if (capacity > MAX_CAPACITY)
			throw new IllegalStateException(
					"Attempt to create a bag whose capacity exceeds " + "allowed maximum of " + MAX_CAPACITY);
	}

	private void checkintegrity() {
		if (!integrityOK)
			throw new SecurityException("ArrayBag object is corrupt.");
	}

	// Union method
	public BagInterface<T> union(BagInterface<T> inputBag) { // Gets bag as input and stores it as inputBag
		T[] thisBagArray = this.toArray(); // Copies contents of the bag in this class into an array
		T[] inputBagArray = inputBag.toArray(); // Copies the content of inputBag into an array
		int totalLength = (this.getCurrentSize() + (inputBag.getCurrentSize())); // Total length of both arrays combined
		@SuppressWarnings("unchecked")
		T[] outputArray = (T[]) new Object[totalLength]; // Creates new array with size of total length
		
		for (int i = 0; i < this.getCurrentSize(); i++) {
			outputArray[i] = thisBagArray[i]; // Copies everything from thisBagArray into output array
		}
		for (int i = this.getCurrentSize(); i < totalLength; i++) {
			outputArray[i] = inputBagArray[i - this.getCurrentSize()]; // Copies everything from inputBagArray into outputArray
		}
		BagInterface<T> outputBag = new ResizableArrayBag<>(outputArray); // Makes new Array Bag with the outputArray
		return outputBag; // Returns outputBag
	}


	// Intersection method
	public BagInterface<T> intersection(BagInterface<T> inputBag) { // Gets bag as input and stores it as inputBag
		T[] inputBagArray = inputBag.toArray(); // Copies content of inputBag into inputBagArray
		BagInterface<T> outputBag = new ResizableArrayBag<>(); // Creates new ResizableArrayBag named outputBag

		for (int i = 0; i < inputBagArray.length; i++) { 
			// For loop goes for as long as inputBagArray is. The reason we don't need to do 
			// another for loop for this.ResizableBagArray length is because we only need to use one bag to compare to the other and see 
			// where the intersection is. 
			if(this.contains(inputBagArray[i]) && !outputBag.contains(inputBagArray[i])) {
				// The if statement checks to make sure that inputBagArray[i] is contained in this.Bag, and is also not already in the output bag
				int timesToInput = inputBag.getFrequencyOf(inputBagArray[i]); // How many times inputBagArray[i] will be put into output bag
				int frequencyOfThis = this.getFrequencyOf(inputBagArray[i]);  // Used to compare with timesToInput
				// The if statement below works by setting timesToInput to the frequency inputBagArray[i] is found in inputBag, however, in a intersection
				// an object that is found in different quantities in 2 different bags/arrays/etc... will always be put into the output with the lesser value
				// since anything that isn't matched is discarded. 
				// Ex: bag1 has a,b,b,c,c and bag2 has a,b,d. The intersected string is b, but the output will only have 1 b in it since bag 2 only has 1 b
				// The if statement determines the maximum amount of times the object is added to the outputBag by finding the lowest frequency the object 
				// is found in either bags. Think of the lower amount as a bottleneck to how many times the object is added to outputBag
				if (timesToInput > frequencyOfThis) {
					timesToInput = frequencyOfThis;
				}
				for (int j = 0; j < timesToInput; j++) {
					outputBag.add(inputBagArray[i]); // Copies inputBagArray[i] into outputBag the amount of times needed.
				}
			}
		}
		return outputBag; // Returns outputBag
	}
}