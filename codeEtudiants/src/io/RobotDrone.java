package io;

public class RobotDrone extends Robot {
	
	public RobotDrone(Case position) {
		super(position, 100, 10000,"DRONE");
	}
	
	public RobotDrone(Case position, int vitesse) {
		super(position, vitesse, 10000,"DRONE");
	}

	@Override
	public void setVitesse(int vitesse) {
		if (vitesse > 150) {
			throw new IllegalArgumentException("Vitesse trop grande !");
		}
		super.setVitesse(vitesse);
	}

	@Override
	public boolean peutDeplacer(NatureTerrain terrain) {
		return true;
	}

	@Override
	public String getNameRobot() {
		return "Drone";
	}
}
