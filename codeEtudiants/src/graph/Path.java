package graph;

import java.util.ArrayList;
import java.util.List;

/** Structure permettant de représenter un chemin donné dans un graph.
 * Le chemin est uniquement représenté par une succession de noeuds */
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
	
	/** Permet de renvoyer la "longueur" d'un path;
	 *  Cela correspondant a la somme de tous les temps effectifs de parcours par le robot */
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
	
	/** Permet d'inser a l'indice @params index un noeud : @params nodeToInsert*/
	public void insertNode(int index, Node nodeToInsert) {
		this.listNodes.add(index, nodeToInsert);
	}
	
	/** Renvoie un Path qui correspond au path mais inversé */
	public Path getReversedPath() {
		Path revertPath = new Path();
		for (Node node : this.listNodes) {
			revertPath.insertNode(0, node);
		}
		return revertPath;
	}

	/** Permet de cloner un path pour en obtenir une deep copy*/
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
