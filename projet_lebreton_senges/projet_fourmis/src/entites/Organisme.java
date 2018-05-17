package entites;

import terrain.Coordonnee;
import terrain.Terrain;

/**
 * Classe abstraite dont hérite l'ensemble 
 * des éléments actifs de la simulation,
 * présents dans les méthodes de
 * l'Arbitre
 */
public abstract class Organisme extends Entite {
	
	//Attributs
	private int pv;
	private int vision; //nombre de cases visibles depuis la position dans une des 4 directions
	private Coordonnee[] zoneVision;
	
	//Accesseurs
	public int getPv() {
		return pv;
	}
	public int getVision() {
		return vision;
	}
	public Coordonnee[] getZoneVision() {
		return zoneVision;
	}
		
	//Mutateurs
	public void setPv(int Pv) {
		this.pv = Pv;
	}
	public void setVision(int v) {
		this.vision = v;
	}
	
	public void setZoneVision(Coordonnee[] ZoneVision) {
		this.zoneVision = ZoneVision;
	}
	
	//Constructeur
	public Organisme(Terrain terre) {
		super(terre);
	}
	
	//Méthodes
	
	/**
	 * Fonction permettant de définir pour chaque nouvelle position de la
	 * fourmi les cases entrant dans sa zone de vision, d'après la valeur de
	 * son attribut 'vision' qui en précise la portée.
	 * Les cases correspondant aux bords et au-delà prennent la valeur 'null'.
	 * @param terre : Terrain
	 */
	public void defZoneVision(Terrain terre) {
		
		int X = this.getPosition().getX();
		int Y = this.getPosition().getY();
		int l = terre.getLargeur();
		int L = terre.getLongeur();
		int V = this.vision;
		int v = 2*V+1;
		int lim = V+1;
		
		
		Coordonnee[] vis = new Coordonnee[v*v];
		
		// Gestion des angles
		
		if (X<lim && Y<lim) { // angle supérieur gauche -> ok !
			for (int i=lim-X; i<v; i++) {
				for (int j=lim-Y; j<v; j++) {
					vis[v*i+j]=terre.getCoord()[terre.getIdCoord(X-V+i,Y-V+j)];
				}
			}
		}
		else if (X<lim && Y>(L-1)-lim) { // angle supérieur droit -> ok!
			for (int i=lim-X; i<v; i++) {
				for (int j=0; j<v-(Y-((L-1)-lim)); j++) {
					vis[v*i+j]=terre.getCoord()[terre.getIdCoord(X-V+i,Y-V+j)];
				}
			}
		}
		else if (X>(l-1)-lim && Y<lim) { // angle inférieur gauche -> ok!
			for (int i=0; i<v-(X-((l-1)-lim)); i++) {
				for (int j=lim-Y; j<v; j++) {
					vis[v*i+j]=terre.getCoord()[terre.getIdCoord(X-V+i,Y-V+j)];
				}
			}
		}
		else if (X>(l-1)-lim && Y>(L-1)-lim) { // angle inférieur droit -> ok!
			for (int i=0; i<v-(X-((l-1)-lim)); i++) {
				for (int j=0; j<v-(Y-((L-1)-lim)); j++) {
					vis[v*i+j]=terre.getCoord()[terre.getIdCoord(X-V+i,Y-V+j)];
			}
		}}
		
		// Gestion des bords seuls (pas les angles)
		else if (X<lim) { // bordure en haut -> ok!
			for (int i=lim-X; i<v; i++) {
				for (int j=0; j<v; j++) {
					vis[v*i+j]=terre.getCoord()[terre.getIdCoord(X-V+i,Y-V+j)];
				}
			}
		}
		else if (X>(l-1)-lim) { //bordure en haut -> ok!
			for (int i=0; i<v-(X-((l-1)-lim)); i++) {
				for (int j=0; j<v; j++) {
					vis[v*i+j]=terre.getCoord()[terre.getIdCoord(X-V+i,Y-V+j)];
				}
			}
		}
		else if (Y<lim) { // bordure à gauche -> ok!
			for (int i=0; i<v; i++) {
				for (int j=lim-Y; j<v; j++) {
					vis[v*i+j]=terre.getCoord()[terre.getIdCoord(X-V+i,Y-V+j)];
				}
			}
		}
		else if (Y>(L-1)-lim) { // bordure à droite -> ok!
			for (int i=0; i<v; i++) {
				for (int j=0; j<v-(Y-((L-1)-lim)); j++) {
					vis[v*i+j]=terre.getCoord()[terre.getIdCoord(X-V+i,Y-V+j)];
				}
			}
		}
		// sans bordures
		else { 
			for (int i=0; i<v; i++) {
				for (int j=0; j<v; j++) {
					vis[v*i+j] = terre.getCoord()[terre.getIdCoord(X-V+i,Y-V+j)];
				}	
			}
		}
		
		this.setZoneVision(vis);
	}
}