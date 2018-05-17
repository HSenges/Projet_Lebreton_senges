package interfacesJeu;

import java.util.Iterator;

import entites.Ennemi;
import entites.FourmiCombattante;
import entites.FourmiEclaireuse;
import entites.FourmiTransporteuse;
import entites.Fourmiliere;
import main.Arbitre;
import terrain.Coordonnee;
import terrain.typeOccupation;

public interface IAttaquer {
	
	/**
	 * Fonction retournant la valeur de la distance euclidienne entre deux
	 * coordonnees 'c1' et 'c2' sous forme d'un double.
	 * @param c1 : Coordonnee
	 * @param c2 : Coordonnee
	 * @return : double
	 */
	public static double distance(Coordonnee c1, Coordonnee c2) {
		return Math.sqrt((c2.getX()-c1.getX())*(c2.getX()-c1.getX())+(c2.getY()-c1.getY())*(c2.getY()-c1.getY()));
	}
	
	
	/**
	 * Fonction permettant de retrouver l'objet FourmiEclaireuse associé
	 * à une coordonnee où un animal a été détecté, pour pouvoir interagir avec lui.
	 * @param c : Coordonnee
	 * @param fourm : Fourmiliere
	 * @param arb : Arbitre
	 * @return : FourmiEclaireuse
	 */
	public static FourmiEclaireuse correspFourmEclair(Coordonnee c, Fourmiliere fourm, Arbitre arb) {

		FourmiEclaireuse A = null;
		
		Iterator<FourmiEclaireuse> itr = fourm.getFourmisE().iterator();
		while (itr.hasNext()) {
			FourmiEclaireuse f = itr.next();
			if (IAttaquer.distance(f.getPosition(), c) < 2) {
				A = f;
				break;
			}
		}
		return A;
	}
		
	/**
	 * Fonction permettant de retrouver l'objet FourmiTransporteuse associé
	 * à une coordonnee où un animal a été détecté, pour pouvoir interagir avec lui.
	 * @param c : Coordonnee
	 * @param fourm : Fourmiliere
	 * @param arb : Arbitre
	 * @return : FourmiTransporteuse
	 */
	public static FourmiTransporteuse correspFourmTransp(Coordonnee c, Fourmiliere fourm, Arbitre arb) {
		
		FourmiTransporteuse A = null;
		
		Iterator<FourmiTransporteuse> itr = fourm.getFourmisT().iterator();
		while (itr.hasNext()) {
			FourmiTransporteuse f = (itr.next());
			if (IAttaquer.distance(f.getPosition(), c) < 2) {
				A = f;
				break;
			}
		}
		return A;
	}
	
	/**
	 * Fonction permettant de retrouver l'objet FourmiCombattante associé
	 * à une coordonnee où un animal a été détecté, pour pouvoir interagir avec lui.
	 * @param c : Coordonnee
	 * @param fourm : Fourmiliere
	 * @param arb : Arbitre
	 * @return : FourmiCombattante
	 */
	public static FourmiCombattante correspFourmComb(Coordonnee c, Fourmiliere fourm, Arbitre arb) {
		
		FourmiCombattante A = null;
			
		Iterator<FourmiCombattante> itr = fourm.getFourmisC().iterator();
		while (itr.hasNext()) {
			FourmiCombattante f = (itr.next());
			if (IAttaquer.distance(f.getPosition(), c) < 2) {
				A = f;
				break;
			}
		}
		return A;
	}
		
	/**
	 * Fonction permettant de retrouver l'objet Ennemi associé
	 * à une coordonnee où un animal a été détecté, pour pouvoir interagir avec lui.
	 * @param c : Coordonnee
	 * @param fourm : Fourmiliere
	 * @param arb : Arbitre
	 * @return : Ennemi
	 */
	public static Ennemi correspEnnemi(Coordonnee c, Arbitre arb) {
		
		Ennemi A = null;
		
		Iterator<Ennemi> itr = arb.getEnnemis().iterator();
		while (itr.hasNext()) {
			Ennemi e = (itr.next());
			if (IAttaquer.distance(e.getPosition(), c) < 2) {
				A = e;
				break;
			}
		}
		return A;
	}
	
	
	/**
	 * Fonction permettant à un animal d'en attaquer un autre en lui enlevant
	 * autant de points de vie (si > 0) qu'il a de points de dégâts.
	 * @param c : Coordonnee
	 * @param fourm : Fourmiliere
	 * @param arb : Arbitre
	 */
	public static void attaquer(Coordonnee c, Fourmiliere fourm, Arbitre arb) {
		
		FourmiEclaireuse A1 = correspFourmEclair(c, fourm, arb);
		FourmiTransporteuse A2 = correspFourmTransp(c, fourm, arb);
		FourmiCombattante A3 = correspFourmComb(c, fourm, arb);
		Ennemi A4 = correspEnnemi(c, arb);

		
		if (A1!=null) {
			int v = A1.getPv();
			if (v>0) {
				A1.setPv(v-=1);
			}
		}
		
		else if (A4!=null) {
			int v = A4.getPv();
			if (v>0) {
				A4.setPv(v-=1);
			}
		}
		
		else if (A3!=null) {
			int v = A3.getPv();
			if (v>0) {
				A3.setPv(v-=1);
			}
		}
		
		else if (arb.getTerrain().getAnnuaire()[arb.getTerrain().getIdCoord(c.getX(), c.getY())].getOccupation()==typeOccupation.Fourmiliere
				&& fourm.getPv()>0) {
			fourm.setPv(fourm.getPv()-1);
		}
		
		else if (A2!=null) {
			int v = A2.getPv();
			if (v>0) {
				A2.setPv(v-=1);
			}
		}
		
	}

}
