package robots;

import io.Case;
import io.Carte;
import io.NatureTerrain;
import io.Simulateur;
import io.VerserEau;

public class RobotAChenilles extends RobotTerrestre {
	
	public RobotAChenilles(Case position, Carte carte, int vitesse) {
		super(position, vitesse, 2000,"CHENILLES", carte);
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
	public boolean peutDeplacer(NatureTerrain terrain) {
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