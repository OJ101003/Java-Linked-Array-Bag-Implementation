package bagPrograms;

public interface BagInterface<T>
{
	// Everything other than union and intersection was gotten from the lectures
	public int getCurrentSize();
	public boolean isEmpty();
	public boolean add(T newEntry);
	public T remove();
    public boolean remove(T anEntry);
	public void clear();
	public int getFrequencyOf(T anEntry);
	public boolean contains(T anEntry);
    public T[] toArray();
    // These 2 BagInterface methods below are the union and intersection methods.
    // They return a bag, and require a bag as input
    public BagInterface<T> union(BagInterface<T> inputBag);
    public BagInterface<T> intersection(BagInterface<T> inputBag);
    
}