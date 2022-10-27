package io;

public class DonneesSimulation {
	private Carte carte;
	private Incendie[] incendies;
	private Robot[] robots;
	
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
	}
	public Carte getCarte() {
		return this.carte;
	}
	
	public Incendie getIncendie(Case pos) {
		for (int i = 0; i < this.incendies.length; i++) {
			if(incendies[i].getPosition().equals(pos)) {
				return this.incendies[i];
			}
		}
		return incendies[0];
	}
}
