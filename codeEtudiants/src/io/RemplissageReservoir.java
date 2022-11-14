package io;

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
	
	private boolean aCoteEau() {
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
		return caseN.getNature() == NatureTerrain.EAU |
				caseS.getNature() == NatureTerrain.EAU |
				caseE.getNature() == NatureTerrain.EAU |
				caseW.getNature() == NatureTerrain.EAU;
	}
	
	private boolean peutSeRemplir() {
		switch (this.robot.getType()) {
		case "DRONE":
			return robot.getPosition().getNature() == NatureTerrain.EAU;
		default:
			return aCoteEau();
		}
	}
	@Override
	public void execute() {
		if (peutSeRemplir()){
			System.out.println("Remplissage terminé!");
			robot.setEauRestante(this.EauRempli);
		}
	}

}
