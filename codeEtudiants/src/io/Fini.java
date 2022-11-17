package io;
import robots.Robot;

public class Fini extends Evenement {
	
	private Robot robot;
	private Incendie incendie;
	
	public Fini(Robot robot, Incendie incendie, long date) {
		super(date);
		this.robot = robot;
		this.incendie = incendie;
	}
	@Override
	public void execute() {
		//System.out.println("Fin de l'ev du robot: "+robot.getNameRobot());
		this.robot.setOccupied(false);
		this.incendie.setRobotQuiTraite(null);
	}
}
