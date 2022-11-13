package io;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import gui.*;
public class Simulateur implements Simulable{
	
	private GUISimulator simu;	
	private int x = 0;
	private int y = 0;
	private int tailleCasesSimu;
	private long dateSimulation = 0;
	private ListEvenement listEvenement = new ListEvenement();
	private DonneesSimulation jeuDeDonnees;
	
	public DonneesSimulation getJeuDeDonnees() {
		return this.jeuDeDonnees;
	}
	public void ajouteEvenement(Evenement e) {
		this.listEvenement.ajouteEvenement(e);
	}
	
	public void incrementeDate() {
		this.dateSimulation += 1;
	}
	
	public boolean simulationTerminee() {
		return true;
	}
	
	public Simulateur(GUISimulator gui) {
		this.simu = gui;
		gui.setSimulable(this);
	
		DonneesSimulation jeuDeDonnees;
		try {
			jeuDeDonnees = LecteurDonnees.lire("codeEtudiants/cartes/carteSujet.map");
			this.jeuDeDonnees = jeuDeDonnees;
			this.tailleCasesSimu = Math.min(this.simu.getPanelHeight(),
					this.simu.getPanelWidth())/Math.max(jeuDeDonnees.getCarte().getNbColonnes(),
					jeuDeDonnees.getCarte().getNbLignes());
			System.out.println("taille des cases: " + this.tailleCasesSimu + "x: " + this.x + "y: " + this.y);
			draw(jeuDeDonnees);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DataFormatException e) {
			e.printStackTrace();
		}
		
	}
	public void next() {
		incrementeDate();
		if (this.listEvenement.getPremier().getDate() == this.dateSimulation) {
			this.listEvenement.getPremier().execute();
			this.listEvenement.suppPremier();
			draw(this.jeuDeDonnees);
			
		}
	}
	
	public void restart() {
		return;
	}

	private void draw(DonneesSimulation jeuDeDonnes) {
		simu.reset();
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
			System.out.println("robo");
			drawRobot(robots[i]);
		}
	}

	private void drawRobot(Robot robot) {
		switch (robot.getType()) {
		case "DRONE":
			ImageElement imageDrone = new ImageElement((int)Math.round(robot.getPosition().getLigne()*this.tailleCasesSimu+this.tailleCasesSimu*0.15),
					(int)Math.round(robot.getPosition().getColonne()*this.tailleCasesSimu+this.tailleCasesSimu*0.15),
					System.getProperty("user.dir")+"/codeEtudiants/image/"+"robot_drone.png",(int)Math.round(this.tailleCasesSimu*0.7),
					(int)Math.round(this.tailleCasesSimu*0.7),this.simu);
	        this.simu.addGraphicalElement(imageDrone);
	        break;
		case "CHENILLES":
			ImageElement imageChenille = new ImageElement((int)Math.round(robot.getPosition().getLigne()*this.tailleCasesSimu+this.tailleCasesSimu*0.15),
					(int)Math.round(robot.getPosition().getColonne()*this.tailleCasesSimu+this.tailleCasesSimu*0.15),
					System.getProperty("user.dir")+"/codeEtudiants/image/"+"robot_chenille.jpeg",(int)Math.round(this.tailleCasesSimu*0.7),
					(int)Math.round(this.tailleCasesSimu*0.7),this.simu);
	        this.simu.addGraphicalElement(imageChenille);
	        break;
		case "PATTES":
			ImageElement imagePattes = new ImageElement((int)Math.round(robot.getPosition().getLigne()*this.tailleCasesSimu+this.tailleCasesSimu*0.15),
					(int)Math.round(robot.getPosition().getColonne()*this.tailleCasesSimu+this.tailleCasesSimu*0.15),
					System.getProperty("user.dir")+"/codeEtudiants/image/"+"robot_patte.jpg",(int)Math.round(this.tailleCasesSimu*0.7),
					(int)Math.round(this.tailleCasesSimu*0.7),this.simu);
	        this.simu.addGraphicalElement(imagePattes);
	        break;
		case "ROUES":
			ImageElement image = new ImageElement((int)Math.round(robot.getPosition().getLigne()*this.tailleCasesSimu+this.tailleCasesSimu*0.15),
					(int)Math.round(robot.getPosition().getColonne()*this.tailleCasesSimu+this.tailleCasesSimu*0.15),
					System.getProperty("user.dir")+"/codeEtudiants/image/"+"robot_roues.jpg",(int)Math.round(this.tailleCasesSimu*0.7),
					(int)Math.round(this.tailleCasesSimu*0.7),this.simu);
	        this.simu.addGraphicalElement(image);
	        break;
		}		
	}
	
	private void drawIncendie(Incendie incendie) {
		ImageElement image = new ImageElement((int)Math.round(incendie.getPosition().getLigne()*this.tailleCasesSimu+this.tailleCasesSimu*0.1),
				(int)Math.round(incendie.getPosition().getColonne()*this.tailleCasesSimu+this.tailleCasesSimu*0.1),
				System.getProperty("user.dir")+"/codeEtudiants/image/"+"incendie.png",(int)Math.round(this.tailleCasesSimu*0.8),
				(int)Math.round(this.tailleCasesSimu*0.8),this.simu);
        this.simu.addGraphicalElement(image);
	}
	
	private void drawEau() {
		
        ImageElement image = new ImageElement(x,y,System.getProperty("user.dir")+"/codeEtudiants/image/"+"case_eau.jpeg",this.tailleCasesSimu,this.tailleCasesSimu,this.simu);
        this.simu.addGraphicalElement(image);
        if (x<Math.min(this.simu.getPanelHeight(), this.simu.getPanelWidth())-this.tailleCasesSimu) {
			x += this.tailleCasesSimu;
		}
		else {
			x = 0;
			y +=this.tailleCasesSimu;
		}
	}
	
	private void drawForet() {
		System.out.println(System.getProperty("user.dir")+"/codeEtudiants/image/"+"case_foret.jpeg");
		ImageElement image = new ImageElement(x,y,System.getProperty("user.dir")+"/codeEtudiants/image/"+"case_foret.jpeg",this.tailleCasesSimu,this.tailleCasesSimu,this.simu);
        this.simu.addGraphicalElement(image);
        if (x<Math.min(this.simu.getPanelHeight(), this.simu.getPanelWidth())-this.tailleCasesSimu) {
			x += this.tailleCasesSimu;
		}
		else {
			x = 0;
			y +=this.tailleCasesSimu;
		}
	}
	
	private void drawHabitat() {
		System.out.println(System.getProperty("user.dir")+"/codeEtudiants/image/"+"petite-amsion.png");
		ImageElement image = new ImageElement(x,y,System.getProperty("user.dir")+"/codeEtudiants/image/"+"petite-maison.png",this.tailleCasesSimu,this.tailleCasesSimu,this.simu);
        this.simu.addGraphicalElement(image);
        if (x<Math.min(this.simu.getPanelHeight(), this.simu.getPanelWidth())-this.tailleCasesSimu) {
			x += this.tailleCasesSimu;
		}
		else {
			x = 0;
			y +=this.tailleCasesSimu;
		}
	}
	
	private void drawRoche() {
		System.out.println(System.getProperty("user.dir")+"/codeEtudiants/image/"+"case_rocher.jpg");
		ImageElement image = new ImageElement(x,y,System.getProperty("user.dir")+"/codeEtudiants/image/"+"case_rocher.jpg",this.tailleCasesSimu,this.tailleCasesSimu,this.simu);
        this.simu.addGraphicalElement(image);
        if (x<Math.min(this.simu.getPanelHeight(), this.simu.getPanelWidth())-this.tailleCasesSimu) {
			x += this.tailleCasesSimu;
		}
		else {
			x = 0;
			y +=this.tailleCasesSimu;
		}
	}
	
	private void drawTerrainLibre() {
		System.out.println(System.getProperty("user.dir")+"/codeEtudiants/image/"+"case_terrain_libre.jpg");
		ImageElement image = new ImageElement(x,y,System.getProperty("user.dir")+"/codeEtudiants/image/"+"case_terrain_libre.jpg",this.tailleCasesSimu,this.tailleCasesSimu,this.simu);
        this.simu.addGraphicalElement(image);
        if (x<Math.min(this.simu.getPanelHeight(), this.simu.getPanelWidth())-this.tailleCasesSimu) {
			x += this.tailleCasesSimu;
		}
		else {
			x = 0;
			y +=this.tailleCasesSimu;
		}
	}
}
