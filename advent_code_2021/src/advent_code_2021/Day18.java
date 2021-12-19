package advent_code_2021;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Day18 {
	private static final int RIGHT = +1;
	private static final int LEFT = -1;
	private static final String DAY18_INPUT_TXT = "src/resources/day18_input.txt";

	public static void main(String[] args) {
		part1();
		part2();
	}

	private static void part1() {
		Node result = FileUtility.readListOfString(DAY18_INPUT_TXT).stream().map(s -> parse(s))
				.reduce((a, b) -> sumAndReduce(a, b)).get();
		System.out.println("Day 18 part 1 " + magnitude(result));
	}
	
	private static void part2() {
		List<String> inputs = FileUtility.readListOfString(DAY18_INPUT_TXT);
		int maxMagnitude = 0;
		for (int i=0; i<inputs.size(); i++) {
			for (int j=0; j<inputs.size(); j++) {
				if (i == j) continue;  // only sum different numbers
				Node a = parse(inputs.get(i));
				Node b = parse(inputs.get(j));
				maxMagnitude = Math.max(maxMagnitude, magnitude(sumAndReduce(a,b)));
				maxMagnitude = Math.max(maxMagnitude, magnitude(sumAndReduce(a,b)));
			}
		}
		System.out.println("Day 18 part 2 " + maxMagnitude);
	}

	private static Node sumAndReduce(Node a, Node b) {
		Node n = new Node(a, b); // sum
		return reduce(n);
	}

	public static Node reduce(Node n) {
		if (explode(n))
			return reduce(n);
		if (split(n))
			return reduce(n);
		return n;
	}

	private static boolean explode(Node n) {
		List<Node> allNodes = inOrderTraversal(n);
		int i = 0;
		while (!isExplodeEligible(allNodes.get(i))) {
			i++;
			if (i == allNodes.size())
				return false; // didn't find a node to explode
		}
		// found a node to explode
		doExplode(allNodes, i);
		return true;
	}

	private static void doExplode(List<Node> allNodes, int i) {
		Node e = allNodes.get(i);
		addValue(allNodes, i, LEFT, e.leftChild.value, e.leftChild); // add exploded node value to nearest neighbor in
																		// left direction
		addValue(allNodes, i, RIGHT, e.rightChild.value, e.rightChild); // add exploded node value to nearest neighbor in
																		// right direction
		e.leftChild = null;
		e.rightChild = null;
		e.value = 0;
	}

	private static boolean isExplodeEligible(Node n) {
		if (n.depth < 4)
			return false;
		if (isValue(n))
			return false;
		return isValue(n.leftChild) && isValue(n.rightChild);
	}

	private static boolean isValue(Node n) {
		return (n != null && n.value != null);
	}

	private static void addValue(List<Node> allNodes, int start, int direction, int value, Node exclude) {
		int i = start + direction;
		if (i < 0 || i == allNodes.size())
			return;
		while (!isValue(allNodes.get(i)) || allNodes.get(i) == exclude) {
			i += direction;
			if (i < 0 || i == allNodes.size())
				return; // no eligible node to update
		}
		allNodes.get(i).value += value;
	}

	private static List<Node> inOrderTraversal(Node n) {
		List<Node> nodes = new ArrayList<>();
		if (n.leftChild != null)
			nodes.addAll(inOrderTraversal(n.leftChild));
		nodes.add(n);
		if (n.rightChild != null)
			nodes.addAll(inOrderTraversal(n.rightChild));
		return nodes;
	}

	private static boolean split(Node n) {
		List<Node> allNodes = inOrderTraversal(n);
		int i = 0;
		while (!isValue(allNodes.get(i)) || allNodes.get(i).value <= 9) {
			i++;
			if (i == allNodes.size())
				return false; // didn't find a node to split
		}
		doSplit(allNodes.get(i));
		return true;
	}

	public static void doSplit(Node n) {
		int num = n.value;
		Node l = new Node();
		l.value = num / 2;
		Node r = new Node();
		r.value = (num + 1) / 2;
		n.value = null;
		n.addLeftChild(l);
		n.addRightChild(r);
	}

	public static Node parse(String input) {
		Stack<Node> stack = new Stack<>();
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c == '[') {
				stack.push(new Node());
			} else if (c == ',') {
				Node n1 = stack.pop();
				Node n2 = stack.pop();
				n2.addLeftChild(n1);
				stack.push(n2);
			} else if (c == ']') {
				Node n1 = stack.pop();
				Node n2 = stack.pop();
				n2.addRightChild(n1);
				stack.push(n2);
			} else { // integer value
				Node n = new Node();
				n.value = Integer.valueOf(c - '0');
				stack.push(n);
			}
		}
		return stack.pop();
	}

	private static int magnitude(Node n) {
		int left = (n.leftChild.value != null) ? n.leftChild.value : magnitude(n.leftChild);
		int right = (n.rightChild.value != null) ? n.rightChild.value : magnitude(n.rightChild);
		return 3 * left + 2 * right;
	}

}
