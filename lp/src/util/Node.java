package util;

import simplex.Simplex;

public class Node implements Comparable<Node> {
	
	public int height;
	public double best;
	public double value;
	public Simplex simplex;
	
	public Node(Node parent) {
		this.height = parent.height + 1;
		this.best = parent.best;
		this.value = parent.value;
	}
	
	public int compareTo(Node other) {
		return (other.height - this.height);
	}
}
