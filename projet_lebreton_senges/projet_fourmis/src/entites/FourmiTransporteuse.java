package entites;

import interfacesJeu.IParcourir;
import main.Arbitre;
import terrain.Coordonnee;
import terrain.Terrain;
import terrain.typeOccupation;

public class FourmiTransporteuse extends FourmiOuvriere{
	
	//Attributs
	private int charge;
	private int chargeN;
	
	//Accesseur
	public int getCharge() {
		return charge;
	}
	public int getChargeN() {
		return chargeN;
	}
	
	//Mutateur
	public void setCharge(int Charge) {
		this.charge = Charge;
	}
	public void setChargeN(int ChargeN) {
		this.chargeN = ChargeN;
	}
	
	//Constructeur
	public FourmiTransporteuse(Coordonnee pos, Terrain terre) {
		super(terre);
		this.chargeN = 0;
		this.charge = 1;
		this.setPosition(pos);
		this.setVision(1);
		this.defForme(pos, terre);
		this.setOdorat(6);
		this.setVitesse(1);
		this.defZoneVision(terre);
		this.defZoneOdorat(terre);
		this.setType(typeOccupation.FourmiTransporteuse);
		this.implanter(terre);
	}
		
	//Méthodes
	
	/**
	 * Fonction permettant à la fourmi transporteuse de prélever de la
	 * nourriture sur un site de nourriture, autant que sa charge le permet.
	 * @param nourr : Nourriture
	 * @param terre : Terrain
	 */
	public void emporter(Nourriture nourr, Terrain terre) {
		nourr.prelever(this.charge, terre);
		this.chargeN = this.charge;
	}
	
	/**
	 * Fonction permettant à la fourmi transporteuse de déposer la nourriture
	 * qu'elle transporte dans le stock de la fourmiliere.
	 * @param fourm : Fourmiliere
	 */
	public void deposer(Fourmiliere fourm) {
		fourm.setStockNourr(fourm.getStockNourr()+this.getCharge());
		this.chargeN = 0;
	}


	@Override
	public void defForme(Coordonnee pos, Terrain terre) {
		Coordonnee[] cases = new Coordonnee[1];
		cases[0] = pos;
		this.setForme(cases);
	}

	public Coordonnee detecterPheromoneNourriture(Terrain terre) {
		
		Coordonnee C = null;
		int nourriture = Arbitre.dureePhN;
		
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
			else if (terre.getAnnuaire()[terre.getIdCoord(c.getX(), c.getY())].getOdeurNourriture()<=nourriture
					&& terre.getAnnuaire()[terre.getIdCoord(c.getX(), c.getY())].getOdeurNourriture()>0){
				/* cas n°3 : la case correspondante renferme une trace
				 * de pheromone danger la plus ancienne (plus proche de 0) */
				C=c;
			}
		}
		return C;
	}
	
	@Override
	public void suivre(Terrain terre, Fourmiliere fourm, Arbitre arb) {

		Coordonnee but = null/*this.detecterNourriture(terre)*/;
		
		if (but==null) {
			but = this.detecterPheromoneNourriture(terre);
		}
		
		if (but==null) { // aucun phéromone n'est détecté
			this.setRetour(true);
		}
		else {
			// suit les pheromones tant qu'elle les détecte
			Coordonnee via = IParcourir.calcPCC(this.getPosition(), but, terre);
			IParcourir.deplacement(this.getPosition(), via, this.getType(), terre);
			this.setPrecedente(this.getPosition());
			this.setPosition(via);
			this.defForme(via, terre);
			this.defZoneOdorat(terre);
			this.defZoneVision(terre);
		}
		
	}

}
