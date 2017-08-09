package edu.wit.cs.comp3370;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/* Calculates the reconstruction matrix of the Floyd-Warshall algorithm for
 * all-pairs shortest paths 
 * 
 * Wentworth Institute of Technology
 * COMP 3370
 * Lab Assignment 10
 * 
 */

public class LAB10 {
	
	// TODO document this method
	public static Vertex[][] FindAllPaths(Graph g) {
		return floydWarshall(g);
	}
	
	public static Vertex[][] floydWarshall(Graph g){
		//Setup
		Vertex[] allVerticies = g.getVertices();
		int allVerticiesSize = allVerticies.length;
		Vertex[][] successor = new Vertex[allVerticiesSize][allVerticiesSize];
		double[][] adjMatrix = new double[allVerticiesSize][allVerticiesSize];
		
		//Initialize the adjMatrix with infinity as default and 0 across diagonal
		for(int i = 0; i < adjMatrix.length; i++) {
			for(int j = 0; j < adjMatrix.length; j++) {
				if(i == j)  adjMatrix[i][j] = 0;
				else adjMatrix[i][j] = Double.POSITIVE_INFINITY;
			}
		}
		
		for(Edge e : g.getEdges()) {adjMatrix[e.src.ID][e.dst.ID] = e.cost; successor[e.src.ID][e.dst.ID] = e.dst;}
		
		//FloydWarshall
		for(int k = 0; k < allVerticiesSize; k++) {
			for(int i = 0; i < allVerticiesSize; i++) {
				for(int j = 0; j < allVerticiesSize; j++) {
					double dij = adjMatrix[i][j];
					double dik = adjMatrix[i][k];
					double dkj = adjMatrix[k][j]; 
					if(dij > dik + dkj) {
						adjMatrix[i][j] = dik + dkj;
						
						if(i != j || dij != Double.POSITIVE_INFINITY) {
							successor[i][j] = successor[i][k];
						}
					}
				}
			}
		}
		
		return successor;
	}
	
	/********************************************
	 * 
	 * You shouldn't modify anything past here
	 * 
	 ********************************************/
	

	// reads in an undirected graph from a specific file formatted with one
	// x/y node coordinate per line:
	private static Graph InputGraph(String vFile, String eFile) {
		
		Graph g = new Graph();
		// vFile is list of (x coord, y coord) tuples
		try (Scanner f = new Scanner(new File(vFile))) {
			while(f.hasNextDouble())
				g.addVertex(f.nextDouble(), f.nextDouble());
		} catch (IOException e) {
			System.err.println("Cannot open file " + vFile + ". Exiting.");
			System.exit(0);
		}
		
		// eFile is list of (src ID, dst ID, cost) tuples
		try (Scanner f = new Scanner(new File(eFile))) {
			while(f.hasNextInt())
				g.addEdge(f.nextInt(), f.nextInt(), f.nextDouble());
		} catch (IOException e) {
			System.err.println("Cannot open file " + eFile + ". Exiting.");
			System.exit(0);
		}
		
		return g;
	}
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		String vFile, eFile;
		
		System.out.printf("Enter <vertices file> <edges file>\n");
		System.out.printf("(e.g: verts/small1 edges/small1)\n");
		vFile = s.next();
		eFile = s.next();

		// read in vertices
		Graph g = InputGraph(vFile, eFile);
		
		Vertex paths[][] = FindAllPaths(g);
		
		System.out.println("next array:");
		for (int i = 0; i < paths.length; i++) {
			for (int j = 0; j < paths.length; j++) {
				if (paths[i][j] == null)
					System.out.printf("%3s","x");
				else
					System.out.printf("%3d",paths[i][j].ID);
			}
			System.out.println();
		}
		s.close();

	}

}
