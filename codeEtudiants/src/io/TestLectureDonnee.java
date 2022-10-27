package io;

import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

public class TestLectureDonnee {
	
	public static void main(String[] args) {
		try{
			LecteurDonnees.lire("/home/jules/Downloads/tpl_2A_POO/codeEtudiants/cartes/carteSujet.map");
		}
		catch (FileNotFoundException e) {
            System.out.println("fichier "  + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + " invalide: " + e.getMessage());
        }
	}
}	
