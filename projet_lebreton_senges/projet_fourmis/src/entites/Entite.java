package entites;

import interfacesJeu.IParcourir;
import terrain.Coordonnee;
import terrain.Terrain;
import terrain.typeOccupation;

/**
 * Classe abstraite la plus haut placée dans l'arbre d'héritage. 
 * Tous les éléments physiques de la simulation en hérite.
 */
public abstract class Entite {
	
	// Attributs
	private Coordonnee position;
	private Coordonnee[] forme;
	private typeOccupation typeOccupation;
	
	//Accesseurs
	public Coordonnee getPosition() {
		return this.position;
	}
	public Coordonnee[] getForme() {
		return this.forme;
	}
	public typeOccupation getType() {
		return this.typeOccupation;
	}
	
	//Mutateurs
	public void setPosition(Coordonnee Position) {
		this.position = Position;
	}
	public void setForme(Coordonnee[] Forme) {
		this.forme = Forme;
	}
	public void setType(typeOccupation n) {
		this.typeOccupation = n;
	}
	
	//Constructeur
	public Entite(Terrain terre) {
		
	}
	//Méthodes
	
	/**
	 * Méthode abstraite détaillée par les différentes classes filles
	 * qui permet de définir une forme, sur le terrain, pour l'objet à
	 * partir de sa position.
	 * @param pos : Coordonnee
	 * @param terre : Terrain
	 */
	protected abstract void defForme(Coordonnee pos, Terrain terre);
	
	/**
	 * Fonction permettant d'entrer dans les données du terrain (les cases)
	 * celle relative à l'occupation de cette case par un des type d'occupation
	 * prédéfinis.
	 * @param terre : Terrain
	 */
	public void implanter(Terrain terre) {
		for (Coordonnee c : forme) {
			int id = terre.getIdCoord(c.getX(), c.getY());
			terre.getAnnuaire()[id].setOccupation(typeOccupation);
			terre.getAnnuaire()[id].setVierge();
		}
	}
	
	/**
	 * Fonction permettant de retirer du terrain toute information
	 * d'occupation du terrain par une entite, dans le cas où celle-ci est morte
	 * par exemple.
	 * @param terre : Terrain
	 */
	public void detruire(Terrain terre) {
		for (Coordonnee c : forme) {
			int id = terre.getIdCoord(c.getX(), c.getY());
			terre.getAnnuaire()[id].setOccupation(null);
			terre.getAnnuaire()[id].setVierge();
		}
	}
	
	/**
	 * Fonction permettant de définir une position aléatoire d'implantation
	 * de l'entité sur une coordonnee permettant l'implantation de sa forme
	 * sans se superposer aux autres entités pré-existantes ni au bord du terrain.
	 * @param terre : Terrain
	 * @return : Coordonnee
	 */
	public Coordonnee posAleat(Terrain terre) {
		Coordonnee c = terre.getCoord()[IParcourir.defNbAleat(terre.getLargeur()*terre.getLongeur())];
		this.defForme(c, terre);
		for (Coordonnee f : this.forme) {
			if (terre.getAnnuaire()[terre.getIdCoord(f.getX(), f.getY())].getOccupation()!=null) {
				c = posAleat(terre);
			}
		}
		return c;
	}
}
