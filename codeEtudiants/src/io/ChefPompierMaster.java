package io;

import donnees.Case;
import donnees.TypeChefPompier;
import robots.Robot;

/**C'est la stratégie évoluée où le chef pompier assigne à un feu le robot qui mettera le plus proche*/
public class ChefPompierMaster extends ChefPompier{
	
	public ChefPompierMaster(Simulateur simu) {
		super(simu, TypeChefPompier.Simple);
	}
	
	public Robot getRobotPret(Case caseIncendie) {
		double min = Double.POSITIVE_INFINITY;
		Robot robotUtilise = null;
		for (Robot robot : this.getSimu().getJeuDeDonnees().getRobots()) {
			if (!robot.getOccupied() && (robot.existsPathTo(caseIncendie))) {
				double temps = robot.getTimeFromPath(robot.getShortestPath(robot.getPosition(), caseIncendie));
				if(temps< min) {
					robotUtilise = robot;
					min = temps;
				}
			}
		}
		return robotUtilise;
	}
}
