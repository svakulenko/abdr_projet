

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;
import com.amazonaws.services.simpledb.model.Attribute;
import com.amazonaws.services.simpledb.model.BatchPutAttributesRequest;
import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.PutAttributesRequest;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;
import com.amazonaws.services.simpledb.model.ReplaceableItem;
import com.amazonaws.services.simpledb.model.SelectRequest;
import com.amazonaws.services.simpledb.model.UpdateCondition;


public class Transaction {
	AmazonSimpleDB sdb;
	public Transaction() throws Exception{
		/**
		 * Important: Be sure to fill in your AWS access credentials in the
		 *            AwsCredentials.properties file before you try to run this
		 *            sample.
		 * http://aws.amazon.com/security-credentials
		 */

		ClientConfiguration config = new ClientConfiguration();
		config.setProxyHost("proxy.ufr-info-p6.jussieu.fr");
		config.setProxyPort(3128);
		//System.out.println(System.getProperty("user.dir"));
		sdb = new AmazonSimpleDBClient(new PropertiesCredentials(
				CreateBase.class.getResourceAsStream("AwsCredentials.properties")), config);


		System.out.println("===========================================");
		System.out.println("Getting Started with Amazon SimpleDB");
		System.out.println("===========================================\n");
		
	}
	public void commander(String produit, String magasin, String magProd, String client, int numCommande, int numLigne, int quantite)throws Exception{
		
		String selectExpression = "select * from `"+ magProd +"` where Nom = '"+ produit +"'";
		SelectRequest sq = new SelectRequest(selectExpression);
		Item item;
		if((item = sdb.select(sq).getItems().get(0))==null)
			return;
		Attribute at = null;
		for(Attribute ate : item.getAttributes()){
			if(ate.getName().equals("QteEnStock")){
				at = ate;
				break;
			}
		}
		if(at == null){
			return;
		}
		int newstock = Integer.parseInt(at.getValue()) - quantite;
		//System.out.println("Nouveau stock = "+newstock);
		//PutAttributesRequest par = new PutAttributesRequest();
		List<ReplaceableAttribute> l = new ArrayList<ReplaceableAttribute>();
		l.add(new ReplaceableAttribute(at.getName(), ""+newstock+"", true));
		UpdateCondition uc = new UpdateCondition(at.getName(), at.getValue(), true);
		//uc.withName("QteEnStock").setValue(""+newstock+"");
		PutAttributesRequest par = new PutAttributesRequest(magProd, item.getName(), l, uc.withName("QteEnStock"));
		
		//
		//sdb.
		sdb.putAttributes(par);
		/*for (Item it : sdb.select(sq).getItems()) {
			System.out.println("  Item");
			System.out.println("    Name: " + it.getName());
			for (Attribute attribute : it.getAttributes()) {
				System.out.println("      Attribute");
				System.out.println("        Name:  " + attribute.getName());
				System.out.println("        Value: " + attribute.getValue());
			}
		}*/
		/*# Commande(magasin, numCommande, client, dateCommande, prixTotal)
		# Produit(magasinProd, numProd, nom, qteEnStock, prix)
		# LigneCommande( magasin, numCommande, ligne, magasinProd, numProd, quantité)*/
		ReplaceableItem Lignecommande = new ReplaceableItem("Ligne_commande_"+numLigne).withAttributes(
				new ReplaceableAttribute("magasinCommande", magasin, true),
				new ReplaceableAttribute("numCom", ""+numCommande+"", true),
				new ReplaceableAttribute("ligne", ""+numLigne+"", true),
				new ReplaceableAttribute("magasinProd", magProd, true),
				new ReplaceableAttribute("numProd", item.getName(), true),
				new ReplaceableAttribute("quantite", ""+quantite+"", true)
				);
		List<ReplaceableItem> sampleData = new ArrayList<ReplaceableItem>();
		sampleData.add(Lignecommande);
		
		sdb.batchPutAttributes(new BatchPutAttributesRequest(magasin, sampleData));
		String selectExpression2 = "select * from `"+ magasin +"` where numCommande = '"+ numCommande +"'";
		SelectRequest sq2 = new SelectRequest(selectExpression2);

		for(Attribute ate : item.getAttributes()){
			if(ate.getName().equals("prix")){
				at = ate;
				break;
			}
		}
		SimpleDateFormat dformat =  new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
		int prixLigne = Integer.parseInt(at.getValue()) * quantite;
		Item item2;
		if(sdb.select(sq2).getItems().size()==0){
			ReplaceableItem commande = new ReplaceableItem("Commande_"+numCommande).withAttributes(
					new ReplaceableAttribute("magasin", magasin, true),
					new ReplaceableAttribute("numCommande", ""+numCommande+"", true),
					new ReplaceableAttribute("client", client, true),
					new ReplaceableAttribute("dateComande", ""+dformat.format(new Date()).toString()+"", true),
					new ReplaceableAttribute("prixTotal", ""+prixLigne+"", true)
					);
			List<ReplaceableItem> sampleData2 = new ArrayList<ReplaceableItem>();
			sampleData2.add(commande);
			sdb.batchPutAttributes(new BatchPutAttributesRequest(magasin, sampleData2));
		}
		else{
			item2 = sdb.select(sq2).getItems().get(0);
			for(Attribute ate : item2.getAttributes()){
				if(ate.getName().equals("prixTotal")){
					at = ate;
					break;
				}
			}
			int newPrixTotal = Integer.parseInt(at.getValue()) + prixLigne;
			List<ReplaceableAttribute> l2 = new ArrayList<ReplaceableAttribute>();
			l2.add(new ReplaceableAttribute(at.getName(), ""+newPrixTotal+"", true));
			UpdateCondition uc2 = new UpdateCondition(at.getName(), at.getValue(), true);
			//uc2.withName("prixTotal").setValue(""+newPrixTotal+"");
			PutAttributesRequest par2 = new PutAttributesRequest(magasin, item2.getName(), l2, uc2.withName(at.getName()));
			sdb.putAttributes(par2);
		}
		
		//PutAttributesRequest pare = new PutAttributesRequest(magasin, "", sampleData);
		//UpdateCondition uc = new UpdateCondition();
		//uc.withName(produit).setValue("524225");
	}
	public void afficherMagasin(String magasin, int numCommande) throws Exception{
		/**
		 * Important: Be sure to fill in your AWS access credentials in the
		 *            AwsCredentials.properties file before you try to run this
		 *            sample.
		 * http://aws.amazon.com/security-credentials
		 */

		ClientConfiguration config = new ClientConfiguration();
		config.setProxyHost("proxy.ufr-info-p6.jussieu.fr");
		config.setProxyPort(3128);
		//System.out.println(System.getProperty("user.dir"));
		AmazonSimpleDB sdb = new AmazonSimpleDBClient(new PropertiesCredentials(
				CreateBase.class.getResourceAsStream("AwsCredentials.properties")), config);


		System.out.println("===========================================");
		System.out.println("Getting Started with Amazon SimpleDB");
		System.out.println("===========================================\n");
		
		
		String selectExpression = "select * from `" + magasin + "` where numCommande='"+numCommande+"'";
		SelectRequest selectRequest = new SelectRequest(selectExpression);
		System.out.println("======================================================================");
		for (Item item : sdb.select(selectRequest).getItems()) {
			//System.out.println("  Item");
			System.out.println("########" + item.getName() + "########");
			for (Attribute attribute : item.getAttributes()) {
				//System.out.println("      Attribute");
				System.out.println(attribute.getName() + "\t\t" + attribute.getValue());
				//System.out.println("        Value: " + attribute.getValue());
			}
			System.out.println("======================================================================");
		}
		System.out.println();
	}
}


