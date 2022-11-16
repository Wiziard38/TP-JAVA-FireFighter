package robots;


import org.graphstream.graph.Path;

import io.Carte;
import io.Case;
import io.Simulateur;

public abstract class RobotAerien extends Robot {
	
	public RobotAerien(Case init_position, int vitesse, long tailleReservoir, String type, Carte carte) {
		super(init_position, vitesse, tailleReservoir, type, carte);
	}

	
	@Override
	public Case getClosestWater(Simulateur simulateur, Case currentPos) {	
		double min_val = Double.POSITIVE_INFINITY;
		Path best_path = null;
		
		for (Case waterTile : simulateur.getJeuDeDonnees().getCasesEaux()) {
			Path path = this.getShortestPath(currentPos, waterTile);
			double current_time = this.getTimeFromPath(path);
			if (current_time < min_val) {
				min_val = current_time;
				best_path = path;
			}
		}

		if (best_path == null) {
			throw new IllegalArgumentException("Gros probleme");
		}
		Object[] array = best_path.peekNode().getAttribute("xy");
		Case caseEau = simulateur.getJeuDeDonnees().getCarte().getCase((int) array[0], (int) array[1]);
		return caseEau;
	}
	
}
