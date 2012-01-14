package tests;
import common.IdGenerator;

import db.*;
public class Test_OneTransaction {

	public static void main(String[] args){
		Integer client = IdGenerator.getUniqueId();
		DbHandler db = new DbHandler();
		db.initDB();
		db.transactionCommandFull(0, client);
	}
}
