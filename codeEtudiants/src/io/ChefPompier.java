package io;
import robots.Robot;
import java.lang.Thread;
public class ChefPompier {
	/**ChefPompier implemente un chef pompier basique qui gère les robots de la manière suivante:
	 * Il donne le premier feu non eteind au premier robot qu'il trouve pas occupé*/
	private Simulateur simu;
	
	public ChefPompier(Simulateur simu) {
		this.simu = simu;
	}
	
	private boolean feuTousEteind() {
		/**feuTousEteind qui nous permet de savoir si tous les feux de la carte sont éteinds */
		for(Incendie incendie: simu.getJeuDeDonnees().getIncendies()) {
			if(incendie.getTraite() != Traitement.eteind) {
				return false;
			}
		}
		return true;
	}

	private Incendie resteFeu() {
		/** resteFeu nous permet de savoir si il reste un feu qui n'est pas encore traité i.e
		 * si aucun robot s'occupe d'éteindre se feu. La fonction retourne un feu si il en reste un non
		 * traité et sinon renvoie null*/
		for (Incendie incendie : simu.getJeuDeDonnees().getIncendies()) {
			if (incendie.getEauNecessaire() != 0 && incendie.getTraite() == Traitement.rien) {
				incendie.setTraite(Traitement.traite);
				return incendie;
			}
		}
		return null;
	}
	
	private Robot getRobotPret(Case caseIncendie) {
		/**getRobotPret nous permet de savoir si il reste un robot n'est pas occupé. Cette fonction
		 * nous renvoie un robot s'il en existe un et null sinon */
		for (Robot robot : simu.getJeuDeDonnees().getRobots()) {
			if (!robot.getOccupied() && (robot.existsPathTo(caseIncendie) || robot.getPosition().equal(caseIncendie))) {
				return robot;
			}
		}
		return null;
	}

	public void start() {
		/** La fonction principal du chef pompier, elle attribue les différents incendies au robot*/
		while(!feuTousEteind()) {
			/*Ce while nous permet de nous assuré que si un feu est resté allumé est plus traité nous
			 le remetons dans le bonne état pour finir de le traité*/
			try {
				Thread.sleep(1000);
			}
			catch(Exception e) {
				System.out.println("oui");
			}
			for (Incendie incendie: this.simu.getJeuDeDonnees().getIncendies()) {
				/* Si les feux n'ont plus besoin d'eaux ils sont éteinds donc on passe leur étét en
				 * "éteinds"
				 * Si un incendie est dans l'état traite mais sans robot qui s'en occupe il y a un
				 * problème et donc on repasse le feu dans l'état "rien" pour s'en occupé ensuite
				 */
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
				/* On prend un incendie non traite un robot non occupé et on lui demande de s'en occuper*/
				robotTraite.traiteIncendie(simu, incendieATraite);
				incendieATraite = resteFeu();
			}
		}
		System.out.println("Tous les incendies sont eteints");
	}
}
