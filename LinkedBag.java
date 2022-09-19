package bagPrograms;

public class LinkedBag<T> implements BagInterface<T> {

	private Node firstNode;
	private int numberOfEntries;

	public LinkedBag() {
		firstNode = null;
		numberOfEntries = 0;
	}

	@Override
	public int getCurrentSize() {
		return numberOfEntries;
	}

	@Override
	public boolean isEmpty() {
		return numberOfEntries == 0;
	}

	@Override
	public boolean add(T newEntry) {
		Node newNode = new Node(newEntry);
		newNode.setNextNode(firstNode);
		firstNode = newNode;
		numberOfEntries++;

		return true;
	}

	@Override
	public T remove() {
		T result = null;
		if (firstNode != null) {
			result = firstNode.getData();
			firstNode = firstNode.getNextNode();
			numberOfEntries--;
		}

		return result;
	}

	private Node getReferenceTo(T anEntry) {
		boolean found = false;
		Node currentNode = firstNode;
		while (!found && (currentNode != null)) {
			if (anEntry.equals(currentNode.getData()))
				found = true;
			else
				currentNode = currentNode.getNextNode();
		}
		return currentNode;
	}

	@Override
	public boolean remove(T anEntry) {
		boolean result = false;
		Node nodeN = getReferenceTo(anEntry);
		if (nodeN != null) {

			nodeN.setData(firstNode.getData());

			firstNode = firstNode.getNextNode();
			numberOfEntries--;
			result = true;
		}
		return result;
	}

	@Override
	public void clear() {
		while (!isEmpty())
			remove();
	}

	@Override
	public int getFrequencyOf(T anEntry) {
		int frequency = 0;
		int loopCounter = 0;
		Node currentNode = firstNode;

		while ((loopCounter < numberOfEntries) && (currentNode != null)) {
			if (anEntry.equals(currentNode.getData())) {
				frequency++;
			}

			loopCounter++;
			currentNode = currentNode.getNextNode();
		}

		return frequency;
	}

	@Override
	public boolean contains(T anEntry) {
		boolean found = false;
		Node currentNode = firstNode;

		while (!found && (currentNode != null)) {
			if (anEntry.equals(currentNode.getData()))
				found = true;
			else
				currentNode = currentNode.getNextNode();
		}
		return found;
	}

	@Override
	public T[] toArray() {
		@SuppressWarnings("unchecked")
		T[] result = (T[]) new Object[numberOfEntries];

		int index = 0;
		Node currentNode = firstNode;
		while ((index < numberOfEntries) && (currentNode != null)) {
			result[index] = currentNode.getData();
			index++;
			currentNode = currentNode.getNextNode();
		}
		return result;
	}

	
	// Union method takes Bag and stores it as inputBag, returns outputBag 
	public BagInterface<T> union(BagInterface<T> inputBag) {
		T[] thisBagArray = this.toArray(); // Stores the current bag in this LinkedBag class into an array
		T[] inputBagArray = inputBag.toArray(); // Stores inputed bag into array
		BagInterface<T> outputBag = new LinkedBag<>(); // Creates output bag 
		int totalLength = (this.getCurrentSize() + (inputBag.getCurrentSize())); // Finds the total length of both bags combined
		for (int i = 0; i < this.getCurrentSize(); i++) {
			outputBag.add(thisBagArray[i]); // Adds the content of this LinkedBag classes array into outputBag
		}
		for (int i = this.getCurrentSize(); i < totalLength; i++) {
			outputBag.add(inputBagArray[i - this.getCurrentSize()]); // Does the same for the input bag array.
		}
		return outputBag; // Outputs bag
	}

	// Intersection method, takes Bag as input and returns Bag
	// Exactly the same as the method used in the ResizableArrayBag.java file
	// For more detailed comments, look in the ResizableArrayBag file, otherwise these comments will be brief since no changes are made
	public BagInterface<T> intersection(BagInterface<T> inputBag) { // User input is stored as inputBag
		T[] inputBagArray = inputBag.toArray(); // Transfers input bag to array
		BagInterface<T> outputBag = new LinkedBag<>(); // Creates new linked bag for output
		
		// For loop goes thru the input bag array, and finds all the content they have the same as, and puts all the similar things into output bag
		for (int i = 0; i < inputBagArray.length; i++) { // Doesn't matter which .length is used
			if(this.contains(inputBagArray[i]) && !outputBag.contains(inputBagArray[i])) { // Checks if it's already in inputBag, and checks if it's already in output bag
				int timesToInput = inputBag.getFrequencyOf(inputBagArray[i]); 
				int frequencyOfThis = this.getFrequencyOf(inputBagArray[i]); // These 2 check how many times to input content into outputBag
				if (timesToInput > frequencyOfThis) {
					timesToInput = frequencyOfThis; // The lesser one is how many times the matching content will be put into outputBag
				}
				for (int j = 0; j < timesToInput; j++) {
					outputBag.add(inputBagArray[i]);
				}
			}
		}
		return outputBag; // Returns outputBag
	}
	
	
	private class Node {
		private T data;
		private Node next;

		public Node(T dataPortion) {
			this(dataPortion, null);
		}

		public Node(T dataPortion, Node nextNode) {
			data = dataPortion;
			next = nextNode;
		}

		public T getData() {
			return data;
		}

		public Node getNextNode() {
			return next;
		}

		public void setData(T newData) {
			data = newData;
		}

		public void setNextNode(Node nextNode) {
			next = nextNode;
		}
	}

}
