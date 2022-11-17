package io;
import robots.Robot;

public abstract class ChefPompier {
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
	
	public abstract Robot getRobotPret(Case caseIncendie);
	
	public DonneesSimulation getJeuDeDonnees() {
		return this.jeuDeDonnees;
	}

	public void start() {
		Incendie incendieATraite = resteFeu();
		while (incendieATraite != null) {
			Robot robotTraite= getRobotPret(incendieATraite.getPosition());
			while(robotTraite == null){
				try {
					Thread.sleep(1000);
				}
				catch (Exception e) {
					System.out.println(e);
				}
				robotTraite= getRobotPret(incendieATraite.getPosition());
			}
			robotTraite.traiteIncendie(simu, incendieATraite);
			incendieATraite = resteFeu();
		}
	}
	
}
