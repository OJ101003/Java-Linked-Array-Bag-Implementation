package bagPrograms;

public class LinkedBagTest {

	public static void main(String[] args) {
		// Creating linked bags
		BagInterface<String> bag1 = new LinkedBag<>();
		BagInterface<String> bag2 = new LinkedBag<>();

		// Making 2 different arrays to be used for union and intersection
		String[] contentsOfbag1 = { "a", "b", "c", "c", "b", "a" };
		String[] contentsOfbag2 = {"w", "v", "b", "c", "b"};

		// These for loops move the contents of the first and second bag arrays to the linked bags
		for (int i = 0; i < (contentsOfbag1.length); i++) {
			bag1.add(contentsOfbag1[i]);
		}
		for (int i = 0; i < (contentsOfbag2.length); i++) {
			bag2.add(contentsOfbag2[i]);
		}
		
		// Creates a bag for the union 
		BagInterface<String> outputUnion = bag1.union(bag2);
		Object[] everything = outputUnion.toArray(); // Gets an array of all the stuff in the union
		System.out.print("Linked Bag Union: ");
		for (int i = 0; i < outputUnion.getCurrentSize(); i++) {
			System.out.print(everything[i]); // Prints out whatever was made in the union
		}
		// Creates a bag for the intersection
		BagInterface<String> commonItems = bag1.intersection(bag2);
		Object[] bagArrayIntersection = commonItems.toArray(); // Same as union
		System.out.println(" ");
		System.out.print("Linked Bag Intersection: ");
		for (int i = 0; i < commonItems.getCurrentSize(); i++) {
			System.out.print(bagArrayIntersection[i]);
		}
	}

}
