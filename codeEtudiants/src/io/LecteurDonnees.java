package io;

import java.io.*;
import java.util.*;
import java.util.zip.DataFormatException;

import donnees.Carte;
import donnees.Case;
import donnees.DonneesSimulation;
import donnees.Incendie;
import donnees.NatureTerrain;
import robots.Robot;
import robots.RobotAChenilles;
import robots.RobotAPattes;
import robots.RobotARoues;
import robots.RobotDrone;

/**
 * Lecteur de cartes au format spectifié dans le sujet.
 * Les données sur les cases, robots puis incendies sont lues dans le fichier,
 * puis ajouté au jeu de données
 */
public class LecteurDonnees {


    /**
     * Lit le contenu d'un fichier de donnees (cases,
     * robots et incendies) et crée un jeu de données en conséquence.
     * Ceci est méthode de classe; utilisation:affiche
     * LecteurDonnees.lire(fichierDonnees)
     * @param fichierDonnees nom du fichier à lire
     */
    public static DonneesSimulation lire(String fichierDonnees)
        throws FileNotFoundException, DataFormatException {
        LecteurDonnees lecteur = new LecteurDonnees(fichierDonnees);
        Carte carte = lecteur.lireCarte();
        Incendie incendies[] = lecteur.lireIncendies(carte);
        Robot[] robots = lecteur.lireRobots(carte);
        DonneesSimulation jeuDeDonnees = new DonneesSimulation(carte, incendies.length, incendies, robots.length, robots); 
        scanner.close();
        return jeuDeDonnees;
    }

    private static Scanner scanner;

    /**
     * Constructeur prive; impossible d'instancier la classe depuis l'exterieur
     * @param fichierDonnees nom du fichier a lire
     */
    private LecteurDonnees(String fichierDonnees)
        throws FileNotFoundException {
        scanner = new Scanner(new File(fichierDonnees));
        scanner.useLocale(Locale.US);
    }

    /**
     * Lit et retourne les donnees de la carte.
     * @throws ExceptionFormatDonnees
     */
    private Carte lireCarte() throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbLignes = scanner.nextInt();
            int nbColonnes = scanner.nextInt();
            int tailleCases = scanner.nextInt();
            Carte carte = new Carte(nbLignes, nbColonnes,tailleCases);// en m
            for (int lig = 0; lig < nbLignes; lig++) {
                for (int col = 0; col < nbColonnes; col++) {
                	carte.setCase(lig,col,lireCase(lig,col));

                }
            }
            
            return carte;

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbLignes nbColonnes tailleCases");
        }
        // une ExceptionFormat levee depuis lireCase est remontee telle quelle
    }




    /**
     * Lit et retourne les donnees d'une case.
     */
    private Case lireCase(int lig, int col) throws DataFormatException {
        ignorerCommentaires();
        String chaineNature = new String();
        try {
            chaineNature = scanner.next();
            NatureTerrain nature = NatureTerrain.valueOf(chaineNature);
            verifieLigneTerminee();
            return new Case(lig,col,nature);

        } catch (NoSuchElementException e) {
            throw new DataFormatException("format de case invalide. "
                    + "Attendu: nature altitude [valeur_specifique]");
        }
    }


    /**
     * Lit et retourne les donnees des incendies.
     */
    private Incendie[] lireIncendies(Carte carte) throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbIncendies = scanner.nextInt();
            Incendie incendies[] = new Incendie[nbIncendies];
            for (int i = 0; i < nbIncendies; i++) {
                Incendie incendie = lireIncendie(i, carte);
                incendies[i] = incendie;
            }
            return incendies;

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbIncendies");
        }
    }


    /**
     * Lit et retourne les donnees du i-eme incendie.
     * @param i
     */
    private Incendie lireIncendie(int i, Carte carte) throws DataFormatException {
        ignorerCommentaires();

        try {
            int lig = scanner.nextInt();
            int col = scanner.nextInt();
            int intensite = scanner.nextInt();
            if (intensite <= 0) {
                throw new DataFormatException("incendie " + i
                        + "nb litres pour eteindre doit etre > 0");
            }
            verifieLigneTerminee();
            Case case_incendie = carte.getCase(lig, col);
            Incendie incendie = new Incendie(case_incendie, intensite);
            return incendie;

        } catch (NoSuchElementException e) {
            throw new DataFormatException("format d'incendie invalide. "
                    + "Attendu: ligne colonne intensite");
        }
    }


    /**
     * Lit et retourne les donnees des robots.
     */
    private Robot[] lireRobots(Carte carte) throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbRobots = scanner.nextInt();
            Robot[] robots= new Robot[nbRobots];
            for (int i = 0; i < nbRobots; i++) {
                Robot robot =lireRobot(i, carte);
                robots[i] = robot;
            }
            return robots;

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbRobots");
        }
    }


    /**
     * Lit et retourne les donnees du i-eme robot.
     * @param i
     */
    private Robot lireRobot(int i, Carte carte) throws DataFormatException {
        ignorerCommentaires();
        try {
        	int vitesse = 0;
            int lig = scanner.nextInt();
            int col = scanner.nextInt();
            Case caseInitRobot = carte.getCase(lig, col);
            String type = scanner.next();
            String s = scanner.findInLine("(\\d+)");
            
            if (s != null) {
                vitesse = Integer.parseInt(s);
            }
            verifieLigneTerminee();
            
            switch(type) {
            case "DRONE":
            	if (vitesse == 0) {
            		vitesse = 100;
            	}
            	Robot robotDrone = new RobotDrone(caseInitRobot, carte, vitesse);
            	return robotDrone;
            	
            case "PATTES":
            	Robot robotPattes = new RobotAPattes(caseInitRobot, carte);
            	return robotPattes;
            	
            case "ROUES":
            	if (vitesse == 0) {
            		vitesse = 80;
            	}
            	Robot robotRoues = new RobotARoues(caseInitRobot, carte, vitesse);
            	return robotRoues;
            	
            case "CHENILLES":
            	if (vitesse == 0) {
            		vitesse = 60;
            	}
            	Robot robotChenilles = new RobotAChenilles(caseInitRobot, carte, vitesse);
            	return robotChenilles;

            default:
            	return (Robot)(new RobotAChenilles(caseInitRobot, carte, 60));
            }

        } catch (NoSuchElementException e) {
            throw new DataFormatException("format de robot invalide. "
                    + "Attendu: ligne colonne type [valeur_specifique]");
        }
    }




    /** Ignore toute (fin de) ligne commencant par '#' */
    private void ignorerCommentaires() {
        while(scanner.hasNext("#.*")) {
            scanner.nextLine();
        }
    }

    /**
     * Verifie qu'il n'y a plus rien a lire sur cette ligne (int ou float).
     * @throws ExceptionFormatDonnees
     */
    private void verifieLigneTerminee() throws DataFormatException {
        if (scanner.findInLine("(\\d+)") != null) {
            throw new DataFormatException("format invalide, donnees en trop.");
        }
    }
}
