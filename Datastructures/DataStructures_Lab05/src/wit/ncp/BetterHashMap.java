package wit.ncp;

import wit.jss.HashNode;

/**
 * 
 * @author purpu
 *
 * @param <Key> The Key type
 * @param <Value> The Value type
 */
public class BetterHashMap<Key, Value> {
	
	/**
	 * Holds the current size of the Hash Map
	 */
	private int size;
	
	
	
	private int usedIndexes;
	
	private int usedSize;
	
	/**
	 * The current collection of nodes
	 */
	private HashNode[] nodes;
	
	/**
	 * 
	 */
	
	
	
	
	
	
	
	/**
	 * Gets the current size of the hashmap
	 * @return the size
	 */
	public int getSize(){
		return size;
	}
	
	public double loadFactor(){
		
	}
	
	/**
	 * Returns the integer fielded to the size of the map
	 * @param i
	 * @return i % size
	 */
	private int mod(int i)
	{
		return i % size;
	}
	
	
	

}
