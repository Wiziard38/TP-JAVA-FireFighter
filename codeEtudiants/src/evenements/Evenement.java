package evenements;

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
