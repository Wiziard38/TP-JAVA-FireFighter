package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import io.Case;

public class Dijkstra {
	private Node source = null;
	private Graph myGraph = null;
	private Map<Node, ResultatsPathFinder> treeMap = null;
	
	public Dijkstra() {
	}

	public void init(Graph graph) {
		treeMap = new HashMap<>();
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
		
		List<Node> nodesToCompute = new ArrayList<>();
		Set<Node> nodesComputed = new HashSet<>();
		
		nodesToCompute.add(source);
		
		while (!nodesToCompute.isEmpty()) {
		
			Node currentNode = nodesToCompute.remove(0);
			nodesComputed.add(currentNode);
			
			if (currentNode.equals(source)) {
				treeMap.get(currentNode).setTime(0);
			}
			
			for (Map.Entry<Node, Double> entry : currentNode.getVoisins().entrySet()) {
				
				Node newNode = entry.getKey();
				if (!nodesComputed.contains(newNode))
					if (!nodesToCompute.contains(newNode))
						nodesToCompute.add(newNode);
				
				long distance = treeMap.get(currentNode).getTime() + Math.round(entry.getValue());
				
				if (distance < treeMap.get(newNode).getTime()) {
					// New minimal time
					treeMap.get(newNode).setTime(distance);
					// New shortest Path
					Path newPath = treeMap.get(currentNode).getPath().clone();
					newPath.addNode(newNode);
					treeMap.get(newNode).setPath(newPath);
				}
			}
		}	
	}
	
	public Path getShortestPath(Case destination) {
		if (this.treeMap == null)
			throw new IllegalArgumentException("Must compute first");
		return this.treeMap.get(myGraph.getNodeFromCase(destination)).getPath();
	}
	
	public long getShortestTime(Case destination) {
		if (this.treeMap == null)
			throw new IllegalArgumentException("Must compute first");
		return this.treeMap.get(myGraph.getNodeFromCase(destination)).getTime();
	}

}