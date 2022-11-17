package robots;


import graph.Dijkstra;

import io.Carte;
import io.Case;
import io.Simulateur;

public abstract class RobotTerrestre extends Robot {
 	/**Robot terrestre est un type particulier de robots qui se déplacent sur terre*/
	public RobotTerrestre(Case init_position, int vitesse, long tailleReservoir, RobotType type, Carte carte) {
		super(init_position, vitesse, tailleReservoir, type, carte);
	}
	
	
	@Override
	public Case getClosestWater(Simulateur simulateur, Case currentPos) {
		/**getColsestWater cherche la case la plus proche de se robot où il peut se rechercher i.e
		 * une case terrestre à coté de l'eau qu'ele renvoie*/
		double min_val = Long.MAX_VALUE;
		Case closestShore = null;
		
		Dijkstra dijkstra = new Dijkstra();
		dijkstra.init(this.getGraph());
		dijkstra.setSource(currentPos);
		dijkstra.compute();
		
		for (Case shoreTile : simulateur.getJeuDeDonnees().getCasesVoisins()) {
			
			if (dijkstra.getShortestTime(shoreTile) < min_val) {
				min_val = dijkstra.getShortestTime(shoreTile);
				closestShore = shoreTile;
			}
		}

		if (closestShore == null) {
			return null;
		}
		
		return closestShore;
	}
}
