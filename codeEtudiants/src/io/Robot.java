package io;

import io.NatureTerrain;

public abstract class Robot {
	private Case position;
	private int vitesse;
	private final double tailleReservoir;
	private double quantiteEau = 0;

	
	public Robot(Case init_position, int vitesse, double tailleReservoir) {
		if (vitesse < 0) {
			throw new IllegalArgumentException("La vitesse doit etre positive !");
		}

		this.position = init_position;
		this.vitesse = this.setVitesse;
		this.tailleReservoir = tailleReservoir;
		
		this.setEauRestante(tailleReservoir);
	}

	
	public Case getPosition() {
		return this.position;
	}
	
	public void setPosition(Case newPosition) {
		if (!this.peutDeplacer(newPosition.getNature())) {
			throw new IllegalArgumentException("Tentative de deplacement sur case interdite !");
		this.position = newPosition;
	}
	
	public int getEauRestante() {
		return this.quantiteEau;
	}
	
	public void setEauRestante(double volume) {
		if (volume < 0) {
			throw new IllegalArgumentException("Volume d'eau négatif !");
		}
		this.quantiteEau = volume;
	}

	public double deverseEau(double volume) {
		double EauRestante = this.getEauRestante();

		if (EauRestante < volume) {
			this.setEauRestante(0);
			return EauRestante;
		}
		this.setEauRestante(EauRestante-volume);
		return volume;
	}

	public void setVitesse(int vitesse) {
		if (vitesse < 0) {
			throw new IllegalArgumentException("Vitesse négative !");
		}
		this.vitesse = vitesse;
	}

	public int getVitesse(NatureTerrain nature) {
		return this.getVitesse();
	}


	public abstract boolean peutDeplacer(NatureTerrain terrain);

	public abstract String getNameRobot();
}



public class RobotDrone extends Robot {
	
	public RobotDrone(Case position) {
		super(position, 100, 10000);
	}
	
	public RobotDrone(Case position, int vitesse) {
		super(position, vitesse, 10000);
	}

	@Override
	public void setVitesse(int vitesse) {
		if (vitesse > 150) {
			throw new IllegalArgumentException("Vitesse trop grande !");
		}
		super.setVitesse(vitesse);
	}

	@Override
	public boolean peutDeplacer(NatureTerrain terrain) {
		return true;
	}

	@Override
	public String getNameRobot() {
		return "Drone";
	}
}


public class RobotARoues extends Robot {
	
	public RobotDrone(Case position) {
		super(position, 80, 5000);
	}
	
	public RobotDrone(Case position, int vitesse) {
		super(position, vitesse, 5000);
	}


	@Override
	public boolean peutDeplacer(NatureTerrain terrain) {
		return (terrain == NatureTerrain.TERRAIN_LIBRE || terrain == NatureTerrain.HABITAT);
	}

	@Override
	public String getNameRobot() {
		return "Robot a roues";
	}
}


public class RobotAChenilles extends Robot {
	
	public RobotDrone(Case position) {
		super(position, 60, 2000);
	}
	
	public RobotDrone(Case position, int vitesse) {
		super(position, vitesse, 2000);
	}


	@Override
	public void setVitesse(int vitesse) {
		if (vitesse > 80) {
			throw new IllegalArgumentException("Vitesse trop grande !");
		}
		super.setVitesse(vitesse);
	}

	@Override
	public int getVitesse(NatureTerrain nature) {
		return (nature == NatureTerrain.FORET ? this.getVitesse() / 2 : this.getVitesse());
	}

	@Override
	public boolean peutDeplacer(NatureTerrain terrain) {
		return !(terrain == NatureTerrain.EAU || terrain == NatureTerrain.ROCHE);
	}

	@Override
	public String getNameRobot() {
		return "Robot a chenilles";
	}
}


public class RobotAPattes extends Robot {
	
	public RobotDrone(Case position) {
		super(position, 30, 2000);
	}


	@Override
	public int getVitesse(NatureTerrain nature) {
		return (nature == NatureTerrain.ROCHE ? 30 : 10);
	}

	@Override
	public boolean peutDeplacer(NatureTerrain terrain) {
		return !(terrain == NatureTerrain.EAU);
	}

	@Override
	public String getNameRobot() {
		return "Robot a pattes";
	}
}