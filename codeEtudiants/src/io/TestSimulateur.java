package io;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import gui.GUISimulator;

public class TestSimulateur {

	public static void main(String[] args) {
//         crée la fenêtre graphique dans laquelle dessiner
        GUISimulator gui = new GUISimulator(800, 600, Color.WHITE);
        // crée l'invader, en l'associant à la fenêtre graphique précédente
        Simulateur simu = new Simulateur(gui);
        BufferedImage icone;
		try {
			icone = ImageIO.read(new File("codeEtudiants/incendie.png"));
			 Image image = gui.createImage(50,50);
		     image.getGraphics().drawImage(icone, 50, 50, 50, 50, gui);
		     gui.imageUpdate(image,1,50, 50, 50, 50);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
	}
}
