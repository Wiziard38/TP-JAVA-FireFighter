package io;

public class RobotAPattes extends Robot {
	
	public RobotAPattes(Case position) {
		super(position, 30, 2000,"PATTES");
	}



	public int getVitesse(NatureTerrain nature) {
		return (nature == NatureTerrain.ROCHE ? 30 : 10);
	}

	@Override
	public boolean peutDeplacer(NatureTerrain terrain) {
		return !(terrain == NatureTerrain.EAU);
	}

	@Override
	public String getNameRobot() {
		return "Robot a pattes";
	}
}
