package io;

public abstract class Robot {
	public abstract Case getPosition();
	public abstract void setPosition();
	public abstract double getVitesse();
	public abstract void deverseEau();
	public abstract void remplirReservoir();
}
