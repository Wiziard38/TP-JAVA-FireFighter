package io;

public class ListEvenement {
	private Evenement premier;
	private Evenement dernier;
	
	public ListEvenement() {
		this.premier = null;
		this.dernier = null;
	}
	
	public Evenement getPremier() {
		return this.premier;
	}
	
	public void ajouteEvenement(Evenement e) {
		Evenement evCourant = premier;
		if (evCourant == null) {
			e.setSuivant(evCourant);
			this.premier = e;
			this.dernier = e;
		}
		else {
			if (evCourant.getDate() > e.getDate()) {
				e.setSuivant(evCourant);
				this.premier = e;
				this.dernier = evCourant;
			}
			else {
				while (evCourant.getSuivant() != null) {
					if (e.getDate() > evCourant.getSuivant().getDate()) {
						evCourant = evCourant.getSuivant();
					}
					else {
						break;
					}
				}
				e.setSuivant(evCourant.getSuivant());
				evCourant.setSuivant(e);
				if(this.dernier.getSuivant() != null) {
					this.dernier = this.dernier.getSuivant();
				}
			}
			
		}
	}
	
	public void ajouteFin(Evenement e) {
		this.dernier.setSuivant(e);
		this.dernier = this.dernier.getSuivant();
	}
	
	public void suppPremier() {
		if (this.premier != null) {
			this.premier = this.premier.getSuivant();
		}
	}
}
