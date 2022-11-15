package robots;


import org.graphstream.graph.Path;

import io.Carte;
import io.Case;
import io.Simulateur;

public abstract class RobotTerrestre extends Robot {
 	
	public RobotTerrestre(Case init_position, int vitesse, long tailleReservoir, String type, Carte carte) {
		super(init_position, vitesse, tailleReservoir, type, carte);
	}
	
	
	@Override
	public Case getClosestWater(Simulateur simulateur, Case currentPos) {
		double min_val = Double.POSITIVE_INFINITY;
		Path best_path = null;
		
		for (Case shoreTile : simulateur.getJeuDeDonnees().getCasesVoisins()) {
			Path path = this.getShortestPath(currentPos, shoreTile);
			double current_time = this.getTimeFromPath(path);
			if (current_time < min_val) {
				min_val = current_time;
				best_path = path;
			}
		}
		
		if (best_path == null) {
			throw new IllegalArgumentException("Aucune case d'eau accessible");
		}
		Object[] array = best_path.peekNode().getAttribute("xy");
		Case caseEau = simulateur.getJeuDeDonnees().getCarte().getCase((int) array[0], (int) array[1]);
		return caseEau;
	}
}
