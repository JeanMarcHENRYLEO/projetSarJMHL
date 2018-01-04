package fr.dauphine.henryleojeanmarc.projetSar;

import java.util.ArrayList;
import java.util.List;

public class Client {
	private String nom;

	public Client(String nom){
		this.nom = nom;
	}


	@Override
	public String toString() {
		return nom;
	}
}
