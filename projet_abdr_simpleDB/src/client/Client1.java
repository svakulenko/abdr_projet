package client;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import common.Conf;
import common.MyS3Timer;


public class Client1 extends Thread {
	private int numCommande;
	
	String client ;
	String magasin ;
	String magProd ;
			
	public Client1(int numCommande, String client, String magasin, String magProd){
		this.numCommande = numCommande;
		this.client = client;
		this.magasin = magasin;
		this.magProd = magProd;
	}
	public void run (){
		try {
			Transaction t = new Transaction();

			String produit[] = new String[10];

			SimpleDateFormat dformat =  new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
			System.out.println(dformat.format(new Date()).toString());
			int numClient = numCommande;
			MyS3Timer msdbt = new MyS3Timer(Conf.MyS3Time);
			while(!msdbt.isTimeIsOut()){
				for(int i=0; i<10; i++){
					Random r0 = new Random();
					int nb= r0.nextInt(100);
					if(i==9){
						t.commander("orange"+nb, magasin, magProd, client, numCommande, i, 1);
						produit[i] = "orange"+nb+" : dans "+magProd;
					}
					else{
						t.commander("orange"+nb, magasin, magasin, client, numCommande, i, 1);
						produit[i] = "orange"+nb+" : dans "+magasin;
					}
				}

				System.out.println("Le client " +numClient+ " a commandé les produit suivant :");
				for(int i=0; i < 10; i++)
					System.out.println(produit[i]);
				System.out.println("-----------------------------------------------------------------");
//				if(numCommande == 0){
//					
//				}
//				else{
//					for(int i=0; i<10; i++){
//						Random r0 = new Random();
//						int nb= r0.nextInt(100);
//						if(i==9){
//							t.commander("orange"+nb, magProd, magasin, client, numCommande, i, 1);
//							produit[i] = "orange"+nb+" : dans "+magasin;
//						}
//						else{
//							t.commander("orange"+nb, magProd, magProd, client, numCommande, i, 1);
//							produit[i] = "orange"+nb+" : dans "+magProd;
//						}
//					}
////					System.out.println("Le client " +numClient+ " a commandé les produit suivant :");
////					for(int i=0; i < 10; i++)
////						System.out.println(produit[i]);
////					System.out.println("-----------------------------------------------------------------");
//				}
			}
			System.out.println("Le client " +numClient+ " a commandé les produit suivant :");
			for(int i=0; i < 10; i++)
				System.out.println(produit[i]);
			System.out.println("-----------------------------------------------------------------");
			System.out.println("----------------------------------------Commande 1-----------------------------------------");
			t.afficherMagasin(magasin, numCommande);
			System.out.println("----------------------------------------commande 2-----------------------------------------");
			t.afficherMagasin(magProd, numCommande);

		} catch (Exception e) {

		}
	}
}
