package wit.jss;

import java.util.Iterator;

public class HashNode {
	
	
	private String key;
	private Integer value = 0;
	private HashNode next;
	
	
	public HashNode(String searchKey, Integer dataValue){
		this(searchKey, dataValue, null);
	}
	
	public HashNode(String searchKey, Integer dataValue, HashNode nextNode){
		key = searchKey;
		value = dataValue;
		nextNode = next;
	}
	
	public String getKey()
	{
		return key;
	}
	
	public Integer getValue(){
		return value;
	}
	
	public HashNode getNextNode(){
		return next;
	}
	
	public void setValue(Integer newValue){
		value = newValue;
	}
	
	public void add(Integer newValue){
		value += newValue;
	}
	
	public void setNextNode(HashNode newNode){
		next = newNode;
	}
	
	public HashNodeIterator iterator(){
		return new HashNodeIterator();
	}
		
		public class HashNodeIterator implements Iterator<Integer>{
			private HashNode node;
			
			@Override
			public boolean hasNext(){
				if(node.getNextNode() == null){
					return false;
				}
				return true;
			}
			
			@Override
			public Integer next(){
				
				
				if(node != null)
				{

					Integer val = node.getValue();
					node = node.getNextNode();
					return val;
				}
				else{
					return null;
				}
				
			}
			
			@Override
			public void remove(){
				throw new UnsupportedOperationException();
			}
			
			public String getKey(){
				return node.key;
			}
		}
}
