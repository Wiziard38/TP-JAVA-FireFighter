package graph;


public class Dijkstra {
	private Node source;
	private Node target;
	private Graph myGraph;
	
	public Dijkstra(Graph graph, Node source, Node target) {
		this.myGraph = graph;
		this.source = source;
		this.target = target;
	}

	public Node getSource() {
		return source;
	}
	
	public Node getTarget() {
		return this.target;
	}
	
}