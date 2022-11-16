package io;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import gui.GUISimulator;
import gui.ImageElement;
import robots.Robot;

import org.graphstream.graph.Path;

public class TestSimulateur {

	public static void main(String[] args) {
		System.out.println(System.getProperty("user.dir")+"/codeEtudiants/");
         //crée la fenêtre graphique dans laquelle dessiner
        GUISimulator gui = new GUISimulator(800, 600, Color.WHITE);
        // crée l'invader, en l'associant à la fenêtre graphique précédente
        Simulateur simu = new Simulateur(gui);
        DonneesSimulation jeuDeDonnees = simu.getJeuDeDonnees();
        //Robot robot = jeuDeDonnees.getRobots()[0];
        ChefPompier chef= new ChefPompier(simu,jeuDeDonnees);
        chef.start();
        
	}
}
