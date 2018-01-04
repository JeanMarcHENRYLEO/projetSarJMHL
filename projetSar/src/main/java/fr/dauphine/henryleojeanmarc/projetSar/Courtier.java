package fr.dauphine.henryleojeanmarc.projetSar;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Courtier extends Thread {
	private String nom;
    private double taux;
    private double espece;

	List<Commande> commandeList;
    List<Client> clientList;

    private int port;
    private ServerSocket serverSocket = null;
    private Socket socket = null;

    /**
     *
     * @param nom - un nom unique
     * @param taux - le taux de commission
     * @param espece - le montant d'argent que le courtier a gagné
     */
	public Courtier(String nom,double taux, double espece){
		this.nom = nom;
		this.taux = taux;
		this.espece = espece;
		clientList.add(new Client("Moore"));

		commandeList = new ArrayList<Commande>();
		clientList = new ArrayList<Client>();
	}
	
	public void addCommande(Client client, Commande commande){
        commandeList.add(commande);
	}

	public void execute(Commande commande){
	}
	
	public void executeList(){
		for(Commande commande: commandeList){
			execute(commande);

			//todo : wait until the receipt from the market
		}
	}
	
	public void addClient(Client c){
		clientList.add(c);
	}
	
	public void afficherClientList(){
		for(Client c : clientList){
			System.out.print(c + ";");
		}
		System.out.println();
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
        return "nom: " + nom + "/nombreClient:" + clientList.size();
	}
}
