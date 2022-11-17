package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import donnees.Carte;
import donnees.Case;
import donnees.Direction;

/** Class permettant d'implenter l'algorithme de Dijkstra.
 *  Cette résolution s'appuie sur un graph, donné par la class Graph.
 *  
 *  Dans cet algorithme, on va venir evaluer une Node -currentNode- donnée (on commence par la source).
 *  Pour tous ses voisins : on prend un voisin -voisinNode-
 *         * on calcule une date d'arrivee correspondant a la distance entre currentNode et voisinNode + la date d'arrive a currentNode
 *         * la nouvelle date d'arrive pour voisinNode sera le min entre la date deja presente et celle qu'on vient de calculer
 *         * si voisinNode n'a pas encore étée évaluée on l'ajoute en fin de liste des nodes a evaluer
 *  
 *  # Point important #
 *  A tout moment de l'algorithme, on se trouve a une distance n de la source car on evalue ses voisins, 
 *  puis ses voisins de ses voisins... en augmentant toujours la distance de 1. Ainsi, notre algorithme est juste
 *  et on sait que une fois qu'une Node est evaluee
 *  
 *  */
public class Dijkstra {
	private Node source = null;
	private Graph myGraph = null;
	private Map<Node, ResultatsPathFinder> mapResultatsExecution = null;
	
	public Dijkstra() {
	}

	/** Fonction d'initialisation de la résolution. Elle initialise la structure de données
	 * @variable mapResultatsExecution et elle permet de récuperer le graphe
	 * @variable myGraph */
	public void init(Graph graph) {
		mapResultatsExecution = new HashMap<>();
		this.myGraph = graph;
		for (Node node : graph.getIte()) {
			mapResultatsExecution.put(node, new ResultatsPathFinder(null, Long.MAX_VALUE));
		}
	}
	
	/** Permet de fixer la source du graph, correspondant à l'origine : une distance de 0*/
	public void setSource(Case source) {
		if (this.myGraph == null)
			throw new IllegalArgumentException("Must init first");
		this.source = this.myGraph.getNodeFromCase(source);
	}

	/** Execution concrète de l'algorithme de Dijkstra. */
	public void compute() {
		if (this.source == null)
			throw new IllegalArgumentException("Must setSource first");
		
		/* Creer la variable qui stock les nodes qui doivent etre calculées, initialisée avec la source */
		List<Node> nodesToCompute = new ArrayList<>();
		nodesToCompute.add(source);
		mapResultatsExecution.get(source).setTime(0);
		
		while (!nodesToCompute.isEmpty()) {
			Node currentNode = nodesToCompute.remove(0);
			
			// On parcourt la liste des voisins
			for (Map.Entry<Node, Double> entry : currentNode.getVoisins().entrySet()) {
				
				Node newNode = entry.getKey();	
				long distance = mapResultatsExecution.get(currentNode).getTime() + Math.round(entry.getValue());
				
				if (distance < mapResultatsExecution.get(newNode).getTime()) {
					nodesToCompute.add(newNode);
					// New minimal time
					mapResultatsExecution.get(newNode).setTime(distance);
					// New shortest Path
					mapResultatsExecution.get(newNode).setLastNode(currentNode);
				}
			}
		}	
	}
	
	/** Fonction qui renvoie le plus court chemin permettant d'aller a une case donnée en @params destination */
	public Path getShortestPath(Case destination) {
		if (this.mapResultatsExecution == null)
			throw new IllegalArgumentException("Must compute first");
		return this.getPath(destination).getReversedPath();
	}
	
	/** Fonction qui renvoie le temps (minimal) permettant d'aller a une case donnée en @params destination */
	public long getShortestTime(Case destination) {
		if (this.mapResultatsExecution == null)
			throw new IllegalArgumentException("Must compute first");
		return this.mapResultatsExecution.get(myGraph.getNodeFromCase(destination)).getTime();
	}
	
	/** Fonction qui va renvoyer le chemin apres calcul par l'algorithme de Dijkstra pour aller à une case
	 * @params destination
	 */
	private Path getPath(Case destination) {
		if (this.mapResultatsExecution == null)
			throw new IllegalArgumentException("Must compute first");
		Node currentNode = myGraph.getNodeFromCase(destination);
		Path resultPath = new Path(currentNode);
		
		while (!currentNode.equals(this.source)) {
			Node newNode = this.mapResultatsExecution.get(currentNode).getLastNode();
			resultPath.addNode(newNode);
			currentNode = newNode;
		}
		return resultPath;
		
	} 

}