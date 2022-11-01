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
	private int x = 5;
	private int y = 5;
	public Simulateur(GUISimulator gui) {
		this.simu = gui;
		gui.setSimulable(this);
	
		NatureTerrain[][] carte;
		try {
			carte = LecteurDonnees.lire("/home/jules/Downloads/tpl_2A_POO/codeEtudiants/cartes/carteSujet.map");
			//draw(carte);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DataFormatException e) {
			e.printStackTrace();
		}
		
	}
	public void next() {
		return;
	}
	public void restart() {
		return;
	}
	private void draw(NatureTerrain[][] carte) {
		simu.reset();
		for(int i =0; i<carte.length; i++) {
			for(int j = 0; j <carte[0].length; j++) {
				switch(carte[i][j]) {
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
	}
	private void drawIncendie() {
		simu.addGraphicalElement(new Rectangle(x,y,Color.RED,Color.RED,10));
		if (x!=75) {
			x += 10;
		}
		else {
			x = 5;
			y +=10;
		}
	}
	
	private void drawEau() {
		ImageIcon icone = new ImageIcon("codeEtudiants/incendie.png"); 
        JLabel image = new JLabel(icone);
        simu.add(image);
			

		//simu.addGraphicalElement(new Rectangle(x,y,Color.BLUE,Color.BLUE,10));
		if (x!=75) {
			x += 10;
		}
		else {
			x = 5;
			y +=10;
		}
		}
	
	private void drawForet() {
		simu.addGraphicalElement(new Rectangle(x,y,Color.GREEN,Color.GREEN,10));
		if (x!=75) {
			x += 10;
		}
		else {
			x = 5;
			y +=10;
		}
	}
	
	private void drawHabitat() {
		simu.addGraphicalElement(new Rectangle(x,y,Color.YELLOW,Color.YELLOW,10));
		if (x!=75) {
			x += 10;
		}
		else {
			x = 5;
			y +=10;
		}
	}
	
	private void drawRoche() {
		simu.addGraphicalElement(new Rectangle(x,y,Color.GRAY,Color.GRAY,10));
		if (x!=75) {
			x += 10;
		}
		else {
			x = 5;
			y +=10;
		}
	}
	
	private void drawTerrainLibre() {
		simu.addGraphicalElement(new Rectangle(x,y,Color.BLACK,Color.BLACK,10));
		if (x!=75) {
			x += 10;
		}
		else {
			x = 5;
			y +=10;
		}
	}
}
