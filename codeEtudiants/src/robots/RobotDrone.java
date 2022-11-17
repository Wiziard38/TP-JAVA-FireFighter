package robots;

import io.Case;
import io.Carte;
import io.NatureTerrain;

public class RobotDrone extends RobotAerien {
	/**RobotDrone est un type particulier de robot*/
	public RobotDrone(Case position, Carte carte, int vitesse) {
		super(position, vitesse, 10000, RobotType.DRONE, carte);
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
	
	@Override
	public long getTempsRemplissage() {
		return 30*60;
	}
	
	@Override
	public long getQuantiteVersementUnitaire() {
		return this.getTailleReservoir();
	}
	
	@Override
	public long getTempsVersementUnitaire() {
		return 30;
	}
}
