package robots;

import io.Case;
import io.NatureTerrain;
import io.Simulateur;

import java.util.ArrayList;
import java.util.List;

import org.graphstream.algorithm.Dijkstra;
import org.graphstream.algorithm.Dijkstra.Element;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.Path;

public abstract class Robot {
	private Case position;
	private int vitesse;
	private String type;
	private final double tailleReservoir;
	private double quantiteEau = 0;
	private boolean occupied;

	
	public Robot(Case init_position, int vitesse, double tailleReservoir, String type) {
		if (vitesse < 0) {
			throw new IllegalArgumentException("La vitesse doit etre positive !");
		}

		this.position = init_position;
		this.vitesse = vitesse;
		this.tailleReservoir = tailleReservoir;
		this.type = type;
		
		this.setEauRestante(tailleReservoir);
		
		this.occupied = false;
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

	public Path pathFinding(Case objective, Simulateur simulateur) {
		Graph graph = simulateur.getGraphsRobots().getGraph(this);
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

	public abstract boolean peutDeplacer(NatureTerrain terrain);

	public abstract String getNameRobot();
}