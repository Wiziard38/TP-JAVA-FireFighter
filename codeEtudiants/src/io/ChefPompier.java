package io;
import robots.Robot;
import java.lang.Thread;
public class ChefPompier {
	private Simulateur simu;
	private DonneesSimulation jeuDeDonnees;
	
	public ChefPompier(Simulateur simu, DonneesSimulation jeuDeDonnees) {
		this.jeuDeDonnees = jeuDeDonnees;
		this.simu = simu;
	}
	
	private boolean feuTousEteind() {
		for(Incendie incendie: jeuDeDonnees.getIncendies()) {
			if(incendie.getTraite() != Traitement.eteind) {
				return false;
			}
		}
		return true;
	}

	private Incendie resteFeu() {
		for (Incendie incendie : jeuDeDonnees.getIncendies()) {
			if (incendie.getEauNecessaire() != 0 && incendie.getTraite() == Traitement.rien) {
				incendie.setTraite(Traitement.traite);
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
		while(!feuTousEteind()) {
			System.out.println("oui");
			try {
				Thread.sleep(1000);
			}
			catch(Exception e) {
				System.out.println("oui");
			}
			for (Incendie incendie: jeuDeDonnees.getIncendies()) {
				if (incendie.getEauNecessaire() == 0) {
					incendie.setTraite(Traitement.eteind);
				}
				if(incendie.getTraite() == Traitement.traite && incendie.getRobotQuiTraite() == null) {
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
	}	
}
