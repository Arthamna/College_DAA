package mytree;

// AVL Tree = Height-Balanced (HB) Tree

public class DAA2 extends DAA1 {

	// 4. isHeightBalanced() [10 points]
	public static boolean isHeightBalanced(MyTree t) {
		// Write your codes in here
        //...
        // Write your codes in here
		class Ch {
			int heightTree(MyTree n){
				if (n.getEmpty()){
					return 0;
				}
				int left = heightTree(n.getLeft());
				if (left == -1) {
					return -1;
				}
				int right = heightTree(n.getRight());
				if (right == -1) {
					return -1;
				}
				if (Math.abs(left-right)> 1) {
					return -1;
				}
				return 1;
			}
		}
		return new Ch().heightTree(t) != -1; 
	}
	
	// 5. insertHB() [10 points]
	public static MyTree insertHB(int n, MyTree t) {
		// Write your codes in here
        //...
        // Write your codes in here
		if (t.getEmpty()) {
			return new MyTree(n, MyTree.emptyTree, MyTree.emptyTree);
		}
		if (n < t.getValue()) {
			MyTree tNew = new MyTree(t.getValue(), insertHB(n, t.getLeft()), t.getRight());
			return rebalanceForLeft(tNew);
		}
		if (n > t.getValue()) {
			MyTree tNew = new MyTree(t.getValue(), t.getLeft(), insertHB(n, t.getRight()));
			return rebalanceForRight(tNew);
		}
		else{
			return t;
		}
	}

	// rebalanceForLeft is called when the left subtree of t may have
	// grown taller by one notch.
	// If it is indeed taller than the right subtree by two notches,
	// return a height-balanced version of t using single or double rotations.
	// The subtrees of t are assumed to be already height-balanced and
	// no effort is made to rebalance them.
	//
	// Likewise, for the case of the right subtree -> rebalanceForRight
	// Both rebalanceForLeft & rebalanceForRight will be used by insertHB() and deleteHB()
	// 6. rebalanceForLeft() [15 points]

	private static MyTree rotateLeft(MyTree t) {
		MyTree r = t.getRight();
		MyTree rl = r.getLeft();
		return new MyTree(r.getValue(), new MyTree(t.getValue(), t.getLeft(), rl), r.getRight());
	}
	
	private static MyTree rotateRight(MyTree t) {
		MyTree l = t.getLeft();
		MyTree lr = l.getRight();
		return new MyTree(l.getValue(), l.getLeft(), new MyTree(t.getValue(), lr, t.getRight()));
	}
	
	private static MyTree rebalanceForLeft(MyTree t) {
		// Write your codes in here
        //...
        // Write your codes in here
		class H {
			int height(MyTree x) {
				if (x.getEmpty()) {
					return 0;
				}
				return 1 + Math.max(height(x.getLeft()), height(x.getRight()));
			}
		}
	
		H h = new H();
	
		if (h.height(t.getLeft()) - h.height(t.getRight()) <= 1) {
			return t;
		}
	
		// left-left case
		if (h.height(t.getLeft().getLeft()) >= h.height(t.getLeft().getRight())) {
			return rotateRight(t);
		}
	
		// left-right case
		MyTree newLeft = rotateLeft(t.getLeft());
		return rotateRight(new MyTree(t.getValue(), newLeft, t.getRight()));
	}
	
	// 7. rebalanceForRight() [15 points]
	private static MyTree rebalanceForRight(MyTree t) {
		// Write your codes in here
        //...
        // Write your codes in here
		class H {
			int height(MyTree x) {
				if (x.getEmpty()) {
					return 0;
				}
				return 1 + Math.max(height(x.getLeft()), height(x.getRight()));
			}
		}
	
		H h = new H();
	
		if (h.height(t.getRight()) - h.height(t.getLeft()) <= 1) {
			return t;
		}
	
		// right-right case
		if (h.height(t.getRight().getRight()) >= h.height(t.getRight().getLeft())) {
			return rotateLeft(t);
		}
	
		// right-left case
		MyTree newRight = rotateRight(t.getRight());
		return rotateLeft(new MyTree(t.getValue(), t.getLeft(), newRight));	
	}
	
	// 8. deleteHB() [10 points]
	/**
	 * Deletes the value 'x' from the given tree, if it exists, and returns the new
	 * Tree.
	 *
	 * Otherwise, the original tree will be returned.
	 */
	public static MyTree deleteHB(MyTree t, int x) {
		// Write your codes in here
        //...
        // Write your codes in here
		class H {
			int min(MyTree node) {
				if (node.getLeft().getEmpty()) {
					return node.getValue();
				}
				return min(node.getLeft());
			}
		}
	
		H h = new H();
	
		if (t.getEmpty()) {
			return t;
		}
	
		if (x < t.getValue()) {
			MyTree newLeft = deleteHB(t.getLeft(), x);
			return rebalanceForRight(new MyTree(t.getValue(), newLeft, t.getRight()));
		}
	
		if (x > t.getValue()) {
			MyTree newRight = deleteHB(t.getRight(), x);
			return rebalanceForLeft(new MyTree(t.getValue(), t.getLeft(), newRight));
		}
	
		// x == t.getValue()
		if (t.getLeft().getEmpty() && t.getRight().getEmpty()) {
			return MyTree.emptyTree;
		}
	
		if (t.getLeft().getEmpty()) {
			return t.getRight();
		}
	
		if (t.getRight().getEmpty()) {
			return t.getLeft();
		}
	
		int least = h.min(t.getRight());
		MyTree rightNew = deleteHB(t.getRight(), least);
		return rebalanceForLeft(new MyTree(least, t.getLeft(), rightNew));
	}

}