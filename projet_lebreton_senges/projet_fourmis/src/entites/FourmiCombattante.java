package entites;

import java.util.ArrayList;

import interfacesJeu.IAttaquer;
import interfacesJeu.IParcourir;
import main.Arbitre;
import terrain.Coordonnee;
import terrain.Terrain;
import terrain.typeOccupation;

public class FourmiCombattante extends Fourmi implements IAttaquer, IParcourir {
	
	
	
	//Constructeur
	public FourmiCombattante(Coordonnee pos, Terrain terre) {
		super(terre);
		this.setPosition(pos);
		this.defForme(pos, terre);
		this.setVision(4);
		this.defZoneVision(terre);
		this.setOdorat(3);
		this.defZoneOdorat(terre);
		this.setType(typeOccupation.FourmiCombattante);
		this.setPv(3);
		this.setVitesse(1);
		this.setDegat(2);
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
	
	/**
	 * Fonction permettant à une fourmi combattante de connaître la position de la
	 * plus ancienne coordonnee où a été laissé un phéromone 'danger', 
	 * dans sa zone d'odorat.
	 * @param terre :Terrain
	 * @return : Coordonnee
	 */
	public Coordonnee detecterPheromoneDanger(Terrain terre) {
		// dans le cas où les phéromones 'danger' fonctionnent comme les 'nourriture'
		Coordonnee C = null;
		int danger = 40;
		
		for (int i=0; i<this.getZoneOdorat().length; i++) {
			Coordonnee c = this.getZoneOdorat()[i];
			if (c==null) {
				// cas n°1 : la coordonnee se situe en bordure du terrain ou au-delà
				continue;
			}
			else if (terre.getAnnuaire()[terre.getIdCoord(c.getX(), c.getY())].isVierge()) {
				/* cas n°2 : la coordonnee est dans le terrain mais la case 
				correspondante ne renferme aucune information*/
				continue;
			}
			else if (terre.getAnnuaire()[terre.getIdCoord(c.getX(), c.getY())].getOdeurDanger()<=danger
					&& terre.getAnnuaire()[terre.getIdCoord(c.getX(), c.getY())].getOdeurDanger()>0){
				/* cas n°3 : la case correspondante renferme une trace
				 * de pheromone danger la plus ancienne (plus proche de 0) */
				C=c;
			}
		}
		return C;
	}

	@Override
	/**
	 * Fonction permettant à la fourmi combattante de suivre, tour après tour,
	 * les phéromones danger qui sont à l'origine de sa génération sur le terrain.
	 * @param terre : Terrain
	 * @param fourm : Fourmiliere
	 */
	public void suivre(Terrain terre, Fourmiliere fourm, Arbitre arb) {
		
		Coordonnee but = null;
		
		ArrayList<Coordonnee> E = this.detecterEnnemis(terre);
		if (E.isEmpty()==false) {
			but = this.detecterEnnemis(terre).get(IParcourir.defNbAleat(this.detecterEnnemis(terre).size()));
		}
		
		if (but==null) {
			but = this.detecterPheromoneDanger(terre);
		}
		
		if (but==null) { // aucun phéromone n'est détecté
			this.revenir(terre, fourm, arb);
		}
		else {
			// suit les pheromones tant qu'elle les détecte
			Coordonnee via = IParcourir.calcPCC(this.getPosition(), but, terre);
			IParcourir.deplacement(this.getPosition(), via, typeOccupation.FourmiCombattante, terre);
			this.setPrecedente(this.getPosition());
			this.setPosition(via);
			this.defForme(via, terre);
			this.defZoneOdorat(terre);
			this.defZoneVision(terre);
		}
	}
}
