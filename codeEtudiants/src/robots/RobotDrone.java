package robots;

import io.Case;
import io.Carte;
import io.NatureTerrain;
import io.Simulateur;

import java.util.Iterator;
import java.util.Set;

import org.graphstream.algorithm.Dijkstra;
import org.graphstream.algorithm.Dijkstra.Element;
import org.graphstream.graph.Graph;

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
	
	@Override
	public double getClosestWater(Simulateur simulateur) {
		double min_val = Double.POSITIVE_INFINITY;
		Set<Case> listWater = simulateur.getJeuDeDonnees().getCasesEaux();
		return 0;
		
		//for (Object case : simulateur.getJeuDeDonnees().getCasesEaux()) {
			
		//}
	}
}
