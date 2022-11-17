package robots;

import io.*;
import graph.*;

import java.util.List;

import evenements.Deplacement;
import evenements.RemplissageReservoir;
import evenements.VerserEau;


public abstract class Robot {
	/**Classe mère de tous les différents types de robot, elle rassemble tout ce qui est 
	 * commun à un robot*/
	private final Case positionRestart;
	private Case position;
	private int vitesse;
	private final RobotType type;
	private final long tailleReservoir;
	private long quantiteEau = 0;
	private boolean occupied;
	private final Graph mapGraph = new Graph("my graph");
	
	public Robot(Case init_position, int vitesse, long tailleReservoir, RobotType type, Carte carte) {
		if (vitesse < 0) {
			throw new IllegalArgumentException("La vitesse doit etre positive !");
		}

		this.position = init_position;
		this.positionRestart = this.position;
		this.vitesse = vitesse;
		this.tailleReservoir = tailleReservoir;
		this.type = type;
		
		this.setEauRestante(tailleReservoir);
		
		this.occupied = false;
		this.initGraph(carte, mapGraph);
	}
	
	public long getTailleReservoir() {
		return this.tailleReservoir;
	}
	
	public RobotType getType(){
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
	
	private void initGraph(Carte carte, Graph mapGraph) {
		/**Fonction qui initialise le graph du robot. Le graph est composé des cases où il peut se rendre
		 * comme noeuds et les noeuds sont reliés par une branche si le robot peut passer de l'une à 
		 * l'autre. Chacune des branches possède un poid correspondant au temps pour passer d'une case
		 * à l'autre*/
		int nbLignes = carte.getNbLignes();
		int nbColonnes = carte.getNbColonnes();
		int cellSize = carte.getTailleCase();
		
		for (int index_col = 0; index_col < nbColonnes; index_col++) {
			for (int index_lin = 0; index_lin < nbLignes; index_lin++) {
				
				if (this.peutDeplacer(carte.getCase(index_lin, index_col).getNature())) {
					Node current = new Node(carte.getCase(index_lin, index_col));
					mapGraph.addNode(current);
					
					if (index_lin != 0) {
						if (this.peutDeplacer(carte.getCase(index_lin - 1, index_col).getNature())) {
							double timeNord = calculateMeanSpeed(this, carte.getCase(index_lin, index_col), carte.getCase(index_lin - 1, index_col), cellSize);
							
							Node voisinNord = mapGraph.getNodeFromCase(carte.getCase(index_lin - 1, index_col));
							mapGraph.addEdge(voisinNord, current, timeNord);
						}
					}
					
					if (index_col != 0) {
						if (this.peutDeplacer(carte.getCase(index_lin, index_col - 1).getNature())) {
							double timeOuest = calculateMeanSpeed(this, carte.getCase(index_lin, index_col), carte.getCase(index_lin, index_col - 1), cellSize);
							
							Node voisinOuest = mapGraph.getNodeFromCase(carte.getCase(index_lin, index_col - 1));
							mapGraph.addEdge(voisinOuest, current, timeOuest);
						}
					}
				}
			}
		}
	}
	
	private double calculateMeanSpeed(Robot robot, Case firstCell, Case secondCell, int cellSize) {
		/**Calcule la vitesse du robot en fonction du terrain*/
		int v1 = robot.getVitesse(firstCell.getNature());
		int v2 = robot.getVitesse(secondCell.getNature());
		return (double) (2 * cellSize / (v1 + v2));
	}

	public Graph getGraph() {
		return this.mapGraph;
	}

	public void RestartPosition() {
		this.setPosition(this.positionRestart);
	}
	

	public Path getShortestPath(Case position, Case destination) {
		/**Calcule le plus court chemin de la case position à la case destination est reoturne ce
		 * chemin*/
		Graph graph = this.getGraph();
		
		Dijkstra dijkstra = new Dijkstra();
		dijkstra.init(graph);
		dijkstra.setSource(position);
		dijkstra.compute();

		if (!peutDeplacer(destination.getNature())) { // Impossible objective
			return null;
		}

		if (dijkstra.getShortestTime(destination) == Long.MAX_VALUE) { // Disconnected nodes
			return null;
		}

		return dijkstra.getShortestPath(destination);
	}
	
	public boolean existsPathTo(Case objective) {
		/**existsPathTo regarde si le robot peut se rendre sur la case objective et renvoie true s'il
		 * peut false sinon*/
		return (getShortestPath(this.getPosition(), objective) != null);
	}

	public double getTimeFromPath(Path path) {
		/**getTimeFromPath caclule le temps que le robot mettera pour faire le chemin path*/
		if (path == null) {
			return Long.MAX_VALUE; 
		}
		return path.getPathLength();
	}
	
	public long goTo(Case objective, Simulateur simulateur, long dateDebut) {
		/**goTo calcule le chemin le plus court pour que ce robot se déplace sur la case objective
		 * et créer la sutie d'événement nécessaire pour s'y rendre */
		Path path = this.getShortestPath(this.getPosition(), objective);
		return execPath(path, this.getPosition(), simulateur, dateDebut);
	}
	
	private long execPath(Path shortestPath, Case currentPosition, Simulateur simulateur, long dateDebut) {
		/**execPath gènere la suite d'événement nécessaire pour faire le chemin path depuis la case
		 * currentPosition*/
		Node currentNode = this.mapGraph.getNodeFromCase(currentPosition) ;
		long current_date = dateDebut;
		
		List<Node> listNodes = shortestPath.getPath();

		if (!listNodes.get(0).equals(currentNode)) {
			throw new IllegalArgumentException("Path not beginning from actual position");
		}
		listNodes.remove(0);
		
		for (Node nextNode : listNodes) {
			Direction dir;
			
			dir = simulateur.getJeuDeDonnees().getCarte().getDirection(currentNode.getCase(), nextNode.getCase());			
			current_date += nextNode.distanceTo(currentNode);
			
			simulateur.ajouteEvenement(new Deplacement(this, simulateur.getJeuDeDonnees().getCarte(), dir, current_date));

			currentNode = nextNode;
		}
		return current_date;
	}

	public long deverserEau(Incendie incendie, long eauAVerser, Simulateur simulateur, long dateDebut) {
		/**deverserEau gènere une événement de déverssement d'eau sur incendie d'une quantité d'eau
		 * de volume eauAVerser*/
		long dateFin = dateDebut;
		dateFin += (eauAVerser % this.getQuantiteVersementUnitaire() == 0) ? (eauAVerser/this.getQuantiteVersementUnitaire()) * this.getTempsVersementUnitaire() : (eauAVerser / this.getQuantiteVersementUnitaire() + 1) * this.getTempsVersementUnitaire();

		VerserEau event = new VerserEau(incendie, this, eauAVerser, dateFin);
		simulateur.ajouteEvenement(event);
		return dateFin;
	}
	
	public long rechargerEau(Simulateur simu, long dateDebut, Case currentPos, Case waterPos) {
		/**recharcherEau gènere des événements de déplacement jusqu'à une case où ce robot peut 
		 * se rechercher et un événement de rechargement d'eau de ce robot sur une case waterPos*/
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
		/**traite incendie gènere la suite d'événements nécessaire pour que ce robot éteigne incendie*/
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
		simulateur.ajouteEvenement(new Fini(this, incendie, currentDate+1));
	}

	public long getVraieEauVersee(long eauSouhaitee) {
		/**????*/
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