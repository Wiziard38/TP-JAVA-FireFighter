package pathfinder;

import java.util.SortedSet;
import io.Carte;
import io.Case;
import io.NatureTerrain;

import robots.Robot;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import org.graphstream.algorithm.Dijkstra;
import org.graphstream.algorithm.Dijkstra.Element;
import org.graphstream.graph.implementations.DefaultGraph;


public class PathFinder {
	private Robot robot;
	private Carte carte;
	private SortedSet<Case> path;
	private Graph graph = new SingleGraph("map graph");
	
	public PathFinder(Carte carte, Robot robot) {
		this.carte = carte;
		this.robot = robot;
	
		//Graph g = InitGraphsRobots.getGraph(robot);
		
	
	
	}
	
}
