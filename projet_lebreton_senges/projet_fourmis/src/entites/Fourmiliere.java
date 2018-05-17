package entites;

import java.util.ArrayList;

import interfacesJeu.IParcourir;
import terrain.Coordonnee;
import terrain.Terrain;
import terrain.typeOccupation;

public class Fourmiliere extends Organisme implements IParcourir{
	
	//Attributs
	private ArrayList<FourmiEclaireuse> FourmisE;
	private ArrayList<FourmiTransporteuse> FourmisT;
	private ArrayList<FourmiCombattante> FourmisC;
	//private ArrayList<Fourmi> Fourmis;
	private int population;
	private Coordonnee[] zoneFourmis;
	private int stockNourr;
	
	//Accesseurs
	public int getPopulation() {
		return population;
	}
	public Coordonnee[] getZoneFourmis() {
		return zoneFourmis;
	}
	public ArrayList<FourmiEclaireuse> getFourmisE() {
		return FourmisE;
	}
	public ArrayList<FourmiTransporteuse> getFourmisT() {
		return FourmisT;
	}
	public ArrayList<FourmiCombattante> getFourmisC() {
		return FourmisC;
	}
	public int getStockNourr() {
		return stockNourr;
	}
	
	//Mutateurs
	public void setPopulation(int population) {
		this.population = population;
	}
	public void setStockNourr(int s) {
		this.stockNourr = s;
	}

	//Constructeur
	public Fourmiliere(Terrain terre) {
		super(terre);
		Coordonnee pos = this.posAleat(terre);
		//Coordonnee pos = terre.getCoordonnee(terre.getIdCoord(terre.getLargeur()/2, terre.getLongeur()/2));
		this.stockNourr = 0;
		this.setType(typeOccupation.Fourmiliere);
		this.setPosition(pos);
		this.defForme(pos, terre);
		this.defZoneFourmis(terre);
		this.defZoneVision(terre);
		this.setPv(50);
		this.FourmisE=new ArrayList<FourmiEclaireuse>();
		this.FourmisT=new ArrayList<FourmiTransporteuse>();
		this.FourmisC=new ArrayList<FourmiCombattante>();
		this.implanter(terre);
	}
	
	//Méthodes
	
	public boolean estMort() {
		return (this.getPv()==0);
	}
	
	/**
	 * Fonction permettant d'augmenter la population (int) de la fourmiliere de 1.
	 */
	public void augmPop() {
		this.setPopulation(this.getPopulation()+1);
	}
	
	/**
	 * Fonction permettant de diminuer la population (int) de la fourmiliere de 1.
	 */
	public void diminPop() {
		this.setPopulation(this.getPopulation()-1);
	}
	
	
	/**
	 * Fonction qui créer une nouvelle fourmi eclaireuse sur une des cases
	 * adjacentes à la fourmiliere (si non occupée), l'ajoute à la collection de fourmis de
	 * la fourmiliere et en augmente la population de 1.
	 * @param terre : Terrain
	 */
	public void genFourmiEclaireuse(Terrain terre) {
		
		ArrayList<Coordonnee> caseslibres = new ArrayList<Coordonnee>();
		for (Coordonnee c : this.getZoneFourmis()) {
			if (terre.getAnnuaire()[terre.getIdCoord(c.getX(), c.getY())].getOccupation()==null) {
				caseslibres.add(c);
			}
		}
		
		if (caseslibres.isEmpty()==false) {
			Coordonnee pos = caseslibres.get(IParcourir.defNbAleat(caseslibres.size()));
			FourmiEclaireuse f = new FourmiEclaireuse(pos, terre);
			f.defExpansion(this, terre);
			this.getFourmisE().add(f);
			this.augmPop();
		}
	}
	
	/**
	 * Fonction qui créer une nouvelle fourmi transporteuse sur une des cases
	 * adjacentes à la fourmiliere (si non occupée), l'ajoute à la collection de fourmis de
	 * la fourmiliere et en augmente la population de 1.
	 * @param terre : Terrain
	 */
	public void genFourmiTransporteuse(Terrain terre) {
		
		ArrayList<Coordonnee> caseslibres = new ArrayList<Coordonnee>();
		for (Coordonnee c : this.getZoneFourmis()) {
			if (terre.getAnnuaire()[terre.getIdCoord(c.getX(), c.getY())].getOccupation()==null) {
				caseslibres.add(c);
			}
		}
		
		if (caseslibres.isEmpty()==false) {
			Coordonnee pos = caseslibres.get(IParcourir.defNbAleat(caseslibres.size()));
			FourmiTransporteuse f = new FourmiTransporteuse(pos, terre);
			this.getFourmisT().add(f);
			this.augmPop();
		}
	}
	
	/**
	 * Fonction qui créer une nouvelle fourmi combattante sur une des cases
	 * adjacentes à la fourmiliere (si non occupée), l'ajoute à la collection de fourmis de
	 * la fourmiliere et en augmente la population de 1.
	 * @param terre : Terrain
	 */
	public void genFourmiCombattante(Terrain terre) {
		
		ArrayList<Coordonnee> caseslibres = new ArrayList<Coordonnee>();
		for (Coordonnee c : this.getZoneFourmis()) {
			if (terre.getAnnuaire()[terre.getIdCoord(c.getX(), c.getY())].getOccupation()==null) {
				caseslibres.add(c);
			}
		}
		
		if (caseslibres.isEmpty()==false) {
			Coordonnee pos = caseslibres.get(IParcourir.defNbAleat(caseslibres.size()));
			FourmiCombattante f = new FourmiCombattante(pos, terre);
			this.getFourmisC().add(f);
			this.augmPop();
		}
	}
	
	/**
	 * Fonction définissant la zone sur laquelle peuvent être créées les fourmis,
	 * correspondant à l'ensemble des cases adjacentes à la fourmiliere, puis
	 * l'entrant dans l'attribut 'zoneFourmis' de la fourmiliere.
	 * @param terre : Terrain
	 */
	public void defZoneFourmis(Terrain terre) {
		Coordonnee[] z = new Coordonnee[16];
		int f = terre.getIdCoord(this.getPosition().getX(), this.getPosition().getY());
		int L = terre.getLongeur();
		for (int i=0; i<5; i++) {
			z[i] = terre.getCoordonnee(f-2*L-2+i);
			z[z.length-1-i] = terre.getCoordonnee(f+2*L+2-i);
		}
		z[5] = terre.getCoordonnee(f-L-2);
		z[6] = terre.getCoordonnee(f-L+2);
		z[7] = terre.getCoordonnee(f-2);
		z[8] = terre.getCoordonnee(f+2);
		z[9] = terre.getCoordonnee(f+L-2);
		z[10] = terre.getCoordonnee(f+L+2);
		this.zoneFourmis=z;
	}

	@Override
	/**
	 * Fonction définissant la forme que prendra la fourmiliere sur le Terrain,
	 * soit un carré de 3x3 coordonnees centré sur la position de la fourmiliere, puis
	 * l'entrant dans l'attribut correspondant de la fourmiliere.
	 * @param pos : Coordonnee
	 * @param terre : Terrain
	 */
	protected void defForme(Coordonnee pos, Terrain terre) {
		
		int id = terre.getIdCoord(pos.getX(), pos.getY());
		
		Coordonnee[] cases = new Coordonnee[9];
		cases[0] = terre.getCoordonnee(id-terre.getLongeur()-1);
		cases[1] = terre.getCoordonnee(id-terre.getLongeur());
		cases[2] = terre.getCoordonnee(id-terre.getLongeur()+1);
		cases[3] = terre.getCoordonnee(id-1);
		cases[4] = pos;
		cases[5] = terre.getCoordonnee(id+1);
		cases[6] = terre.getCoordonnee(id+terre.getLongeur()-1);
		cases[7] = terre.getCoordonnee(id+terre.getLongeur());
		cases[8] = terre.getCoordonnee(id+terre.getLongeur()+1);

		this.setForme(cases);
	}
	
	@Override
	/**
	 * Fonction permettant de définir une position aléatoire d'implantation
	 * de la fourmiliere sur une coordonnee permettant l'implantation de sa forme
	 * sans se superposer aux autres entités pré-existantes ni au bord du terrain.
	 * @param terre : Terrain
	 * @return : Coordonnee
	 */
	public Coordonnee posAleat(Terrain terre) {
		int L = terre.getLongeur();
		int l = terre.getLargeur();
		int X = IParcourir.defNbAleat(l-l/2)+l/4;
		int Y = IParcourir.defNbAleat(L-L/2)+L/4;
		Coordonnee c = terre.getCoordonnee(terre.getIdCoord(X, Y));
		this.defForme(c, terre);
		return c;
	}
	
	public int getNbEclaireuses() {
		return this.getFourmisE().size();
	}
	public int getNbTransporteuses() {
		return this.getFourmisT().size();
	}
	public int getNbCombattantes() {
		return this.getFourmisC().size();
	}

}
