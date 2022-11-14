package io;
import robots.Robot;

public class ChefPompier {
	private Simulateur simu;
	private DonneesSimulation jeuDeDonnees;
	private Incendie incendiesTraites[];
	
	public ChefPompier(Simulateur simu, DonneesSimulation jeuDeDonnees, Incendie[] incendiesTraites) {
		this.incendiesTraites = incendiesTraites;
		this.jeuDeDonnees = jeuDeDonnees;
		this.simu = simu;
	}
	private Incendie resteFeu() {
		for (int i = 0; i<this.jeuDeDonnees.getIncendies().length; i++) {
			if (this.jeuDeDonnees.getIncendies()[i].getEauNecessaire() != 0) {
				return jeuDeDonnees.getIncendies()[i];
			}
		}
		return null;
	}
	
	private Robot getRobotPret(Case caseIncendie) {
		for (Robot robot : jeuDeDonnees.getRobots()) {
			if (!robot.getOccupied() && robot.pathFinding(caseIncendie, simu) != null) {
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
			robotTraite.goTo(incendieATraite.getPosition(), simu);
		}
	}
}
