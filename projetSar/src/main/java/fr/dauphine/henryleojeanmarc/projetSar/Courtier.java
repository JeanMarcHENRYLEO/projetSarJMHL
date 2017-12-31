package fr.dauphine.henryleojeanmarc.projetSar;

import java.util.ArrayList;
import java.util.List;

public class Courtier extends Thread {
	List<Client> clientList= new ArrayList<Client>();
	String nom;
	List<Commande> CommList= new ArrayList<Commande>();
	double taux;
	double espece;
	public Courtier(String name,double t, double e){
		nom=name;
		taux=t;
		espece=e;
		clientList.add(new Client("Moore"));
	}
	
	public void addCommande(Commande c){
		CommList.add(c);
	}
	public void execute(Commande c){
	}
	
	public void executeList(){
		for(Commande c : CommList){
			execute(c);
			//todo : wait until the receipt from the market
		}
	}
	
	public void addClient(Client c){
		clientList.add(c);
	}
	
	public void AffClient(){
		for(Client c : clientList){
			System.out.println(c);
		}
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return nom+": taux : "+taux+" / espece :"+espece;
	}
}
