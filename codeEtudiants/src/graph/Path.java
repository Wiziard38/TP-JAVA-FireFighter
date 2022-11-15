package graph;

import java.util.ArrayList;
import java.util.List;


public class Path {
	private List<Node> listNodes = new ArrayList<Node>();
	
	public void addNode(Node nodeToAdd) {
		listNodes.add(nodeToAdd);
	}
	
	public List<Node> getPath() {
		return this.listNodes;
	}
	
	public double getPathLenght() {
		double length = 0;
		Node first = listNodes.get(0);
		
		for (int i = 1; i < listNodes.size(); i++) {
			Node second = listNodes.get(i);
			length += first.distanceTo(second);
		}
		return length;
	}
}
