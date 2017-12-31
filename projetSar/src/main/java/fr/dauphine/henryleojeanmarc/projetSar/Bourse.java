package fr.dauphine.henryleojeanmarc.projetSar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bourse {
	List<Societe> SocieteList= new ArrayList<Societe>();
	List<Courtier> CourtierList= new ArrayList<Courtier>();
	//List<Commande> CommandeList= new ArrayList<Commande>();
	//Map<String, List<Commande>> CommandeParEntreprise= new HashMap<String, List<Commande>>();
	public Bourse(){
		SocieteList.add(new Societe("Google",10,10000,10000));
		
		CourtierList.add(new Courtier("gecko", 10, 50000));
	}
	
	public void AffListSociete(){
		for(Societe s:SocieteList){
			System.out.println(s);
		}
	}
	public void AffListCoutier(){
		for(Courtier s:CourtierList){
			System.out.println(s);
		}
	}

}
