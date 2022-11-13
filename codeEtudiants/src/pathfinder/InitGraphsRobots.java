package pathfinder;

import io.Case;
import io.Carte;
import robots.*;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.SingleGraph;

public class InitGraphsRobots {
	private Graph graphRobotDrone = null;
	private Graph graphRobotAPattes = null;
	private Graph graphRobotARoues = null;
	private Graph graphRobotAChenilles = null;
	
	public InitGraphsRobots(Carte carte, Robot[] listeRobots) {
		for (Robot robot : listeRobots) {
			if (!existsGraph(robot)) {	
				setGraph(CreateGraph(carte, robot), robot);
			}
		}
	}
	
	
	private Graph CreateGraph(Carte carte, Robot robot) {
		Graph graph = new SingleGraph("graph robot drone");
		int nbLignes = carte.getNbLignes();
		int nbColonnes = carte.getNbColonnes();
		int cellSize = carte.getTailleCase();
		
		for (int index_lin = 0; index_lin < nbLignes; index_lin++) {
			for (int index_col = 0; index_col < nbColonnes; index_col++) {
				
				if (robot.peutDeplacer(carte.getCase(index_lin, index_col).getNature())) {
					String cellName = String.format("%x %x", index_lin, index_col);
					graph.addNode(cellName).setAttribute("xy", index_lin, index_col);
					
					if (index_lin != 0) {
						if (robot.peutDeplacer(carte.getCase(index_lin - 1, index_col).getNature())) {
							double time = calculateMeanSpeed(robot, carte.getCase(index_lin, index_col), carte.getCase(index_lin - 1, index_col), cellSize);
							String cellNorthName = String.format("%x %x", index_lin - 1, index_col);
							graph.addEdge(cellName + " - " + cellNorthName, cellName, cellNorthName).setAttribute("time", time);													
						}
					}
					
					if (index_col != 0) {
						if (robot.peutDeplacer(carte.getCase(index_lin, index_col - 1).getNature())) {
							double time = calculateMeanSpeed(robot, carte.getCase(index_lin, index_col), carte.getCase(index_lin, index_col - 1), cellSize);
							String cellWestName = String.format("%x %x", index_lin, index_col - 1);
							graph.addEdge(cellName + " - " + cellWestName, cellName, cellWestName).setAttribute("time", time);													
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
	
		return graph;
	}
	
	private double calculateMeanSpeed(Robot robot, Case firstCell, Case secondCell, int cellSize) {
		int v1 = robot.getVitesse(firstCell.getNature());
		int v2 = robot.getVitesse(secondCell.getNature());
		return (double) (2 * cellSize / (v1 + v2));
	}
	
	private boolean existsGraph(Robot robot) {
		switch (robot.getNameRobot()) {
		case "Robot a chenilles":
			if (this.graphRobotAChenilles == null) {
				return false;
			}
			return true;
		case "Robot a roues":
			if (this.graphRobotARoues == null) {
				return false;
			}
			return true;
		case "Robot a pattes":
			if (this.graphRobotAPattes == null) {
				return false;
			}
			return true;
		case "Robot drone":
			if (this.graphRobotDrone == null) {
				return false;
			}
			return true;
		default:
			throw new IllegalArgumentException("Robot inconnu !");
		}
	}
	
	public Graph getGraph(Robot robot) {
		switch (robot.getNameRobot()) {
		case "Robot a chenilles":
			return this.graphRobotAChenilles;
		case "Robot a roues":
			return this.graphRobotARoues;
		case "Robot a pattes":
			return this.graphRobotAPattes;
		case "Robot drone":
			return this.graphRobotDrone;
		default:
			throw new IllegalArgumentException("Robot inconnu !");
		}
	}
	
	private void setGraph(Graph g, Robot robot) {
		switch (robot.getNameRobot()) {
		case "Robot a chenilles":
			this.graphRobotAChenilles = g;
			break;
		case "Robot a roues":
			this.graphRobotARoues = g;
			break;
		case "Robot a pattes":
			this.graphRobotAPattes = g;
			break;
		case "Robot drone":
			this.graphRobotDrone = g;
			break;
		default:
			throw new IllegalArgumentException("Robot inconnu !");
		}
	}

}
