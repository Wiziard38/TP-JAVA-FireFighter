package robots;

import io.*;

import org.graphstream.algorithm.Dijkstra;
import org.graphstream.algorithm.Dijkstra.Element;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.Path;
import org.graphstream.graph.implementations.SingleGraph;
import io.VerserEau;
import io.RemplissageReservoir;
import io.Fini;
import io.TypeEvent;

public abstract class Robot {
	private Case position;
	private int vitesse;
	private String type;
	private final long tailleReservoir;
	private long quantiteEau = 0;
	private boolean occupied;
	private Graph mapGraph;
	private TypeEvent dernierEventType = TypeEvent.Debut;
	
	public Robot(Case init_position, int vitesse, long tailleReservoir, String type, Carte carte) {
		if (vitesse < 0) {
			throw new IllegalArgumentException("La vitesse doit etre positive !");
		}

		this.position = init_position;
		this.vitesse = vitesse;
		this.tailleReservoir = tailleReservoir;
		this.type = type;
		
		this.setEauRestante(tailleReservoir);
		
		this.occupied = false;
		this.initGraph(carte);
	}
	
	public TypeEvent getDernierEventType() {
		return this.dernierEventType;
	}
	
	public void setDernierEventType(TypeEvent s) {
		this.dernierEventType = s;
	}
	
	public long getTailleReservoir() {
		return this.tailleReservoir;
	}
	
	public String getType(){
		return this.type;
	}
	
	public Case getPosition() {
		return this.position;
	}
	
	public void setPosition(Case newPosition) {
		if (!this.peutDeplacer(newPosition.getNature())) {
			System.out.println("Impossible de se déplacer ici!");
		}
		else {
			this.position = newPosition;
		}
	}
		
	public long getEauRestante() {
		return this.quantiteEau;
	}
	
	public void setEauRestante(long volume) {
		if (volume < 0) {
			throw new IllegalArgumentException("Volume d'eau négatif !");
		}
		this.quantiteEau = volume;
	}

	public double setEau(long volume) {
		long EauRestante = this.getEauRestante();

		if (EauRestante < volume) {
			this.setEauRestante(0);
			return EauRestante;
		}
		this.setEauRestante(EauRestante-volume);
		return volume;
	}

	public void setVitesse(int vitesse) {
		if (vitesse < 0) {
			throw new IllegalArgumentException("Vitesse négative !");
		}
		this.vitesse = vitesse;
	}

	public int getVitesse(NatureTerrain terrain) {
		return this.vitesse;
	}

	public int getVitesse() {
		return this.vitesse;
	}
	
	public boolean getOccupied() {
		return this.occupied;
	}
	
	public void setOccupied(boolean state) {
		this.occupied = state;
	}
	
	private void initGraph(Carte carte) {
		Graph graph = new SingleGraph("graph");
		int nbLignes = carte.getNbLignes();
		int nbColonnes = carte.getNbColonnes();
		int cellSize = carte.getTailleCase();
		
		for (int index_col = 0; index_col < nbColonnes; index_col++) {
			for (int index_lin = 0; index_lin < nbLignes; index_lin++) {
				
				if (this.peutDeplacer(carte.getCase(index_lin, index_col).getNature())) {
					String cellName = String.format("%x %x", index_lin, index_col);
					graph.addNode(cellName).setAttribute("xy", index_lin, index_col);
					
					if (index_lin != 0) {
						if (this.peutDeplacer(carte.getCase(index_lin - 1, index_col).getNature())) {
							double time = calculateMeanSpeed(this, carte.getCase(index_lin, index_col), carte.getCase(index_lin - 1, index_col), cellSize);
							String cellNorthName = String.format("%x %x", index_lin - 1, index_col);
							Edge e = graph.addEdge(cellName + " - " + cellNorthName, cellName, cellNorthName);
							e.setAttribute("time", time);
							e.setAttribute("Node1", index_lin, index_col);
							e.setAttribute("Node2", index_lin - 1, index_col);
						}
					}
					
					if (index_col != 0) {
						if (this.peutDeplacer(carte.getCase(index_lin, index_col - 1).getNature())) {
							double time = calculateMeanSpeed(this, carte.getCase(index_lin, index_col), carte.getCase(index_lin, index_col - 1), cellSize);
							String cellWestName = String.format("%x %x", index_lin, index_col - 1);
							Edge e = graph.addEdge(cellName + " - " + cellWestName, cellName, cellWestName);
							e.setAttribute("time", time);
							e.setAttribute("Node1", index_lin, index_col);
							e.setAttribute("Node2", index_lin, index_col - 1);
						}
					}
				}
			}
		}
		
		for (Node n:graph.getEachNode()) {
			n.setAttribute("label", String.format("(%x;%x)", n.getAttribute("xy")));
		}
		for (Edge e:graph.getEachEdge()) {
			e.setAttribute("label", "" + (double) e.getNumber("time"));
		}
	
		this.mapGraph = graph;
	}
	
	private double calculateMeanSpeed(Robot robot, Case firstCell, Case secondCell, int cellSize) {
		int v1 = robot.getVitesse(firstCell.getNature());
		int v2 = robot.getVitesse(secondCell.getNature());
		return (double) (2 * cellSize / (v1 + v2));
	}

	public Graph getGraph() {
		return this.mapGraph;
	}

	

	public Path getShortestPath(Case position, Case objective) {
		Graph graph = this.getGraph();
		Node start = graph.getNode(String.format("%x %x", position.getLigne(), position.getColonne()));
		Node end = graph.getNode(String.format("%x %x", objective.getLigne(), objective.getColonne()));
		
		Dijkstra dijkstra = new Dijkstra(Element.EDGE, "result", "time");
		dijkstra.init(graph);
		dijkstra.setSource(start);
		dijkstra.compute();
		if (end == null) {
			return null;
		}
		if (dijkstra.getPathLength(end) == Double.POSITIVE_INFINITY) { // Disconnected nodes
			return null;
		}
		if (!peutDeplacer(objective.getNature())) { // Impossible objective
			return null;
		}

		return dijkstra.getPath(end);
	}
	
	public boolean existsPathTo(Case objective) {
		return (getShortestPath(this.getPosition(), objective) != null);
	}

	public double getTimeFromPath(Path path) {
		if (path == null) {
			return Double.POSITIVE_INFINITY; 
		}
		return path.getPathWeight("time");
	}
	
	public long goTo(Case objective, Simulateur simulateur, long dateDebut) {
		Path path = this.getShortestPath(this.getPosition(), objective);
		return execPath(path, this.getPosition(), simulateur, dateDebut);
	}
	
	private long execPath(Path shortestPath, Case currentPos, Simulateur simulateur, long dateDebut) {
		Case current_pos = currentPos;
		long current_date = dateDebut;
		
		for (Edge edge : shortestPath.getEachEdge()) {
			Direction dir;
			Object[] array1 = edge.getAttribute("Node1");
			Object[] array2 = edge.getAttribute("Node2");
			Case case1 = simulateur.getJeuDeDonnees().getCarte().getCase((int)array1[0], (int)array1[1]);
			Case case2 = simulateur.getJeuDeDonnees().getCarte().getCase((int)array2[0], (int)array2[1]);
			if (case1.equals(current_pos)) {
				dir = simulateur.getJeuDeDonnees().getCarte().getDirection(case1, case2);
				current_pos = case2;
			} else {
				dir = simulateur.getJeuDeDonnees().getCarte().getDirection(case2, case1);
				current_pos = case1;
			}
						
			double time = (double) edge.getAttribute("time");
			current_date += (long) time;
			
			simulateur.ajouteEvenement(new Deplacement(this, simulateur.getJeuDeDonnees().getCarte(), dir, current_date));
		}
		return current_date;
	}

	public long deverserEau(Incendie incendie, long eauAVerser, Simulateur simulateur, long dateDebut) {

		long dateFin = dateDebut;
		dateFin += (eauAVerser % this.getQuantiteVersementUnitaire() == 0) ? (eauAVerser/this.getQuantiteVersementUnitaire()) * this.getTempsVersementUnitaire() : (eauAVerser / this.getQuantiteVersementUnitaire() + 1) * this.getTempsVersementUnitaire();

		VerserEau event = new VerserEau(incendie, this, eauAVerser, dateFin);
		simulateur.ajouteEvenement(event);
		return dateFin;
	}
	
	public long rechargerEau(Simulateur simu, long dateDebut, Case currentPos, Case waterPos) {
		long currentDate = dateDebut;
		
		// Aller a l'eau
		currentDate = this.execPath(this.getShortestPath(currentPos, waterPos), currentPos, simu, currentDate);
		
		// Se remplir
		currentDate += this.getTempsRemplissage();
		RemplissageReservoir event = new RemplissageReservoir(simu.getJeuDeDonnees().getCarte(), this, this.tailleReservoir, currentDate);
		simu.ajouteEvenement(event);
		
		// Revenir a l'incendie
		currentDate = this.execPath(this.getShortestPath(waterPos, currentPos), waterPos, simu, currentDate);
		
		return currentDate;
	}
	
	public void traiteIncendie(Simulateur simulateur, Incendie incendie) {
		this.setOccupied(true);
		incendie.setRobotQuiTraite(this);
		long currentDate = simulateur.getDateSimulation();
		long eauIncendie = incendie.getEauNecessaire();
		long eauReservoir = this.getEauRestante();
		Case positionClosestWater = null;
		
		// Deplacement jusqu'a l'incendie
		currentDate = this.goTo(incendie.getPosition(), simulateur, currentDate);
		
		// Tant que il reste du feu
		while (eauIncendie > 0) {
			
			// Si le reservoir est vide
			if (eauReservoir == 0) {
				if (positionClosestWater == null ) {
					positionClosestWater = this.getClosestWater(simulateur, incendie.getPosition());
				}
				currentDate = this.rechargerEau(simulateur, currentDate, incendie.getPosition(), positionClosestWater);
				eauReservoir = this.tailleReservoir;
			}
			else {
				if (eauIncendie > eauReservoir) {
					currentDate = (long) this.deverserEau(incendie, eauReservoir, simulateur, currentDate);
					eauIncendie -= eauReservoir;
					eauReservoir = 0;
				} else {
					currentDate = (long) this.deverserEau(incendie, eauIncendie, simulateur, currentDate);
					eauReservoir -= eauIncendie;
					eauIncendie = 0;
				}
			}
		}
		simulateur.ajouteEvenement(new Fini(this, incendie, currentDate));
	}

	public long getVraieEauVersee(long eauSouhaitee) {
		long eauReelle;
		long versementUnitaire = this.getQuantiteVersementUnitaire();
		if (eauSouhaitee % versementUnitaire == 0) {
			eauReelle = eauSouhaitee;
		} else {
			eauReelle = (eauSouhaitee/versementUnitaire + 1) * versementUnitaire;
			eauReelle = (eauReelle > this.getEauRestante()) ? this.getEauRestante() : eauReelle;
		}
		return eauReelle;
	}
	
	public abstract long getTempsVersementUnitaire();
	public abstract long getQuantiteVersementUnitaire();
	public abstract long getTempsRemplissage();
	public abstract Case getClosestWater(Simulateur simulateur, Case currentPos);
	public abstract boolean peutDeplacer(NatureTerrain terrain);
	public abstract String getNameRobot();
}