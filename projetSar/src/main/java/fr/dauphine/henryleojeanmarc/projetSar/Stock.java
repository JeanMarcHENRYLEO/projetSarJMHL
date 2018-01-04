package fr.dauphine.henryleojeanmarc.projetSar;

import java.util.ArrayList;
import java.util.List;

public class Stock {
	String nom;
	double valeur;
	int nbActionTotal;
	int nbActionFlottant;
	List<Commande>CommandeList=new ArrayList<Commande>();
	
	public Stock(String n,double v,int nbAT,int nbAF){
		nom=n;
		valeur=v;
		nbActionTotal=nbAT;
		nbActionFlottant=nbAF;
	}
	
	public String getNom(){
		return nom;
	}
	
	public double getValeur() {
		return valeur;
	}

	public void setValeur(double valeur) {
		this.valeur = valeur;
	}

	public int getNbActionFlottant() {
		return nbActionFlottant;
	}

	public void setNbActionFlottant(int nbActionFlottant) {
		this.nbActionFlottant = nbActionFlottant;
	}

	public List<Commande> getCommandeList() {
		return CommandeList;
	}

	public void setCommandeList(List<Commande> commandeList) {
		CommandeList = commandeList;
	}

	public void addCommande(Commande c){
		CommandeList.add(c);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return nom+": action : "+valeur+"/ ActionTot :"+ nbActionTotal +"/ ActionFlottantes "+ nbActionFlottant;
	}
}

