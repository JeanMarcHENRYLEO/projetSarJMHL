package fr.dauphine.henryleojeanmarc.projetSar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ThreadClient extends Thread {

    private Socket socket;
    private Courtier courtier;

    private BufferedReader in;
    private PrintWriter out;

    private String requete = "";
    private String reponse = "";

    public ThreadClient(Socket socket, Courtier courtier) {
        this.socket = socket;
        this.courtier = courtier;
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

        requete = "hello from " + courtier.getNom();
        out.println(this.getName() + ":" + requete);

        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "[" + this.getName() + "]";
    }
}

