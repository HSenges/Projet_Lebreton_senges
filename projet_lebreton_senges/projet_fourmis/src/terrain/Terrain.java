package terrain;

import entites.Nourriture;
import interfacesJeu.IParcourir;

/**
 * Classe à instanciation unique, appellée au début de la simulation par l'Arbitre
 * le Terrain recense les méthodes de déplacements et enregistre les différents
 * objets implantés sur lui (raison pour laquelle il apparaît souvent 
 * en paramètre de leurs méthodes)
 */
public class Terrain implements IParcourir{
	
	//Attributs
	private Coordonnee[] coord; 	//registre des coordonnées, en bijection avec l'annuaire
	private Case[] annuaire;		//idem pour les Cases
	private int largeur;
	private int longueur;
	private Nourriture[] listNourr;	//idem pour les sources de nourriture
	
	//Accesseurs
	public int getLargeur() {
		return largeur;
	}
	public int getLongeur() {
		return longueur;
	}
	public Coordonnee[] getCoord() {
		return coord;
	}
	//Accesseur à une coordonnée d'après son identifiant i
	public Coordonnee getCoordonnee(int i) {
		return coord[i];
	}
	//Accesseur à l'indice dans Coord d'après X et Y
	public int getIdCoord(int x, int y) {
		return x*this.longueur+y;
	}
	
	public Case[] getAnnuaire() {
		return annuaire;
	}
	public Nourriture[] getListNourr() {
		return listNourr;
	}
	
	//Mutateurs
	//(inexistants car attributs immuables durant le simulation)
	
	//Constructeur
	public Terrain(int l, int L, int nbNourr){
		this.largeur = l;
		this.longueur = L;
		this.listNourr = new Nourriture[nbNourr];
		// création des attributs vides
		Coordonnee[] coords = new Coordonnee[L*l];
		Case[] ann = new Case[L*l];
		// initialisation des attributs
		int k = 0;
		for (int i = 0; i<l; i=i+1){
			for (int j = 0; j<L; j=j+1){
					Coordonnee C =  new Coordonnee(i,j);
					Case case_k = new Case(k, null, 0, 0); //ajout des cases
					coords[k]=C;
					ann[k]=case_k;
					k += 1;
		}}
		// affectation attributs initialisés
		this.coord = coords;
		this.annuaire = ann;
		this.setBords();
	}
		
	//Méthodes
	
	public void setBords() {
		int L = this.getLongeur();
		int l = this.getLargeur();
		for (int i=0; i<this.largeur*this.longueur; i++) {
			Coordonnee c = this.getCoord()[i];
			if (c.getX()==0) {
				this.getAnnuaire()[i].setOccupation(typeOccupation.Bord);
			}
			else if (c.getX()==l-1) {
				this.getAnnuaire()[i].setOccupation(typeOccupation.Bord);
			}
			else if (c.getY()==0) {
				this.getAnnuaire()[i].setOccupation(typeOccupation.Bord);
			}
			else if (c.getY()==L-1) {
				this.getAnnuaire()[i].setOccupation(typeOccupation.Bord);
			}
		}
	}
	
	//Rafraîchit la piste de phéromones à chaque tour, appellée par Arbitre
	/** @see Arbitre.run() */
	public void diminPherom() {
		Case[] cases = this.annuaire;
		for (int i=0; i<cases.length; i++) {
			Case c = cases[i];
			if (c.getOdeurNourriture()>0) {
				c.setOdeurNourriture(c.getOdeurNourriture()-1);
			}
			if (c.getOdeurDanger()>0) {
				c.setOdeurDanger(c.getOdeurDanger()-1);
			}			
		}
	}
}
