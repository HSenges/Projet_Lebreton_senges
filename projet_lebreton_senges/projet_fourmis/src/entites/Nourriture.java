package entites;

import terrain.Coordonnee;
import terrain.Terrain;
import terrain.typeOccupation;

public class Nourriture extends Entite{
	
	//Attributs
	private int quantite;
	
	//Accesseurs
	public int getQuantite() {
		return this.quantite;
	}
	
	//Mutateurs
	public void setQuantite(int Quantite) {
		this.quantite=Quantite;
	}
	
	//Constructeurs
	public Nourriture(int qt, Terrain terre) {
		super(terre);
		Coordonnee pos = this.posAleat(terre);
		this.setQuantite(qt); //valeur choisie
		this.setType(typeOccupation.Nourriture);
		this.setPosition(pos);
		this.defForme(pos, terre);
		this.implanter(terre);
	}
	
	//Méthodes
	
	/**
	 * Fonction gérant le prélèvement de nourriture et faisant appel à testFin
	 * @param terre
	 */
	public void prelever(int charge, Terrain terre) {
		if (this.quantite>0) {
			this.setQuantite(this.getQuantite()-charge);
			this.testFin(terre);
		}		
	}
	
	/**
	 * Fonction booléenne retournant si le site de nourriture doit être retirer
	 * du terrain, selon une condition d'épuisement du stock.
	 * @param terre : Terrain
	 * @return : boolean
	 */
	public boolean testFin(Terrain terre) {
		return (this.getQuantite()==0);
	}
	
	@Override
	/**
	 * Fonction permettant de définir une forme, sur le terrain, pour l'objet à
	 * partir de sa position et de la rentrer dans son attribut correspondant.
	 * @param pos : Coordonnee
	 * @param terre : Terrain
	 */
	protected void defForme(Coordonnee pos, Terrain terre) {
		Coordonnee[] cases = new Coordonnee[1];
		cases[0] = pos;
		this.setForme(cases);
	}
}
