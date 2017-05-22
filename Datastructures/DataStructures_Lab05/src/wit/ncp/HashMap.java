package wit.ncp;

import java.util.Iterator;

import wit.jss.HashNode;
import wit.jss.HashNode.HashNodeIterator;

public class HashMap implements Iterable<Integer>{
	
	/**
	 * Iterates through every node in the hashmap and all the nodes that are linked to
	 * every node
	 * @author Nathan Purpura
	 *
	 */
	public class HashMapIterator implements Iterator<Integer>{

		//Keeper of the current supernode index
		private int i = 0;
		
		//Holder of the current node that is being parsed
		private HashNodeIterator current;
		
		@Override
		public boolean hasNext() {
			//Check to see if we are at the end of the array AND the end of th
			if(current==null) return false;
			return current != null && (i == nodes.length || current.hasNext());
		}

		@Override
		public Integer next() {
			if(hasNext()){
				while(current == null) current = nodes[i++].iterator();
				//If the current node has more values in it
				if(current != null && current.hasNext())
				{
					return current.next();
				}
				else
				{
					current = nodes[i++].iterator();
					return current.next();
				}
				
			}
			return null;
		}
		
	}
	
	private HashNode[] nodes;
	
	//Used to check if all the unique indexes are filled
	private int uniqueIndexes = 0;
	
	//Easy way to calculate the absolute size of the hashmap
	private int absoluteSize = 0;
	
	//The hashmap will redistribute if this is set to true
	private boolean queueResize = false;
	
	private static double QUEUE_GROWTH_FACTOR = 1.2;
	
	private static double NON_QUEUE_GROWTH_FACTOR = 2;
	
	public HashMap(int initsize)
	{
		//No even
		if(initsize % 2 == 0) initsize++;
		
		nodes = new HashNode[initsize];
	}
	
	public void put(String key, int value)
	{
		checkSize();
		int ahash = hash(key);
		HashNode n = nodes[ahash];
	
		//The value is null
		//The key cannot exist already
		if(n == null)
		{
			//System.out.println("Key does not exist... creating");
			n = new HashNode(key,value);
			nodes[ahash] = n;
			uniqueIndexes++;
			absoluteSize++;
			return;
		}
		else //the value is not null and we need to put another node on
		{
			//Iterate to the end of the node
			//At the same time check to see if the key already exists
			while(n.getNextNode() != null) 
			{
				if(n.getKey().equals(key)) {
					System.out.println("Key '"+key+"' already exists!");
					n.add(value);
					return;
				}
				n = n.getNextNode();
			}
			
			
			//Create and set the next node
			HashNode temp = new HashNode(key,value);
			n.setNextNode(temp);
			absoluteSize++;
		}
	}
	
	public boolean exists(String key)
	{
		int ahash = hash(key);
		HashNode n = nodes[ahash];
		//The value is null
		//The key cannot exist already
		if(n == null){
			return false;
		}
		else //the value is not null and we need to put another node on
		{
			//Iterate to the end of the node
			//At the same time check to see if the key already exists
			do
			{
				if(n.getKey().equals(key)) return true; //Value exists within hashmap
				n = n.getNextNode();
			}while(n.getNextNode() != null) ;
			return false;
		}
	}
	
	public Integer get(String key)
	{
		int ahash = hash(key);
		
		HashNode n = nodes[ahash];
		
		//The value is null
		//The key cannot exist already
		if(n == null)
		{
			return null;
		}
		else //the value is not null and we need to put another node on
		{
			//Iterate to the end of the node
			//At the same time check to see if the key already exists
			
			do
			{
				
				if(n.getKey().equals(key)) return n.getValue();	
				n = n.getNextNode();
			}while(n.getNextNode() != null);
			return n.getValue();
		}
	}
	
	private int hash(String key)
	{
		int hash = (StringHasher.hash(key) % nodes.length);
	//	System.out.println("hash="+hash);
		return hash;
	}
	
	/**
	 * Used to check the size and resize the hashmap if it is full OR a forced resize is queued by the hashmap
	 */
	private void checkSize()
	{
		if(uniqueIndexes == nodes.length)
		{
			//resize
			resize(NON_QUEUE_GROWTH_FACTOR);
			//set this to false because we do not need to resize now
			queueResize = false;
		}
		else if(queueResize)
		{
			resize(QUEUE_GROWTH_FACTOR);
		}
	}
	
	/**
	 * Will resize the hashmap by a multiplicative factor of multfact
	 * Hashmap will maintain oddness if resulting size would be even
	 * @param multfact
	 */
	public void resize(double multfact)
	{
		int finalsize = (int) Math.ceil(nodes.length * multfact);
		//Keep it odd
		if(finalsize % 2 == 0) finalsize++;
		int newAbsoluteSize = 0;
		int newUniqueIndexes = 0;
		
		HashNode[] temp = new HashNode[finalsize];
		for(HashNode supernode : nodes)
		{
			if(supernode == null) continue;
			
			HashNodeIterator hni = supernode.iterator();
			Integer subvalue;
			while((subvalue = hni.next()) != null)
			{
				String key = hni.getKey();
				int ahash = StringHasher.hash(key) % finalsize;
				HashNode check = temp[ahash];
				if(check == null)
				{
					check = new HashNode(key,subvalue);
					temp[ahash] = check;
					newUniqueIndexes++;
					newAbsoluteSize++;
				}
				else
				{
					while(check.getNextNode() != null) 
					{
						check = check.getNextNode();
					}
					check.setNextNode(new HashNode(key,subvalue));
					newAbsoluteSize++;
				}
			}
		}
		nodes = temp;
		absoluteSize = newAbsoluteSize;
		uniqueIndexes = newUniqueIndexes;	
	}
	
	
	
	public int getAbsoluteSize()
	{
		return absoluteSize;
	}
	
	public int getUsedUniqueIndexes()
	{
		return uniqueIndexes;
	}
	
	public double getLoadFactor()
	{
		return ((double) absoluteSize) / ((double) uniqueIndexes);
	}
	
	

	@Override
	public Iterator<Integer> iterator() {
		return new HashMapIterator();
	}
	

}
