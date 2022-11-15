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
				return incendie;
			}
		}
		return null;
	}
	
	private Robot getRobotPret(Case caseIncendie) {
		for (Robot robot : jeuDeDonnees.getRobots()) {
			if (!robot.getOccupied() && (robot.pathFinding(caseIncendie, simu) != null || robot.getPosition().equal(caseIncendie))) {
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
			switch(robotTraite.getDernierEventType()) {
			case Debut:
				robotTraite.goTo(incendieATraite.getPosition(), simu, (long)robotTraite.getEauRestante());
				System.out.println("Debut de l'oppération pour: "+robotTraite.getNameRobot());
				incendieATraite.setTraite(true);
				break;
			case Deplacement:
				if(robotTraite.getEauRestante() != 0) {
				System.out.println("go deverser de l'eau pour: "+robotTraite.getNameRobot());
				robotTraite.deverserEau(simu, (long)robotTraite.getEauRestante(), simu.getDateSimulation()+30);
				}
				break;
			default:
				System.out.println("go se déplacer pour: "+robotTraite.getNameRobot());
				robotTraite.goTo(incendieATraite.getPosition(), simu, (long)robotTraite.getEauRestante());
				incendieATraite.setTraite(true);
				break;
			}
			incendieATraite = resteFeu();
		}
	}
	
}
