package edu.wit.cs.comp3370;

public class FloatHeap {
	private float[] heap;
	private int     size;
	
	public int getSize(){
		return size;
	}
	
	private static int getParent(int index){
		return (index - 1) / 2;
	}
	
	private static int getLeftChild(int index){
		return index * 2 + 1;
	}
	
	private static int getRightChild(int index){
		return index * 2 + 2;
	}
	
	public void insert(float value){
		heap[size] = value;
		pullup(size);
		size++;
	}
	
	public float extractMin(){
		if(size == 0) return -1;
		float toRemove = heap[0];
		heap[0] = heap[size-1];
		heap[size-1] = 0;
		--size;
		pushdown(0);
		return toRemove;
	}
	
	private void pullup(int index){
		if(index == 0) return;
		int parenti = getParent(index);
		if(heap[index] < heap[parenti]){
			exchange(index, parenti);
			pullup(parenti);
		}
	}
	
	private void verify(){
		for(int i = size/2-1; i >= 0; i--){
			pushdown(i);
		}
	}
	
	private void pushdown(int index){
		
		int leftChild  = getLeftChild(index);
		int rightChild = getRightChild(index);
		
		int minI  = index;
		if(leftChild < size && heap[leftChild] < heap[minI])   minI = leftChild;
		if(rightChild < size && heap[rightChild] < heap[minI]) minI = rightChild;
		
		if(minI != index){
			exchange(index, minI);
			pushdown(minI);
		}
		
		
	}
	
	private void exchange(int index1, int index2){
		float tmp = heap[index1];
		heap[index1] = heap[index2];
		heap[index2] = tmp;
	}
	
	public FloatHeap(float[] contents){
		heap = contents;
		size = contents.length;
		verify();
	}
	
	public FloatHeap(int size){
		heap = new float[size];
	}
	
	public float[] getHeap(){
		return heap;
	}
	
	
	
	
}
