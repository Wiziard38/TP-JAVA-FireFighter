package io;

import robots.Robot;
import graph.Path;
public class ChefPompierMaster extends ChefPompier{
	
	public ChefPompierMaster(Simulateur simu) {
		super(simu, TypeChefPompier.Simple);
	}
	
	public Robot getRobotPret(Case caseIncendie) {
		double min = Double.POSITIVE_INFINITY;
		Robot robotUtilise = null;
		for (Robot robot : this.getSimu().getJeuDeDonnees().getRobots()) {
			if (!robot.getOccupied() && (robot.existsPathTo(caseIncendie) || robot.getPosition().equal(caseIncendie))) {
				Path path = robot.getShortestPath(robot.getPosition(), caseIncendie);
				double temps = robot.getTimeFromPath(path);
				if(temps< min) {
					robotUtilise = robot;
				}
			}
		}
		return robotUtilise;
	}
}
