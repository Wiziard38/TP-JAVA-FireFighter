package robots;

import io.Case;
import io.Carte;
import io.NatureTerrain;

public class RobotDrone extends Robot {
	
	public RobotDrone(Case position, Carte carte, int vitesse) {
		super(position, vitesse, 10000,"DRONE", carte);
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
		return "Robot drone";
	}
}
