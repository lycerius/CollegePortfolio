package edu.wit.cs.comp3370;

import java.util.LinkedList;

/* Implements a trie data structure 
 * 
 * Wentworth Institute of Technology
 * COMP 3370
 * Lab Assignment 6 solution
 * 
 */

public class Trie extends Speller {
	
	private class Node {
		public boolean endpoint = false; //If the node constitutes the last character of an inserted string
		public char associaton; //Child parent relation of this node, which index of the parents child array was this
		public Node parent = null; //direct parent of this child
		public Node[] children = new Node[26]; //a collection of children, one is possible for every letter of our alphabet
	}
	
	private Node top = new Node();
	
	/**
	 * Modulates this character using the character 'a' as reference 0
	 * @param c char to modulate relative to 'a'
	 * @return char c modulated on 'a'
	 */
	private static int normalize(char c){
		return (int) (c - 'a');
		//Modulates the character based on the letter a as the 0th index
		//a=0,b=1...
	}
	
	
	/**
	 * Inserts a word into this Trie
	 */
	@Override
	public void insertWord(String s) {
		Node current = top;//Path tracker
		for(char c : s.toCharArray()){
			int index = normalize(c); //get modulated alpha
			Node test = current.children[index];
			if(test == null){ //If the path does not exist then create it
				test = new Node();    
				test.associaton = c;   //Associate this node based on the current char for string reconstruction
				test.parent = current; //parent lies above new node
				current.children[index] = test;
			}
			current = test; //Traverse
		}
		//Assign the last node traversed to be an end point
		current.endpoint = true;
	}

	/**
	 * Returns whether or not the string in question exists within this Trie's dictionary
	 */
	@Override
	public boolean contains(String s) {
		Node current = top;
		for(char c : s.toCharArray()){
			int index = normalize(c); //modulate
			Node test = current.children[index];
			if(test == null) //if the path stops here then it is not possible for the string to exist
				return false;
			else
				current = test; //Go down to the last character
		}
		if(current.endpoint)
			return true; //If the last node is an end point then the string actually exists
		else
			return false; //The testing string is a substring of another inserted string != whole inserted string
	}
	
	/**
	 * Finds all the suggestions for a given word using a Node as a reference point
	 * @param sugList current state of suggestions, if one (or more) is found it will be added to this list
	 * @param towards the string that is being used to find suggestions
	 * @param path the node to use as reference
	 * @param editDistance edit distance desired, will be passed along through recursion
	 * @param depth used to keep track of the current depth of traversal
	 */
	private void suggest(LinkedList<String> sugList, String towards, Node path, int editDistance, int depth){
		if(path.associaton != towards.charAt(depth)) editDistance--; //If we have a character mismatch between our node and current character then spend an editing distance
		if(editDistance >= 0){ //If we haven't spent all our editing distance getting here than we can process the word
			if(path.endpoint && depth == towards.length()-1) sugList.add(createWord(path,depth)); //If we have arrived at an end point and this word is as long as our suggesting word, then add it
			if(depth < towards.length()-1)  //If we haven't surpassed the length of our suggesting word
				for(Node n : path.children) if(n != null) //For all existent children
					suggest(sugList, towards, n, editDistance, depth+1); //Suggest deeper	
		}
		
	}
	
	/**
	 * Creates a word starting from the Node n and working up to the top. Node n is the last character in the string and the top most node will be the beginning.
	 * @param n Node denoting the end of the string
	 * @param depth the current depth of traversal, helps determine length of string
	 * @return The string in the order of (N0,N1,N2,...,Nn);
	 */
	private String createWord(Node n, int depth){
		StringBuilder sb = new StringBuilder(depth);
		while(n != top){
			sb.append(n.associaton);
			n = n.parent;
		}
		return sb.reverse().toString();
	}
	
	/**
	 * Returns a list of suggestions for the current string using the dictionary of words currently inside this Trie.
	 */
	@Override
	public String[] getSugg(String s) {
		//This list will store the results
		LinkedList<String> list = new LinkedList<String>();
		//Go through every non null node off the top most node
		for(Node n : top.children) if(n != null)
			suggest(list, s, n, 2, 0);
		
		return list.toArray(new String[list.size()]);
		
	}

}
