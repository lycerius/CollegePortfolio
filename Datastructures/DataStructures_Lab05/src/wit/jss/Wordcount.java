package wit.jss;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import wit.ncp.HashMap;

public class Wordcount {

	public static void main(String[] args) throws FileNotFoundException{
		Scanner scan = new Scanner(new FileInputStream(new File("american-english-JL.txt")));
		String line;
		String[] words;
		HashMap map = new HashMap(11);
		while(scan.hasNextLine()){
			line = scan.nextLine();
			words = line.split("\\s"); 
			for(int i = 0; i < words.length; i++){
				map.put(words[i], 1);
			}
		}
		printOutHashMap(map);
	/*	HashMap englishTest = new HashMap(10);
		int hold = 1;
		while(scan.hasNextLine())englishTest.put(scan.nextLine(), hold++);
		printOutHashMap(englishTest);
		for(Integer i : englishTest)
		{
			System.out.println(i);
		}*/
		scan.close();
	}
	
	public static void printOutHashMap(HashMap h)
	{
		
		System.out.println("Absolute size of hashmap="+h.getAbsoluteSize());
		System.out.println("Unique Indexes Used="+h.getUsedUniqueIndexes());
		System.out.println("Load Distribution="+h.getLoadFactor());
		System.out.println("Abby: " + h.get("Abby"));
	}
}
