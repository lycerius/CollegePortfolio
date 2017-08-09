
package edu.wit.cs.comp3370;

/* Implements methods to use a red-black tree 
 * 
 * Wentworth Institute of Technology
 * COMP 3370
 * Lab Assignment 5
 * 
 */

public class RBTree extends LocationHolder {
	/*
	 * Properties of a red black tree:
	 * 1. every node is either black or red
	 * 2. the root is black
	 * 3. nil is black
	 * 4. both children of a red node are black
	 * 5. from any node, all the paths to leaves have the same number of black nodes
	 * 
	 * Search and traversal are unchanged
	 */
	/* sets a disk location's color to red.
	 * 
	 * Use this method on fix-insert instead of directly
	 * coloring nodes red to avoid setting nil as red.
	 */
	
	private void setRed(DiskLocation z) {
		if (z != nil)
			z.color = RB.RED;
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
		if(d.right != null && d.right != nil){
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
		while(current.parent != null && current != current.parent.left && current.parent != nil) current = current.parent;
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
		while(current.left != null && current.left != nil) current = current.left;
		return current;
	}

	@Override
	public DiskLocation prev(DiskLocation d) {
		return pred(d);
	}
	
	private DiskLocation pred(DiskLocation d){
		if(d.left != null && d.left != nil){
			return max(d.left);
		}
		else
			return down(d);
	}
	
	private DiskLocation down(DiskLocation d){
		DiskLocation current = d;
		while(current.parent != null && current.parent != nil && current != current.parent.right) current = current.parent;
		return current.parent;
	}
	
	private DiskLocation max(DiskLocation node){
		DiskLocation current = node;
		while(current.right != null && current.right != nil) current = current.right;
		return current;
	}

	@Override
	public void insert(DiskLocation d) {
		d.parent = findParent(d);
		if(d.parent == nil) {
			root = d;
		}
		else{
			if(d.parent.isGreaterThan(d))
				d.parent.left = d;
			else
				d.parent.right = d;
		}
		d.left = nil;
		d.right = nil;
		setRed(d);
		fixInsert(d);
	}
	
	private void fixInsert(DiskLocation d){
		while(d.parent.color == RB.RED){
			//If z's parent is a left child
			if(d.parent == d.parent.parent.left){
				//Y=d's grandparent's right child
				DiskLocation y = d.parent.parent.right;
				if(y.color == RB.RED){
					d.parent.color = RB.BLACK;
					y.color = RB.BLACK;
					setRed(d.parent.parent);
					d = d.parent.parent;
				}
				else{
					if(d == d.parent.right){
						d = d.parent;
						rotateLeft(d);
					}
					d.parent.color = RB.BLACK;
					setRed(d.parent.parent);
					rotateRight(d.parent.parent);
				}
			}
			else{
				//Y=d's grandparent's right child
				DiskLocation y = d.parent.parent.left;
				if(y.color == RB.RED){
					d.parent.color = RB.BLACK;
					y.color = RB.BLACK;
					setRed(d.parent.parent);
					d = d.parent.parent;
				}
				else{
					if(d == d.parent.left){
						d = d.parent;
						rotateRight(d);
					}
					d.parent.color = RB.BLACK;
					setRed(d.parent.parent);
					rotateLeft(d.parent.parent);
				}
			}
		}
		root.color = RB.BLACK;
	}
	
	
	private DiskLocation findParent(DiskLocation value){
		DiskLocation current = root;
		DiskLocation parent  = nil;
		while(current != null && current != nil){
			parent = current;
			if(value.isGreaterThan(current)) current = current.right;
			else current = current.left;
		}
		return parent;
	}

	@Override
	public int height() {
		return height(root);
	}
	
	private int height(DiskLocation d){
		if(d == nil || (d.left == nil && d.right == nil)) return 0;
		else return (int) (1 + Math.max(height(d.left), height(d.right)));
	}

	private void rotateLeft(DiskLocation x){
		DiskLocation y = x.right;
		x.right = y.left;
		if(y.left != null && y.left != nil)
			y.left.parent = x;
		y.parent = x.parent;
		if(x.parent == null || x.parent == nil)
			root = y;
		else if(x == x.parent.left)
			x.parent.left = y;
		else
			x.parent.right = y;
		y.left = x;
		x.parent = y;
	}
	
	private void rotateRight(DiskLocation x){
		DiskLocation y = x.left;
		x.left = y.right;
		if(y.right != null && y.right != nil)
			y.right.parent = x;
		y.parent = x.parent;
		if(x.parent == null || x.parent == nil)
			root = y;
		else if(x == x.parent.right)
			x.parent.right = y;
		else
			x.parent.left = y;
		y.right = x;
		x.parent = y;
	}
}
