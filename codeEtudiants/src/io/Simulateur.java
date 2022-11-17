package io;
import evenements.Evenement;
import evenements.ListEvenement;

import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import donnees.Carte;
import donnees.DonneesSimulation;
import donnees.Incendie;
import donnees.TraitementIncendieEtat;
import donnees.TypeChefPompier;
import gui.*;
import robots.Robot;

/**Class qui gère le simulateur*/
public class Simulateur implements Simulable{
	
	
	private final GUISimulator guiSimu;	
	private int tailleCasesSimu;
	private DonneesSimulation jeuDeDonnees;
	private int x = 0;
	private int y = 0;
	private long dateSimulation = 0;
	private ListEvenement listEvenement = new ListEvenement();
	private ChefPompier chef;
	
	public Simulateur(GUISimulator gui) {
		this.guiSimu = gui;
		gui.setSimulable(this);
	}
	
	/**On dessine la carte et on commence la simulation en demandant au chef pompier d'assigner
	 * les tâches*/
	public void start() {
		draw(jeuDeDonnees);
	}
	
	/**chooseMap permet de chosir une des cartes de simulation plus simplement pour les tests:
	 * 0 = carteSUjet
	 * 1 = desertOfDeath
	 * 2 = mushroomOfHell
	 * 3 = spiralOfMadness*/
	public void chooseMap(int mapIndex) {
		
		if (!((1 <= mapIndex) && (mapIndex <= 4))) {
			throw new IllegalArgumentException("Le numero de carte doit etre cmpris entre 1 et 4");
		}
		
		DonneesSimulation jeuDeDonnees = null;
		try {
			switch (mapIndex) {
			case 1:
				jeuDeDonnees = LecteurDonnees.lire("codeEtudiants/cartes/carteSujet.map");
				break;
			case 2:
				jeuDeDonnees = LecteurDonnees.lire("codeEtudiants/cartes/desertOfDeath-20x20.map");
				break;
			case 3:
				jeuDeDonnees = LecteurDonnees.lire("codeEtudiants/cartes/mushroomOfHell-20x20.map");
				break;
			case 4:
				jeuDeDonnees = LecteurDonnees.lire("codeEtudiants/cartes/spiralOfMadness-50x50.map");
				break;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DataFormatException e) {
			e.printStackTrace();
		}
		this.jeuDeDonnees = jeuDeDonnees;
		this.tailleCasesSimu = Math.min(this.guiSimu.getPanelHeight(),
				this.guiSimu.getPanelWidth())/Math.max(jeuDeDonnees.getCarte().getNbColonnes(),
				jeuDeDonnees.getCarte().getNbLignes());
		draw(jeuDeDonnees);
		
	}
	
	/**chooseChef permet de défnir quel chef pompier sera utilisé, donc la stratégie d'affectation
	 * des feux:
	 * - 1 = chef pompier basique
	 * - 2 = chef pompier "master" avec stratégie plus évoluée
	 */
	public void chooseChef(int chefIndex) {
		
		if (!((1 <= chefIndex) && (chefIndex <= 2))) {
			throw new IllegalArgumentException("Le numero de chef doit etre cmpris entre 1 et 2");
		}
		
		switch (chefIndex) {
		case 1:
			this.chef = new ChefPompierSimple(this);
			break;
		case 2:
			this.chef = new ChefPompierMaster(this);
			break;
		}
	}
	
	public DonneesSimulation getJeuDeDonnees() {
		return this.jeuDeDonnees;
	}
	
	public void ajouteEvenement(Evenement e) {
		this.listEvenement.ajouteEvenement(e);
	}
	
	public void ajouteEvenementFin(Evenement e) {
		this.listEvenement.ajouteFin(e);
	}
	
	public void incrementeDate() {
		this.dateSimulation += 40;
	}
	
	public long getDateSimulation() {
		return this.dateSimulation;
	}
	
	public boolean simulationTerminee() {
		return true;
	}
		
	public ChefPompier getChefPompier() {
		return this.chef;
	}

	/**next éxecute les prochains événement dans la liste des événements
	 * dont la date est inférieur à la date actuel du simulateur*/
	public void next() {
		
		incrementeDate();
		
		if (!this.chef.getSimulationOver()) {
			this.chef.assigneIncendie();
		}
		
		while (this.listEvenement.getPremier() != null && this.listEvenement.getPremier().getDate() <= this.dateSimulation) {
			
			this.listEvenement.getPremier().execute();
			this.listEvenement.supprPremier();
				
		}
		draw(this.jeuDeDonnees);
		
	}
	
	/**permet de restart la simulation pour la revoir*/
	public void restart() {
		this.dateSimulation = 0;
		for (Robot robot : this.jeuDeDonnees.getRobots()) {
			robot.RestartPosition();
			robot.setOccupied(false);
		}
		for (Incendie incendie : this.jeuDeDonnees.getIncendies()) {
			incendie.EauNecessaireRestart();
			incendie.setTraite(TraitementIncendieEtat.rien);
		}

		listEvenement.flush();
		
		if (this.chef.getType() == TypeChefPompier.Simple) {
			this.chooseChef(1);
		} else {
			this.chooseChef(2);
		}
		
		System.out.println("Remise a zero de la simulation");	
		draw(this.jeuDeDonnees);
	}

	/**Fonction qui dessine la carte entière, les incendies et les robots à leur position initial*/
	private void draw(DonneesSimulation jeuDeDonnes) {
		
		guiSimu.reset();
		this.x = 0;
		this.y = 0;
		Carte carte = jeuDeDonnes.getCarte();
		Incendie[] incendies = jeuDeDonnes.getIncendies();
		Robot[] robots = jeuDeDonnes.getRobots();
		for(int i =0; i<carte.getNbLignes(); i++) {
			for(int j = 0; j <carte.getNbColonnes(); j++) {
				switch(carte.getCase(i, j).getNature()) {
				case EAU:
					drawEau();
					break;
				case FORET:
					drawForet();
					break;
				case ROCHE:
					drawRoche();
					break;
				case HABITAT:
					drawHabitat();
					break;
				case TERRAIN_LIBRE:
 					drawTerrainLibre();
					break;
				default:
					break;
				}
			}
		}
		for(int i = 0; i<incendies.length; i++) {
			drawIncendie(incendies[i]);
		}
		for(int i = 0; i < robots.length; i++) {
			drawRobot(robots[i]);
		}
	}
	
	/*Les fonctions d'après permette chacune de dessiner un élément de la carte, un robot,
	 *  un incendie ou une carte*/
	
	private void drawRobot(Robot robot) {
		switch (robot.getType()) {
		case DRONE:
			ImageElement imageDrone = new ImageElement((int)Math.round(robot.getPosition().getColonne()*this.tailleCasesSimu+this.tailleCasesSimu*0.15),
					(int)Math.round(robot.getPosition().getLigne()*this.tailleCasesSimu+this.tailleCasesSimu*0.15),
					System.getProperty("user.dir")+"/codeEtudiants/image/"+"robot_drone.png",(int)Math.round(this.tailleCasesSimu*0.7),
					(int)Math.round(this.tailleCasesSimu*0.7),this.guiSimu);
	        this.guiSimu.addGraphicalElement(imageDrone);
	        break;
		case CHENILLES:
			ImageElement imageChenille = new ImageElement((int)Math.round(robot.getPosition().getColonne()*this.tailleCasesSimu+this.tailleCasesSimu*0.15),
					(int)Math.round(robot.getPosition().getLigne()*this.tailleCasesSimu+this.tailleCasesSimu*0.15),
					System.getProperty("user.dir")+"/codeEtudiants/image/"+"robot_chenilles.png",(int)Math.round(this.tailleCasesSimu*0.7),
					(int)Math.round(this.tailleCasesSimu*0.7),this.guiSimu);
	        this.guiSimu.addGraphicalElement(imageChenille);
	        break;
		case PATTES:
			ImageElement imagePattes = new ImageElement((int)Math.round(robot.getPosition().getColonne()*this.tailleCasesSimu+this.tailleCasesSimu*0.15),
					(int)Math.round(robot.getPosition().getLigne()*this.tailleCasesSimu+this.tailleCasesSimu*0.15),
					System.getProperty("user.dir")+"/codeEtudiants/image/"+"robot_pattes.png",(int)Math.round(this.tailleCasesSimu*0.7),
					(int)Math.round(this.tailleCasesSimu*0.7),this.guiSimu);
	        this.guiSimu.addGraphicalElement(imagePattes);
	        break;
		case ROUES:
			ImageElement image = new ImageElement((int)Math.round(robot.getPosition().getColonne()*this.tailleCasesSimu+this.tailleCasesSimu*0.15),
					(int)Math.round(robot.getPosition().getLigne()*this.tailleCasesSimu+this.tailleCasesSimu*0.15),
					System.getProperty("user.dir")+"/codeEtudiants/image/"+"robot_roues.png",(int)Math.round(this.tailleCasesSimu*0.7),
					(int)Math.round(this.tailleCasesSimu*0.7),this.guiSimu);
	        this.guiSimu.addGraphicalElement(image);
	        break;
		}		
	}
	
	private void drawIncendie(Incendie incendie) {
		if(incendie.getEauNecessaire() != 0) {
			ImageElement image = new ImageElement((int)Math.round(incendie.getPosition().getColonne()*this.tailleCasesSimu+this.tailleCasesSimu*0.15),
					(int)Math.round(incendie.getPosition().getLigne()*this.tailleCasesSimu+this.tailleCasesSimu*0.15),
					System.getProperty("user.dir")+"/codeEtudiants/image/"+"incendie.png",(int)Math.round(this.tailleCasesSimu*0.8),
					(int)Math.round(this.tailleCasesSimu*0.8),this.guiSimu);
	        this.guiSimu.addGraphicalElement(image);
		}
	}
	
	private void drawEau() {
		
        ImageElement image = new ImageElement(x,y,System.getProperty("user.dir")+"/codeEtudiants/image/"+"case_eau.jpg",this.tailleCasesSimu,this.tailleCasesSimu,this.guiSimu);
        this.guiSimu.addGraphicalElement(image);
        if (x<Math.min(this.guiSimu.getPanelHeight(), this.guiSimu.getPanelWidth())-this.tailleCasesSimu) {
			x += this.tailleCasesSimu;
		}
		else {
			x = 0;
			y +=this.tailleCasesSimu;
		}
	}
	
	private void drawForet() {
		ImageElement image = new ImageElement(x,y,System.getProperty("user.dir")+"/codeEtudiants/image/"+"case_foret.png",this.tailleCasesSimu,this.tailleCasesSimu,this.guiSimu);
        this.guiSimu.addGraphicalElement(image);
        if (x<Math.min(this.guiSimu.getPanelHeight(), this.guiSimu.getPanelWidth())-this.tailleCasesSimu) {
			x += this.tailleCasesSimu;
		}
		else {
			x = 0;
			y +=this.tailleCasesSimu;
		}
	}
	
	private void drawHabitat() {
		ImageElement image = new ImageElement(x,y,System.getProperty("user.dir")+"/codeEtudiants/image/"+"case_habitat.png",this.tailleCasesSimu,this.tailleCasesSimu,this.guiSimu);
        this.guiSimu.addGraphicalElement(image);
        if (x<Math.min(this.guiSimu.getPanelHeight(), this.guiSimu.getPanelWidth())-this.tailleCasesSimu) {
			x += this.tailleCasesSimu;
		}
		else {
			x = 0;
			y +=this.tailleCasesSimu;
		}
	}
	
	private void drawRoche() {
		ImageElement image = new ImageElement(x,y,System.getProperty("user.dir")+"/codeEtudiants/image/"+"case_rocher.png",this.tailleCasesSimu,this.tailleCasesSimu,this.guiSimu);
        this.guiSimu.addGraphicalElement(image);
        if (x<Math.min(this.guiSimu.getPanelHeight(), this.guiSimu.getPanelWidth())-this.tailleCasesSimu) {
			x += this.tailleCasesSimu;
		}
		else {
			x = 0;
			y +=this.tailleCasesSimu;
		}
	}
	
	private void drawTerrainLibre() {
		ImageElement image = new ImageElement(x,y,System.getProperty("user.dir")+"/codeEtudiants/image/"+"case_terrain_libre.png",this.tailleCasesSimu,this.tailleCasesSimu,this.guiSimu);
        this.guiSimu.addGraphicalElement(image);
        if (x<Math.min(this.guiSimu.getPanelHeight(), this.guiSimu.getPanelWidth())-this.tailleCasesSimu) {
			x += this.tailleCasesSimu;
		}
		else {
			x = 0;
			y +=this.tailleCasesSimu;
		}
	}
}
