package io;

public class Incendie {
	private Case position;
	private long eauNecessaire;
	private Traitement traite;
	
	public Incendie(Case pos, long eau) {
		this.eauNecessaire = eau;
		this.position = pos;
		this.traite = Traitement.rien;
	}
	
	public Traitement getTraite() {
		return this.traite ;
	}
	public void setTraite(Traitement val) {
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
