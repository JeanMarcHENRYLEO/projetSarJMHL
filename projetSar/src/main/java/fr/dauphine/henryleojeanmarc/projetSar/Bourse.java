package fr.dauphine.henryleojeanmarc.projetSar;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bourse extends Thread implements BourseInterface{
	List<Stock> stockList= new ArrayList<Stock>();
	List<Courtier> courtierList= new ArrayList<Courtier>();
	List<Commande> CommandeAchatList= new ArrayList<Commande>();
	List<Commande> CommandeVenteList= new ArrayList<Commande>();
	//Map<String, List<Commande>> CommandeParEntreprise= new HashMap<String, List<Commande>>();
	public Bourse(){
		stockList.add(new Stock("Google",10,10000,10000));
		
		courtierList.add(new Courtier("gecko", 10, 50000));
	}
	
	public void AffListSociete(){
		for(Stock s:stockList){
			System.out.println(s);
		}
	}
	public void AffListCoutier(){
		for(Courtier s:courtierList){
			System.out.println(s);
		}
	}
	public int AchatStock(Stock s){
		int res=0;
		for(Commande c: CommandeAchatList){
			if(c.getStock().getNom().equals(s.getNom())){
				res=res+c.getNbAction();
			}
		}
		return res;
		
	}
	
	public int VenteStock(Stock s){
		int res=0;
		for(Commande c: CommandeVenteList){
			if(c.getStock().getNom().equals(s.getNom())){
				res=res+c.getNbAction();
			}
		}
		return res;
		
	}
	
	public void MetAJourPrix(){
		for(Stock s:stockList){
			int delta=(AchatStock(s)-VenteStock(s))/s.nbActionTotal;
			s.setValeur(s.getValeur()*(1+delta));
		}
		
	}
	
	public void run(){
		int port=5000;
		ServerSocket se;
		Socket ssv=null;
		
			try{
				se = new ServerSocket(port); // socket d'écoute
				System.out.println("Serveur ecoute");
				
				 while(true){ 
					ssv = se.accept(); 
					System.out.println("Connexion accpetée par le client ");
					//ThreadClient th =new ThreadClient(ssv); // on donne la socket ssv
				}
									
			}
			
			catch (IOException e){
				System.err.println("Erreur : " +e);
			}
			
			finally{
				
				try{
					ssv.close();
				}
				
				catch (IOException e){}
		    }
		
	}

	

}
