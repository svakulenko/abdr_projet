package tests;


import db.*;
public class Test_InitDB {

	DbHandler dbhand = new DbHandler();
	
	public void reInitDb(){
		dbhand.initDB();
		destroyDb();
		initDb();
		dbhand.initProduitTable();
		
		
		
		
	}
	public void initDb(){
		dbhand.createDb(0);
		dbhand.createDb(1);
	}
	public void destroyDb(){
		dbhand.deleteDb(0);
		dbhand.deleteDb(1);
	}
	
	public static void main(String[] args){
		System.out.println("Main");
		
		new Test_InitDB().reInitDb();
	}
	
}
