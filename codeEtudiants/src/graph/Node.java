package graph;

import java.util.HashMap;
import java.util.Map;
import io.Case;

/** Class permettant de représenter les noeuds d'un graph. Ces noeuds sont associés aux cases de la carte.
 * Les noeuds sont reliés a leur voisins directs par un temps correspondant au temps de parcours du robot*/
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

	/** Renvoie un iterateur sur les voisins du noeud donné*/
	public Map<Node, Double> getVoisins() {
		return this.adjacentNodes;
	}
	
	@Override
	public String toString() {
		return this.getCase().toString();
	}

}