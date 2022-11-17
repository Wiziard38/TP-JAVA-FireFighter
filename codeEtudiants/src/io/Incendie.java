package io;
import robots.Robot;

public class Incendie {
	private Case position;
	private long eauNecessaire;
	private Traitement traite;
	private Robot robotQuiTraite;
	
	public Incendie(Case pos, long eau) {
		this.eauNecessaire = eau;
		this.position = pos;
		this.traite = Traitement.rien;
		this.robotQuiTraite = null;
	}
	
	public Robot getRobotQuiTraite() {
		return this.robotQuiTraite;
	}
	
	public void setRobotQuiTraite(Robot robot) {
		this.robotQuiTraite = robot;
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
