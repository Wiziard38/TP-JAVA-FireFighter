package evenements;

import donnees.Incendie;
import donnees.TraitementIncendieEtat;
import robots.Robot;

/**Un événement qui s'execute quand un robot à fini de traité un incendie*/
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
		this.robot.setOccupied(false);
		this.incendie.setRobotQuiTraite(null);
		if(incendie.getEauNecessaire() == 0) {
			incendie.setTraite(TraitementIncendieEtat.eteind);
		}
	}
}
