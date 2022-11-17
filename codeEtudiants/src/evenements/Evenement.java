package evenements;

/**Classe d'un événement qui regroupe ce qui est commun à nimporte quelle événement */
public abstract class Evenement {
	private long date;
	private Evenement suivant = null;
	
	public void setSuivant(Evenement e) {
		this.suivant = e;
	}
	
	public Evenement getSuivant() {
		return this.suivant;
	}
	
	public Evenement(long date) {
		this.date = date;
	}
	
	public long getDate() {
		return this.date;
	}
	
	public abstract void execute();
		
}
