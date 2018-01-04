package fr.dauphine.henryleojeanmarc.projetSar;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;


public class Courtier extends Thread{
	boolean journeeDebute;
	private String nom;
    private double taux;
    private double espece;

    private List<Commande> commandeList;
    private List<ThreadClient> clientList;

    private int serverPort;
    private final int boursePort = Bourse.getPort();
    private ServerSocket serverSocket = null;
    private Socket socketClient = null;

    private Socket socketBourse = null;
    private InetAddress hote = null;
    private PrintWriter out = null;
    private BufferedReader in = null;

    String reponse = "";

    /**
     *
     * @param nom - un nom unique
     * @param taux - le taux de commission
     * espece - le montant d'argent que le courtier a gagné
     */
    public Courtier(String nom, double taux, int serverPort) {
        this.nom = nom;
        this.taux = taux;
        this.espece = 0;
        commandeList = new ArrayList<>();
        clientList = new ArrayList<>();
        this.serverPort = serverPort;

        try {
            this.hote = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            System.err.println("impossible d'initialiser le hote");
        }
    }

    public void run() {
        try {
            serverSocket = new ServerSocket(serverPort);
            socketBourse = new Socket(hote, boursePort);

            System.out.println("courtier " + nom + " est connecté à la bourse se trouvant sur " + socketBourse.getPort());
            System.out.println("courtier " + nom + " est à l'écoute");

            in = new BufferedReader(new InputStreamReader(socketBourse.getInputStream()));
            out = new PrintWriter(socketBourse.getOutputStream(), true);

            System.out.println("envoi de mon ID à la bourse");
            out.println(this.nom);
            System.out.println("attente de l'autorisation par la bourse");
            reponse = in.readLine();
            System.out.println("reponse récue:" + reponse);

            if (reponse.equals("accept")) {

                System.out.println("accés autorisé");

                while (true) {
                    socketClient = serverSocket.accept();
                    ThreadClient threadClient = new ThreadClient(socketClient, this);
                    addClient(threadClient);

                    sleep(2500);

                    this.afficherClientList();
                }
            }
            else
                System.out.println("accés refusé");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.err.println("erreur: sleep interrompu");
        } finally {
            try {
                socketBourse.close();
                socketClient.close();
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    
    public void ClientConnecte(){
		journeeDebute = true;
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
        //courtiers.add(new Courtier("Yan", 0.05, 5050));

        for (Courtier courtier: courtiers)
            courtier.start();

    }
}


