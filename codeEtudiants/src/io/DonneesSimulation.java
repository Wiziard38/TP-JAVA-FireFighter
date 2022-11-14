package io;

import robots.Robot;

import java.util.HashSet;
import java.util.Set;

public class DonneesSimulation {
	private Carte carte;
	private Incendie[] incendies;
	private Robot[] robots;
	private Set<Case> eaux = new HashSet<Case>();
	
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
		return this.eaux;
	}
	
	public void initCasesEaux() {
		for (int index_lin = 0; index_lin < carte.getNbColonnes(); index_lin++) {
			for (int index_col = 0; index_col < carte.getNbColonnes(); index_col++) {
				if (carte.getCase(index_lin, index_col).getNature() == NatureTerrain.EAU) {
					this.eaux.add(carte.getCase(index_lin, index_col));
				}
			}	
		}
	}
}
