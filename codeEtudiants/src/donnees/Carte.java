package donnees;

/**La classe qui représente la carte sur lesquels les robots évoluent*/
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
	
	/** Retourne vraie si il existe une case adjacente dans la direction dir de la case src et faux sinon*/
	public boolean voisinExiste(Case src, Direction dir) {
		
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
	
	/** Retourne la case voisine à src dans la direction dir, attention pas de précaution prise à ca moment
	 * 	C'est à l'utilisateur de penser à appeler voisinExiste avant
	 */
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
	
	/** Retourne la direction du prochain déplacement à effectuer pour aller de la case origin
	 * à la case source 
	 */
	public Direction getDirection(Case origin, Case destination) {
		if (origin.getLigne() == destination.getLigne()) {
			if (origin.getColonne() > destination.getColonne()) {
				if (origin.getColonne() != destination.getColonne() + 1)
					throw new IllegalArgumentException("Cases non voisines !");
				return Direction.OUEST;
			}
			if (origin.getColonne() != destination.getColonne() - 1)
				throw new IllegalArgumentException("Cases non voisines !");
			return Direction.EST;
		}
		if (origin.getLigne() > destination.getLigne()) {
			if (origin.getLigne() != destination.getLigne() + 1)
				throw new IllegalArgumentException("Cases non voisines !");
			return Direction.NORD;
		}
		if (origin.getLigne() != destination.getLigne() - 1)
			throw new IllegalArgumentException("Cases non voisines !");
		return Direction.SUD;
	}
}
