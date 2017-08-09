package edu.wit.cs.comp3370;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/* Provides a solution to the 0-1 knapsack problem 
 * 
 * Wentworth Institute of Technology
 * COMP 3370
 * Lab Assignment 8
 * 
 */

public class LAB8 {

	// TODO: document this method
	public static Item[] FindDynamic(Item[] table, int weight) {
		int[][] sack = new int[table.length][weight+1];
		
		//We start at 1 because the max price of weight 0 = 0
		for(int weightindex = 1; weightindex <= weight; weightindex++) {
			//From top to bottom
			for(int itemindex = 0; itemindex < table.length; itemindex++) {
				Item item = table[itemindex];
				int iprice = item.value;
				int iweight = item.weight;
				//If the weight of this item is greater than the current weight index then we can't include it
				if(iweight > weightindex) {
					//If we can't include the item, then the maximum value is whatever came before this item
					//Set best to 0, because if there is no value above this item then the maximum value is just 0
					int best = 0;
					if(itemindex > 0) {
						//If this item index is not the first item then we can get the value above this item
						best = sack[itemindex-1][weightindex];
					}
					sack[itemindex][weightindex] = best;
					continue;
				} else { //This item can be included, we will test for the best value with and without inclusion 
					int best = iprice;
					//If this is not the first item, then we can consider the previous best, else the best is only the value after inclusion
					if(itemindex > 0) {
						//We need to fill in the rest of the weight after inclusion
						int weightdelta = weightindex - iweight; //Subtract this items weight from the current considering weight
						int incbest = iprice + sack[itemindex-1][weightdelta];
						int notincbest = sack[itemindex-1][weightindex];
						if(incbest > notincbest) best = incbest;
						else {best = notincbest;}
					}
					sack[itemindex][weightindex] = best;
					continue;
				}
			}
		}
		
		best_value = sack[table.length-1][weight];
		
		ArrayList<Item> included = new ArrayList<>();
		int x = weight;
		for(int y = table.length-1; y >= 0; y--) {
			if(y > 0) {
				if(sack[y][x] != sack[y-1][x]) {
					included.add(table[y]);
					x-=table[y].weight;
				}
			}else {
				if(sack[y][x] != 0) {
					included.add(table[y]);
				}
			}
		}
		
		return included.toArray(new Item[0]);
		
	}
	/********************************************
	 * 
	 * You shouldn't modify anything past here
	 * 
	 ********************************************/

	// set by calls to Find* methods
	private static int best_value = 0;
	
	public static class Item {
		public int weight;
		public int value;
		public int index;

		public Item(int w, int v, int i) {
			weight = w;
			value = v;
			index = i;
		}

		public String toString() {
			return "(" + weight + "#, $" + value + ")"; 
		}
	}

	// enumerates all subsets of items to find maximum value that fits in knapsack
	public static Item[] FindEnumerate(Item[] table, int weight) {

		if (table.length > 31) {	// bitshift fails for larger sizes
			System.err.println("Problem size too large. Exiting");
			System.exit(0);
		}
		
		int nCr = 1 << table.length; // bitmask for included items
		int bestSum = -1;
		boolean[] bestUsed = {}; 
		boolean[] used = new boolean[table.length];
		
		for (int i = 0; i < nCr; i++) {	// test all combinations
			int temp = i;

			for (int j = 0; j < table.length; j++) {
				used[j] = (temp % 2 == 1);
				temp = temp >> 1;
			}

			if (TotalWeight(table, used) <= weight) {
				if (TotalValue(table, used) > bestSum) {
					bestUsed = Arrays.copyOf(used, used.length);
					bestSum = TotalValue(table, used);
				}
			}
		}

		int itemCount = 0;	// count number of items in best result
		for (int i = 0; i < bestUsed.length; i++)
			if (bestUsed[i])
				itemCount++;

		Item[] ret = new Item[itemCount];
		int retIndex = 0;

		for (int i = 0; i < bestUsed.length; i++) {	// construct item list
			if (bestUsed[i]) {
				ret[retIndex] = table[i];
				retIndex++;
			}
		}
		best_value = bestSum;
		return ret;

	}

	// returns total value of all items that are marked true in used array
	private static int TotalValue(Item[] table, boolean[] used) {
		int ret = 0;
		for (int i = 0; i < table.length; i++)
			if (used[i])
				ret += table[i].value;

		return ret;
	}

	// returns total weight of all items that are marked true in used array
	private static int TotalWeight(Item[] table, boolean[] used) {
		int ret = 0;
		for (int i = 0; i < table.length; i++) {
			if (used[i])
				ret += table[i].weight;
		}

		return ret;
	}

	// adds items to the knapsack by picking the next item with the highest
	// value:weight ratio. This could use a max-heap of ratios to run faster, but
	// it runs in n^2 time wrt items because it has to scan every item each time
	// an item is added
	public static Item[] FindGreedy(Item[] table, int weight) {
		boolean[] used = new boolean[table.length];
		int itemCount = 0;

		while (weight > 0) {	// while the knapsack has space
			int bestIndex = GetGreedyBest(table, used, weight);
			if (bestIndex < 0)
				break;
			weight -= table[bestIndex].weight;
			best_value += table[bestIndex].value;
			used[bestIndex] = true;
			itemCount++;
		}

		Item[] ret = new Item[itemCount];
		int retIndex = 0;

		for (int i = 0; i < used.length; i++) { // construct item list
			if (used[i]) {
				ret[retIndex] = table[i];
				retIndex++;
			}
		}

		return ret;
	}
	
	// finds the available item with the best value:weight ratio that fits in
	// the knapsack
	private static int GetGreedyBest(Item[] table, boolean[] used, int weight) {

		double bestVal = -1;
		int bestIndex = -1;
		for (int i = 0; i < table.length; i++) {
			double ratio = (table[i].value*1.0)/table[i].weight;
			if (!used[i] && (ratio > bestVal) && (weight >= table[i].weight)) {
				bestVal = ratio;
				bestIndex = i;
			}
		}

		return bestIndex;
	}

	public static int getBest() {
		return best_value;
	}
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		String file1;
		int weight = 0;
		System.out.printf("Enter <objects file> <knapsack weight> <algorithm>, ([d]ynamic programming, [e]numerate, [g]reedy).\n");
		System.out.printf("(e.g: objects/small 10 g)\n");
		file1 = s.next();
		weight = s.nextInt();

		ArrayList<Item> tableList = new ArrayList<Item>();

		try (Scanner f = new Scanner(new File(file1))) {
			int i = 0;
			while(f.hasNextInt())
				tableList.add(new Item(f.nextInt(), f.nextInt(), i++));
		} catch (IOException e) {
			System.err.println("Cannot open file " + file1 + ". Exiting.");
			System.exit(0);
		}

		Item[] table = new Item[tableList.size()];
		for (int i = 0; i < tableList.size(); i++)
			table[i] = tableList.get(i);

		String algo = s.next();
		Item[] result = {};

		switch (algo.charAt(0)) {
		case 'd':
			result = FindDynamic(table, weight);
			break;
		case 'e':
			result = FindEnumerate(table, weight);
			break;
		case 'g':
			result = FindGreedy(table, weight);
			break;
		default:
			System.out.println("Invalid algorithm");
			System.exit(0);
			break;
		}

		s.close();

		System.out.printf("Index of included items: ");
		for (int i = 0; i < result.length; i++)
			System.out.printf("%d ", result[i].index);
		System.out.printf("\nBest value: %d\n", best_value);	
	}

}
