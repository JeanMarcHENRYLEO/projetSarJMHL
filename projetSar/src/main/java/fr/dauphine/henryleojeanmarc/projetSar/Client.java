package fr.dauphine.henryleojeanmarc.projetSar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class Client extends Thread{
    private String nom;

    private int port;
    private InetAddress hote = null;
    private Socket socket = null;
    private BufferedReader in = null;
    private PrintWriter out = null;

    private String requete;
    private String[] reponse;
    private String nomCourtier;

    public Client(String nom, int port) {
        this.nom = nom;
        this.port = port;
        try {
            this.hote = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            System.err.println("Impossible d'initialiser le hote");
        }
    }

    public void run() {
        try {
            socket = new Socket(hote, port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("connecté au " + socket.getInetAddress() + socket.getPort());

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("erreur lors de la initialisation des flux");
        }

        System.out.println("les flux ont été initialisés");

        try {
            System.out.println("envoi de mon identifiant au courtier (" + this.nom + ")");
            out.println(nom);

            reponse = in.readLine().split(" ");
            System.out.println(reponse[0]);

            nomCourtier = reponse[1];

            if (reponse[0].equals("accept")){
                System.out.println("le courtier " + nomCourtier + " a accepté ma connexion");


                while (true) {

                }
            }
            else {
                System.out.println("le courtier " + nomCourtier + " a refusé la connexion");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            in.close();
            out.close();
            socket.close();

            System.out.println("les flux associés au client " + this.nom + " ont été fermés");
        } catch (IOException e) {
            System.err.println("erreur lors de la fermeture des flux de Client");
        }
    }


    @Override
    public String toString() {
        return nom;
    }

    public static void main(String[] args) {

        String nom = "Marie";
        int port = 4040;

        if (args.length >= 2) {
            nom = args[0];
            port = Integer.parseInt(args[1]);
        } else if (args.length == 1)
            nom = args[0];

        Client client = new Client(nom, port);
        client.start();
    }
}

