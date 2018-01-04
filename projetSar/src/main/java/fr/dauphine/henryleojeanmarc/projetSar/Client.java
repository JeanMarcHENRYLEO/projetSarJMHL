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
    private String reponse;

    public Client(String nom, int port) {
        this.nom = nom;
        this.port = port;
        try {
            this.hote = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
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

        out.println(nom);

        System.out.println("les flux ont été initialisés");

        try {
            reponse = in.readLine();
            System.out.println("reponse: " + reponse);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            socket.close();
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String toString() {
        return nom;
    }

    public static void main(String[] args) {

        List<Client> clients = new ArrayList<>();

        clients.add(new Client("Stephane", 4040));
        clients.add(new Client("Serget", 5050));
        clients.add(new Client("Liza", 4040));
        clients.add(new Client("Tatiana", 5050));
        clients.add(new Client("Maria", 4040));

        for (Client client: clients)
            client.start();


    }
}

