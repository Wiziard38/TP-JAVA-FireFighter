package robots;

import io.Carte;
import io.Case;
import io.NatureTerrain;
import io.Simulateur;
import io.Deplacement;
import io.Direction;

import org.graphstream.algorithm.Dijkstra;
import org.graphstream.algorithm.Dijkstra.Element;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.Path;
import org.graphstream.graph.implementations.SingleGraph;

public abstract class Robot {
	private Case position;
	private int vitesse;
	private String type;
	private final double tailleReservoir;
	private double quantiteEau = 0;
	private boolean occupied;
	private Graph mapGraph;

	
	public Robot(Case init_position, int vitesse, double tailleReservoir, String type, Carte carte) {
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
		
	public double getEauRestante() {
		return this.quantiteEau;
	}
	
	public void setEauRestante(double volume) {
		if (volume < 0) {
			throw new IllegalArgumentException("Volume d'eau négatif !");
		}
		this.quantiteEau = volume;
	}

	public double deverseEau(double volume) {
		double EauRestante = this.getEauRestante();

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
	
	private double calculateMeanSpeed(Robot robot, Case firstCell, Case secondCell, int cellSize) {
		int v1 = robot.getVitesse(firstCell.getNature());
		int v2 = robot.getVitesse(secondCell.getNature());
		return (double) (2 * cellSize / (v1 + v2));
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
	
	public Graph getGraph() {
		return this.mapGraph;
	}

	public Path pathFinding(Case objective, Simulateur simulateur) {
		Graph graph = this.getGraph();
		Node start = graph.getNode(String.format("%x %x", this.position.getLigne(), this.position.getColonne()));
		Node end = graph.getNode(String.format("%x %x", objective.getLigne(), objective.getColonne()));
		
		Dijkstra dijkstra = new Dijkstra(Element.EDGE, "result", "time");
		dijkstra.init(graph);
		dijkstra.setSource(start);
		dijkstra.compute();
		
		if (dijkstra.getPathLength(end) == Double.POSITIVE_INFINITY) { // Disconnected nodes
			return null;
		}
		if (!peutDeplacer(objective.getNature())) { // Impossible objective
			return null;
		}

		return dijkstra.getPath(end);
	}
	
	public double getShortestTimePath(Path path) {
		if (path == null) {
			throw new IllegalArgumentException("Path null !"); 
		}
		return path.getPathWeight("time");
	}
	
	public void goTo(Case objective, Simulateur simulateur) {
		Path path = this.pathFinding(objective, simulateur);
		Case current_pos = this.getPosition();
		long current_date = simulateur.getDateSimulation();
		
		for (Edge edge : path.getEachEdge()) {
			Direction dir;
			Object[] array1 = edge.getAttribute("Node1");
			Object[] array2 = edge.getAttribute("Node2");
			Case case1 = simulateur.getJeuDeDonnees().getCarte().getCase((int)array1[0], (int)array1[1]);
			Case case2 = simulateur.getJeuDeDonnees().getCarte().getCase((int)array2[0], (int)array2[1]);
			if (case1.equals(current_pos)) {
				dir = getDirection(case1, case2);
				current_pos = case2;
			} else {
				dir = getDirection(case2, case1);
				current_pos = case1;
			}
						
			double time = (double) edge.getAttribute("time");
			current_date += (long) time;
			
			simulateur.ajouteEvenement(new Deplacement(this, simulateur.getJeuDeDonnees().getCarte(), dir, current_date));
		}
	}
	
	public Direction getDirection(Case origin, Case dest) {
		if (origin.getLigne() == dest.getLigne()) {
			if (origin.getColonne() > dest.getColonne()) {
				return Direction.OUEST;
			}
			return Direction.EST;
		}
		if (origin.getLigne() > dest.getLigne()) {
			return Direction.NORD;
		}
		return Direction.SUD;
	} 

	public abstract boolean peutDeplacer(NatureTerrain terrain);
	public abstract String getNameRobot();
}