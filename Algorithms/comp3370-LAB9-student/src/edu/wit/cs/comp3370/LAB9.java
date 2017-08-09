package edu.wit.cs.comp3370;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/* Calculates the minimal spanning tree of a graph 
 * 
 * Wentworth Institute of Technology
 * COMP 3370
 * Lab Assignment 9
 * 
 */

public class LAB9 {
	
	public static void FindMST(Graph g) {
		primsBetter(g);
	}
	
	public static void primsBetter(Graph g) {
		//Prep Stage
		Graph workingGraph = new Graph();
		workingGraph.epsilon = g.epsilon;
		//Add all of G's verticies to the working graph
		for(Vertex v : g.getVertices()) {
			workingGraph.addVertex(v.x, v.y);
		}
		Vertex[] allVerticies = workingGraph.getVertices();
		//Attempt to connect every point on the graph, those that are longer than epsilon will be ignored
		for(Vertex v : allVerticies) for(Vertex q : allVerticies) if(v != q) workingGraph.addEdge(v, q); 
			
		//Create some adjList
		HashMap<Vertex, ArrayList<Edge>> adjList = new HashMap<>();
		for(Edge e : workingGraph.getEdges()) {
			if(!adjList.containsKey(e.src)) {
				ArrayList<Edge> newEdges = new ArrayList<Edge>();
				newEdges.add(e);
				adjList.put(e.src, newEdges);
			} else adjList.get(e.src).add(e);
		}
		
		//Prims
		VertexMinHeap vmh = new VertexMinHeap(allVerticies);
		Vertex current = null;
		while((current = vmh.dequeu()) != null) { //While the Queue is not empty
			ArrayList<Edge> edges = adjList.get(current);
			for(Edge e : edges) {
				Vertex dst = e.dst;
				if(vmh.hasElement(dst) && e.cost < dst.cost) { 
					dst.cost = e.cost;
					dst.parent = current;
					vmh.pullup(dst.heapIndex); //We updated dst's cost, and should be pulled to fix heap
				}
			}
		}
		
		for(Vertex v : allVerticies) {
			if(v.parent != null) {
				g.addEdge(v, v.parent);
			}
		}
		
	}
	
	
	
	/********************************************
	 * 
	 * You shouldn't modify anything past here
	 * 
	 ********************************************/
	

	// reads in an undirected graph from a specific file formatted with one
	// x/y node coordinate per line:
	private static Graph InputGraph(String file1) {
		
		Graph g = new Graph();
		try (Scanner f = new Scanner(new File(file1))) {
			while(f.hasNextDouble()) // each vertex listing
				g.addVertex(f.nextDouble(), f.nextDouble());
		} catch (IOException e) {
			System.err.println("Cannot open file " + file1 + ". Exiting.");
			System.exit(0);
		}
		
		return g;
	}
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		String file1;
		
		System.out.printf("Enter <points file> <edge neighborhood>\n");
		System.out.printf("(e.g: points/small .5)\n");
		file1 = s.next();

		// read in vertices
		Graph g = InputGraph(file1);
		g.epsilon = s.nextDouble();
		
		FindMST(g);

		s.close();

		System.out.printf("Weight of tree: %f\n", g.getTotalEdgeWeight());
	}

}
