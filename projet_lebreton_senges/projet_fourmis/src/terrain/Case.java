package terrain;

/**Classe permettant de recueillir les informations 
 * du terrain appellées à évoluer au fil de la simulation.
 */
public class Case {
	
	//Attributs
	private int num;	/** @see Terrain.annuaire */
	private boolean vierge;	//Attribut remis à jour à chaque set, économise des opérations de vérifications
	
	private typeOccupation typeOccupation;
	/*Cet attribut est primordial car il permet
	la représentation graphique du Terrain via le type 
	d'occupation des cases qui le composent*/
	
	private int odeurNourriture;
	private int odeurDanger;
	
	//Accesseurs
	public int getNum() {
		return num;
	}
	public typeOccupation getOccupation() {
		return typeOccupation;
	}	
	public int getOdeurNourriture() {
		return odeurNourriture;
	}
	public int getOdeurDanger() {
		return odeurDanger;
	}
	public boolean isVierge() {
		return (this.vierge);
	}
	
	//Mutateurs
	public void setNum(int n) {
		this.num = n;
	}
	public void setOccupation(typeOccupation t) {
		this.typeOccupation = t;
		this.setVierge();
	}
	public void setOdeurNourriture(int odeurNourriture) {
		this.odeurNourriture = odeurNourriture;
		this.setVierge();
	}
	public void setOdeurDanger(int odeurDanger) {
		this.odeurDanger = odeurDanger;
		this.setVierge();
	}
	
	//méthode appellée après chaque autre set de cette classe 
	public void setVierge() {
		this.vierge = ((this.typeOccupation == null) && (odeurNourriture == 0) && (odeurDanger == 0));
	}
	
	//Constructeurs
	public Case(int n, typeOccupation occup, int nourr, int dang) {
		this.num = n;
		this.typeOccupation = occup;
		this.odeurDanger = dang;
		this.odeurNourriture = nourr;
		this.setVierge();
	}
	
	//Méthodes
	
	@Override
	public String toString() {
		return this.isVierge()+", "+this.getOccupation()+", "+this.getOdeurNourriture()+", "+this.getOdeurDanger();
	}
}
