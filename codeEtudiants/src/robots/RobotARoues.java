package robots;

import io.Case;
import io.Carte;
import io.NatureTerrain;


public class RobotARoues extends RobotTerrestre {
	/**RobotARoues est un type particulier de robot*/
	public RobotARoues(Case position, Carte carte, int vitesse) {
		super(position, vitesse, 5000, RobotType.ROUES, carte);
	}


	@Override
	public boolean peutDeplacer(NatureTerrain terrain) {
		return (terrain == NatureTerrain.TERRAIN_LIBRE | terrain == NatureTerrain.HABITAT);
	}

	@Override
	public String getNameRobot() {
		return "Robot a roues";
	}
	
	@Override
	public long getTempsRemplissage() {
		return 10*60;
	}
	
	@Override
	public long getQuantiteVersementUnitaire() {
		return 100;
	}
	
	@Override
	public long getTempsVersementUnitaire() {
		return 5;
	}
}
