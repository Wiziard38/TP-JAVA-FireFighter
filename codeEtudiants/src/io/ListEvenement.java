package io;

public class ListEvenement {
	private Evenement premier;
	
	public ListEvenement() {
		this.premier = null;
	}
	
	public Evenement getPremier() {
		return this.premier;
	}
	
	public void ajouteEvenement(Evenement e) {
		Evenement evCourant = premier;
		if (evCourant == null) {
			e.setSuivant(evCourant);
			this.premier = e;
		}
		else {
			if (evCourant.getDate() > e.getDate()) {
				e.setSuivant(evCourant);
				this.premier = e;
			}
			else {
				while (evCourant.getSuivant() != null) {
					if (e.getDate() <= evCourant.getSuivant().getDate()) {
						evCourant = evCourant.getSuivant();
					}
					else {
						break;
					}
				}
				e.setSuivant(evCourant.getSuivant());
				evCourant.setSuivant(e);
			}
			
		}
		
	}
	
	public void suppPremier() {
		if (this.premier != null) {
			this.premier = this.premier.getSuivant();
		}
	}
}
