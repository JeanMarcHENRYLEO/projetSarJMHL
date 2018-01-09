package fr.dauphine.henryleojeanmarc.projetSar;

public class Commande {
	private boolean achat;
	private double prix;
	private Stock stock;
	private int nbAction;
	private Client client;
	private Courtier courtier;

	public Commande(boolean achat, double prix, Stock stock, int nbAction) {
		this.achat = achat;
		this.prix = prix;
		this.stock = stock;
		this.nbAction = nbAction;
	}

	public boolean isAchat() {
		return achat;
	}

    public Stock getStock() {
        return stock;
    }

    public int getNbAction() {
        return nbAction;
    }
}
