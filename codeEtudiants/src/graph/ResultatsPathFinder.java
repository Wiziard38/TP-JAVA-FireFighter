package graph;

/** Structure de donnée permettant de stocker les résultats de l'algorithme de Dijkstra pendant son execution.
 *  Cette structure comprend le temps minimal a un moment de l'exucution de l'algorithme,
 *  et une Node, correspondant au chemin a prendre pour obtenir le temps minimal. */
public class ResultatsPathFinder {
	private Node lastNode;
	private long myTime;
	
	public ResultatsPathFinder(Node lastNode, long myTime) {
		this.lastNode = lastNode;
		this.myTime = myTime;
	}
	
	public long getTime() {
		return this.myTime;
	}
	
	public Node getLastNode() {
		return this.lastNode;
	}
	
	public void setLastNode(Node newLast) {
		this.lastNode = newLast;
	}
	
	public void setTime(long newTime) {
		this.myTime = newTime;
	}
	
	@Override 
	public String toString() {
		return "Position : " + this.lastNode + " // Time : " + this.myTime;
	}
}
