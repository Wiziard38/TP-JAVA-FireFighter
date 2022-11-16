package graph;

import java.util.HashMap;
import java.util.Map;

import io.Case;

public class Node{
	private Case caseLinked;
	private Map<Node, Double> adjacentNodes = new HashMap<>();
	
	public Node(Case mapCase) {
		this.caseLinked = mapCase;
	}
	
	public void addVoisin(Node node, double weight) {
		this.adjacentNodes.put(node, weight);
	}
	
	public Case getCase() {
		return this.caseLinked;
	}
	
	public double distanceTo(Node voisin) {
		return this.adjacentNodes.get(voisin);
	}

	public Map<Node, Double> getVoisins() {
		return this.adjacentNodes;
	}
	
	@Override
	public String toString() {
		return this.getCase().toString();
	}

}