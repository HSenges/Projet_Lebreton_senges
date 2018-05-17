package entites;

import java.util.ArrayList;

import interfacesJeu.IAttaquer;
import interfacesJeu.IParcourir;
import main.Arbitre;
import terrain.Coordonnee;
import terrain.Terrain;
import terrain.typeOccupation;

/**
 * Classe abstraite regroupant les différents
 * comportements et caractéristiques communs
 * à toutes les fourmis.
 */
public abstract class Fourmi extends Animal implements IParcourir{
	
	//Attribut
	private int num;	//correspond à sa position dans Fourmilière.FourmisE/T/C
	private int odorat;	//@see Animal.vision, même principe mais pour la détection de phéromones
	private Coordonnee[] zoneOdorat;
	private boolean retour;		//état de la fourmi à l'instant t, permettant d'adapter son comportement au tour par tour
	private boolean danger;		//idem
	private Coordonnee precedente;
	
	//Accesseurs
	public int getNum() {
		return num;
	}
	public int getOdorat() {
		return odorat;
	}
	public Coordonnee[] getZoneOdorat() {
		return zoneOdorat;
	}
	public boolean getRetour() {
		return retour;
	}
	public boolean getDanger() {
		return danger;
	}
	public Coordonnee getPrecedente() {
		return precedente;
	}
	
	//Mutateurs
	public void setOdorat(int odorat) {
		this.odorat = odorat;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public void setZoneOdorat(Coordonnee[] ZoneOd) {
		this.zoneOdorat = ZoneOd;
	}
	public void setRetour(boolean b) {
		this.retour = b;
	}
	public void setDanger(boolean b) {
		this.danger = b;
	}
	public void setPrecedente(Coordonnee prec) {
		this.precedente = prec;
	}
	
	//Constructeur
	public Fourmi(Terrain terre) {
		super(terre);
		this.retour=false;
		this.danger=false;
		this.setDegat(1);
		this.setVitesse(1);
		this.setPv(1);
	}
	
	//Méthodes
	
	/**
	 * Fonction permettant de définir pour chaque nouvelle position de la
	 * fourmi les cases entrant dans sa zone d'odorat, d'après la valeur de
	 * son attribut 'odorat' qui en précise la portée.
	 * Les cases correspondant aux bords et au-delà prennent la valeur 'null'.
	 * @param terre : Terrain
	 */
	public void defZoneOdorat(Terrain terre) {
		
		int X = this.getPosition().getX();
		int Y = this.getPosition().getY();
		int l = terre.getLargeur();
		int L = terre.getLongeur();
		int O = this.odorat;
		int o = 2*O+1;
		int lim = O+1;
		
		Coordonnee[] od = new Coordonnee[o*o];
		
		// Gestion des angles
		
		if (X<lim && Y<lim) { // angle supérieur gauche -> ok !
			for (int i=lim-X; i<o; i++) {
				for (int j=lim-Y; j<o; j++) {
					od[o*i+j]=terre.getCoord()[terre.getIdCoord(X-O+i,Y-O+j)];
				}
			}
		}
		else if (X<lim && Y>(L-1)-lim) { // angle supérieur droit -> ok!
			for (int i=lim-X; i<o; i++) {
				for (int j=0; j<o-(Y-((L-1)-lim)); j++) {
					od[o*i+j]=terre.getCoord()[terre.getIdCoord(X-O+i,Y-O+j)];
				}
			}
		}
		else if (X>(l-1)-lim && Y<lim) { // angle inférieur gauche -> ok!
			for (int i=0; i<o-(X-((l-1)-lim)); i++) {
				for (int j=lim-Y; j<o; j++) {
					od[o*i+j]=terre.getCoord()[terre.getIdCoord(X-O+i,Y-O+j)];
				}
			}
		}
		else if (X>(l-1)-lim && Y>(L-1)-lim) { // angle inférieur droit -> ok!
			for (int i=0; i<o-(X-((l-1)-lim)); i++) {
				for (int j=0; j<o-(Y-((L-1)-lim)); j++) {
					od[o*i+j]=terre.getCoord()[terre.getIdCoord(X-O+i,Y-O+j)];
			}
		}}
		
		// Gestion des bords seuls (pas les angles)
		else if (X<lim) { // bordure en haut -> ok!
			for (int i=lim-X; i<o; i++) {
				for (int j=0; j<o; j++) {
					od[o*i+j]=terre.getCoord()[terre.getIdCoord(X-O+i,Y-O+j)];
				}
			}
		}
		else if (X>(l-1)-lim) { //bordure en haut -> ok!
			for (int i=0; i<o-(X-((l-1)-lim)); i++) {
				for (int j=0; j<o; j++) {
					od[o*i+j]=terre.getCoord()[terre.getIdCoord(X-O+i,Y-O+j)];
				}
			}
		}
		else if (Y<lim) { // bordure à gauche -> ok!
			for (int i=0; i<o; i++) {
				for (int j=lim-Y; j<o; j++) {
					od[o*i+j]=terre.getCoord()[terre.getIdCoord(X-O+i,Y-O+j)];
				}
			}
		}
		else if (Y>(L-1)-lim) { // bordure à droite -> ok!
			for (int i=0; i<o; i++) {
				for (int j=0; j<o-(Y-((L-1)-lim)); j++) {
					od[o*i+j]=terre.getCoord()[terre.getIdCoord(X-O+i,Y-O+j)];
				}
			}
		}
		// sans bordures
		else { 
			for (int i=0; i<o; i++) {
				for (int j=0; j<o; j++) {
					od[o*i+j] = terre.getCoord()[terre.getIdCoord(X-O+i,Y-O+j)];
				}	
			}
		}
		this.setZoneOdorat(od);
	}
	
	/**
	 * Fonction visant à détecter les ennemis présents dans le
	 * champ de vision (déjà défini) de la fourmi
	 * @param terre : Terrain
	 * @return [ ] : ArrayList[Coordonnee]
	 */
	public ArrayList<Coordonnee> detecterEnnemis(Terrain terre) {

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
			else if (terre.getAnnuaire()[terre.getIdCoord(c.getX(), c.getY())].getOccupation()==typeOccupation.Ennemi){
				/* cas n°3 : la case correspondante renferme une information
				 * d'occupation par un ennemi */
				C.add(c);
				this.danger=true;
			}
		}
		return C;
	}
	
	/**
	 * Fonction testant si la fourmi est toujours vivante et qui dans le cas
	 * contraire la retire du terrain et décrémente la population de la fourmilière
	 * @param terre : Terrain
	 * @param fourm : Fourmiliere
	 * @return 
	 */
	public boolean estMort(Terrain terre, Fourmiliere fourm) {
		
		boolean bool = false;
		
		if (this.getPv()==0) {
			bool = true;
		}
		if (this.getType()==typeOccupation.FourmiEclaireuse
				&& this.getPosition()==this.getPrecedente()
				&& fourm.getFourmisE().size()>(terre.getLongeur()+terre.getLargeur())) {
			bool = true;
		}
		if (this.getType()==typeOccupation.FourmiTransporteuse
				&& ((FourmiTransporteuse)this).getChargeN()==0
				&& this.retour) {
			bool = true;
		}
		if (this.getType()==typeOccupation.FourmiCombattante
				&& ((FourmiCombattante)this).detecterEnnemis(terre).isEmpty()
				&& ((FourmiCombattante)this).detecterPheromoneDanger(terre)==null) {
			bool = true;
		}
		
		if (this.getRetour()==true && (int)(IAttaquer.distance(this.getPosition(), fourm.getPosition())) < 3) {
			bool = true;
		}
		return bool;
	}
	
	/**
	 * Fonction abstraite que chaque fourmi implémente afin de suivre
	 * les phéromones qui l'intéressent.
	 * @param terre : Terrain
	 * @param fourm : Fourmiliere
	 */
	public abstract void suivre(Terrain terre, Fourmiliere fourm, Arbitre arb);
	
	/**
	 * Focntion permettant à la fourmi d'emprunter la 1ere case du plus court chemin
	 * pour revenir à la fourmilière depuis sa position (en tenant compte des obstacles).
	 * @param terre : Terrain
	 * @param fourm : Fourmiliere
	 */
	public void revenir(Terrain terre, Fourmiliere fourm, Arbitre arb) {
		Coordonnee c = IParcourir.calcPCC(this.getPosition(), fourm.getPosition(), terre);
		int stop = 0;
		while (terre.getAnnuaire()[terre.getIdCoord(c.getX(), c.getY())].getOccupation()!=null
			  || terre.getAnnuaire()[terre.getIdCoord(c.getX(), c.getY())].getOccupation()==typeOccupation.FourmiEclaireuse) {
			if (terre.getAnnuaire()[terre.getIdCoord(c.getX(), c.getY())].getOccupation()==typeOccupation.FourmiEclaireuse) {
				FourmiEclaireuse fe = IAttaquer.correspFourmEclair(c, fourm, arb);
				fe.detruire(terre);
				fe.setPv(0);
				break;
			}
			else {
				stop++;
			}
			if (stop==4) {
				c = IParcourir.deplacAleat(this.getPosition(), this.getType(), terre, arb);
				this.setPrecedente(this.getPosition());
				this.setPosition(c);
				this.defForme(c, terre);
				this.defZoneOdorat(terre);
				this.defZoneVision(terre);
				break;
			}
		}
		IParcourir.deplacement(this.getPosition(), c, this.getType(), terre);
		this.setPrecedente(this.getPosition());
		this.setPosition(c);
		this.defForme(c, terre);
		this.defZoneOdorat(terre);
		this.defZoneVision(terre);
	}
	
	

}
