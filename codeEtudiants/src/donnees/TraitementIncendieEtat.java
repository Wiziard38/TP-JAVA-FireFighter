package donnees;

/**Traitmement représente tous les états possibles d'un robot:
 * -traite: pas encore éteind mais un robot y travaille!
 * -eteind: le feu est éteind
 * -rien: le feu est encore présent et aucun robot s'en occupe*/
public enum TraitementIncendieEtat {
	traite, eteind, rien
}
