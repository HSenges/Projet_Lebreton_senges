package main;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import terrain.Terrain;
import terrain.typeOccupation;

@SuppressWarnings("serial")
/**
 * Classe héritant de java.swing.JPanel permettant la création
 * des éléments d'affichage du terrain en fonction du type d'occupation,
 * ainsi que la légende associée au mode de jeu par défaut
 */
public class Panneau extends JPanel{
	
	final int pixCases = 10; 	// nombre de pixels de l'écran définissant la longueur de la case telle qu'affichée
	Arbitre arb;				// arbitre permettant le déroulement du jeu
	
	public Panneau(Arbitre arb) {
			this.arb = arb;
	}
	
	public Graphics defComponent(Graphics g, Arbitre arb) {
		Terrain terre = arb.getTerrain();
		for (int i=0; i<terre.getLargeur(); i++) {
			for (int j=0; j<terre.getLongeur(); j++) {
				
				// fond de jeu
				if (terre.getAnnuaire()[terre.getIdCoord(i, j)].getOccupation()==null) {
					g.setColor(Color.darkGray);
					g.fillRect(j*pixCases, i*pixCases, pixCases, pixCases);
				}
				
				// bords du terrain
				else if (terre.getAnnuaire()[terre.getIdCoord(i, j)].getOccupation()==typeOccupation.Bord) {
					g.setColor(Color.black);
					g.fillRect(j*pixCases, i*pixCases, pixCases, pixCases);
				}
				
				// fourmiliere
				else if (terre.getAnnuaire()[terre.getIdCoord(i, j)].getOccupation()==typeOccupation.Fourmiliere) {
					// en bon état
					if (arb.getFourmiliere().getPv()>25) {
						g.setColor(Color.white);
						g.fillRect(j*pixCases, i*pixCases, pixCases, pixCases);
						g.setColor(Color.lightGray);
						g.drawRect(j*pixCases, i*pixCases, pixCases, pixCases);
					}
					// état moyen
					else if (arb.getFourmiliere().getPv()>15 && arb.getFourmiliere().getPv()<=25) {
						g.setColor(Color.lightGray);
						g.fillRect(j*pixCases, i*pixCases, pixCases, pixCases);
						g.setColor(Color.gray);
						g.drawRect(j*pixCases, i*pixCases, pixCases, pixCases);
					}
					// piètre état
					else if (arb.getFourmiliere().getPv()>5 && arb.getFourmiliere().getPv()<=15) {
						g.setColor(Color.gray);
						g.fillRect(j*pixCases, i*pixCases, pixCases, pixCases);
						g.setColor(Color.darkGray);
						g.drawRect(j*pixCases, i*pixCases, pixCases, pixCases);
					}
					// fourmilière détruite
					else if (arb.getFourmiliere().estMort()) {
						g.setColor(Color.darkGray);
						g.fillRect(j*pixCases, i*pixCases, pixCases, pixCases);
						g.setColor(Color.darkGray);
						g.drawRect(j*pixCases, i*pixCases, pixCases, pixCases);
					}
					
				}
				
				// obstacles
				else if (terre.getAnnuaire()[terre.getIdCoord(i, j)].getOccupation()==typeOccupation.Obstacle) {
					g.setColor(Color.gray);
					g.fillRect(j*pixCases, i*pixCases, pixCases, pixCases);
					g.setColor(Color.lightGray);
					g.drawRect(j*pixCases, i*pixCases, pixCases, pixCases);
				}
				
				// nourriture
				else if (terre.getAnnuaire()[terre.getIdCoord(i, j)].getOccupation()==typeOccupation.Nourriture) {
					g.setColor(Color.orange);
					g.fillRect(j*pixCases, i*pixCases, pixCases, pixCases);
				}
				
				// ennemi
				else if (terre.getAnnuaire()[terre.getIdCoord(i, j)].getOccupation()==typeOccupation.Ennemi) {
					g.setColor(Color.red);
					g.fillRect(j*pixCases, i*pixCases, pixCases, pixCases);
				}
				
				// fourmi eclaireuse
				else if (terre.getAnnuaire()[terre.getIdCoord(i, j)].getOccupation()==typeOccupation.FourmiEclaireuse) {
					g.setColor(Color.darkGray);
					g.fillRect(j*pixCases, i*pixCases, pixCases, pixCases);
					g.setColor(Color.white);
					g.fillOval(j*pixCases, i*pixCases, pixCases, pixCases);
				}
				
				// fourmi transporteuse
				else if (terre.getAnnuaire()[terre.getIdCoord(i, j)].getOccupation()==typeOccupation.FourmiTransporteuse) {
					g.setColor(Color.darkGray);
					g.fillRect(j*pixCases, i*pixCases, pixCases, pixCases);
					g.setColor(Color.yellow);
					g.fillOval(j*pixCases, i*pixCases, pixCases, pixCases);
				}
				
				// fourmi combattante
				else if (terre.getAnnuaire()[terre.getIdCoord(i, j)].getOccupation()==typeOccupation.FourmiCombattante) {
					g.setColor(Color.darkGray);
					g.fillRect(j*pixCases, i*pixCases, pixCases, pixCases);
					g.setColor(Color.pink);
					g.fillOval(j*pixCases, i*pixCases, pixCases, pixCases);
				}
			}
			
			// LEGENDE
			int L = terre.getLongeur();
			for (int b=1; b<terre.getLargeur()-1; b++) {
				g.setColor(Color.darkGray);
				g.fillRect((L+1+11)*pixCases, (b)*pixCases, 5*pixCases, pixCases);
				g.fillRect((L+1)*pixCases-5, (b)*pixCases, pixCases-5, pixCases);
				g.fillRect((L+1)*pixCases-5, (1)*pixCases, 15*pixCases, pixCases-5);
				g.fillRect((L+1)*pixCases-5, (13)*pixCases, 15*pixCases, pixCases-5);
				g.fillRect((L+1)*pixCases-5, (40)*pixCases, 15*pixCases, pixCases-5);
				g.fillRect((L+1)*pixCases-5, (terre.getLargeur()-1)*pixCases, 16*pixCases+5, pixCases-5);
			}
			
			for (int k=0; k<3; k++) {
				for (int l=0; l<3; l++) {
					g.setColor(Color.white);
					g.fillRect((L+13+l)*pixCases, (2+k)*pixCases, pixCases, pixCases);
					g.setColor(Color.lightGray);
					g.drawRect((L+13+l)*pixCases, (2+k)*pixCases, pixCases, pixCases);					
				}
			}
			
			g.setColor(Color.gray);
			g.fillRect((L+2+11)*pixCases, (8)*pixCases, pixCases, pixCases);
			g.fillRect((L+2+11)*pixCases, (9)*pixCases, pixCases, pixCases);
			g.fillRect((L+3+11)*pixCases, (9)*pixCases, pixCases, pixCases);
			g.fillRect((L+3+11)*pixCases, (10)*pixCases, pixCases, pixCases);
			g.setColor(Color.lightGray);
			g.drawRect((L+2+11)*pixCases, (8)*pixCases, pixCases, pixCases);
			g.drawRect((L+2+11)*pixCases, (9)*pixCases, pixCases, pixCases);
			g.drawRect((L+3+11)*pixCases, (9)*pixCases, pixCases, pixCases);
			g.drawRect((L+3+11)*pixCases, (10)*pixCases, pixCases, pixCases);
			
			g.setColor(Color.red);
			g.fillRect((L+3+11)*pixCases, (16)*pixCases, pixCases, pixCases);
			
			g.setColor(Color.orange);
			g.fillRect((L+3+11)*pixCases, (21)*pixCases, pixCases, pixCases);
			
			g.setColor(Color.white);
			g.fillOval((L+3+11)*pixCases, (26)*pixCases+5, pixCases, pixCases);
			
			g.setColor(Color.yellow);
			g.fillOval((L+3+11)*pixCases, (31)*pixCases+5, pixCases, pixCases);
			
			g.setColor(Color.pink);
			g.fillOval((L+3+11)*pixCases, (36)*pixCases+5, pixCases, pixCases);
			
			g.setColor(Color.black);
			g.fillRect((L+2+11)*pixCases, (42)*pixCases, pixCases, pixCases);
			g.fillRect((L+3+11)*pixCases, (42)*pixCases, pixCases, pixCases);
			g.fillRect((L+4+11)*pixCases, (42)*pixCases, pixCases, pixCases);
			g.fillRect((L+2+11)*pixCases, (43)*pixCases, pixCases, pixCases);
			g.fillRect((L+2+11)*pixCases, (44)*pixCases, pixCases, pixCases);
			g.fillRect((L+2+11)*pixCases, (45)*pixCases, pixCases, pixCases);
			g.fillRect((L+2+11)*pixCases, (46)*pixCases, pixCases, pixCases);
			g.fillRect((L+4+11)*pixCases, (43)*pixCases, pixCases, pixCases);
			g.fillRect((L+4+11)*pixCases, (44)*pixCases, pixCases, pixCases);
			g.fillRect((L+4+11)*pixCases, (45)*pixCases, pixCases, pixCases);
			g.fillRect((L+4+11)*pixCases, (46)*pixCases, pixCases, pixCases);
			g.fillRect((L+2+11)*pixCases, (47)*pixCases, pixCases, pixCases);
			g.fillRect((L+3+11)*pixCases, (47)*pixCases, pixCases, pixCases);
			g.fillRect((L+4+11)*pixCases, (47)*pixCases, pixCases, pixCases);
			
			g.setColor(Color.black);
			g.drawString("FOURMILIERE",(L+2)*pixCases+5, 4*pixCases);
			g.drawString("OBSTACLE",(L+3)*pixCases, 10*pixCases);
			g.drawString("ENNEMI",(L+3)*pixCases+5, 17*pixCases);
			g.drawString("NOURRITURE",(L+2)*pixCases, 22*pixCases);
			g.drawString("FOURMI",(L+3)*pixCases+5, 27*pixCases);
			g.drawString("ECLAIREUSE",(L+2)*pixCases+5, 28*pixCases+2);
			g.drawString("FOURMI",(L+3)*pixCases+5, 32*pixCases);
			g.drawString("TRANSPORTEUSE",(L+1)*pixCases+3, 33*pixCases+2);
			g.drawString("FOURMI",(L+3)*pixCases+5, 37*pixCases);
			g.drawString("COMBATTANTE",(L+1)*pixCases+3, 38*pixCases+2);
			g.drawString("BORDS",(L+4)*pixCases+3, 45*pixCases);
			
		}
		return g;
	}
	
	public void paintComponent(Graphics g) {
		g = this.defComponent(g, arb);
	}
}