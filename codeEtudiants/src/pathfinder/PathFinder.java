package pathfinder;

import java.util.SortedSet;
import io.Carte;
import io.Case;

import robots.Robot;


public class PathFinder {
	private Robot robot;
	private Carte carte;
	private SortedSet<Case> path;
	
	public PathFinder(Carte carte, Robot robot) {
		this.carte = carte;
		this.robot = robot;
	}
	
}
