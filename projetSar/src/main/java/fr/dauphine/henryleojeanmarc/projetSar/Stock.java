package fr.dauphine.henryleojeanmarc.projetSar;

import java.util.ArrayList;
import java.util.List;

public class Stock {
	private String nom;
	private double prix;
	int nbActionTotal;
	int nbActionFlottant;
	List<Commande>CommandeList=new ArrayList<Commande>();
	
	public Stock(String nom, double prix, int nbAT,int nbAF){
		this.nom = nom;
		this.prix = prix;
		this.nbActionTotal=nbAT;
		this.nbActionFlottant=nbAF;
	}
	
	public String getNom(){
		return nom;
	}
	
	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
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

	public Commande getCommande(int i) {
	    return CommandeList.get(i);
    }

    public void setCommande(Commande commande) {
	    this.CommandeList.add(commande);
    }

	public void addCommande(Commande commande){
		CommandeList.add(commande);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return nom + ": action : " + prix + "/ ActionTot :" + nbActionTotal + "/ ActionFlottantes " + nbActionFlottant;
	}
}

