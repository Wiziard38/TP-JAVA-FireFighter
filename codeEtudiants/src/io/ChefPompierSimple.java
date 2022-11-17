package io;
import donnees.Case;
import donnees.TypeChefPompier;
import robots.Robot;

/**C'est un chef pompier classique qui assigne un feu au premier robot pompier trouvé non occupé*/
public class ChefPompierSimple extends ChefPompier{
	
	public ChefPompierSimple(Simulateur simu) {
		super(simu, TypeChefPompier.Master);
	}

	public Robot getRobotPret(Case caseIncendie) {
		for (Robot robot : this.getSimu().getJeuDeDonnees().getRobots()) {
			if (!robot.getOccupied() && (robot.existsPathTo(caseIncendie))) {
				return robot;
			}
		}
		return null;
	}
}
