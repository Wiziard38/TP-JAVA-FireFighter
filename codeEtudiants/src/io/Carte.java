package io;

public class Carte {
	/**La classe qui représente la carte sur lesquels les robots évoluent*/
	private int tailleCase;
	private Case tab[][];
	private int nbLignes;
	private int nbColonnes;
	
	public Carte(int nbLignes, int nbColonnes, int tailleCase) {
		this.tab = new Case[nbLignes][nbColonnes];
		this.nbLignes = nbLignes;
		this.nbColonnes = nbColonnes;
		this.tailleCase = tailleCase;
	}
	
	public int getNbLignes() {
		return this.nbLignes;
	}
	
	public int getNbColonnes() {
		return this.nbColonnes;
	}
	
	public int getTailleCase() {
		return this.tailleCase;
	}
	
	public Case getCase(int lig, int col) {
		return this.tab[lig][col];
	}
	
	public void setCase(int lig, int col, Case c) {
		this.tab[lig][col] = c;
	}
	
	public boolean voisinExiste(Case src, Direction dir) {
		/** Retourne vraie si il existe une case adjacente dans la direction dir de la case src et faux sinon*/
		switch(dir) {
		case NORD:
			return src.getLigne() - 1>= 0;
		case SUD:
			return src.getLigne() + 1 <= this.nbLignes - 1;
		case EST:
			return src.getColonne() + 1 <= this.nbColonnes - 1;
		case OUEST:
			return src.getColonne() - 1 >=0;
		default:
			return false;
		}
	}
	
	public Case getVoisin(Case src, Direction dir) {
		/** Retourne la case voisine à src dans la direction dir, attention pas de précaution prise à ca moment
		 * 	C'est à l'utilisateur de penser à appeler voisinExiste avant
		 */
		switch(dir) {
		case NORD:
			return this.tab[src.getLigne()-1][src.getColonne()];
		case SUD:
			return this.tab[src.getLigne()+1][src.getColonne()];
		case EST:
			return this.tab[src.getLigne()][src.getColonne()+1];
		case OUEST:
			return this.tab[src.getLigne()][src.getColonne()-1];
		default:
			return src;
		}
	}
	
	public Direction getDirection(Case origin, Case dest) {
		/** Retourne la direction du prochain déplacement à effectuer pour aller de la case origin
		 * à la case source 
		 */
		if (origin.getLigne() == dest.getLigne()) {
			if (origin.getColonne() > dest.getColonne()) {
				return Direction.OUEST;
			}
			return Direction.EST;
		}
		if (origin.getLigne() > dest.getLigne()) {
			return Direction.NORD;
		}
		return Direction.SUD;
	}
	
}
