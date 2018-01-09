package fr.dauphine.henryleojeanmarc.projetSar;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Bourse extends Thread {
	private List<Stock> stockList;
	private List<ThreadCourtier> courtierList;
	private List<Commande> commandeAchatList;
	private List<Commande> commandeVenteList;

	private Socket socket;
	private ServerSocket serverSocket = null;
	private static final int port = 1234;

	private String nom;

    private String namefile = "stock";

    public Bourse(String nom) {
        stockList = new ArrayList<>();
        courtierList = new ArrayList<>();
        commandeAchatList = new ArrayList<>();
        commandeVenteList = new ArrayList<>();

        this.nom = nom;
    }

	public void afficherListStock(){
		for(Stock stock: stockList){
			System.out.println(stock);
		}
	}

	public void afficherListCourtier(){
		for(ThreadCourtier courtier: courtierList){
			System.out.print(courtier + " ");
		}
		System.out.println();
	}

	private int achatStock(Stock s){
		int res = 0;
		for(Commande commande: commandeAchatList){
			if(commande.getStock().getNom().equals(s.getNom())){
				res = res + commande.getNbAction();
			}
		}
		return res;
	}
	
	private int venteStock(Stock s){
		int res = 0;
		for(Commande commande: commandeVenteList){
			if(commande.getStock().getNom().equals(s.getNom())){
				res = res + commande.getNbAction();
			}
		}
		return res;
	}
	
	synchronized public void mettreAJourPrix(){
		for(Stock stock: stockList){
			int delta = (achatStock(stock) - venteStock(stock))/stock.nbActionTotal;
			stock.setPrix(stock.getPrix() * (1 + delta));
		}
	}

    public void run(){
			try{
				serverSocket = new ServerSocket(port); // socket d'ecoute
				System.out.println("serveur ecoute");
				 while(true){
				     socket = serverSocket.accept();
				     ThreadCourtier threadCourtier = new ThreadCourtier(socket, this); // on donne la socket ssv
                     addCourtier(threadCourtier);
                     
				 }
			} catch (IOException e) {
				System.err.println("erreur : " +e);
			} finally {
				try {
					socket.close();

					System.out.println("serversocket de Bourse a été fermé");
				} catch (IOException e){
					System.err.println("erreur lors de la fermeture du serversocket de la Bourse");
				}
		    }
		
	}

	synchronized String afficherListEntreprises() {
	    String result = "";

	    for (Stock stock: stockList)
	        result += stock.toString() + "\n";

	    return result;
    }

    public static int getPort() {
        return port;
    }

    private void ecrireFile() {
        BufferedWriter bufferedWriter = null;

        try {
            bufferedWriter = new BufferedWriter(new FileWriter(new File(namefile)));

            for (Stock stock: stockList)
                bufferedWriter.write(stock.getNom() + "," + stock.getPrix() + "," + stock.getNbActionTotal() + "," + stock.getNbActionFlottant() +"\n");

            bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void lireFile() {
        BufferedReader bufferedReader = null;
        String line;

        String[] lineArray;
        String nom;
        double prix;
        int nbAT;
        int nbAF;

        try {
            bufferedReader = new BufferedReader(new FileReader(new File(namefile)));

            while ((line = bufferedReader.readLine()) != null) {

                lineArray = line.split(",");

                nom = lineArray[0];
                prix = Double.parseDouble(lineArray[1]);
                nbAT = Integer.parseInt(lineArray[2]);
                nbAF = Integer.parseInt(lineArray[3]);

                Stock stock = new Stock(nom, prix, nbAT, nbAF);

                this.addStock(stock);

                System.out.println("entreprise: " + stock + " added");
            }

            bufferedReader.close();

        } catch (FileNotFoundException e) {
            System.err.println("fichier " + namefile + " non trouvé");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addCourtier(ThreadCourtier threadCourtier) {
	    courtierList.add(threadCourtier);
	    threadCourtier.start();
	    afficherListCourtier();
    }
    
    public void addStock(Stock s){
    	stockList.add(s);
    }

    synchronized private void removeCourtier(ThreadCourtier threadCourtier) {
        if (!threadCourtier.isAlive())
            courtierList.remove(threadCourtier);
    }

    public static void main(String[] args) {
        String nom = "CAC40";

        if (args.length >= 1)
            nom = args[0];

        Bourse bourse = new Bourse(nom);

        bourse.lireFile();

        bourse.start();
		try {
			bourse.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

        bourse.ecrireFile();

	}
}
