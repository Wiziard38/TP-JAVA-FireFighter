package robots;

import donnees.Carte;
import donnees.Case;
import donnees.NatureTerrain;

/**RobotAPattes est un type particulier de robot*/
public class RobotAPattes extends RobotTerrestre {
	
	public RobotAPattes(Case position, Carte carte) {
		super(position, 30, Long.MAX_VALUE, RobotType.PATTES, carte);
	}


	@Override
	public int getVitesse(NatureTerrain nature) {
		return (nature == NatureTerrain.ROCHE ? 10 : 30);
	}

	@Override
	public boolean peutSeDeplacer(NatureTerrain terrain) {
		return !(terrain == NatureTerrain.EAU);
	}

	@Override
	public String getNameRobot() {
		return "Robot a pattes";
	}
	
	@Override
	public long getTempsRemplissage() {
		throw new IllegalArgumentException("oulala");
	}
	
	@Override
	public long getQuantiteVersementUnitaire() {
		return 10;
	}
	
	@Override
	public long getTempsVersementUnitaire() {
		return 1;
	}
}
