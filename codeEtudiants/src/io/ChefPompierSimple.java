package io;
import robots.Robot;

public class ChefPompierSimple extends ChefPompier{
	
	public ChefPompierSimple(Simulateur simu) {
		super(simu, TypeChefPompier.Master);
	}

	public Robot getRobotPret(Case caseIncendie) {
		for (Robot robot : this.getSimu().getJeuDeDonnees().getRobots()) {
			if (!robot.getOccupied() && (robot.existsPathTo(caseIncendie) || robot.getPosition().equal(caseIncendie))) {
				return robot;
			}
		}
		return null;
	}
}
