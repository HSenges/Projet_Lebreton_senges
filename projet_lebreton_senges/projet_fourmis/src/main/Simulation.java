package main;

import java.awt.Dimension;

import javax.swing.JFrame;

/**
 * Classe principale utilisant une java.swing.JFrame pour créer la fenêtre
 * d'affichage, de la remplir avec le JPanel. C'est elle qui contient la
 * fonction 'main' qui est exécutée.
 */
public class Simulation {
	
	public static void main (String[] arg) {
		// Création de la simulation
		Arbitre arbitre = new Arbitre();
		int[] param = arbitre.getParam();
		// Création de la fenêtre d'affichage
		JFrame frame = new JFrame("SIMULATION D'UNE FOURMILIERE");
		Dimension dimFrame;
		if (arbitre.defaut==1) {
			dimFrame = new Dimension(param[1]*10+175, param[0]*10);
		}
		else {
			dimFrame = new Dimension(param[1]*10, param[0]*10);
		}
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(dimFrame);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
		// Création et récupération des éléments à afficher
			
		Panneau panel = new Panneau(arbitre);
		panel.setLocation(0, 0);
		
		frame.add(panel);

				
		// Affichage simple de la fenêtre
		frame.setVisible(true);
		
		// Affichage des tours de jeu
		while (arbitre.isRunning()) {
			arbitre.run();
			try {Thread.sleep(250);} 
			catch (InterruptedException e) {e.printStackTrace();}
			panel.repaint();
		}
		
	}

}
