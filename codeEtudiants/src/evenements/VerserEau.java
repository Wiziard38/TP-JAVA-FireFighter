package evenements;
import io.Incendie;
import robots.Robot;

/** Evénement qui permet à un robot de verser une certaine quantité d'eau*/
public class VerserEau extends Evenement {
	
	private Incendie incendie;
	private Robot robot;
	private long eauAVerser;
	
	public VerserEau(Incendie incendie, Robot robot, long quantiteEau, long date) {
		super(date);
		this.incendie = incendie;
		this.robot = robot;
		this.eauAVerser = quantiteEau;
	}
	
	@Override
	public void execute() {
		//On vérifie que le robot est bien sur un incendie
		if (robot.getPosition().equals(this.incendie.getPosition())) {
			this.incendie.setEauNecessaire(Math.max(this.incendie.getEauNecessaire()-this.eauAVerser,0));
			robot.setEauRestante(Math.max(robot.getEauRestante() - this.eauAVerser, 0));
		}
	}
}
