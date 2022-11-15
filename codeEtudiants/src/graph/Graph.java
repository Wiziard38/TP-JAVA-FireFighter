package graph;

import io.Case;
import java.util.Set;
import java.util.HashSet;

public class Graph {
	private final String id;
	private Set<Node> nodeArray = new HashSet<Node>();
	
	public Graph(String id) {
		this.id = id;
	}
	
	public void addNode(Case mapTile) {
		Node mapNode = new Node(mapTile);
		nodeArray.add(mapNode);
	}
	
	public void addEdge(Node first, Node second, double weight) {
		if (nodeArray.contains(first) && nodeArray.contains(second)) {
			first.addVoisin(second, weight);
			second.addVoisin(first, weight);
		}
	}
	
	public Set<Node> getIte() {
		return nodeArray;
	}
}