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

    private String[] reponse;

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
        this.commandeList = new ArrayList<>();
        this.clientList = new ArrayList<>();
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

            System.out.println("la bourse se trouve sur " + socketBourse.getPort());

            in = new BufferedReader(new InputStreamReader(socketBourse.getInputStream()));
            out = new PrintWriter(socketBourse.getOutputStream(), true);

            System.out.println("envoi de mon ID à la bourse (" + this.nom + ")");
            out.println(this.nom);

            System.out.println("attente de l'autorisation par la bourse");
            reponse = in.readLine().split(" ");
            afficherReponse();

            if (reponse[0].equals("accept")) {

                System.out.println("accés autorisé");

                while (true) {
                    System.out.println("courtier " + nom + " est en attente d'une connexion d'un client");

                    socketClient = serverSocket.accept();
                    ThreadClient threadClient = new ThreadClient(socketClient, this);
                    addClient(threadClient);

                    sleep(2000);

                    this.afficherClientList();
                }

            } else
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

                System.out.println("les flux de courtier " + this.nom + " ont été fermés");
            } catch (IOException e) {
                System.err.println("erreur lors de la fermeture des flux de Courtiers");
            }
        }
    }

    public void addCommande(Commande commande) {
        commandeList.add(commande);
    }

    public void addClient(ThreadClient threadClient){
        clientList.add(threadClient);

        System.out.println(this.toString());

        threadClient.start();
    }

    public void removeClient(ThreadClient threadClient) {
        if (!threadClient.isAlive())
            clientList.remove(threadClient);
    }

    public void afficherClientList() {
        for(ThreadClient threadClient : clientList){
            System.out.print(threadClient);
        }
        System.out.println();
    }
    @Override
    public String toString() {
        return "nom: " + nom + " nombreClient:" + clientList.size();
    }

    public String getNom() {
        return nom;
    }

    private void afficherReponse() {
        System.out.print("reponse:");

        for (String string: reponse)
            System.out.print(string + " ");

        System.out.println();
    }

    public static void main(String[] args) {

        String nom = "Max";
        double taux = 0.01;
        int port = 4040;

        if (args.length >= 3) {
            nom = args[0];
            taux = Double.parseDouble(args[1]);
            port = Integer.parseInt(args[2]);
        } else if (args.length == 2) {
            nom = args[0];
            taux = Double.parseDouble(args[1]);
        } else if (args.length == 1)
            nom = args[0];

        Courtier courtier = new Courtier(nom, taux, port);
        courtier.start();

    }
}


