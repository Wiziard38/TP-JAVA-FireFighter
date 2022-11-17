package io;

import java.awt.Color;

import gui.GUISimulator;


public class TestSimulateur {
	/** Fonction de test*/
	public static void main(String[] args) {
		System.out.println(System.getProperty("user.dir")+"/codeEtudiants/");
        // creer la fenetre graphique dans laquelle dessiner
        GUISimulator gui = new GUISimulator(800, 600, Color.WHITE);
        
        Simulateur simu = new Simulateur(gui);
        simu.chooseMap(3);
        simu.chooseChef(0);
        simu.start();

	}
}
