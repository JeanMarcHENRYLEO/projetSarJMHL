package fr.dauphine.henryleojeanmarc.projetSar;

public class App 
{
    public static void main( String[] args )
    {
    	Bourse Nasdaq = new Bourse();
    	Nasdaq.afficherListSociete();
    	Nasdaq.afficherListCourtier();
    }
}
