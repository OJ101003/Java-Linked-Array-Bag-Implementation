package bagPrograms;

public class ArrayBagTest {

	public static void main(String[] args) {
		// This ArrayBagTest.java file is extremely similar to the LinkedbagTest.java, just different layouts
		
		// The content used in the bags
		String[] contentsOfbag1 = { "a", "b", "c", "c", "b", "a" };
		String[] contentsOfbag2 = {"w", "v", "b", "c",};

		// Creates the resizeable array bags
		BagInterface<String> bag1 = new ResizableArrayBag<>(contentsOfbag1);
		BagInterface<String> bag2 = new ResizableArrayBag<>(contentsOfbag2);


		// Calls the union and intersection method, and then moves the result to the union and intersection arrays respectively
		BagInterface<String> everything = bag1.union(bag2);
		BagInterface<String> commonItems = bag1.intersection(bag2);
		Object[] bagArrayUnion = everything.toArray();
		Object[] bagArrayIntersection = commonItems.toArray();
		
		// Outputs results
		System.out.print("Array Union: ");
		for (int i = 0; i < everything.getCurrentSize(); i++) {
			System.out.print(bagArrayUnion[i]);
		}
		System.out.println(" ");
		System.out.print("Array Intersection: ");
		for (int i = 0; i < commonItems.getCurrentSize(); i++) {
			System.out.print(bagArrayIntersection[i]);
		}
	}

}
