package fr.dauphine.henryleojeanmarc.projetSar;

import java.util.ArrayList;
import java.util.List;

public class Courtier {
	List<Client> Cl;
	String nom;
	List<Commande> Comm;
	double taux;
	double espece;
	public Courtier(ArrayList<Client> ac,String name,ArrayList<Commande> c,double t, double e){
		Cl=ac;
		nom=name;
		Comm=c;
		taux=t;
		espece=e;
	}
	
	public void execute(Commande c){
	}
}
