package io;

import robots.Robot;

public class MasterChefPompier extends ChefPompier{
	
	public MasterChefPompier(Simulateur simu, DonneesSimulation jeuDeDonnees) {
		super(simu,jeuDeDonnees);
	}
	
	public Robot getRobotPret(Case caseIncendie) {
		double min = Double.POSITIVE_INFINITY;
		Robot robotUtilise =null;
		for (Robot robot : this.getJeuDeDonnees().getRobots()) {
			if (!robot.getOccupied() && (robot.existsPathTo(caseIncendie) || robot.getPosition().equal(caseIncendie))) {
				double temps = robot.getTimeFromPath(robot.getShortestPath(robot.getPosition(), caseIncendie));
				if(temps< min) {
					robotUtilise = robot;
				}
			}
		}
		return robotUtilise;
	}
}
