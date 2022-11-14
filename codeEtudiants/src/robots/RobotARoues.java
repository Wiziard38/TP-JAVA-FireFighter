package robots;

import io.Case;
import io.NatureTerrain;

public class RobotARoues extends Robot {
	
	public RobotARoues(Case position) {
		super(position, 80, 5000,"ROUES");
	}
	
	public RobotARoues(Case position, int vitesse) {
		super(position, vitesse, 5000,"ROUES");
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
