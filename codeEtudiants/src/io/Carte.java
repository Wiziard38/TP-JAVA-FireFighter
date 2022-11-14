package io;

public class Carte {
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
		switch(dir) {
		case NORD:
			return src.getLigne() - 1>= 0;
		case SUD:
			return src.getLigne() + 1 <= this.nbLignes;
		case EST:
			return src.getColonne() + 1 >= 0;
		case OUEST:
			return src.getColonne() - 1 <= this.nbColonnes;
		default:
			return false;
		}
	}
	
	public Case getVoisin(Case src, Direction dir) {
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
	
}
