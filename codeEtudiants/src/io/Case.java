package io;

public class Case {
	private int ligne;
	private int colonne;
	NatureTerrain nature;
	
	public Case(int lig, int col, NatureTerrain nature) {
		this.ligne = lig;
		this.colonne= col;
		this.nature = nature;
	}
	public int getLigne() {
		return this.ligne;
	}
	
	public int getColonne() {
		return this.colonne;
	}
	
	public NatureTerrain getNature() {
		return this.nature;
	}
}
