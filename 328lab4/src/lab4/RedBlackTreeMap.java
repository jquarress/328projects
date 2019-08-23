package lab4;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

//A Map ADT structure using a red-black tree, where keys must implement
//Comparable.
//The key type of a map must be Comparable. Values can be any type.
public class RedBlackTreeMap<TKey extends Comparable<TKey>, TValue> {
	// A Node class.
	private class Node {
		private TKey mKey;
		private TValue mValue;
		private Node mParent;
		private Node mLeft;
		private Node mRight;
		private boolean mIsRed;

		// Constructs a new node with NIL children.
		public Node(TKey key, TValue data, boolean isRed) {
			mKey = key;
			mValue = data;
			mIsRed = isRed;

			mLeft = NIL_NODE;
			mRight = NIL_NODE;
		}

		@Override
		public String toString() {
			return mKey + " : " + mValue + " (" + (mIsRed ? "red)" : "black)");
		}
	}

	private Node mRoot;
	private int mCount;
	

	// Rather than create a "blank" black Node for each NIL, we use one shared
	// node for all NIL leaves.
	private final Node NIL_NODE = new Node(null, null, false);

	//////////////////// I give you these utility functions for free.

	// Get the # of keys in the tree.
	public int getCount() {
		return mCount;
	}

	// Finds the value associated with the given key.
	public TValue find(TKey key) {
		Node n = bstFind(key, mRoot); // find the Node containing the key if any
		if (n == null || n == NIL_NODE)
			throw new RuntimeException("Key not found");
		return n.mValue;
	}

	/////////////////// You must finish the rest of these methods.

	// Inserts a key/value pair into the tree, updating the red/black balance
	// of nodes as necessary. Starts with a normal BST insert, then adjusts.
	public void add(TKey key, TValue data) {
		Node n = new Node(key, data, true); // nodes start red

		// normal BST insert; n will be placed into its initial position.
		// returns false if an existing node was updated (no rebalancing needed)
		boolean insertedNew = bstInsert(n, mRoot);
		if (!insertedNew)
			return;

		// check cases 1-5 for balance violations.
		checkBalance(n);
	}

	// Applies rules 1-5 to check the balance of a tree with newly inserted
	// node n.
	private void checkBalance(Node n) {
		if (n == mRoot) {
			// case 1: new node is root.
			n.mIsRed = false;
			return;
		}
		Node gp = getGrandparent(n);
		// handle additional insert cases here.
		//case 2: p is black
		if (!n.mParent.mIsRed) {
			return;
		}
		//case 3: P and U are red
		if (getUncle(n).mIsRed) {
			n.mParent.mIsRed = false;
			getUncle(n).mIsRed = false;
			gp.mIsRed = true;
			checkBalance(gp);
		}
		//case 4 and 5: U is black and P is red (LL/RR/LR/RL)
		if (n.mParent.mIsRed && !getUncle(n).mIsRed) {
			if(gp != NIL_NODE && gp.mLeft != NIL_NODE) {
				if(gp.mLeft.mLeft == n) {
					gp.mIsRed = true;
					n.mParent.mIsRed = false;
					singleRotateRight(gp);
					
				}else if(gp.mLeft.mRight == n) {
					gp.mIsRed = true;
					singleRotateLeft(n.mParent);
					singleRotateRight(gp);
					
				}
			}if(gp != NIL_NODE && gp.mRight != NIL_NODE) {
				 if(gp.mRight.mRight == n) {
					gp.mIsRed = true;
					n.mParent.mIsRed = false;
					singleRotateLeft(gp);
					
				}else if(gp.mRight.mLeft == n) {
					gp.mIsRed = true;
					singleRotateRight(n.mParent);
					singleRotateLeft(gp);
				
				}
			}
		}
	}

	// Returns true if the given key is in the tree.
	public boolean containsKey(TKey key) {
		// TODO: using at most three lines of code, finish this method.
		// HINT: write the bstFind method first.
		if(bstFind(key, mRoot) != null) {
			return true;
		}
		return false;
	}

	// Prints a pre-order traversal of the tree's nodes, printing the key, value,
	// and color of each node.
	public void printStructure() {
		// TODO: a pre-order traversal. Will need recursion.
		printStructure(mRoot);

	}
	private void printStructure(Node n) {
		if (n == NIL_NODE) {
			return;
		}
		System.out.println(n.toString());
		//System.out.println(n.mKey + " " +  n.mValue + " " + n.mIsRed);
		printStructure(n.mLeft);
		printStructure(n.mRight);
	}

	// Retuns the Node containing the given key. Recursive.
	private Node bstFind(TKey key, Node currentNode) {
		// TODO: write this method. Given a key to find and a node to start at,
		// proceed left/right from the current node until finding a node whose
		// key is equal to the given key.
		if(currentNode != NIL_NODE && currentNode.mKey.equals(key)) {
			return currentNode;
		}
		if (currentNode.mLeft != NIL_NODE) {
		Node left = bstFind(key, currentNode.mLeft);
		if (left != null) {
			return left;
		}
		}
		if (currentNode.mRight != NIL_NODE) {
		Node right = bstFind(key, currentNode.mRight);
		if (right != null) {
			return right;
		}	
		}
		return null;
	}

	//////////////// These functions are needed for insertion cases.

	// Gets the grandparent of n.
	private Node getGrandparent(Node n) {
		// TODO: return the grandparent of n
		if (n == null) {
			return NIL_NODE;
		}
		return n.mParent.mParent;
	}

	// Gets the uncle (parent's sibling) of n.
	private Node getUncle(Node n) {
		// TODO: return the uncle of n
		Node gp = getGrandparent(n);
		Node mUncle = NIL_NODE;
				if(gp.mRight.mRight == n) {
					mUncle = gp.mLeft;
				}if(gp.mRight.mLeft == n) {
					mUncle = gp.mLeft;
				}
				if(gp.mLeft.mLeft == n) {
					mUncle = gp.mRight;
				}
				if(gp.mLeft.mRight == n) {
					mUncle = gp.mRight;
				}
		return mUncle;
	}

	// Rotate the tree right at the given node.
	private void singleRotateRight(Node n) {
		// TODO: do a single right rotation (AVL tree calls this a "ll" rotation)
		Node l = n.mLeft, lr = l.mRight, p = n.mParent;
		n.mLeft = lr;
		lr.mParent = n;
		l.mRight = n;
		n.mParent = l;

		if (p == null) { // n is root
			mRoot = l;
		}
		else if (p.mLeft == n) {
			p.mLeft = l;
		} 
		else {
			p.mRight = l;
		}

		l.mParent = p;
	}

	// Rotate the tree left at the given node.
	private void singleRotateLeft(Node n) {
		// TODO: do a single left rotation (AVL tree calls this a "rr" rotation)
		// at n.
		Node r = n.mRight, rl = r.mLeft, p = n.mParent;
		n.mRight = rl;
		rl.mParent = n;
		r.mLeft = n;
		n.mParent = r;

		if (p == null) { // n is root
			mRoot = r;
		}
		else if (p.mLeft == n) {
			p.mLeft = r;
		} 
		else {
			p.mRight = r;
		}

		r.mParent = p;

	}


	// This method is used by insert. It is complete.
	// Inserts the key/value into the BST, and returns true if the key wasn't
	// previously in the tree.
	private boolean bstInsert(Node newNode, Node currentNode) {
		if (mRoot == null) {
			// case 1
			mRoot = newNode;
			return true;
		} 
		else {
			int compare = currentNode.mKey.compareTo(newNode.mKey);
			if (compare < 0) {
				// newNode is larger; go right.
				if (currentNode.mRight != NIL_NODE) {
					return bstInsert(newNode, currentNode.mRight);
				}
				else {
					currentNode.mRight = newNode;
					newNode.mParent = currentNode;
					mCount++;
					return true;
				}
			} 
			else if (compare > 0) {
				if (currentNode.mLeft != NIL_NODE) {
					return bstInsert(newNode, currentNode.mLeft);
				}
				else {
					currentNode.mLeft = newNode;
					newNode.mParent = currentNode;
					mCount++;
					return true;
				}
			} 
			else {
				// found a node with the given key; update value.
				currentNode.mValue = newNode.mValue;
				return false; // did NOT insert a new node.
			}
		}
	}
	public static void main(String[] args) throws IOException {
		RedBlackTreeMap rbt = new RedBlackTreeMap();
		BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\drizz\\OneDrive\\Documents\\Docs\\328\\players_homeruns.csv"));
		String line = reader.readLine();
		String[] kvPair;

//		while(line != null) {
//			kvPair = line.split(",");
//			//System.out.println(kvPair[0] + " " + kvPair[1]);
//			rbt.add(kvPair[0], kvPair[1]);
//			line = reader.readLine();
//		}
		for(int i = 0; i < 10; i++) {
			kvPair = line.split(",");
			//System.out.println(kvPair[0] + " " + kvPair[1]);
			rbt.add(kvPair[0], kvPair[1]);
			line = reader.readLine();
		}
		rbt.printStructure();
		System.out.println(rbt.find("Babe Ruth"));
		System.out.println(rbt.find("Honus Wagner"));
		System.out.println(rbt.find("Rogers Hornsby"));
		System.out.println(rbt.find("Stan Musial"));
	}
}


