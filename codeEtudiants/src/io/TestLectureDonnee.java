package io;

import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

public class TestLectureDonnee {
	
	public static void main(String[] args) {
		System.out.println(System.getProperty("user.dir")+ "/codeEtudiants/cartes/carteSujet.map");
		try{
			LecteurDonnees.lire(System.getProperty("user.dir")+ "/codeEtudiants/cartes/carteSujet.map");
		}
		catch (FileNotFoundException e) {
            System.out.println("fichier "  + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + " invalide: " + e.getMessage());
        }
	}
}	
