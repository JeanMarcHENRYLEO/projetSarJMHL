package fr.dauphine.henryleojeanmarc.projetSar;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class Courtier extends Thread{
	boolean journeeDebute;
	private String nom;
    private double taux;
    private double espece;

    private List<Commande> commandeList;
    private List<ThreadClient> clientList;

    private int port;
    private ServerSocket serverSocket = null;
    private Socket socket = null;

    /**
     *
     * @param nom - un nom unique
     * @param taux - le taux de commission
     * espece - le montant d'argent que le courtier a gagné
     */
    public Courtier(String nom, double taux, int port) {
        this.nom = nom;
        this.taux = taux;
        this.espece = 0;
        commandeList = new ArrayList<>();
        clientList = new ArrayList<>();
        this.port = port;
    }

    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("courtier " + nom + " est à l'écoute");

            while (true) {
                socket = serverSocket.accept();
                ThreadClient threadClient = new ThreadClient(socket, this);
                addClient(threadClient);

                sleep(2500);

                this.afficherClientList();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.err.println("erreur interrompu");
        }

    }
    
    public void ClientConnecte(){
		journeeDebute=true;
	}
	

    public void addCommande(Commande commande) {
        commandeList.add(commande);
    }

    public void execute(Commande commande) {

    }

    public void executeList(){
        for(Commande commande: commandeList){
            execute(commande);

            //todo : wait until the receipt from the market
        }
    }

    public void addClient(ThreadClient threadClient){
        clientList.add(threadClient);

        System.out.println(this.toString());

        threadClient.start();
    }

    public void afficherClientList() {
        for(ThreadClient threadClient : clientList){
            System.out.print(threadClient);
        }
        System.out.println();
    }
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "nom: " + nom + "/nombreClient:" + clientList.size();
    }

    public String getNom() {
        return nom;
    }

    public static void main(String[] args) {

        List<Courtier> courtiers = new ArrayList<>();

        courtiers.add(new Courtier("Max", 0.01, 4040));
        courtiers.add(new Courtier("Yan", 0.05, 5050));

        for (Courtier courtier: courtiers)
            courtier.start();

    }
}


