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
import robots.Robot;

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
	
	public void ajouteEvenementFin(Evenement e) {
		this.listEvenement.ajouteFin(e);
	}
	
	public void incrementeDate() {
		this.dateSimulation += 30;
	}
	
	public long getDateSimulation() {
		return this.dateSimulation;
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
			draw(jeuDeDonnees);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DataFormatException e) {
			e.printStackTrace();
		}
		
	}

	public void next() {
		incrementeDate();
		boolean flag;
		if (this.listEvenement.getPremier() != null) {
			flag = this.listEvenement.getPremier().getDate() <= this.dateSimulation;
			while (flag) {
				this.listEvenement.getPremier().execute();
				this.listEvenement.suppPremier();
				System.out.println(this.listEvenement.getPremier());
				if (this.listEvenement.getPremier() != null) {
					flag = this.listEvenement.getPremier().getDate() <= this.dateSimulation; 
				}
				else {
					flag = false;
				}
			}
		}
		draw(this.jeuDeDonnees);
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
			drawRobot(robots[i]);
		}
	}

	private void drawRobot(Robot robot) {
		switch (robot.getType()) {
		case "DRONE":
			ImageElement imageDrone = new ImageElement((int)Math.round(robot.getPosition().getColonne()*this.tailleCasesSimu+this.tailleCasesSimu*0.15),
					(int)Math.round(robot.getPosition().getLigne()*this.tailleCasesSimu+this.tailleCasesSimu*0.15),
					System.getProperty("user.dir")+"/codeEtudiants/image/"+"robot_drone.png",(int)Math.round(this.tailleCasesSimu*0.7),
					(int)Math.round(this.tailleCasesSimu*0.7),this.simu);
	        this.simu.addGraphicalElement(imageDrone);
	        break;
		case "CHENILLES":
			ImageElement imageChenille = new ImageElement((int)Math.round(robot.getPosition().getColonne()*this.tailleCasesSimu+this.tailleCasesSimu*0.15),
					(int)Math.round(robot.getPosition().getLigne()*this.tailleCasesSimu+this.tailleCasesSimu*0.15),
					System.getProperty("user.dir")+"/codeEtudiants/image/"+"robot_chenilles.png",(int)Math.round(this.tailleCasesSimu*0.7),
					(int)Math.round(this.tailleCasesSimu*0.7),this.simu);
	        this.simu.addGraphicalElement(imageChenille);
	        break;
		case "PATTES":
			ImageElement imagePattes = new ImageElement((int)Math.round(robot.getPosition().getColonne()*this.tailleCasesSimu+this.tailleCasesSimu*0.15),
					(int)Math.round(robot.getPosition().getLigne()*this.tailleCasesSimu+this.tailleCasesSimu*0.15),
					System.getProperty("user.dir")+"/codeEtudiants/image/"+"robot_pattes.png",(int)Math.round(this.tailleCasesSimu*0.7),
					(int)Math.round(this.tailleCasesSimu*0.7),this.simu);
	        this.simu.addGraphicalElement(imagePattes);
	        break;
		case "ROUES":
			ImageElement image = new ImageElement((int)Math.round(robot.getPosition().getColonne()*this.tailleCasesSimu+this.tailleCasesSimu*0.15),
					(int)Math.round(robot.getPosition().getLigne()*this.tailleCasesSimu+this.tailleCasesSimu*0.15),
					System.getProperty("user.dir")+"/codeEtudiants/image/"+"robot_roues.png",(int)Math.round(this.tailleCasesSimu*0.7),
					(int)Math.round(this.tailleCasesSimu*0.7),this.simu);
	        this.simu.addGraphicalElement(image);
	        break;
		}		
	}
	
	private void drawIncendie(Incendie incendie) {
		if(incendie.getEauNecessaire() != 0) {
			ImageElement image = new ImageElement((int)Math.round(incendie.getPosition().getColonne()*this.tailleCasesSimu+this.tailleCasesSimu*0.15),
					(int)Math.round(incendie.getPosition().getLigne()*this.tailleCasesSimu+this.tailleCasesSimu*0.15),
					System.getProperty("user.dir")+"/codeEtudiants/image/"+"incendie.png",(int)Math.round(this.tailleCasesSimu*0.8),
					(int)Math.round(this.tailleCasesSimu*0.8),this.simu);
	        this.simu.addGraphicalElement(image);
		}
	}
	
	private void drawEau() {
		
        ImageElement image = new ImageElement(x,y,System.getProperty("user.dir")+"/codeEtudiants/image/"+"case_eau.jpg",this.tailleCasesSimu,this.tailleCasesSimu,this.simu);
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
		ImageElement image = new ImageElement(x,y,System.getProperty("user.dir")+"/codeEtudiants/image/"+"case_foret.png",this.tailleCasesSimu,this.tailleCasesSimu,this.simu);
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
		ImageElement image = new ImageElement(x,y,System.getProperty("user.dir")+"/codeEtudiants/image/"+"case_habitat.png",this.tailleCasesSimu,this.tailleCasesSimu,this.simu);
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
		ImageElement image = new ImageElement(x,y,System.getProperty("user.dir")+"/codeEtudiants/image/"+"case_rocher.png",this.tailleCasesSimu,this.tailleCasesSimu,this.simu);
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
		ImageElement image = new ImageElement(x,y,System.getProperty("user.dir")+"/codeEtudiants/image/"+"case_terrain_libre.png",this.tailleCasesSimu,this.tailleCasesSimu,this.simu);
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
