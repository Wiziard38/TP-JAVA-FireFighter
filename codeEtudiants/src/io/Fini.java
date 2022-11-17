package io;
import evenements.Evenement;
import robots.Robot;

public class Fini extends Evenement {
	/**Un événement qui s'execute quand un robot à fini de traité un incendie*/
	
	private Robot robot;
	private Incendie incendie;
	
	public Fini(Robot robot, Incendie incendie, long date) {
		super(date);
		this.robot = robot;
		this.incendie = incendie;
	}
	@Override
	public void execute() {
		this.robot.setOccupied(false);
		this.incendie.setRobotQuiTraite(null);
		if(incendie.getEauNecessaire() == 0) {
			incendie.setTraite(Traitement.eteind);
		}
	}
}
