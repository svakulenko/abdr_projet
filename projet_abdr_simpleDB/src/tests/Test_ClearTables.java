package tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;
import com.amazonaws.services.simpledb.model.DeleteAttributesRequest;
import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.SelectRequest;

import common.Conf;
import common.Utils;

public class Test_ClearTables {

	public static void main(String[] args) {
		try {
			AmazonSimpleDB sdb = new AmazonSimpleDBClient(
					new PropertiesCredentials(new File(
							Conf.getCredentialFilePath())));

			for (int i = 0; i < Conf.domainsName.length; i++) {

				while (true) {
					List<Item> l = sdb.select(
							new SelectRequest("select * from "
									+ Conf.domainsName[i])).getItems();
					if (l.size() == 0) {
						System.out.println("Domain " + Conf.domainsName[i] +  "is clean");
						break;
					}

					Utils.showItemList(l);
					for (Item item : l) {
						sdb.deleteAttributes(new DeleteAttributesRequest(
								Conf.domainsName[i], item.getName()));
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
