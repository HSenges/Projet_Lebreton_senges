package interfacesJeu;

import entites.FourmiEclaireuse;
import main.Arbitre;
import terrain.Coordonnee;
import terrain.Terrain;
import terrain.typeOccupation;

public interface IParcourir {
	
	/**
	 * Fonction retournant de manière aléatoire un entier entre 0 et le 
	 * paramètre 'max' (qui est exclu).
	 * @param max : int
	 * @return : int
	 */
	public static int defNbAleat(int max) {
		return (int)(Math.random()*max); // 'max' n'est pas inclu !!
	}
	
	/**
	 * Fonction retournant une coordonnee au hasard parmi celles selon les quatres
	 * points cardinaux autour d'une position 'pos'.
	 * @param pos : Coordonnee
	 * @param terre : Terrain
	 * @return : Coordonnee
	 */
	public static Coordonnee caseAleat(Coordonnee pos, Terrain terre) {
		Coordonnee[] cases = new Coordonnee[4];
		cases[0] = terre.getCoord()[terre.getIdCoord(pos.getX()-1, pos.getY())];
		cases[1] = terre.getCoord()[terre.getIdCoord(pos.getX()+1, pos.getY())];
		cases[2] = terre.getCoord()[terre.getIdCoord(pos.getX(), pos.getY()-1)];
		cases[3] = terre.getCoord()[terre.getIdCoord(pos.getX(), pos.getY()+1)];
		return cases[defNbAleat(4)];
	}
	
	/**
	 * Fonction permettant d'inscrire sur le terrain un déplacement aléatoire
	 * à partir de la position 'pos' d'un objet de type 't' (en tenant compte
	 * des bords) et retournant la nouvelle position.
	 * DOIT ÊTRE SUIVIE DE LA FONCTION '.setPosition()' DE L'OBJET APPLIQUÉE À
	 * LA NOUVELLE POSITION !!
	 * @param pos : Coordonnee
	 * @param t : typeOccupation
	 * @param terre : Terrain
	 * @return : Coordonnee
	 */
	public static Coordonnee deplacAleat(Coordonnee pos, typeOccupation t, Terrain terre, Arbitre arb) {
		
		Coordonnee newpos = IParcourir.caseAleat(pos, terre);
		
		int stop = 0;
		while (terre.getAnnuaire()[terre.getIdCoord(newpos.getX(), newpos.getY())].getOccupation()!=null) {
			if (terre.getAnnuaire()[terre.getIdCoord(newpos.getX(), newpos.getY())].getOccupation()==typeOccupation.FourmiEclaireuse) {
				FourmiEclaireuse f = IAttaquer.correspFourmEclair(newpos, arb.getFourmiliere(), arb);
				f.setPv(0);
				f.detruire(terre);
				break;
			}
			else {
				newpos = IParcourir.caseAleat(pos, terre);
			}
			stop++;
			if (stop==10) {
				newpos = pos;
				break;
			}
		}
		
		terre.getAnnuaire()[terre.getIdCoord(pos.getX(), pos.getY())].setOccupation(null);
		terre.getAnnuaire()[terre.getIdCoord(pos.getX(), pos.getY())].setVierge();
		
		terre.getAnnuaire()[terre.getIdCoord(newpos.getX(), newpos.getY())].setOccupation(t);
		terre.getAnnuaire()[terre.getIdCoord(newpos.getX(), newpos.getY())].setVierge();
	
		return newpos;
	}
	
	/**
	 * Fonction calculant et retournant la première coordonnee du 
	 * plus court chemin entre 'c1' et 'c2' (en tenant compte des bords).
	 * @param c1 : Coordonnee
	 * @param c2 : Coordonnee
	 * @param terre : Terrain
	 * @return
	 */
	public static Coordonnee calcPCC(Coordonnee c1, Coordonnee c2, Terrain terre) {
		int X1 = c1.getX();
		int Y1 = c1.getY();
		int X2 = c2.getX();
		int Y2 = c2.getY();
		int dX = X2-X1;
		int dY = Y2-Y1;
		Coordonnee[] c = new Coordonnee[]{c2,c2};
		if (dX<0 && Math.abs(dX)>=Math.abs(dY)) {
			c[0] = terre.getCoord()[terre.getIdCoord(c1.getX()-1, c1.getY())];
			c[1] = terre.getCoord()[terre.getIdCoord(c1.getX(), c1.getY()-1)];
		}
		if (dX>0 && Math.abs(dX)>=Math.abs(dY)) {
			c[0] = terre.getCoord()[terre.getIdCoord(c1.getX()+1, c1.getY())];
			c[1] = terre.getCoord()[terre.getIdCoord(c1.getX(), c1.getY()+1)];
		}
		if (dY<0 && Math.abs(dX)<=Math.abs(dY)) {
			c[0] = terre.getCoord()[terre.getIdCoord(c1.getX(), c1.getY()-1)];
			c[1] = terre.getCoord()[terre.getIdCoord(c1.getX()+1, c1.getY())];
		}
		if (dY>0 && Math.abs(dX)<=Math.abs(dY)) {
			c[0] = terre.getCoord()[terre.getIdCoord(c1.getX(), c1.getY()+1)];
			c[1] = terre.getCoord()[terre.getIdCoord(c1.getX()-1, c1.getY())];
		}
		
		int i = 0;
		while (terre.getAnnuaire()[terre.getIdCoord(c[i].getX(), c[i].getY())].getOccupation()!=null) {
			i++;
			if (i==2) {
				return c1;
			}
		}
		
		return c[i];
	}
	
	/**
	 * Fonction permettant d'inscrire sur le terrain le déplacement d'un objet
	 * de type 't' de la coordonnée 'c1' à la coordonnée 'c2'.
	 * DOIT ÊTRE SUIVIE DE LA FONCTION '.setPosition()' DE L'OBJET APPLIQUÉE À c2 !!
	 * @param c1 : Coordonnee
	 * @param c2 : Coordonnee
	 * @param t : typeOccupation
	 * @param terre : Terrain
	 */
	public static void deplacement(Coordonnee c1, Coordonnee c2, typeOccupation t, Terrain terre) {
		
		terre.getAnnuaire()[terre.getIdCoord(c1.getX(), c1.getY())].setOccupation(null);
		terre.getAnnuaire()[terre.getIdCoord(c1.getX(), c1.getY())].setVierge();
		
		terre.getAnnuaire()[terre.getIdCoord(c2.getX(), c2.getY())].setOccupation(t);
		terre.getAnnuaire()[terre.getIdCoord(c2.getX(), c2.getY())].setVierge();
	}
	
}
