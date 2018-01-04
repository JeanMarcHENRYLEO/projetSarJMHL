package fr.dauphine.henryleojeanmarc.projetSar;

public abstract class Commande{
    boolean typeCommande;
    private double price;
    private int quantity;
    private Societe societe;
    private Client client;
    private Courtier courtier;
}