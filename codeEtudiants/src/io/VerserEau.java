package io;
import robots.Robot;

public class VerserEau extends Evenement {
	private Incendie incendie;
	private Robot robot;
	long quantiteEau;
	
	public VerserEau(Incendie incendie, Robot robot, long eau, long date) {
		super(date);
		this.incendie = incendie;
		this.robot = robot;
		this.quantiteEau = eau;
	}
	
	@Override
	public void execute() {
		System.out.println(robot.getPosition().getLigne());
		System.out.println(this.incendie.getPosition().getLigne());
		if (robot.getPosition().equal(this.incendie.getPosition())) {
			System.out.println(robot.getEauRestante() - this.quantiteEau);
			this.incendie.setEauNecessaire(Math.max(this.incendie.getEauNecessaire()-this.quantiteEau,0));
			robot.deverseEau(quantiteEau);
			System.out.println("quantité necessaire incendie: "+this.incendie.getEauNecessaire()+ "quantité eau: "+robot.getEauRestante());;
		}

	}

}
