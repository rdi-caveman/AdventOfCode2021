package advent_code_2021;

public class Node {
	Node parent;
	Node leftChild;
	Node rightChild;
	Integer value = null;
	Integer depth = 0;
	
	public Node() {
		
	}
	
	public Node(Node a, Node b) {
		addLeftChild(a);
		addRightChild(b);
	}
	
	public void addLeftChild(Node n) {
		this.leftChild = n;
		n.parent = this;
		n.depth = depth+1;
		updateChildDepths(leftChild);
	}
	
	public void addRightChild(Node n) {
		this.rightChild = n;
		n.parent = this;
		n.depth = this.depth+1;
		updateChildDepths(rightChild);
	}
	
	private void updateChildDepths(Node n) {
		n.depth = n.parent.depth+1;
		if (n.leftChild != null) updateChildDepths(n.leftChild);
		if (n.rightChild != null) updateChildDepths(n.rightChild);
	}
	
	public String toString() {
		return (value != null) ? value.toString() : String.format("[%s,%s]", leftChild, rightChild); 
	}

	public String singleNodeToString() {
		return String.format("[%s,%s] = %d, depth %d", leftChild == null ? null : leftChild.value, rightChild==null ? null : rightChild.value, value, depth);
	}
	
}
