package io;

import java.awt.Color;

import gui.GUISimulator;

/** Fonction de test du simulateur*/
public class TestSimulateur {
	
	public static void main(String[] args) {
		System.out.println(System.getProperty("user.dir")+"/codeEtudiants/");
        // creer la fenetre graphique dans laquelle dessiner
        GUISimulator gui = new GUISimulator(800, 600, Color.WHITE);
        
        Simulateur simu = new Simulateur(gui);
        simu.chooseMap(1);
        simu.chooseChef(2);
        simu.start();
	}
}
