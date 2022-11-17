package evenements;

import io.Carte;
import io.Case;
import io.Direction;
import robots.Robot;

public class Deplacement extends Evenement {
	/**Deplacement est un événement qui permet de faire bouger un robot dans une direction*/
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
		if (carte.voisinExiste(robot.getPosition(), dir)) { //Si le voisin dans la direction dir existe
			Case caseFin = carte.getVoisin(robot.getPosition(), dir); //Le robot change de case
			robot.setPosition(caseFin);
		}
	}

}
