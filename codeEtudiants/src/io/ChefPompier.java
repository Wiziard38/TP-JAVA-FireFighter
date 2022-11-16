package io;
import robots.Robot;

public class ChefPompier {
	private Simulateur simu;
	
	public ChefPompier(Simulateur simu) {
		this.simu = simu;
	}
	
	private boolean feuTousEteind() {
		for(Incendie incendie: simu.getJeuDeDonnees().getIncendies()) {
			if(incendie.getTraite() != Traitement.eteind) {
				return false;
			}
		}
		return true;
	}

	private Incendie resteFeu() {
		for (Incendie incendie : simu.getJeuDeDonnees().getIncendies()) {
			if (incendie.getEauNecessaire() != 0 && incendie.getTraite() == Traitement.rien) {
				incendie.setTraite(Traitement.traite);
				return incendie;
			}
		}
		return null;
	}
	
	private Robot getRobotPret(Case caseIncendie) {
		for (Robot robot : simu.getJeuDeDonnees().getRobots()) {
			if (!robot.getOccupied() && (robot.existsPathTo(caseIncendie) || robot.getPosition().equal(caseIncendie))) {
				return robot;
			}
		}
		return null;
	}

	public void start() {
		while(!feuTousEteind()) {
			for (Incendie incendie: simu.getJeuDeDonnees().getIncendies()) {
				if(incendie.getTraite() == Traitement.traite) {
					incendie.setTraite(Traitement.rien);
				}
			}
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
		System.out.println("fin");
	}
}
