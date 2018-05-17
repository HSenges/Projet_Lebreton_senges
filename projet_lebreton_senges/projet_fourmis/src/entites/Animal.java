package entites;

import terrain.Terrain;

/**
 * Classe abstraite dont hérite l'ensemble
 * des éléments mobiles de la simulation
 */
public abstract class Animal extends Organisme {
	
		//Attributs
		private int vitesse;
		private int pt_deg;
		
		//Accesseurs
		
		public int getVitesse(){
			return this.vitesse;
		}
		
		public int getDegat(){
			return this.pt_deg;
		}
		
		//Mutateurs
		
		public void setVitesse(int v) {
			this.vitesse = v;
		}
		
		public void setDegat(int d) {
			this.pt_deg = d;
		}
		
		
		//Constructeur
		public Animal (Terrain terre){
			super(terre);
			this.vitesse = 1;
			this.pt_deg = 1;
		}

}
