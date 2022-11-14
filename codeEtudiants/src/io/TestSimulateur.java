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
        Robot robot = jeuDeDonnees.getRobots()[0];
        Deplacement ev = new Deplacement(robot,jeuDeDonnees.getCarte(), Direction.OUEST,1);
        simu.ajouteEvenement(ev);
        Deplacement ev13 = new Deplacement(jeuDeDonnees.getRobots()[1],jeuDeDonnees.getCarte(), Direction.NORD,1);
        simu.ajouteEvenement(ev13); 
        Deplacement ev3 = new Deplacement(robot,jeuDeDonnees.getCarte(), Direction.OUEST,2);
        simu.ajouteEvenement(ev3);
        Deplacement ev4 = new Deplacement(robot,jeuDeDonnees.getCarte(), Direction.SUD,3);
        simu.ajouteEvenement(ev4);
        Deplacement ev6 = new Deplacement(robot,jeuDeDonnees.getCarte(), Direction.SUD, 4);
        simu.ajouteEvenement(ev6);
        Deplacement ev7 = new Deplacement(robot,jeuDeDonnees.getCarte(), Direction.SUD, 5);
        simu.ajouteEvenement(ev7);
        VerserEau ev2 = new VerserEau(jeuDeDonnees.getIncendie(jeuDeDonnees.getCarte().getCase(6, 1)),robot, 10000, 6);
        simu.ajouteEvenement(ev2);        
        Deplacement ev9 = new Deplacement(robot,jeuDeDonnees.getCarte(), Direction.EST, 7);
        simu.ajouteEvenement(ev9);
        RemplissageReservoir ev10 = new RemplissageReservoir(jeuDeDonnees.getCarte(),robot,10000,8);
        simu.ajouteEvenement(ev10);
        Deplacement ev11 = new Deplacement(robot,jeuDeDonnees.getCarte(), Direction.OUEST, 9);
        simu.ajouteEvenement(ev11);
        VerserEau ev12 = new VerserEau(jeuDeDonnees.getIncendie(jeuDeDonnees.getCarte().getCase(6, 1)),robot, 10000, 10);
        simu.ajouteEvenement(ev12); 
	}
}
