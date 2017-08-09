package edu.wit.cs.comp3370;

// TODO: document class
public class BST extends LocationHolder {
	
	@Override
	public DiskLocation next(DiskLocation d) {
		return succ(d);
	}
	
	private DiskLocation succ(DiskLocation d){
		//If we have a right branch then we return the smallest number in the right branch
		//It is known that all elements that lie to the right of this one are larger than the current element, so the smallest larger
		//Should be the next number.
		//If we have no right branch, however, than the next number must lie somewhere above this one.
		//We must search for this number.
		
		//If we have a right branch
		if(d.right != null){
			//Find the smallest element within the right branch
			return min(d.right);
		}
		else
			//We need to find the number
			return up(d);
	}
	
	/**
	 * Attempts to find the next successor to a number by traveling up until we encounter a left branch traversal to reach a parent.
	 * When this happens it means that the parent is a larger element than the current element, which could mean that it is a successor.
	 * @param d the node to travel up from
	 * @return the element that was reached by a left traversal
	 */
	private DiskLocation up(DiskLocation d){
		//This is also an iterative solution, instead of a recursion
		//Keep track of the current node we are on (starting at d)
		DiskLocation current = d;
		
		//We search until we either reach the top of the tree (parent == null) or encounter a left traversal (current = current.parent.left)
		while(current.parent != null && current != current.parent.left) current = current.parent;
		return current.parent;
	}
	
	/**
	 * Finds the minimum number within a tree by traveling the left branch until the end is reached
	 * @param node the node you want to drill into
	 * @return the left most node (the smallest element)
	 */
	private DiskLocation min(DiskLocation node){
		//Keep track of the current node
		DiskLocation current = node;
		//Travel left until there are no more left branches
		while(current.left != null) current = current.left;
		return current;
	}
	
	@Override
	public DiskLocation prev(DiskLocation d) {
		return pred(d);
	}
	
	private DiskLocation pred(DiskLocation d){
		if(d.left != null){
			return max(d.left);
		}
		else
			return down(d);
	}
	
	private DiskLocation down(DiskLocation d){
		DiskLocation current = d;
		while(current.parent != null && current != current.parent.right) current = current.parent;
		return current.parent;
	}
	
	private DiskLocation max(DiskLocation node){
		DiskLocation current = node;
		while(current.right != null) current = current.right;
		return current;
	}
	
	
	@Override
	public void insert(DiskLocation d) {
		d.parent = findParent(d);
		if(d.parent == null) root = d;
		else{
			if(d.parent.isGreaterThan(d))
				d.parent.left = d;
			else
				d.parent.right = d;
		}
	}
	
	
	private DiskLocation findParent(DiskLocation value){
		DiskLocation current = root;
		DiskLocation parent  = null;
		while(current != null && current != nil){
			parent = current;
			if(value.isGreaterThan(current)) current = current.right;
			else current = current.left;
		}
		return parent;
	}
	@Override
	public DiskLocation find(DiskLocation d) {
		return _find(d);
	}
	
	
	private DiskLocation _find(DiskLocation d){
		DiskLocation current = root;
		while(current != null && current != nil && !d.equals(current)){
			if(d.isGreaterThan(current)) current = current.right;
			else current = current.left;
		}
		return current;
	}

	@Override
	public int height() {
		return height(root);
	}
	
	private int height(DiskLocation d){
		if(d == null || (d.left == null && d.right == null)) return 0;
		else return (int) (1 + Math.max(height(d.left), height(d.right)));
	}
	
	

	

}
