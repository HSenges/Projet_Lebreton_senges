package entites;

import java.util.ArrayList;

import interfacesJeu.IParcourir;
import interfacesJeu.IAttaquer;
import terrain.Coordonnee;
import terrain.Terrain;
import terrain.typeOccupation;

/**
 * Classe dont les instances générées par le Terrain se déplacent pseudo-aléatoirement jusqu'à
 * rencontrer des fourmis ou la fourmilière. Elles passent alors en phase d'attaque. 
 * Attirées à intervalles réguliers vers la fourmilière. 
 */
public class Ennemi extends Animal implements IAttaquer, IParcourir {
	
	//Constructeur
	public Ennemi(Terrain terre) {
		super(terre);
		Coordonnee pos = this.posAleat(terre);
		this.setPosition(pos);
		this.defForme(pos, terre);
		this.setVision(4);
		this.defZoneVision(terre);
		this.setType(typeOccupation.Ennemi);
		this.setVitesse(1);
		this.setDegat(1);
		this.setPv(10);
		this.implanter(terre);
	}
	
	//Méthodes
	@Override
	/**
	 * Fonction définissant la forme de l'ennemi à partir de sa position, sous
	 * forme d'une collection de Coordonnee, et la rentrant dans l'attribut associé
	 * @param pos : Coordonnee
	 * @param terre : Terrain
	 */
	public void defForme(Coordonnee pos, Terrain terre) {
		Coordonnee[] cases = new Coordonnee[1];
		cases[0] = pos;
		this.setForme(cases);
	}
	
	/**
	 * Fonction visant à détecter les fourmis présentes dans le
	 * champ de vision (déjà défini) de l'ennemi
	 * @param terre : Terrain
	 * @return [ ] : ArrayList[Coordonnee]
	 */
	public ArrayList<Coordonnee> detecterFourmi(Terrain terre) {
		
		ArrayList<Coordonnee> C = new ArrayList<Coordonnee>();
		
		for (int i=0; i<this.getZoneVision().length; i++) {
			Coordonnee c = this.getZoneVision()[i];
			if (c==null) {
				// cas n°1 : la coordonnee se situe en bordure du terrain ou au-delà
				continue;
			}
			else if (terre.getAnnuaire()[terre.getIdCoord(c.getX(), c.getY())].isVierge()) {
				/* cas n°2 : la coordonnee est dans le terrain mais la case 
				correspondante ne renferme aucune information*/
				continue;
			}
			else if (terre.getAnnuaire()[terre.getIdCoord(c.getX(), c.getY())].getOccupation()==typeOccupation.FourmiEclaireuse ||
					terre.getAnnuaire()[terre.getIdCoord(c.getX(), c.getY())].getOccupation()==typeOccupation.FourmiTransporteuse ||
					terre.getAnnuaire()[terre.getIdCoord(c.getX(), c.getY())].getOccupation()==typeOccupation.FourmiCombattante){
				/* cas n°3 : la case correspondante renferme une information
				 * d'occupation par une fourmi (eclaireuse, transporteuse ou combattante) */
				C.add(c);
			}
		}
		return C;
	}
	
	/**
	 * Fonction déterminant un mouvement aléatoire parmi les quatres points
	 * cardinaux, modifiant le terrain en conséquence et actualisant la position
	 * de l'ennemi.
	 * @param terre : Terrain
	 */
	public void deplacAleat(Terrain terre) {
		Coordonnee newpos = IParcourir.caseAleat(this.getPosition(), terre);
		
		terre.getAnnuaire()[terre.getIdCoord(this.getPosition().getX(), this.getPosition().getY())].setOccupation(null);
		terre.getAnnuaire()[terre.getIdCoord(this.getPosition().getX(), this.getPosition().getY())].setVierge();
		
		terre.getAnnuaire()[terre.getIdCoord(newpos.getX(), newpos.getY())].setOccupation(typeOccupation.FourmiEclaireuse);
		terre.getAnnuaire()[terre.getIdCoord(newpos.getX(), newpos.getY())].setVierge();
		
		this.setPosition(newpos);
	}
	
	@Override
	/**
	 * Fonction déterminant une coordonnee aleatoire pour implanter
	 * l'ennemi dans un des 4 grands angles, modifiant le terrain en 
	 * conséquence et actualisant la position de l'ennemi.
	 * @param terre : Terrain
	 * @return : Coordonnee
	 */
	public Coordonnee posAleat(Terrain terre) {
		int X = IParcourir.defNbAleat((int)(terre.getLargeur()/4));
		int Y = IParcourir.defNbAleat((int)(terre.getLongeur()/4));
		Coordonnee c1 = terre.getCoord()[terre.getIdCoord(X, Y)];
		Coordonnee c2 = terre.getCoord()[terre.getIdCoord(terre.getLargeur()-(X+1), Y)];
		Coordonnee c3 = terre.getCoord()[terre.getIdCoord(X, terre.getLongeur()-(Y+1))];
		Coordonnee c4 = terre.getCoord()[terre.getIdCoord(terre.getLargeur()-(X+1), terre.getLongeur()-(Y+1))];
		Coordonnee[] C = new Coordonnee[4];
		C[0] = c1;	C[1] = c2;	C[2] = c3;	C[3] = c4;
		Coordonnee c = C[IParcourir.defNbAleat(4)];
		this.defForme(c, terre);
		for (Coordonnee f : this.getForme()) {
			if (terre.getAnnuaire()[terre.getIdCoord(f.getX(), f.getY())].getOccupation()!=null) {
				c = posAleat(terre);
			}
		}
		return c;
	}
	
	/**
	 * Fonction booléenne renvoyant si l'ennemi est mort et doit être
	 * retiré du jeu.
	 * @param terre : Terrain
	 * @return : boolean
	 */
	public boolean estMort(Terrain terre) {
		return(this.getPv()==0);	
	}
}
