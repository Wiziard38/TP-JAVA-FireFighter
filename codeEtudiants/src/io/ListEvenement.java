package io;

import evenements.Evenement;

public class ListEvenement {
	/**ListEvenement implémente une liste chainé d'événement triée par rapport à la date d'éxecution de 
	 * l'événement avec une référence vers le premier et le dernier*/
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
		/**Cette fonction ajoute un événement dans la liste à la bonne place i.e la liste reste
		 * triée par rapport à la date des événements*/
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
		/** ajoute à la fin de la liste, cette fonction est moins couteuses que ajouteEvenement grâce 
		 * à la référence vers la fin de la liste
		 */
		this.dernier.setSuivant(e);
		this.dernier = this.dernier.getSuivant();
	}
	
	public void suppPremier() {
		if (this.premier != null) {
			this.premier = this.premier.getSuivant();
		}
	}
}
