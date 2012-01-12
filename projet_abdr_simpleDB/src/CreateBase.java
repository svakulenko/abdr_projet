

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;
import com.amazonaws.services.simpledb.model.BatchPutAttributesRequest;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;
import com.amazonaws.services.simpledb.model.ReplaceableItem;

public class CreateBase {
	public static void main(String[] args) throws Exception {
		/*
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

		try {

			String myDomain = "D11";
			String myDomain2 = "D12";
			// Create a domain
			//            System.out.println("Creating domain called " + myDomain + ".\n");
			//sdb.createDomain(new CreateDomainRequest(myDomain));

			//List domains
			/* System.out.println("Listing all domains in your account:\n");
	        for (String domainName : sdb.listDomains().getDomainNames()) {
	        	System.out.println("  " + domainName);
	        }*/
			//            System.out.println();

			// Put data into a domain
			/*System.out.println("Deleting data into Magasin0");
			System.out.println("Deleting data into Magasin1");
			for(int i=0; i<1000; i++){
				sdb.deleteAttributes(new DeleteAttributesRequest(myDomain, "Prod_"+i+"_Mag_0"));
				sdb.deleteAttributes(new DeleteAttributesRequest(myDomain2, "Prod_"+i+"_Mag_1"));
			}
			for(int i=0; i<10; i++){
				sdb.deleteAttributes(new DeleteAttributesRequest(myDomain, "Ligne_commande_"+i));
				sdb.deleteAttributes(new DeleteAttributesRequest(myDomain2, "Ligne_commande_"+i));
				sdb.deleteAttributes(new DeleteAttributesRequest(myDomain, "Commande_"+i));
				sdb.deleteAttributes(new DeleteAttributesRequest(myDomain2, "Commande_"+i));
			}*/
			System.out.println("Putting data into Magasin0 = " + myDomain + " domain.\n");
			for(int i=0; i<40; i++){
				sdb.batchPutAttributes(new BatchPutAttributesRequest(myDomain, createSampleData(i*25)));	
				sdb.batchPutAttributes(new BatchPutAttributesRequest(myDomain2, createSampleData2(i*25)));
			}
			
			System.out.println("Putting data into Magasin1 = " + myDomain2 + " domain.\n");
			
			// Select data from a domain
			// Notice the use of backticks around the domain name in our select expression.
			//String selectExpression = "select * from `" + myDomain + "`";

			//	SelectRequest selectRequest = new SelectRequest(selectExpression);
			/*System.out.println("Produits du magasin 0");
			System.out.println("======================================================================");
			for (Item item : sdb.select(selectRequest).getItems()) {
				//System.out.println("  Item");
				System.out.println("Id_Produit: \t\t" + item.getName());
				for (Attribute attribute : item.getAttributes()) {
					//System.out.println("      Attribute");
					System.out.println(attribute.getName() + "\t\t" + attribute.getValue());
					//System.out.println("        Value: " + attribute.getValue());
				}
				System.out.println("======================================================================");
			}
			System.out.println();

			// Select data from a domain
			// Notice the use of backticks around the domain name in our select expression.
			System.out.println("Produits du magasin 1");
			System.out.println("======================================================================");
			for (Item item : sdb.select(selectRequest2).getItems()) {
				//System.out.println("  Item");
				System.out.println("Id_Produit: \t\t" + item.getName());
				for (Attribute attribute : item.getAttributes()) {
					//System.out.println("      Attribute");
					System.out.println(attribute.getName() + "\t\t" + attribute.getValue());
					//System.out.println("        Value: " + attribute.getValue());
				}
				System.out.println("======================================================================");
			}*/
			/*// Delete values from an attribute
			System.out.println("Deleting Blue attributes in Item_O3.\n");
			Attribute deleteValueAttribute = new Attribute("Color", "Blue");
			sdb.deleteAttributes(new DeleteAttributesRequest(myDomain, "Item_03")
			.withAttributes(deleteValueAttribute));

			// Delete an attribute and all of its values
			System.out.println("Deleting attribute Year in Item_O3.\n");
			sdb.deleteAttributes(new DeleteAttributesRequest(myDomain, "Item_03")
			.withAttributes(new Attribute().withName("Year")));

			// Replace an attribute
			System.out.println("Replacing Size of Item_03 with Medium.\n");
			List<ReplaceableAttribute> replaceableAttributes = new ArrayList<ReplaceableAttribute>();
			replaceableAttributes.add(new ReplaceableAttribute("Size", "Medium", true));
			sdb.putAttributes(new PutAttributesRequest(myDomain, "Item_03", replaceableAttributes));

			// Delete an item and all of its attributes
			System.out.println("Deleting Item_03.\n");
			sdb.deleteAttributes(new DeleteAttributesRequest(myDomain, "Item_01"));
			sdb.deleteAttributes(new DeleteAttributesRequest(myDomain, "Item_02"));
			sdb.deleteAttributes(new DeleteAttributesRequest(myDomain, "Item_03"));
			sdb.deleteAttributes(new DeleteAttributesRequest(myDomain, "Item_04"));
			sdb.deleteAttributes(new DeleteAttributesRequest(myDomain, "Item_05"));*/


			// Delete a domain
			// System.out.println("Deleting " + myDomain + " domain.\n");
			// sdb.deleteDomain(new DeleteDomainRequest(myDomain));
			//Transaction t = new Transaction();
			//System.out.println("----------------------------------------Magasin 1-----------------------------------------");
		//	t.afficherMagasin(myDomain);
		//	System.out.println("----------------------------------------Magasin 2-----------------------------------------");
		//	t.afficherMagasin(myDomain2);
		} catch (AmazonServiceException ase) {
			System.out.println("Caught an AmazonServiceException, which means your request made it "
					+ "to Amazon SimpleDB, but was rejected with an error response for some reason.");
			System.out.println("Error Message:    " + ase.getMessage());
			System.out.println("HTTP Status Code: " + ase.getStatusCode());
			System.out.println("AWS Error Code:   " + ase.getErrorCode());
			System.out.println("Error Type:       " + ase.getErrorType());
			System.out.println("Request ID:       " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			System.out.println("Caught an AmazonClientException, which means the client encountered "
					+ "a serious internal problem while trying to communicate with SimpleDB, "
					+ "such as not being able to access the network.");
			System.out.println("Error Message: " + ace.getMessage());
		}
	}

	/**
	 * Creates an array of SimpleDB ReplaceableItems populated with sample data.
	 *
	 * @return An array of sample item data.
	 */
	/*# Commande(magasin, numCommande, client, dateCommande, prixTotal)
	# Produit(magasinProd, numProd, nom, qteEnStock, prix)
	# LigneCommande( magasin, numCommande, ligne, magasinProd, numProd, quantité)*/
	private static List<ReplaceableItem> createSampleData(Integer index) {
		List<ReplaceableItem> sampleData = new ArrayList<ReplaceableItem>();
		int step = 0;
		int indexDebut = index;
		while(true){
			
			sampleData.add(new ReplaceableItem("Prod_"+indexDebut+"_Mag_0").withAttributes(
					new ReplaceableAttribute("MagasinProd", "0", true),
					new ReplaceableAttribute("numProd", ""+indexDebut+"", true),
					new ReplaceableAttribute("Nom", "orange"+indexDebut, true),
					new ReplaceableAttribute("QteEnStock", "1000", true),
					new ReplaceableAttribute("prix", "1", true)
					));
			
			if (step == 24)
				break;
			else
				step++;
			
			indexDebut++;
		}
		return sampleData;
	}

	private static List<ReplaceableItem> createSampleData2(int index) {
		List<ReplaceableItem> sampleData = new ArrayList<ReplaceableItem>();
		int step = 0;
		int indexDebut = index;
		while(true){
			
			sampleData.add(new ReplaceableItem("Prod_"+indexDebut+"_Mag_1").withAttributes(
					new ReplaceableAttribute("MagasinProd", "1", true),
					new ReplaceableAttribute("numProd", ""+indexDebut+"", true),
					new ReplaceableAttribute("Nom", "orange"+indexDebut, true),
					new ReplaceableAttribute("QteEnStock", "1000", true),
					new ReplaceableAttribute("prix", "1", true)
					));
			
			if (step == 24)
				break;
			else
				step++;
			
			indexDebut++;
		}
		return sampleData;
	}
}
