package robots;

import io.Case;

import java.util.Set;

import io.Carte;
import io.NatureTerrain;
import io.Simulateur;

public class RobotARoues extends RobotTerrestre {
	
	public RobotARoues(Case position, Carte carte, int vitesse) {
		super(position, vitesse, 5000, "ROUES", carte);
	}


	@Override
	public boolean peutDeplacer(NatureTerrain terrain) {
		return (terrain == NatureTerrain.TERRAIN_LIBRE | terrain == NatureTerrain.HABITAT);
	}

	@Override
	public String getNameRobot() {
		return "Robot a roues";
	}
}
