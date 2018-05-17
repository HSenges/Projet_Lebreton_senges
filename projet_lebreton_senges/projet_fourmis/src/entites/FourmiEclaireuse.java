package entites;

import interfacesJeu.IParcourir;
import main.Arbitre;
import terrain.Coordonnee;
import terrain.Terrain;
import terrain.typeOccupation;

public class FourmiEclaireuse extends FourmiOuvriere implements IParcourir {
	
	// Attributs
	private Coordonnee expansion;
	
	//Accesseurs
	public Coordonnee getExpansion() {
		return expansion;
	}
	
	//Mutateurs
	public void setExpansion(Coordonnee expansion) {
		this.expansion = expansion;
	}

	//Constructeur
	public FourmiEclaireuse(Coordonnee pos, Terrain terre) {
		super(terre);
		this.setPosition(pos);
		this.setVision(3);
		this.defForme(pos, terre);
		this.setOdorat(5);
		this.defZoneVision(terre);
		this.defZoneOdorat(terre);
		this.setVitesse(2);
		this.setType(typeOccupation.FourmiEclaireuse);
		this.implanter(terre);
	}

	@Override
	/**
	 * Fonction permettant de définir une forme, sur le terrain, pour l'objet à
	 * partir de sa position et de la rentrer dans son attribut correspondant.
	 * @param pos : Coordonnee
	 * @param terre : Terrain
	 */
	public void defForme(Coordonnee pos, Terrain terre) {
		Coordonnee[] cases = new Coordonnee[1];
		cases[0] = pos;
		this.setForme(cases);
	}
	

	@Override
	/**
	 * Fonction permettant à une fourmi de suivre, tour après tour,
	 * les phéromones d'intérêt, ce qui est sans intérêt pour les fourmis eclaireuses.
	 * @param terre : Terrain
	 * @param fourm : Fourmiliere
	 */
	public void suivre(Terrain terre, Fourmiliere fourm, Arbitre arb) {
		// TODO Auto-generated method stub		
	}
	
	public void defExpansion(Fourmiliere fourm, Terrain terre) {
		
		if (this.getPosition().getX() < fourm.getPosition().getX() &&
			this.getPosition().getY() < fourm.getPosition().getY()) {
			this.expansion = terre.getCoordonnee(terre.getIdCoord(1, 1));
			}
		if (this.getPosition().getX() < fourm.getPosition().getX() &&
			this.getPosition().getY() > fourm.getPosition().getY()) {
				this.expansion = terre.getCoordonnee(terre.getIdCoord(1, terre.getLongeur()-2));
			}
		if (this.getPosition().getX() > fourm.getPosition().getX() &&
			this.getPosition().getY() < fourm.getPosition().getY()) {
				this.expansion = terre.getCoordonnee(terre.getIdCoord(terre.getLargeur()-2, 1));
			}
		if (this.getPosition().getX() > fourm.getPosition().getX() &&
			this.getPosition().getY() > fourm.getPosition().getY()) {
				this.expansion = terre.getCoordonnee(terre.getIdCoord(terre.getLargeur()-2, terre.getLongeur()-2));
			}
		
	}














}