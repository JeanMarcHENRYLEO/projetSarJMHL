package fr.dauphine.henryleojeanmarc.projetSar;

import java.util.ArrayList;
import java.util.List;

public class Societe {
	String nom;
	double valeur;
	int nbActionTotal;
	int nbActionFlottant;
	List<Commande>CommandeList=new ArrayList<Commande>();
	
	public Societe(String n,double v,int nbAT,int nbAF){
		nom=n;
		valeur=v;
		nbActionTotal=nbAT;
		nbActionFlottant=nbAF;
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
