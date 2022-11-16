package io;
import evenements.Evenement;
import robots.Robot;

public class Fini extends Evenement {
	
	Robot robot;
	
	public Fini(Robot robot, long date) {
		super(date);
		this.robot = robot;
	}
	@Override
	public void execute() {
		//System.out.println("Fin de l'ev du robot: "+robot.getNameRobot());
		this.robot.setOccupied(false);
	}

}
