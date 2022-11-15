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
		if (robot.getPosition().equal(this.incendie.getPosition())) {
			System.out.println(robot.getEauRestante() - this.quantiteEau);
			this.incendie.setEauNecessaire(Math.max(this.incendie.getEauNecessaire()-this.quantiteEau,0));
			robot.setEauRestante(Math.max(robot.getEauRestante() - quantiteEau, 0));
			System.out.println("quantité necessaire incendie: "+this.incendie.getEauNecessaire()+ "quantité eau: "+robot.getEauRestante());;
		}
		else {
			System.out.println(robot.getPosition().getLigne() + " "+ robot.getPosition().getColonne());
			System.out.println(incendie.getPosition().getLigne() + " "+ incendie.getPosition().getColonne());
			System.out.println("perdu");
		}
	}

}
