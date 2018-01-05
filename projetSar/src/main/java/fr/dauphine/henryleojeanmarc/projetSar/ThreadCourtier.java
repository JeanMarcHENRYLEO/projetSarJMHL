package fr.dauphine.henryleojeanmarc.projetSar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ThreadCourtier extends Thread {

    private Bourse bourse;
    private Socket socket;

    private BufferedReader in;
    private PrintWriter out;

    private String requete = "";
    private String reponse = "";

    public ThreadCourtier(Socket socket, Bourse bourse) {
        this.bourse = bourse;
        this.socket = socket;
    }

    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            System.err.println("impossible d'initialiser les flux de sortie/entrée");
        }

        try {
            reponse = in.readLine();
            this.setName(reponse);
            System.out.println("courtier connecté:" + reponse);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("envoi de l'accept");
        requete = "accept";
        out.println(requete);

        System.out.println("accept envoyé");

        try {
            in.close();
            out.close();
            socket.close();

            System.out.println("les flux associés au ThreadCourtier " + this.toString() + " ont été fermés");
        } catch (IOException e) {
            System.err.println("erreur lors de la fermeture des flux");
        }
    }

    @Override
    public String toString() {
        return "{" + this.getName() + "}";
    }
}
