package main;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

import entites.FourmiTransporteuse;
import entites.Nourriture;
import entites.Ennemi;
import entites.FourmiCombattante;
import entites.FourmiEclaireuse;
import entites.Fourmiliere;
import interfacesJeu.IAttaquer;
import interfacesJeu.IParcourir;
import terrain.Coordonnee;
import terrain.Obstacle;
import terrain.Terrain;
import terrain.typeOccupation;

/**
 * Classe à instanciation unique. Créé dès le début de la partie l'Arbitre est chargé
 * de la création du terrain, de son remplissage, de l'implantation de la Fourmilière. 
 * Passée cette phase de création il a pour rôle d'attribuer les tour de jeu et de préciser
 * la séquence d'actions à réaliser par chaque organisme.
 */

public class Arbitre {
	//Attributs
		private int tour;		//Nombre de tour écoulés
		private Terrain terre;	//Attribut liant définitivement l'arbitre et le terrain de la simulation
		private int[] param;	//ensemble des paramètres interactifs que l'utilisateurs peut entrer pour paramétrer la simulation
		private Fourmiliere fourmiliere;	//idem pour la fourmiliere
		private ArrayList<Ennemi> ennemisList;	//Annuaire des ennemis 
		public final static int dureePhD = 10;	//Durée de vie des phéromones
		public final static int dureePhN = 30;	//Durée de vie des phéromones
		public int defaut;
		
		//Accesseurs
		public int getTour() {
			return this.tour;
		}
		public Fourmiliere getFourmiliere() {
			return fourmiliere;
		}
		public Terrain getTerrain() {
			return terre;
		}
		public int[] getParam() {
			return param;
		}
		public ArrayList<Ennemi> getEnnemis() {
			return ennemisList;
		}
		
		//Mutateurs;
		public void nextTour() {
			this.tour += 1;
		}
		
		
		//Constructeur
		public Arbitre () {
			this.param = selection_param();
			this.terre = new Terrain(param[0], param[1], param[4]);
			this.fourmiliere = new Fourmiliere(terre);
			this.ennemisList = new ArrayList<Ennemi>();
			
			//set nourriture
			for (int i=0; i<param[4]; i++) {
				Nourriture n = new Nourriture(20, terre);
				this.terre.getListNourr()[i] = n;
			}
			
			//set obstacle			
			for (int i = 0; i<param[2]; i++){
				Coordonnee p = null;
				int f = 0;
				boolean ok = false;
				while (ok==false){
					Coordonnee pos = terre.getCoord()[terre.getIdCoord((int) (Math.random()*(param[0])), (int) (Math.random()*(param[1])))];
					int k = terre.getIdCoord(pos.getX(), pos.getY());
					while ((terre.getAnnuaire()[k].isVierge()==false)
							|| (pos.getX()>=terre.getLargeur()-4)
							|| (pos.getY()>=terre.getLongeur()-4)) {
						k++;
						if (k==terre.getLargeur()*terre.getLongeur()) {
							k = terre.getIdCoord(1, 1);
						}
						pos = terre.getCoord()[k];
					}
					int n = (int) (Math.random()*4);
					Coordonnee[] forme = Obstacle.defForme(pos, n, terre);
					for (int j=0; j<4; j++){
						int id = terre.getIdCoord(forme[j].getX(),forme[j].getY());
						if (this.terre.getAnnuaire()[id].getOccupation() != null){
							ok = false;
							break;
						}
						else {
							ok = true;
						}
					}
					if (ok) {
						p = pos;
						f = n;
					}
					
				}
				
				Coordonnee[] F = Obstacle.defForme(p, f, terre);
				for (Coordonnee c : F) {
					int n = terre.getIdCoord(c.getX(), c.getY());
					this.terre.getAnnuaire()[n].setOccupation(typeOccupation.Obstacle);
				}
			}
			
			//set Ennemi
				for (int i= 0; i<param[3]; i++){
					Ennemi e = new Ennemi(terre);
					ennemisList.add(e);
				}
			this.tour = 0;
		}
				
		//Méthodes
		
		/**
		 * Méthodes d'entrée des paramètres de la simulation à venir 
		 * [Largeur terrain, Longueur Terrain, Nombre d'obstacles,
		 * Nombre d'ennemis, Nombre de source de nourriture, Nombre de tour de jeu].
		 * Dispose d'un paramètrage par défaut.
		 * @return int[6] : 
		 */
		public int[] selection_param () {
			// initialisation du retour
			int[] tab = new int[6];
			int defaut = 0;
			Scanner sc = new Scanner(System.in);
			System.out.println ("Paramètres par défaut ? \n     'oui' : taper 1\n     'non' : taper 0");
			try {defaut = sc.nextInt(); this.defaut = defaut;}
			catch (InputMismatchException e) {
				System.out.println ("Erreur : Entrez un entier ("+e+")");}
			if (defaut == 1) {
				tab[0] = 50;
				tab[1] = 80;
				tab[2] = 10;
				tab[3] = 8;
				tab[4] = 5;
				tab[5] = 1000;
				sc.close();
			}
			else {
				// 1er param : largeur
				boolean p1 = false;
				System.out.println ("Entrez le paramètre 1 : largeur du terrain (contrainte: entier compris entre 15 et 65)");
				int param1 = 0;
				while (p1==false) {		
					try {param1 = sc.nextInt();}
					catch (InputMismatchException e) {
						System.out.println ("Erreur : Entrez un entier ("+e+")");}
					if (param1 < 15 || param1 > 65) {
						System.out.println("Erreur : Entrez un entier compris entre 15 et 65.");
					}
					else {
						p1 = true;
					}
				}
				tab[0] = param1;
				// 2e param : longueur
				boolean p2 = false;
				System.out.println ("Entrez le paramètre 2 : longueur du terrain (contrainte: entier compris entre 15 et 130)");
				int param2 = 0;
				while (p2 == false){			
					try {param2 = sc.nextInt();}
					catch (InputMismatchException e) {
						System.out.println ("Erreur : Entrez un entier ("+e+")");}
					if (param2 < 15 || param2 > 130) {
						System.out.println("Erreur : Entrez un entier compris entre 15 et 130.");
					}
					else {
						p2 = true;
					}
				}
				tab[1] = param2;
				// 3e param : nbObstacles
				boolean p3 = false;
				int c3 = (int) (2*(tab[0]+tab[1]));
				System.out.println ("Entrez le paramètre 3 : nombre d'obstacles (contrainte: entier inférieur à "+c3+" )");
				int param3 = 0;
				while (p3 == false){			
					try {param3 = sc.nextInt();}
					catch (InputMismatchException e) {
						System.out.println ("Erreur : Entrez un entier ("+e+")");}
					if (param3 >=c3) {
						System.out.println("Erreur : Entrez un entier inférieur à "+c3);
					}
					else {
						p3 = true;
					}
				}
				tab[2] = param3;
				// 4e param : nbEnnemis
				boolean p4 = false;
				int c4 = (int)(tab[0]*tab[1]/(tab[0]+tab[1])*0.75);
				if (c4<3) {	c4=3; }
				System.out.println ("Entrez le paramètre 4 : nombre d'ennemis (contrainte: entier inférieur à "+c4+" )");
				int param4 = 0;
				while (p4 == false){		
					try {param4 = sc.nextInt();
						if (param4 >= c4) {
							System.out.println("Erreur : Entrez un entier inférieur à "+c4);
						}
						else {
							p4 = true;
						}
						tab[3] = param4;}
					catch (InputMismatchException e) {
						System.out.println ("Erreur : Entrez un entier ("+e+")");}	
				}
			
				// 5e param : nbNourritures
				boolean p5 = false;
				int c5 = (int)(tab[0]*tab[1]/(tab[0]+tab[1])*0.50);
				if (c5<2) {	c5=2; }
				System.out.println ("Entrez le paramètre 5 : nombre de sources de nourriture (contrainte: entier inférieur à "+c5+" )");
				int param5 = 0;
				while (p5 == false){			
					try {param5 = sc.nextInt();}
					catch (InputMismatchException e) {
						System.out.println ("Erreur : Entrez un entier ("+e+")");}
					if (param5 >= c5) {
						System.out.println("Erreur : Entrez un entier inférieur à "+c5);
					}
					else {
						p5 = true;
					}
				}
				tab[4] = param5;
				// 6e param : nbToursMax
				boolean p6 = false;
				int c6 = (tab[0]+tab[1])*20;
				System.out.println ("Entrez le paramètre 6 : nombre de tours maximum (contrainte: entier supérieur à "+c6+" )");
				int param6 = 0;
				while (p6 == false){			
					try {param6 = sc.nextInt();}
					catch (InputMismatchException e) {
						System.out.println ("Erreur : Entrez un entier ("+e+")");}
					if (param6 <= c6) {
						System.out.println("Erreur : Entrez un entier supérieur à "+c6);
					}
					else {
						p6 = true;
					}
				}
				tab[5] = param6;
				sc.close();
			}
			return tab;
		}
		
		
		//principes de jeu de la fourmilière
		public void run_fourm () {
			Fourmiliere fourm = this.getFourmiliere();
			Terrain terre = this.getTerrain();
			
			//destruction des fourmis qui doivent l'être
			Iterator<FourmiEclaireuse> itrE = fourm.getFourmisE().iterator();
			while (itrE.hasNext()) {
				FourmiEclaireuse f = (itrE.next());
				if (f.estMort(terre, fourm)) {
					f.detruire(terre);
					fourm.diminPop();
					itrE.remove();
				}
			}
			Iterator<FourmiTransporteuse> itrT = fourm.getFourmisT().iterator();
			while (itrT.hasNext()) {
				FourmiTransporteuse f = (itrT.next());
				if (f.estMort(terre, fourm)) {
					f.detruire(terre);
					fourm.diminPop();
					itrT.remove();
				}
			}	
			Iterator<FourmiCombattante> itrC = fourm.getFourmisC().iterator();
			while (itrC.hasNext()) {
				FourmiCombattante f = (itrC.next());
				if (f.estMort(terre, fourm)) {
					f.detruire(terre);
					fourm.diminPop();
					itrC.remove();
				}
			}
			
			///spawn fourmis
			
				// 1)test phero ==> combattantes et transporteuses
			boolean N = false;
			boolean D = false;
			for (Coordonnee c : fourm.getZoneFourmis()) {
				if (terre.getAnnuaire()[terre.getIdCoord(c.getX(), c.getY())].getOdeurDanger()>0) {
					D = true;
				}
				if (terre.getAnnuaire()[terre.getIdCoord(c.getX(), c.getY())].getOdeurNourriture()>0) {
					N = true;
				}
			for (Coordonnee k : fourm.getZoneVision()) {
				if (terre.getAnnuaire()[terre.getIdCoord(k.getX(), k.getY())].getOccupation()==typeOccupation.Ennemi) {
					D = true;
					break;
				}
			}
			}
			if (fourm.estMort()==false && N && this.getFourmiliere().getNbTransporteuses()<this.getTerrain().getLargeur() && this.tour%3==0) {
				fourm.genFourmiTransporteuse(terre);
			}
			if (fourm.estMort()==false && D && this.getFourmiliere().getNbCombattantes()<(this.getTerrain().getLongeur()+this.getTerrain().getLargeur()) && this.tour%3==0) {
				fourm.genFourmiCombattante(terre);
			}
			if (fourm.estMort()==false && this.getFourmiliere().getFourmisC().size()<5 && this.getFourmiliere().getFourmisT().size()<5) {
				fourm.genFourmiEclaireuse(terre);
			}
			

			//appelle au jeu pour chaque fourmi
			for (FourmiTransporteuse ft : fourm.getFourmisT()) {
				for (int v=0; v<ft.getVitesse(); v++) {
					run_transporteuse(ft);
				}
				ft.estMort(terre, fourm);
			}
			for (FourmiCombattante fc : fourm.getFourmisC()) {
				for (int v=0; v<fc.getVitesse(); v++) {
					run_combattante(fc);
				}
				fc.estMort(terre, fourm);
			}			
			for (FourmiEclaireuse fe : fourm.getFourmisE()) {
				for (int v=0; v<fe.getVitesse(); v++) {
					run_eclaireuse(fe);
				}
				fe.estMort(terre, fourm);
			}
		}
		
		//principes de jeu des éclaireuses
		public void run_eclaireuse (FourmiEclaireuse f) {
			Fourmiliere fourm = this.getFourmiliere();
			Terrain terre = this.getTerrain();
			
			// detection d'ennemis
			f.detecterEnnemis(terre);
			if (f.getDanger()) {
				f.libererPheromoneDanger(terre);
				f.revenir(terre, fourm, this);;
			}
			// sur le chemin du retour
			if (f.getRetour()) {
				f.revenir(terre, fourm, this);
				f.libererPheromoneNourriture(terre);
			}
			// detecte de la nourriture
			if (f.getRetour()==false && f.detecterNourriture(terre)!=null) {
				Coordonnee N = f.detecterNourriture(terre);
				if ((int)(IAttaquer.distance(f.getPosition(), N))<2) {
					f.setRetour(true);
				}
				else {
					Coordonnee c = IParcourir.calcPCC(f.getPosition(), N, terre);
					IParcourir.deplacement(f.getPosition(), c, f.getType(), terre);
					f.setPrecedente(f.getPosition());
					f.setPosition(c);
					f.defForme(c, terre);
					f.defZoneOdorat(terre);
					f.defZoneVision(terre);
					
				}
			}
			// parcours aleatoire
			if (f.detecterNourriture(terre)==null) {
				if (this.tour%4==0 && f.getExpansion()!=null) {
					Coordonnee k = IParcourir.calcPCC(f.getPosition(), f.getExpansion(), terre);
					IParcourir.deplacement(f.getPosition(), k, f.getType(), terre);
					f.setPosition(k);
					f.defForme(k, terre);
					f.defZoneVision(terre);
					f.defZoneOdorat(terre);
				}
				else {
					Coordonnee c = IParcourir.deplacAleat(f.getPosition(), f.getType(), terre, this);
					f.setPrecedente(f.getPosition());
					f.setPosition(c);
					f.defForme(c, terre);
					f.defZoneOdorat(terre);
					f.defZoneVision(terre);
				}		
			}
		}
		
		
		
		//principes de jeu des transporteuses
		public void run_transporteuse (FourmiTransporteuse f) {
			Fourmiliere fourm = this.getFourmiliere();
			Terrain terre = this.getTerrain();
			
			// detection d'ennemis
			f.detecterEnnemis(terre);
			if (f.getDanger()) {
				f.libererPheromoneDanger(terre);
			}
			// sur le chemin du retour
			if (f.getRetour() && f.getChargeN()>0) {
				if (IAttaquer.distance(f.getPosition(), fourm.getPosition())<4) {
					f.deposer(fourm);
				}
				else {
					f.revenir(terre, fourm, this);
				}
			}
			// detecte de la nourriture
			if (f.getRetour()==false && f.detecterNourriture(terre)!=null) {
				Coordonnee N = f.detecterNourriture(terre);
				Nourriture nourr = null;
				for (Nourriture n : terre.getListNourr()) {
					if (n!=null && n.getPosition()==N) {
						nourr = n;
					}
				}
				if ((int)(IAttaquer.distance(f.getPosition(), nourr.getPosition())) < 2) {
					f.emporter(nourr, terre);
					f.setRetour(true);
				}
				else {
					Coordonnee c = IParcourir.calcPCC(f.getPosition(), nourr.getPosition(), terre);
					IParcourir.deplacement(f.getPosition(), c, f.getType(), terre);
					f.setPrecedente(f.getPosition());
					f.setPosition(c);
					f.defForme(c, terre);
					f.defZoneOdorat(terre);
					f.defZoneVision(terre);
				}
			}
			// detection des pheromones nourriture
			f.suivre(terre, fourm, this);
			
		}
		
		//principes de jeu des combattantes
		public void run_combattante (FourmiCombattante f) {
			Fourmiliere fourm = this.getFourmiliere();
			Terrain terre = this.getTerrain();
			
			// sur le chemin du retour
			if (f.getRetour()) {
				f.revenir(terre, fourm, this);
			}
			
			// detecte des ennemis
			if (f.getRetour()==false && f.detecterEnnemis(terre).isEmpty()==false) {
				ArrayList<Coordonnee> E = f.detecterEnnemis(terre);
				Ennemi enn = null;
				Coordonnee c = terre.getCoord()[IParcourir.defNbAleat(E.size())];
				for (Ennemi e : this.getEnnemis()) {
					enn = e;
					if (e!=null && e.getPosition()==c) {
						break;
					}
				}
				if (enn!=null) {
					if (IAttaquer.distance(f.getPosition(), enn.getPosition())<2) {
						IAttaquer.attaquer(enn.getPosition(), fourm, this);
					}
					else {
						Coordonnee k = IParcourir.calcPCC(f.getPosition(), enn.getPosition(), terre);
						IParcourir.deplacement(f.getPosition(), k, f.getType(), terre);
						f.setPrecedente(f.getPosition());
						f.setPosition(k);
						f.defForme(k, terre);
						f.defZoneOdorat(terre);
						f.defZoneVision(terre);
					}	
				}
			}
			// detecte les pheromones
			f.suivre(terre, fourm, this);
		}
		
		//principes de jeu des ennemis
		public void run_ennemi (Ennemi e) {
			
			Fourmiliere fourm = this.getFourmiliere();
			
			if (IAttaquer.distance(fourm.getPosition(), e.getPosition())<3) {
				IAttaquer.attaquer(fourm.getPosition(), fourm, this);
			}
				
			ArrayList<Coordonnee> F = e.detecterFourmi(terre);
			if (F.isEmpty()==false) {
				for (Coordonnee c : F) {
					if (IAttaquer.distance(e.getPosition(), c)<2) {
						IAttaquer.attaquer(c, fourm, this);
						break;
					}
					else {
						Coordonnee k = F.get(IParcourir.defNbAleat(F.size()));
						Coordonnee C = IParcourir.calcPCC(e.getPosition(), k, terre);
						IParcourir.deplacement(e.getPosition(), C, typeOccupation.Ennemi, terre);
						e.setPosition(C);
						e.defForme(C, terre);
						e.defZoneVision(terre);
					}
				}
			}
			else {
				Coordonnee C = IParcourir.deplacAleat(e.getPosition(), e.getType(), terre, this);
				e.setPosition(C);
				e.defForme(C, terre);
				e.defZoneVision(terre);
			}
			
			if (e.getPv()==e.getPv()-e.getDegat()) {
				e.setPv(e.getPv()+e.getDegat());
			}
		}
				
		//le tour de jeu complet
		public void run() {
			
			// TOUR DES FOURMIS
			run_fourm();
			
			// TOUR DES ENNEMIS
			for (int i = 0; i<ennemisList.size(); i++) {
				
				Ennemi e = ennemisList.get(i);				
				run_ennemi(e);
				if (this.tour%10==0) {
					Coordonnee C = IParcourir.calcPCC(e.getPosition(), this.getFourmiliere().getPosition(), terre);
					IParcourir.deplacement(e.getPosition(), C, e.getType(), terre);
					e.setPosition(C);
					e.defForme(C, terre);
					e.defZoneVision(terre);
				}
				
				if (e.estMort(terre)) {
					e.detruire(terre);
					ennemisList.remove(i);
				}
			}
			
			//gestion des sites de nourriture
			Nourriture[] N = this.getTerrain().getListNourr();
			for (int i=0; i<N.length; i++) {
				if (N[i]!=null
					&& N[i].testFin(this.getTerrain())) {
					N[i].detruire(this.getTerrain());
					N[i]=null;
				}
			}	
			this.getTerrain().diminPherom();
			nextTour();
		}
		
		
		public boolean isRunning() {
			
			boolean bool = true;
			
			Nourriture[] testN = new Nourriture[param[4]];
			for (int i=0; i<param[4]; i++) {
				testN[i] = null;
			}
			
			if ((this.getTour()>param[5]) ||
				(this.getTerrain().getListNourr().equals(testN))) {
				bool = false;
			}
			
			return bool;
		}
}
