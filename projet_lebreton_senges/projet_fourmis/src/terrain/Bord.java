package terrain;

import entites.Entite;

/**Classe permettant de caractériser les 
 * bords du terrain.
 * Evite les exceptions d'implantation
 * d'objets hors des limites du Terrrain
 */
public class Bord extends Entite{
	
	//Constructeur
	public Bord(Coordonnee c, Terrain terre) {
		super(terre);
		this.setPosition(c);
		this.defForme(c, terre);
		this.setType(typeOccupation.Bord);
		this.implanter(terre);
	}
	
	//Méthode 
			
	@Override
	//Les bords occupent un espace d'une case
	protected void defForme(Coordonnee pos, Terrain terre) {
		Coordonnee[] cases = new Coordonnee[1];
		cases[0] = pos;
		this.setForme(cases);
	}
}
