package robots;


import graph.*;

import io.Carte;
import io.Case;
import io.Simulateur;

public abstract class RobotAerien extends Robot {
	
	public RobotAerien(Case init_position, int vitesse, long tailleReservoir, String type, Carte carte) {
		super(init_position, vitesse, tailleReservoir, type, carte);
	}

	
	@Override
	public Case getClosestWater(Simulateur simulateur, Case currentPos) {	
		double min_val = Long.MAX_VALUE;
		Case closestWater = null;
		
		Dijkstra dijkstra = new Dijkstra();
		dijkstra.init(this.getGraph());
		dijkstra.setSource(currentPos);
		dijkstra.compute();
		
		for (Case waterTile : simulateur.getJeuDeDonnees().getCasesEaux()) {
			
			if (dijkstra.getShortestTime(waterTile) < min_val) {
				min_val = dijkstra.getShortestTime(waterTile);
				closestWater = waterTile;
			}
		}

		if (closestWater == null) {
			throw new IllegalArgumentException("Gros probleme");
		}
		
		return closestWater;
	}
	
}
