package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.Carte;

public class Dijkstra {
	private static final int HashSet = 0;
	private Node source;
	private Node target;
	private Graph myGraph;
	private Map<Node, Long> treeMap;
	
	public Dijkstra(Graph graph, Node source) {
		this.myGraph = graph;
		this.source = source;	
	}

	public Node getSource() {
		return source;
	}
	
	public Node getTarget() {
		return this.target;
	}
	
	public void setTarget(Node node) {
		this.target = node;
	}
	
	public void init(Graph graph) {
		this.myGraph = graph;
		for (Node node : graph.getIte()) {
			treeMap.put(node, Long.MAX_VALUE);
		}
	}
	
	
	public void compute() {
		int computedNodes = 0;
		List<Node> nodesToCompute = new ArrayList<>();
		
		while (computedNodes != myGraph.getIte().size()) {
			
			if (!nodesToCompute.isEmpty()) {
				Node currentNode = nodesToCompute.remove(0);
				
				if (currentNode.equals(source)) {
					treeMap.replace(currentNode, (long) 0);
					for (Map.Entry<Node, Double> entry : currentNode.getVoisins().entrySet()) {
						nodesToCompute.add(entry.getKey());
						long distance = treeMap.get(currentNode) + Math.round(entry.getKey().distanceTo(currentNode));
						if (distance < Math.round(entry.getValue())) {
							treeMap.replace(entry.getKey(), distance);
						}
					}
				}
			}
		}
		
	}
	
}