package io;
import robots.Robot;

/**ChefPompier implemente un un chef pompier générale sans stratégie, elle est a définir dans des classes filles*/
public abstract class ChefPompier {
	
	private Simulateur simu;
	private boolean simulationOver;
	private final TypeChefPompier type;
	
	public ChefPompier(Simulateur simu, TypeChefPompier type) {
		this.simu = simu;
		this.simulationOver = false;
		this.type = type;
	}
	
	public TypeChefPompier getType() {
		return this.type;
	}
	
	public Simulateur getSimu() {
		return this.simu;
	}
	/**feuTousEteind qui nous permet de savoir si tous les feux de la carte sont éteinds */
	private boolean feuTousEteind() {
		
		for(Incendie incendie: simu.getJeuDeDonnees().getIncendies()) {
			if(incendie.getTraite() != Traitement.eteind) {
				return false;
			}
		}
		return true;
	}
	
	/** resteFeu nous permet de savoir si il reste un feu qui n'est pas encore traité i.e
	 * si aucun robot s'occupe d'éteindre se feu. La fonction retourne un feu si il en reste un non
	 * traité et sinon renvoie null*/
	private Incendie resteFeu() {
		
		for (Incendie incendie : simu.getJeuDeDonnees().getIncendies()) {
			if (incendie.getEauNecessaire() != 0 && incendie.getTraite() == Traitement.rien) {
				incendie.setTraite(Traitement.traite);
				return incendie;
			}
		}
		return null;
	}

	/** La fonction principal du chef pompier, elle attribue les différents incendies au robot*/
	public void assigneIncendie() {
		if (!feuTousEteind()) {
			/*Ce while nous permet de nous assuré que si un feu est resté allumé est plus traité nous
			 le remetons dans le bonne état pour finir de le traité*/
			
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
			if (incendieATraite != null) {
				Robot robotTraite= getRobotPret(incendieATraite.getPosition());
				if(robotTraite != null) {
					/* On prend un incendie non traite un robot non occupé et on lui demande de s'en occuper*/
					robotTraite.traiteIncendie(simu, incendieATraite);
					incendieATraite = resteFeu();
				}
				
			}
		} else {
			System.out.println("Tous les incendies sont eteints");
			this.simulationOver = true;
		}
	}
	
	public boolean getSimulationOver() {
		return this.simulationOver;
	}
	
	public abstract Robot getRobotPret(Case caseIncendie);
}
