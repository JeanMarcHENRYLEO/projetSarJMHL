package fr.dauphine.henryleojeanmarc.projetSar;

import java.util.ArrayList;
import java.util.List;

public class Societe {
	private String nom;
	private double valeur;
	private int nombreActionTotal;
	private int nombreActionFlottant;
	private List<Commande>CommandeList = new ArrayList<>();
	
	public Societe(String nom, double valeur, int nombreActionTotal, int nombreActionFlottant){
		this.nom = nom;
		this.valeur = valeur;
		this.nombreActionTotal = nombreActionTotal;
		this.nombreActionFlottant = nombreActionFlottant;
	}
	
	public void addCommande(Commande commande){
		CommandeList.add(commande);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return nom + ": action : " + valeur + "/ ActionTot :" + nombreActionTotal + "/ ActionFlottantes " + nombreActionFlottant;
	}
}