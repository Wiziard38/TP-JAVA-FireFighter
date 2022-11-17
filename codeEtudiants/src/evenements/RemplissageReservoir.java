package evenements;
import io.Carte;
import io.Case;
import io.Direction;
import io.NatureTerrain;
import robots.Robot;

/**RemplissageReservoir permet à un robot de se remplir d'une certaine quantité d'eau*/
public class RemplissageReservoir extends Evenement {
	
	private Robot robot;
	private Carte carte;
	private long EauRempli;
	
	public RemplissageReservoir(Carte carte, Robot robot, long eau, long date) {
		super(date);
		this.carte = carte;
		this.robot = robot;
		this.EauRempli = eau;
	}
	
	/**aCoteEau renvoie true si le robot se trouve à côté d'une case de type eau*/
	private boolean aCoteEau() {
		Case caseAct = robot.getPosition();
	
		boolean flag = false;
		if(carte.voisinExiste(caseAct, Direction.NORD)) {
			flag = flag | (carte.getVoisin(caseAct, Direction.NORD).getNature() == NatureTerrain.EAU);
		}
		if(carte.voisinExiste(caseAct, Direction.SUD)) {
			flag = flag | (carte.getVoisin(caseAct, Direction.SUD).getNature() == NatureTerrain.EAU);
		}
		if(carte.voisinExiste(caseAct, Direction.EST)) {
			flag = flag | (carte.getVoisin(caseAct, Direction.EST).getNature() == NatureTerrain.EAU);
		}
		if(carte.voisinExiste(caseAct, Direction.OUEST)) {
			flag = flag | (carte.getVoisin(caseAct, Direction.OUEST).getNature() == NatureTerrain.EAU);
		}
		return flag;
	}
	
	/**peutSeRemplir renvoie true si le robot peut se remplir*/
	private boolean peutSeRemplir() {

		switch (this.robot.getType()) {
		case DRONE:
			return robot.getPosition().getNature() == NatureTerrain.EAU;
		default:
			return aCoteEau();
		}
	}
	@Override
	public void execute() {
		if (peutSeRemplir()){
			robot.setEauRestante(this.EauRempli);
		}
	}

}
