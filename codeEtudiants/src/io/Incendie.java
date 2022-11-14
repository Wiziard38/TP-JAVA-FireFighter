package io;

public class Incendie {
	private Case position;
	private double eauNecessaire;
	private boolean traite;
	
	public Incendie(Case pos, double eau) {
		this.eauNecessaire = eau;
		this.position = pos;
		this.traite = false;
	}
	
	public boolean getTraite() {
		return this.traite;
	}
	
	public void setTraite(boolean val) {
		this.traite = val;
	}
	
	public Case getPosition() {
		return this.position;
	}
	
	public double getEauNecessaire() {
		return this.eauNecessaire;
	}
	
	public void setPosition(Case newPos) {
		this.position = newPos;
	}
	
	public void setEauNecessaire(double newQuant) {
		this.eauNecessaire = newQuant;
	}
}
