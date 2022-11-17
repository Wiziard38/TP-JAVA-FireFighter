package graph;

import java.util.ArrayList;
import java.util.List;


public class Path {
	private List<Node> listNodes = new ArrayList<Node>();
	
	public Path() {
	}

	public Path(Node firstNode) {
		this.addNode(firstNode);
	}
	
	public void addNode(Node nodeToAdd) {
		listNodes.add(nodeToAdd);
	}
	
	public List<Node> getPath() {
		return this.listNodes;
	}
	
	public double getPathLength() {
		double length = 0;
		Node first = listNodes.get(0);
		for (int i = 1; i < listNodes.size(); i++) {
			Node second = listNodes.get(i);
			length += first.distanceTo(second);
			first = second;
		}
		return length;
	}

	@Override
	public Path clone() {
		Path newPath = new Path();
		for (Node node : this.getPath()) {
			newPath.addNode(node);
		}
		
		return newPath;
	}
	
	@Override
	public String toString() {
		String result = "";
		for (Node node : this.getPath()) {
			result += node.toString() + " --> ";
		}
		return result;
	}
}
