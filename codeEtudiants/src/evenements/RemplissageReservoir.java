package evenements;
import io.Carte;
import io.Case;
import io.Direction;
import io.NatureTerrain;
import robots.Robot;
import robots.RobotType;
public class RemplissageReservoir extends Evenement {
	/**RemplissageReservoir permet à un robot de se remplir d'une certaine quantité d'eau*/
	private Robot robot;
	private Carte carte;
	private long EauRempli;
	
	public RemplissageReservoir(Carte carte, Robot robot, long eau, long date) {
		super(date);
		this.carte = carte;
		this.robot = robot;
		this.EauRempli = eau;
	}
	
	private boolean aCoteEau() {
		/**aCoteEau renvoie true si le robot se trouve à côté d'une case de type eau*/
		Case caseN = robot.getPosition();
		Case caseS = robot.getPosition();
		Case caseE = robot.getPosition();
		Case caseW = robot.getPosition();
		Case caseAct = robot.getPosition();
		if(carte.voisinExiste(caseAct, Direction.NORD)) {
			caseN = carte.getVoisin(caseAct, Direction.NORD);
		}
		if(carte.voisinExiste(caseAct, Direction.SUD)) {
			caseS = carte.getVoisin(caseAct, Direction.SUD);
		}
		if(carte.voisinExiste(caseAct, Direction.EST)) {
			caseE = carte.getVoisin(caseAct, Direction.EST);
		}
		if(carte.voisinExiste(caseAct, Direction.OUEST)) {
			caseW = carte.getVoisin(caseAct, Direction.OUEST);
		}
		return caseN.getNature() == NatureTerrain.EAU ||
				caseS.getNature() == NatureTerrain.EAU ||
				caseE.getNature() == NatureTerrain.EAU ||
				caseW.getNature() == NatureTerrain.EAU;
	}
	
	private boolean peutSeRemplir() {
		/**peutSeRemplir renvoie true si le robot peut se remplir*/
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
