package fr.dauphine.henryleojeanmarc.projetSar;

import java.util.ArrayList;
import java.util.List;

public class Stock {
	private String nom;
	private double prix;
	int nbActionTotal;
	int nbActionFlottant;
	List<Commande>CommandeList=new ArrayList<>();
	
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

	public int getNbActionTotal() {
		return nbActionTotal;
	}

	public int getNbActionFlottant() {
		return nbActionFlottant;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return nom + " P: " + prix + " AT: " + nbActionTotal + " AF: " + nbActionFlottant;
	}
}

