package fr.dauphine.henryleojeanmarc.projetSar;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bourse extends Thread implements BourseInterface{
	List<Societe> SocieteList= new ArrayList<Societe>();
	List<Courtier> CourtierList= new ArrayList<Courtier>();
	//List<Commande> CommandeList= new ArrayList<Commande>();
	//Map<String, List<Commande>> CommandeParEntreprise= new HashMap<String, List<Commande>>();
	public Bourse(){
		SocieteList.add(new Societe("Google",10,10000,10000));
		
		CourtierList.add(new Courtier("gecko", 10, 50000));
	}
	
	public void afficherListSociete(){
		for(Societe s:SocieteList){
			System.out.println(s);
		}
	}
	public void afficherListCourtier(){
		for(Courtier s:CourtierList){
			System.out.println(s);
		}
	}
	
	public void run(){
		int port=5000;
		ServerSocket se;
		Socket ssv=null;
		
			try{
				se = new ServerSocket(port); // socket d'�coute
				System.out.println("Serveur ecoute");
				
				 while(true){ 
					ssv = se.accept(); 
					System.out.println("Connexion accpet�e par le client ");
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
