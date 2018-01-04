package fr.dauphine.henryleojeanmarc.projetSar;

public class Commande {
	private boolean achat;
	private double prix;
	private Stock stock;
	private int nbAction;
	private Client client;
	private Courtier courtier;
	
	public Commande(boolean ac,double p,Stock s,int nba,Client c,Courtier cr){
		achat=ac;
		prix=p;
		stock=s;
		client=c;
		courtier=cr;
	}

	public boolean isAchat() {
		return achat;
	}

	public void setAchat(boolean achat) {
		this.achat = achat;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public int getNbAction() {
		return nbAction;
	}

	public void setNbAction(int nbAction) {
		this.nbAction = nbAction;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Courtier getCourtier() {
		return courtier;
	}

	public void setCourtier(Courtier courtier) {
		this.courtier = courtier;
	}

}
