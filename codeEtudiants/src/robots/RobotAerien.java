package robots;


import graph.Dijkstra;

import io.Carte;
import io.Case;
import io.Simulateur;

public abstract class RobotAerien extends Robot {
	/**RobotAerien représente tous les robots qui se déplace dans le ciel*/
	
	public RobotAerien(Case init_position, int vitesse, long tailleReservoir, RobotType type, Carte carte) {
		super(init_position, vitesse, tailleReservoir, type, carte);
	}

	
	@Override
	public Case getClosestWater(Simulateur simulateur, Case currentPos) {
		/**Permet d'avoir la case d'eau la plus proche du robot aérien. On retourne cette case
		 * parce que le robot se recharge sur cette case*/
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
