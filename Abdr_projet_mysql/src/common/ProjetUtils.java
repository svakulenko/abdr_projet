package common;

public class ProjetUtils {
	
	public  ProjetUtils() {}

	public static Integer numMagasin = 0; 
	
	public Integer getProduitPrix(String Produit){
		Integer rvalue = null;
		
		String produit = Produit.toLowerCase();
		
		if ( produit.equals("orange") )
			rvalue = 10;
		else if ( produit.equals("pomme") )
			rvalue = 11;
		else if ( produit.equals("pommedeterre") )
			rvalue = 12;
		else if ( produit.equals("boisson") )
			rvalue = 13;
		else if ( produit.equals("viande") )
			rvalue = 14;
		else if ( produit.equals("poisson") )
			rvalue = 15;
		else if ( produit.equals("poivre") )
			rvalue = 16;
		else if ( produit.equals("tomate") )
			rvalue = 17;
		else if ( produit.equals("banane") )
			rvalue = 18;
		else if ( produit.equals("ananas") )
			rvalue = 19;
		
	
		return rvalue;
	}
	public Integer getProduitPrix(Integer numProduit){
		Integer rvalue = null;
		
		if ( numProduit == 0 )
			rvalue = 5;
		else if ( numProduit == 1 )
			rvalue = 5;
		else if ( numProduit == 2 )
			rvalue = 5;
		else if ( numProduit == 3 )
			rvalue = 5;
		else if ( numProduit == 4 )
			rvalue = 5;
		else if ( numProduit == 5 )
			rvalue = 5;
		else if ( numProduit == 6 )
			rvalue = 5;
		else if ( numProduit == 7 )
			rvalue = 5;
		else if ( numProduit == 8 )
			rvalue = 5;
		else if ( numProduit == 9 )
			rvalue = 5;
	
		return rvalue;
	}
}
