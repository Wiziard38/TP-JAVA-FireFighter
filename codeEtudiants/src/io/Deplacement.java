package io;

import robots.Robot;

public class Deplacement extends Evenement {
	private Robot robot;
	private Direction dir;
	private Carte carte;
	
	public Deplacement(Robot robot, Carte carte, Direction dir, long date) {
		super(date);
		this.carte = carte;
		this.robot = robot;
		this.dir = dir;
	}
	
	@Override
	public void execute() {
		if (carte.voisinExiste(robot.getPosition(), dir)) {
			Case caseFin = carte.getVoisin(robot.getPosition(), dir);
			//System.out.println("lig: "+caseFin.getLigne()+" col: "+caseFin.getColonne()+" type: "+caseFin.getNature());
			robot.setPosition(caseFin);
		}
		else {
			//System.out.println("Impossible de se déplacer");
		}
	}

}
