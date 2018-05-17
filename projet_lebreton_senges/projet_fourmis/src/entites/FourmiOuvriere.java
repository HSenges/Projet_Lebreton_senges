package entites;

import main.Arbitre;
import terrain.Coordonnee;
import terrain.Terrain;
import terrain.typeOccupation;

public abstract class FourmiOuvriere extends Fourmi {
	
	//Constructeur
	public FourmiOuvriere(Terrain terre) {
		super(terre);
	}
	
	//Méthodes
	
	/**
	 * Fonction permettant à une fourmi eclaireuse ou transporteuse de déposer
	 * un phéromone danger sur la case qu'elle occupe.
	 * @param terre
	 */
	public void libererPheromoneDanger(Terrain terre) {
		
		Coordonnee pos = this.getPosition();
		terre.getAnnuaire()[terre.getIdCoord(pos.getX(), pos.getY())].setOdeurDanger(Arbitre.dureePhD);
	}
	
	/**
	 * Fonction permettant à une fourmi eclaireuse ou transporteuse de déposer
	 * un phéromone nourriture sur la case qu'elle occupe.
	 * @param terre
	 */
	public void libererPheromoneNourriture(Terrain terre) {
		
		Coordonnee pos = this.getPosition();
		terre.getAnnuaire()[terre.getIdCoord(pos.getX(), pos.getY())].setOdeurNourriture(Arbitre.dureePhN);
	}
	
	/**
	 * Fonction visant à détecter UN site de nourriture présent
	 * dans le champ d'odorat (déjà défini) de la fourmi
	 * @param terre : Terrain
	 * @return [x,y] : Coordonnee
	 */
	public Coordonnee detecterNourriture(Terrain terre) {
		
		Coordonnee C = null;
		
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
			else if (terre.getAnnuaire()[terre.getIdCoord(c.getX(), c.getY())].getOccupation()==typeOccupation.Nourriture){
				/* cas n°3 : la case correspondante renferme une information
				 * d'occupation par un site de nourriture */
				C=c;
			}
		}
		return C;
	}

}
