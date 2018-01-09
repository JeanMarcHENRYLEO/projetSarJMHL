package fr.dauphine.henryleojeanmarc.projetSar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ThreadClient extends Thread {

    private Courtier courtier;
    private Socket socket;

    private BufferedReader in;
    private PrintWriter out;

    private String requete = "";
    private String reponse = "";
    private String message = "";

    public ThreadClient(Socket socket, Courtier courtier,String message) {
        this.socket = socket;
        this.courtier = courtier;
        this.message=message;
    }

    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            System.err.println("Impossible d'initialiser les flux de sortie/entrée");
        }

        try {
            reponse = in.readLine();
            this.setName(reponse);
        } catch (IOException e) {
            e.printStackTrace();
        }

        requete = "accept " + courtier.getNom();
        out.println(this.getName() + ":" + requete);
        System.out.println(message);
        out.println(message);

        try {
            in.close();
            out.close();
            socket.close();

            System.out.println("les flux de ThreadClient " + this.getName() + " ont été fermés");
        } catch (IOException e) {
            System.err.println("erreur lors de la fermeture des flux de ThreadClient");
        }
    }

    @Override
    public String toString() {
        return "[" + this.getName() + "]";
    }
}

