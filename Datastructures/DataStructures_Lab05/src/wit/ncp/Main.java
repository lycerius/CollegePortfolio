package wit.ncp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

	/*public static void main(String[] args) throws FileNotFoundException
	{
		//stringHashTest();
		hashMapTester();
	}*/
	
	public static void stringHashTest(){
		long hash = StringHasher.hash("Hello World");
		System.out.println("Hello World="+hash);
		
		long hash2 = StringHasher.hash("dlroW olleH");
		System.out.println("dlroW olleH="+hash2);
		
		System.out.println("hash==hash2? "+(hash==hash2));
		
	}
	
	public static void hashMapTester() throws FileNotFoundException
	{
		Scanner scan = new Scanner(new FileInputStream(new File("american-english-JL.txt")));
		HashMap englishTest = new HashMap(10);
		int hold = 1;
		while(scan.hasNextLine())englishTest.put(scan.nextLine(), hold++);
		printOutHashMap(englishTest);
		for(Integer i : englishTest)
		{
			System.out.println(i);
		}
		scan.close();
		
		
	}
	
	
	public static void printOutHashMap(HashMap h)
	{
		System.out.println("Absolute size of hashmap="+h.getAbsoluteSize());
		System.out.println("Unique Indexes Used="+h.getUsedUniqueIndexes());
		System.out.println("Load Distribution="+h.getLoadFactor());
		System.out.println(h.get("Abby"));
	}
}
