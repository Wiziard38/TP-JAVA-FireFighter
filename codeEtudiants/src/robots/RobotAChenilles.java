package robots;

import donnees.Carte;
import donnees.Case;
import donnees.NatureTerrain;

/**RobotAChenilles est un type particuluer de robot*/
public class RobotAChenilles extends RobotTerrestre {
	
	public RobotAChenilles(Case position, Carte carte, int vitesse) {
		super(position, vitesse, 2000,RobotType.CHENILLES, carte);
	}


	@Override
	public void setVitesse(int vitesse) {
		if (vitesse > 80) {
			throw new IllegalArgumentException("Vitesse trop grande !");
		}
		super.setVitesse(vitesse);
	}

	public int getVitesse(NatureTerrain nature) {
		return (nature == NatureTerrain.FORET ? super.getVitesse() / 2 : super.getVitesse());
	}

	@Override
	public boolean peutSeDeplacer(NatureTerrain terrain) {
		return !(terrain == NatureTerrain.EAU || terrain == NatureTerrain.ROCHE);
	}

	@Override
	public String getNameRobot() {
		return "Robot a chenilles";
	}
	
	@Override
	public long getTempsRemplissage() {
		return 5*60;
	}
	
	@Override
	public long getQuantiteVersementUnitaire() {
		return 100;
	}
	
	@Override
	public long getTempsVersementUnitaire() {
		return 8;
	}

}