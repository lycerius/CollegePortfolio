package edu.wit.cs.comp3370;

import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;

/* Sorts integers from command line using various algorithms 
 * 
 * Wentworth Institute of Technology
 * COMP 3370
 * Lab Assignment 1
 * 
 */

public class LAB1 {

	// TODO: document this method
	public static int[] countingSort(int[] a) {
		
		//The size for the counting array should be able to accommodate the entire range of the integer set
		int size = max(a)+1;
		
		//The count of each occuring integer, starting from 0 to the max of the set
		int[] count = new int[size];
		
		//The resultant set sorted in ascending order
		int[] output = new int[a.length];
		
		//Keep track of the amount of times each integer occurs within the input
		for(int num : a) count[num]++;
		
		//Calculate the delta between each interval to get the position of the represented int at count[x] within the output
		for(int i = 1; i < size; i++) count[i] += count[i-1];
		
		//Take each input number in the input, find its position at count[x], and insert it into output at that position.
		for(int i = 0; i < a.length; i++){output[count[a[i]]-1] = a[i];count[a[i]]--;}
		
		return output;
	}
	
	public static int max(int[] a){
		
		//Quickly find the max for these fringe cases
		if(a.length == 0){ return 0; }
		if(a.length == 1){ return a[0]; }
		
		//We assume that the max is the first number until proven
		int max = a[0];
		
		//Find the max by saving the greatest number while iterating through the set
		for(int num : a)if(num > max) max = num;
		
		return max;
	}
	
	
	public static int[] betterStableSort(int[] a, int exp){
		//It is assumed that we are working in base 10, so the maximum input is 0-9 (size=10)
		int[] count = new int[10];
		
		//The position for each int should be the same size as the count
		int[] pos   = new int[10];
		
		int[] output = new int[a.length];
		
		//Integer used to keep track of the total ints parsed
		int total = 0;
		for(int num : a){
			//Modulate the input number to get a single digit (base 10)
			count[num / exp % 10]++;
		}
		
		for(int i = 0; i < 10;i++){
			//Set the position of each int based on the amount of preceding ints (total)
			pos[i] = total;
			total += count[i]; //Increase the amount of ints parsed
		}
		
		for(int num : a){
			
			//Get the position of the input int from the position based on a single digit and put it into output
			output[pos[num / exp % 10]++] = num; 
			//Increment position because the next occurrence of the same input will be put after this one
		}
		return output;
	}
	

	
	public static int[] radixSort(int[] a) {
		
		//We need to find the maximum radix within our set to define our iterative limit
		int maxradix = 0;
		for(int num : a){
			int length = String.valueOf(num).length();
			if(length > maxradix){ maxradix = length; } 
		}
		
		//We start our radix parse at the far right
		int exp = 1;
		
		//We go through every element maxradix times to account for the largest radix
		for(int i = 0; i < maxradix; i++){
			//Stable sort for the given digit, Save the output into our input for our next iteration
			a = betterStableSort(a, exp);
			
			//Next digit
			exp*=10;
		}
		return a;
	}

	/********************************************
	 * 
	 * You shouldn't modify anything past here
	 * 
	 ********************************************/

	public final static int MAX_INPUT = 524287;
	public final static int MIN_INPUT = 0;

	// example sorting algorithm
	public static int[] insertionSort(int[] a) {

		for (int i = 1; i < a.length; i++) {
			int tmp = a[i];
			int j;
			for (j = i-1; j >= 0 && tmp < a[j]; j--)
				a[j+1] = a[j];
			a[j+1] = tmp;
		}

		return a;
	}

	/* Implementation note: The sorting algorithm is a Dual-Pivot Quicksort by Vladimir Yaroslavskiy,
	 *  Jon Bentley, and Joshua Bloch. This algorithm offers O(n log(n)) performance on many data 
	 *  sets that cause other quicksorts to degrade to quadratic performance, and is typically 
	 *  faster than traditional (one-pivot) Quicksort implementations. */
	public static int[] systemSort(int[] a) {
		Arrays.sort(a);
		return a;
	}

	// read ints from a Scanner
	// returns an array of the ints read
	private static int[] getInts(Scanner s) {
		ArrayList<Integer> a = new ArrayList<Integer>();

		while (s.hasNextInt()) {
			int i = s.nextInt();
			if ((i <= MAX_INPUT) && (i >= MIN_INPUT))
				a.add(i);
		}

		return toIntArray(a);
	}

	// copies an ArrayList of Integer to an array of int
	private static int[] toIntArray(ArrayList<Integer> a) {
		int[] ret = new int[a.size()];
		for(int i = 0; i < ret.length; i++)
			ret[i] = a.get(i);
		return ret;
	}

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		System.out.printf("Enter the sorting algorithm to use ([c]ounting, [r]adix, [i]nsertion, or [s]ystem): ");
		char algo = s.next().charAt(0);

		System.out.printf("Enter the integers that you would like sorted, followed by a non-integer character: ");
		int[] unsorted_values = getInts(s);
		int[] sorted_values = {};

		s.close();

		switch (algo) {
		case 'c':
			sorted_values = countingSort(unsorted_values);
			break;
		case 'r':
			sorted_values = radixSort(unsorted_values);
			break;
		case 'i':
			sorted_values = insertionSort(unsorted_values);
			break;
		case 's':
			sorted_values = systemSort(unsorted_values);
			break;
		default:
			System.out.println("Invalid sorting algorithm");
			System.exit(0);
			break;
		}

		System.out.println(Arrays.toString(sorted_values));
	}

}
