package io;

import robots.Robot;
import java.util.HashSet;
import java.util.Set;

/**DonneesSimulation regroupe toutes les informations nécessaires pour la simulation:
 * la carte, les incendies, les robots, les cases de type eau et leurs cases voisines*/
public class DonneesSimulation {
	
	private Carte carte;
	private Incendie[] incendies;
	private Robot[] robots;
	private Set<Case> listEaux = new HashSet<Case>();
	private Set<Case> listVoisins = new HashSet<Case>();

	
	public DonneesSimulation(Carte carte, int nbIncendie, Incendie incendies[], int nbRobot, Robot robots[] ) {
		this.carte = carte;
		this.incendies = new Incendie[nbIncendie];
		this.robots = new Robot[nbRobot];
		for(int i = 0; i < nbIncendie; i++) {
			this.incendies[i] = incendies[i];
		}
		for(int i = 0; i < nbRobot; i++) {
			this.robots[i] = robots[i];
		}
		initCasesEaux();
		initCasesVoisins();
	}
	
	public Carte getCarte() {
		return this.carte;
	}
	public Incendie[] getIncendies() {
		return this.incendies;
	}
	public Robot[] getRobots() {
		return this.robots;
	}
	public Incendie getIncendie(Case pos) {
		for (int i = 0; i < this.incendies.length; i++) {
			if(incendies[i].getPosition().equals(pos)) {
				return this.incendies[i];
			}
		}
		return incendies[0];
	}
	
	public Set<Case> getCasesEaux() {
		return this.listEaux;
	}
	
	public Set<Case> getCasesVoisins() {
		return this.listVoisins;
	}
	
	/**initCasesEaux ajotue toutes les cases de type eau dans this.listEaux*/
	public void initCasesEaux() {
		
		for (int index_lin = 0; index_lin < carte.getNbColonnes(); index_lin++) {
			for (int index_col = 0; index_col < carte.getNbColonnes(); index_col++) {
				if (carte.getCase(index_lin, index_col).getNature() == NatureTerrain.EAU) {
					this.listEaux.add(carte.getCase(index_lin, index_col));
				}
			}	
		}
	}
	
	/**initCasesVoisins ajoute toutes les cases voisines aux cases eaux dans this.listVoisins*/
	public void initCasesVoisins() {
		
		for (Case waterTile : this.getCasesEaux()) {
			for (Direction dir : Direction.values()) {
				if (carte.voisinExiste(waterTile, dir) && carte.getVoisin(waterTile, dir).getNature() != NatureTerrain.EAU) {
					listVoisins.add(this.carte.getVoisin(waterTile, dir));
				}
			}
		}
	}
}
