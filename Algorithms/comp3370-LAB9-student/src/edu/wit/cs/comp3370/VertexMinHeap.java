package edu.wit.cs.comp3370;

public class VertexMinHeap {
	private Vertex[] heap;
	private int size = 0;
	
	/**
	 * Initializes the heap, with stuff as the initial list of values. Stuff does not have to be ordered.
	 * @param stuff List of initial elements
	 */
	public VertexMinHeap(Vertex[] stuff) {
		heap = new Vertex[stuff.length];
		heap[0] = stuff[0];
		heap[0].heapIndex = 0;
		size = stuff.length;
		for(int i = 1; i < stuff.length; i++) {
			heap[i] = stuff[i];
			heap[i].heapIndex = i;
		}
	}
	
	/**
	 * Takes the top (min) off the min heap, and re establishes heap
	 * If the heap is empty, this will return null
	 * @return min or null if empty
	 */
	public Vertex dequeu() {
		if(size > 0) { //We can't be at 0 length, this means its empty
			Vertex top = heap[0]; //Return this later
			heap[0] = heap[size-1]; //Put the bottom on top
			heap[0].heapIndex = 0; //Set the top element's heapIndex to the top index
			heap[size-1] = null; //Remove the bottom value of the heap
			pushdown(0); //Bury top element
			size--; //Item removed, size goes down
			top.heapIndex = -1; //The returning value is no longer part of the heap
			return top;
		}else {
			return null; //Null=Empty
		}
		
	}
	
	/**
	 * Check to see if vertex v is in the heap
	 * @param v Vertex
	 * @return true if in heap, false if not
	 */
	public boolean hasElement(Vertex v) {
		//Check to see if v is possibly heaped (heapIndex != -1)
		//If it is, see if the pointers match at that index
		return v.heapIndex != -1; //&& v.heapIndex < size && heap[v.heapIndex] == v;
		
	}
	
	/**
	 * If you changed the cost of v, call this function to establish min heap (NOTE: ONLY IF THE VALUE IS LOWER THAN THE ORIGINAL)
	 * @param v Vertex with changed cost
	 */
	/*public void checkin(Vertex v) {
		if(hasElement(v)) {
			int parent = getParent(v.heapIndex);
			if(parent >= 0 && heap[parent].cost > v.cost) pullup(v.heapIndex);
			else pushdown(v.heapIndex);
		}
	}
	*/
	
	/**
	 * Helps establish min heap after insertion by bubbling the value as far up as possible
	 * @param index Index of heap to bubble
	 */
	public void pullup(int index) {
		int parenti = getParent(index);
		if(parenti > -1 && heap[parenti].cost > heap[index].cost) {
			exchange(index, parenti);
			pullup(parenti);
		}
	}
	
	/**
	 * Helps establish heap by burying a value into the heap until it can go no farther
	 * @param index Index of heap to bury
	 */
	private void pushdown(int index) {
		int lefti = getLeft(index);
		int righti = getRight(index);
		int min = index;
		if(lefti < size && heap[lefti] != null && heap[lefti].cost < heap[min].cost) min=lefti;
		if(righti < size && heap[righti] != null && heap[righti].cost < heap[min].cost) min=righti;
		if(min != index) {
			exchange(index, min);
			pushdown(min);
		}
	}
	
	/**
	 * Exchanges two heap elements, including their heap indices
	 * @param index1
	 * @param index2
	 */
	private void exchange(int index1, int index2) {
		Vertex v1 = heap[index1];
		Vertex v2 = heap[index2];
		v1.heapIndex = index2;
		v2.heapIndex = index1;
		heap[index1] = v2;
		heap[index2] = v1;
	}
	
	private static int getParent(int forIndex) {
		return (forIndex - 1) / 2;
	}
	
	private static int getLeft(int forIndex) {
		return 2 * forIndex + 1;
	}
	
	private static int getRight(int forIndex) {
		return 2 * forIndex + 2;
	}
}
