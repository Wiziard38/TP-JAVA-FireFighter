package io;
import robots.Robot;

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
		if (robot.getPosition().equal(this.incendie.getPosition())) {
			// System.out.println(robot.getEauRestante() - this.eauAVerser);
			this.incendie.setEauNecessaire(Math.max(this.incendie.getEauNecessaire()-this.eauAVerser,0));
			robot.setEauRestante(Math.max(robot.getEauRestante() - eauAVerser, 0));
			// System.out.println("quantité necessaire incendie: "+this.incendie.getEauNecessaire()+ " / quantité eau: "+robot.getEauRestante());;
		}
		else {
			System.out.println(robot.getPosition());
			System.out.println(incendie.getPosition());
			System.out.println("perdu");
		}
	}

}
