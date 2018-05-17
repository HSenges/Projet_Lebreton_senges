package terrain;

/**Une classe d'objets construits dès le début de la simulation 
 * par l'arbitre. 
 * Infranchissable par les Animaux,
 * l'obstacle à une forme tirée aléatoirement 
 * parmi une liste de quatre formes possibles
 */
public class Obstacle {
	
	public Obstacle() {
		
	}
	
	/**
	 * Fonction définissant quatre formes différentes d'obstacle à partir d'une position 'pos'
	 * puis qui renvoie celle associée à l'entier 'n' en entrée (obtenu aléatoirement).
	 * @param pos : Coordonnee
	 * @param n : int
	 * @param terre : Terrain
	 * @return : Coordonnee[]
	 */
	public static Coordonnee[] defForme (Coordonnee pos, int n, Terrain terre){
		Coordonnee[] Forme = new Coordonnee[4];
		int i = n;
		if (n>3) {
			i=0; // valeur par defaut si entrée outofrange
		}
		switch (i){
			case 0 : Coordonnee[] forme1 = new Coordonnee[4];
					 forme1[0] = pos;
					 forme1[1] = terre.getCoord()[terre.getIdCoord(pos.getX(), pos.getY()+1)];
					 forme1[2] = terre.getCoord()[terre.getIdCoord(pos.getX()+1, pos.getY())];
					 forme1[3] = terre.getCoord()[terre.getIdCoord(pos.getX()+1, pos.getY()+1)];
					 Forme = forme1;
					 break;
			case 1 : Coordonnee[] forme2 = new Coordonnee[4];
					 forme2[0] = pos;
			 		 forme2[1] = terre.getCoord()[terre.getIdCoord(pos.getX()+1, pos.getY())];
			 		 forme2[2] = terre.getCoord()[terre.getIdCoord(pos.getX()+2, pos.getY())];
			 		 forme2[3] = terre.getCoord()[terre.getIdCoord(pos.getX()+3, pos.getY())];
			 		 Forme = forme2;
			 		 break;
			case 2 : Coordonnee[] forme3 = new Coordonnee[4];
					 forme3[0] = pos;
			 		 forme3[1] = terre.getCoord()[terre.getIdCoord(pos.getX(), pos.getY()+1)];
			 		 forme3[2] = terre.getCoord()[terre.getIdCoord(pos.getX(), pos.getY()+2)];
			 		 forme3[3] = terre.getCoord()[terre.getIdCoord(pos.getX(), pos.getY()+3)];
			 		 Forme = forme3;
			 		 break;
			case 3 : Coordonnee[] forme4 = new Coordonnee[4];
					 forme4[0] = pos;
					 forme4[1] = terre.getCoord()[terre.getIdCoord(pos.getX()+1, pos.getY())];
					 forme4[2] = terre.getCoord()[terre.getIdCoord(pos.getX()+1, pos.getY()+1)];
					 forme4[3] = terre.getCoord()[terre.getIdCoord(pos.getX()+2, pos.getY()+1)];
					 Forme = forme4;
					 break;
		}
		return Forme;
	}

}
