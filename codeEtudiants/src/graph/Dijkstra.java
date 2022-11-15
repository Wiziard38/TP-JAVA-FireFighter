package graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.Case;

public class Dijkstra {
	private Node source = null;
	private Graph myGraph = null;
	private Map<Node, ResultatsPathFinder> treeMap = null;
	
	public Dijkstra() {
	}

	public void init(Graph graph) {
		this.myGraph = graph;
		for (Node node : graph.getIte()) {
			treeMap.put(node, new ResultatsPathFinder(new Path(node), Long.MAX_VALUE));
		}
	}
	
	public void setSource(Case source) {
		if (this.myGraph == null)
			throw new IllegalArgumentException("Must init first");
		this.source = this.myGraph.getNodeFromCase(source);
	}

	public void compute() {
		if (this.source == null)
			throw new IllegalArgumentException("Must setSource first");
		
		int computedNodes = 0;
		List<Node> nodesToCompute = new ArrayList<>();
		
		while (computedNodes != myGraph.getIte().size()) {
			
			if (!nodesToCompute.isEmpty()) {
				Node currentNode = nodesToCompute.remove(0);
				
				if (currentNode.equals(source)) {
					treeMap.get(currentNode).setTime(0);;
				}
				
				for (Map.Entry<Node, Double> entry : currentNode.getVoisins().entrySet()) {
					
					Node newNode = entry.getKey();
					nodesToCompute.add(newNode);
					
					long distance = treeMap.get(currentNode).getTime() + Math.round(entry.getValue());
					
					if (distance < treeMap.get(newNode).getTime()) {
						// New minimal time
						treeMap.get(newNode).setTime(distance);
						// New shortest Path
						treeMap.get(newNode).setPath(treeMap.get(currentNode).getPath());
						treeMap.get(newNode).getPath().addNode(newNode);
					}
				}
			}
		}	
	}
	
	public Path getShortestPath(Node destination) {
		if (this.treeMap == null)
			throw new IllegalArgumentException("Must compute first");
		return this.treeMap.get(destination).getPath();
	}

}