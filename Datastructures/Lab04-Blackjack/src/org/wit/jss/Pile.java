/*
 * Lab 04 - Group 22
 * Nathan Purpura, Jovin Schneider, Lam Pham
 * Blackjack
 * 3-17-16 
 */
package org.wit.jss;
import java.util.ArrayList;
import java.util.Random;
public class Pile<T> implements ListInterface<T> {
	private class Node{
		private T data;
		private Node next;
		private Node previous;
		/**
		 * Default constructor for Node. Calls the other constructor
		 * @param dataPortion
		 */
		private Node(T dataPortion){
			this(dataPortion, null, null);
		}
		/**
		 * Creates a new Node with next and previous links
		 * @param dataPortion
		 * @param nextNode
		 * @param previousNode
		 */
		private Node(T dataPortion, Node nextNode, Node previousNode){
			data = dataPortion;
			next = nextNode;
			previous = previousNode;
		}
		/**
		 * Obtains the data portion of the current Node
		 * @return data
		 */
		private T getData(){
			return data;
		}
		/**
		 * Acquires the link to the next Node
		 * @return the Node at the next index
		 */
		private Node getNextNode(){
			return next;
		}
		/**
		 * Acquires the link to the previous Node
		 * @return the Node at the previous index
		 */
		private Node getPreviousNode(){
			return previous;
		}
		/**
		 * Overrides the data of the current Node with the given parameter
		 * @param newData
		 */
		private void setData(T newData){
			data = newData;
		}
		/**
		 * Creates a new link for the Next Node
		 * @param nextNode
		 */
		private void setNextNode(Node nextNode){
			next = nextNode;
		}
		/**
		 * Creates a new link to the Previous Node
		 * @param previousNode
		 */
		private void setPreviousNode(Node previousNode){
			previous = previousNode;
		}
	}
	/**
	 * Unit tests; some are omitted due to not being used
	 * @param args
	 */
	public static void main(String[] args){
		Pile<Integer> newPile = new Pile<>();
		newPile.add(4);
		newPile.add(5);
		newPile.add(6);
		newPile.add(7);
		newPile.add(8);
		newPile.replace(3, 9);
		//System.out.println(newPile.getEntry(3));
		//newPile.display();
		//System.out.println(newPile.contains(6));
		//System.out.println(newPile.remove(3));
		/*	newPile.shuffle();
		newPile.display();
		newPile.shuffle();
		newPile.display();*/
		Object[] a = newPile.toArray();
		for(int i = 0; i < a.length; i++){
			System.out.println(a[i]);
		}
	}
	private Node firstNode;
	private Node lastNode;
	private int numberOfEntries;
	/**
	 * Default constructor; calls initializing method
	 */
	public Pile(){
		initializeDataFields();
	}
	/**
	 * Adds new Node to a given position 
	 */
	@Override
	public boolean add(int newPosition, T newEntry) {						
		Node newNode = new Node(newEntry);																			
		if(newPosition < 1 || newPosition > (numberOfEntries + 1)){
			return false;
		}
		if(isEmpty()){
			firstNode = newNode;
			lastNode = firstNode;
			numberOfEntries++;
			return true;
		}
		Node testNode = getNodeAt(newPosition);	
		newNode.setPreviousNode(testNode.getPreviousNode());
		newNode.setNextNode(testNode);
		testNode.setPreviousNode(newNode);
		numberOfEntries++;
		return true;
	}
	/**
	 * Adds a new Node at the end of the List
	 */
	@Override
	public boolean add(T newEntry){
		Node newNode = new Node(newEntry);

		if(isEmpty()){
			firstNode = newNode;
			lastNode = firstNode;
		}
		else{
			newNode.setPreviousNode(lastNode);
			lastNode.setNextNode(newNode);
			lastNode = newNode;
		}

		numberOfEntries++;
		return true;
	}
	/**
	 * Calls initializing method
	 */
	@Override
	public void clear() {
		initializeDataFields();
	}
	/**
	 * Searches through Pile for the given Entry
	 */
	@Override
	public boolean contains(T anEntry) {
		Node testNode = firstNode;
		for(int i = 1; i <= numberOfEntries; i++){
			if(anEntry.equals(testNode.getData())){
				return true;
			}
			testNode = testNode.getNextNode();
		}
		return false;
	}
	/**
	 * Prints the contents of the pile
	 */
	@Override
	public void display() {
		Node testNode = firstNode;
		for(int i = 1; i <= numberOfEntries; i++){
			System.out.println(testNode.getData());
			testNode = testNode.getNextNode();
		}

	}
	/**
	 * Obtains the data from Node at a given position non-destructively
	 * @return  the data from the given Node
	 */
	@Override
	public T getEntry(int givenPosition) {
		T temp;
		if(givenPosition == 1){
			temp = firstNode.getData();
			return temp;
		}
		if(givenPosition == numberOfEntries){
			temp = lastNode.getData();
			return temp;
		}
		Node testNode = firstNode;
		for(int i = 1; i <= numberOfEntries; i++){
			if(i == givenPosition){
				temp = testNode.getData();
				return temp;
			}
			testNode = testNode.getNextNode();
		}
		return null;
	}
	/**
	 * Obtains number of elements
	 * @return number of elements
	 */
	@Override
	public int getLength() {
		return numberOfEntries;
	}
	/**
	 * Acquires the Node at the given position
	 * @param givenPosition
	 * @return Node at certain position
	 */
	private Node getNodeAt(int givenPosition){
		if(givenPosition < 1 || givenPosition > numberOfEntries + 1){
			return null;
		}
		Node testNode = firstNode;
		for(int i = 1; i <= givenPosition; i++){
			testNode = testNode.getNextNode();
		}
		return testNode;
	}
	/**
	 * Initializing method; Creates a new, empty Node and sets the head and tail pointers at it
	 */
	private void initializeDataFields(){
		Node dummy  = new Node(null, lastNode, firstNode);
		firstNode = dummy;
		lastNode = dummy;
		numberOfEntries = 0;
	}
	/**
	 * Determines if pile is empty
	 * @return True if the pile is empty, false otherwise
	 */
	@Override
	public boolean isEmpty() {
		return numberOfEntries == 0;
	}
	/**
	 * Determines if the Pile is full, which it can't
	 * @return false
	 */
	@Override
	public boolean isFull() {
		return false;
	}
	/**
	 * Searches for the Node at a given position, retrieves the data, and unlinks the Node
	 * @return the data from the Node at the given position
	 */
	@Override
	public T remove(int givenPosition) {
		T temp;
		if(givenPosition == 1){
			temp = firstNode.getData();
			if(numberOfEntries == 1){
				initializeDataFields();
				return temp;
			}
			firstNode = firstNode.getNextNode();
			//firstNode.setPreviousNode(null);
			numberOfEntries--;
			return temp;
		}
		if(givenPosition == numberOfEntries){
			temp = lastNode.getData();
			lastNode = lastNode.getPreviousNode();
			lastNode.setNextNode(null);
			numberOfEntries--;
			return temp;
		}
		Node testNode = firstNode;
		for(int i = 1; i <= numberOfEntries; i++){
			if(i == givenPosition){
				temp = testNode.getData();
				Node p = testNode.getPreviousNode();
				Node n = testNode.getNextNode();
				p.setNextNode(n);
				n.setPreviousNode(p);
				numberOfEntries--;
				return temp;
			}
			testNode = testNode.getNextNode();
		}
		return null;
	}
	/**
	 * Replaces the data of the Node at the given position with the given entry
	 * @return true if replacement was successful, false otherwise
	 */
	@Override
	public boolean replace(int givenPosition, T newEntry) {
		if(givenPosition < 1 || givenPosition > numberOfEntries + 1){
			return false;
		}
		Node testNode = firstNode;
		for(int i = 1; i <= givenPosition; i++){
			if(givenPosition == i){
				testNode.setData(newEntry);
				return true;
			}
			testNode = testNode.getNextNode();
		}
		return true;
	}
	/**
	 * Takes the data of the top Node and switches it with the data of a random Node as many times as there are elements
	 */
	public void shuffle(){
		Random r = new Random();

		Node testNode = firstNode;
		T temp;
		for(int i = 1; i <= numberOfEntries; i++){
			int rand = r.nextInt(numberOfEntries) + 1;
			temp = getEntry(rand);
			replace(rand, testNode.getData());
			replace(1, temp);
		}
	}

	/**
	 * Creates and array of the Pile
	 * @return Object array of the data from each Node
	 */
	public Object[] toArray(){
		ArrayList<T> ta = new ArrayList<T>();
		Node testNode = firstNode;
		for(int i = 0; i < numberOfEntries; i++){
			ta.add(testNode.getData());
			testNode = testNode.getNextNode();
		}
		return ta.toArray();
	}

}
