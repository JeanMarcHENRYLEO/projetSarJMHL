package fr.dauphine.henryleojeanmarc.projetSar;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;


public class Courtier extends Thread{
	private boolean journeeFinie;
	private String nom;
    private double taux;
    private double espece;
    private static int count;

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
        journeeFinie = false;

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

            System.out.println("la bourse se trouve sur " + socketBourse.getPort());

            in = new BufferedReader(new InputStreamReader(socketBourse.getInputStream()));
            out = new PrintWriter(socketBourse.getOutputStream(), true);

            System.out.println("envoi de mon ID à la bourse (" + this.nom + ")");
            out.println(this.nom);

            System.out.println("attente de l'autorisation par la bourse");

            /*String reponse = in.readLine();
            while(reponse.isEmpty()){
            	System.out.println("notyet");
            	sleep(500);
            }
            System.out.println("reponse récue:" + reponse);
            if (reponse.equals("accept")) {*/

            String[] reponse = in.readLine().split(" ");
            if (reponse[0].equals("accept")) {
                System.out.println("accés autorisé");
                afficherReponse();

                String a = in.readLine();
                String message = "";

                while(!a.isEmpty()){
                	message += a + "\n";
                	a = in.readLine();
                }

	            System.out.println(message);
                while (true) {
                    System.out.println("courtier " + nom + " est en attente d'une connexion d'un client");

                    socketClient = serverSocket.accept();
                    ThreadClient threadClient = new ThreadClient(socketClient, this,message);
                    addClient(threadClient);
                    sleep(2000);

                    this.afficherClientList();
                    if(journeeFinie){
                    	break;
                    }
                }

            } else
                System.out.println("accés refusé");
        } catch(BindException b){
        	System.err.println("erreur : choisir un autre port");
        } catch (IOException e) {
            e.printStackTrace();
        
        } catch (InterruptedException e) {
            System.err.println("erreur: sleep interrompu");
        } finally {
            try {
                socketBourse.close();
               // socketClient.close();
                in.close();
                out.close();

                System.out.println("les flux de courtier " + this.nom + " ont ete fermes");
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

        /*
        List<Courtier> courtiers = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        System.out.println("Veuillez saisir votre nom :");
        String str = sc.nextLine();
        System.out.println("Veuillez un port entre 4000 et 6000 :");
        String stp = sc.nextLine();
        int port = Integer.parseInt(stp);
        courtiers.add(new Courtier(str, 0.01,port));
        for (Courtier courtier: courtiers)
            courtier.start();
        */
    }
}


