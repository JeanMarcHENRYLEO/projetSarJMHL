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
import java.util.Scanner;

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
            e.printStackTrace();
        }

        System.out.println("les flux ont été initialisés");

        try {
            System.out.println("envoi de mon identifiant au courtier");
            out.println(nom);
            reponse = in.readLine().split(" ");
            nomCourtier = reponse[1];
            System.out.println(reponse[0]);
            if (reponse[0].equals(nom+":accept")){
                System.out.println("le courtier " + nomCourtier + " a accepté ma connexion");
                System.out.println("voici les stocks disponibles :");
                String tampon=in.readLine();
                String message="";
                while(!tampon.isEmpty()){
                	message+=tampon+"\n";
                	tampon =in.readLine();
                }
                System.out.println(message);
                Scanner sc = new Scanner(System.in);
                String str = sc.nextLine();
                while(true){
                	if(str.equals("bye")){
                		break;
                	}
                }
                System.out.println("sortiewhile");

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

        List<Client> clients = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        System.out.println("Veuillez saisir votre nom :");
        String str = sc.nextLine();
        System.out.println("Veuillez un port entre 4000 et 6000 :");
        String stp = sc.nextLine();
        int port = Integer.parseInt(stp);
        clients.add(new Client(str, port));

        for (Client client: clients)
            client.start();
    }
}

