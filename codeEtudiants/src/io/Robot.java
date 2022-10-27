package io;

public abstract class Robot {
	private Case position;
	private boolean accesTerrains[] = new boolean[5];
	private int tailleReservoir;
	private int vitesseRemplissage;
	private boolean remplitAdjacentEau;
	private int interventionUnitaire;
	private int quantiteEau = 0;
	
	public Robot(Case position, boolean accesTerrains[], int tailleReservoir, int vitesseRemplissage, boolean remplitAdjacentEau, int interventionUnitaire) {
		this.position = position;
		this.accesTerrains = accesTerrains;
		this.tailleReservoir = tailleReservoir;
		this.vitesseRemplissage = vitesseRemplissage;
		this.remplitAdjacentEau = remplitAdjacentEau;
		this.interventionUnitaire = interventionUnitaire;;
	}
	
	public Case getPosition() {
		return this.position;
	}
	
	public void setPosition(Case newPosition) {
		this.position = newPosition;
	}
	
	public int getEau() {
		return this.quantiteEau;
	}
	
	public void setEau(int volume) {
		this.quantiteEau = volume;
	}
	
	public abstract double getVitesse(NatureTerrain terrain);
	
	public void deverseEau(int volume) {
		this.quantiteEau -= volume;
	}
	
	public void remplirReservoir() {
		this.quantiteEau = tailleReservoir;
	}
}


class RobotDrone extends Robot {
	private int vitesse;
	private static boolean[] acces = {true, true, true, true, true};
	
	public RobotDrone(Case position) {
		super(position, acces, 10000, 30, false, 30);
		this.vitesse = 100;
	}
	
	public RobotDrone(Case position, int vitesse) {
		super(position, acces, 10000, 30, false, 30);
		this.vitesse = vitesse;
	}

	public double getVitesse(NatureTerrain terrain) {
		return this.vitesse;
	}
}













