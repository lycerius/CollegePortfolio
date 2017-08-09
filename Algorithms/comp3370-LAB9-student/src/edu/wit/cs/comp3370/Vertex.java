package edu.wit.cs.comp3370;

// represents a vertex in a graph, including a unique ID to keep track of vertex
public class Vertex {
	public double cost = Double.MAX_VALUE;
	public Vertex parent = null;
	public int heapIndex; //Used for pullup on elements
	
	/********************************************
	 * 
	 * You shouldn't modify anything past here
	 * 
	 ********************************************/

	public double x;
	public double y;
	public int ID;
}
