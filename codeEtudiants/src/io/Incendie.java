package io;

public class Incendie {
	private Case position;
	private double eauNecessaire;
	
	public Case getPosition() {
		return this.getPosition();
	}
	
	public double getEauNecessaire() {
		return this.eauNecessaire;
	}
	
	public Incendie(Case pos, double eau) {
		this.eauNecessaire = eau;
		this.position = pos;
	}
	
	public void setPosition(Case newPos) {
		this.position = newPos;
	}
	
	public void setEauNecessaire(double newQuant) {
		this.eauNecessaire = newQuant;
	}
}
