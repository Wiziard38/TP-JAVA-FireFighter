package io;

public class Incendie {
	private Case position;
	private long eauNecessaire;
	private boolean traite;
	
	public Incendie(Case pos, long eau) {
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
	
	public long getEauNecessaire() {
		return this.eauNecessaire;
	}
	
	public void setPosition(Case newPos) {
		this.position = newPos;
	}
	
	public void setEauNecessaire(long newQuant) {
		this.eauNecessaire = newQuant;
	}
}
