package io;
import robots.Robot;

public class ChefPompier {
	private Simulateur simu;
	private DonneesSimulation jeuDeDonnees;
	
	public ChefPompier(Simulateur simu, DonneesSimulation jeuDeDonnees) {
		this.jeuDeDonnees = jeuDeDonnees;
		this.simu = simu;
	}

	private Incendie resteFeu() {
		for (Incendie incendie : jeuDeDonnees.getIncendies()) {
			if (incendie.getEauNecessaire() != 0 && !incendie.getTraite()) {
				incendie.setTraite(true);
				return incendie;
			}
		}
		return null;
	}
	
	private Robot getRobotPret(Case caseIncendie) {
		for (Robot robot : jeuDeDonnees.getRobots()) {
			if (!robot.getOccupied() && (robot.existsPathTo(caseIncendie) || robot.getPosition().equal(caseIncendie))) {
				return robot;
			}
		}
		return null;
	}

	public void start() {
		Incendie incendieATraite = resteFeu();
		while (incendieATraite != null) {
			Robot robotTraite= getRobotPret(incendieATraite.getPosition());
			while(robotTraite == null){
				robotTraite= getRobotPret(incendieATraite.getPosition());
			}
			robotTraite.traiteIncendie(simu, incendieATraite);
			incendieATraite = resteFeu();
		}
	}
}
