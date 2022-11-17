package graph;

import io.Case;
import java.util.Set;
import java.util.HashSet;

/** Un Graph est une class permettant de représenter le graphe d'une carte selon la représentation d'un robot. 
 * Il est composé de noeuds : des "Node" ainsi que du temps pris par le robot pour se déplacer entre ces noeuds
 * Les noeuds représentent les cases du terrain*/
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
	
	public void addNode(Node node) {
		nodeArray.add(node);
	}
	
	/** Une edge, ou arrete, représente la distance entre deux noeuds deja existants du graph*/
	public void addEdge(Node first, Node second, double weight) {
		if (nodeArray.contains(first) && nodeArray.contains(second)) {
			first.addVoisin(second, weight);
			second.addVoisin(first, weight);
		} else {
			throw new IllegalArgumentException("Erreur, node non present dans le graph");
		}
	}
	
	/** Renvoie un iterateur sur tous les noeuds du graph*/
	public Set<Node> getIte() {
		return nodeArray;
	}
	
	/** Retourne le noeud associe a une case du graph*/
	public Node getNodeFromCase(Case myCase) {
		for (Node node : this.getIte()) {
			if (node.getCase().equals(myCase)) {
				return node;
			}
		}
		throw new IllegalArgumentException("Case non presente dans le graph");
	}
}