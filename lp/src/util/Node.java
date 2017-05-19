package util;

import simplex.Simplex;

public class Node implements Comparable<Node> {
	
	public int height;
	public double best;
	public Simplex simplex;

	public Node() {
		best = 0;
		height = -1;
	}
	
	public Node(Node parent, Simplex simplex) {
		this.height = parent.height + 1;
		this.best = parent.best;
		this.simplex = simplex;
	}

	public Node(double best, Simplex simplex) {
		this.height = 0;
		this.best = best;
		this.simplex = simplex;
	}

	public Node(double best, Simplex simplex, int height) {
		this.height = height;
		this.best = best;
		this.simplex = simplex;
	}
	
	public int compareTo(Node other) {
		return (other.height - this.height);
	}
}
