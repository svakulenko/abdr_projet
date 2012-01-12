package db;

import java.sql.*;
import java.util.Random;

import common.Conf;


public class DbHandler {
	
	Integer maxx = 1000;
	
	public loggerClass log = new loggerClass();
	
//LOCAL CONNECTION
//    String dbUrl[] = {
//  		  "jdbc:mysql://localhost:9996/test"
//  		, "jdbc:mysql://localhost:9998/test"} ;

//JUSSIEU MACHINES
//    String dbUrl[] = {
//    		  "jdbc:mysql://ari-31-201-12:3306/test"
//    		, "jdbc:mysql://ari-31-201-07:3306/test2"} ;
    
    
    

    
    Connection con [] = {null, null};
    String sql_query;
    
    public DbHandler(){

    }
	public void initDB() {
    	  System.out.println("invoke mysql driver .. ");
    	  try {
    		  Class.forName("com.mysql.jdbc.Driver").newInstance();
    		  con[0] = DriverManager.getConnection(Conf.url1 , Conf.login, Conf.pwd);
    		  System.out.println("connect to " + Conf.url1  + " !!!");
    		  con[1] = DriverManager.getConnection(Conf.url2 , Conf.login, Conf.pwd);
    		  System.out.println("connect to " + Conf.url2  + " !!!");
    	  } catch (Exception e ){
    		  System.out.println( "initDB exception:" + e.getMessage());
    	  }
	      
	}
	
    
	public boolean connectionExist(Integer Id)	{
		if (con[Id] == null) System.out.println("error, no connection!");
		return con[Id] != null;
	}

	public int updateStatement(String sql_query, Integer Id){
		System.out.println(sql_query);
		log.saveBuffer(sql_query + '\n');
		int rvalue = -1;
		Statement st = null;
		try {
		st =  con[Id].createStatement();
		rvalue = st.executeUpdate(sql_query);
		
		} catch (Exception e){
			System.out.println( "updateStatement exception:" + e.getMessage());
		}
		return rvalue;
	}

	

	

	
	public void deleteDb(Integer Id){
		try {
		con[Id].createStatement().executeUpdate("DROP TABLE LIGNECOMMANDE");
		} catch (Exception e) {}
		
		try {
		con[Id].createStatement().executeUpdate("DROP TABLE PRODUIT");
		} catch (Exception e) {}
		
		try {
		con[Id].createStatement().executeUpdate("DROP TABLE COMMANDE");
		} catch (Exception e) {}
		System.out.println("here");
		
 }
	
	public void createDb(Integer id) {
		if (connectionExist(id)){
			/*
			 * request scheme:
			 * 1) get produit
			 * 2) insert into ligneCommande 1...N
			 * 3) insert into Commande
			 * 4) update produit
			*/
				//TABLE COMMANDE
				sql_query = "CREATE TABLE COMMANDE("
						+ "  numMagasin   BIGINT NOT NULL"
						+ ", numCommande  BIGINT NOT NULL"
						+ ", client       BIGINT NOT NULL"		
						+ ", dateCommande DATE   NOT NULL"						
						+ ", prixTotal    BIGINT NOT NULL"
						+ ", PRIMARY KEY (numMagasin, numCommande)"
					    + ");"
					;
				updateStatement(sql_query, id);
				
				//TABLE PRODUIT
				sql_query = "CREATE TABLE PRODUIT ("
						+ "  numProduit BIGINT NOT NULL"
						+ ", numMagasin BIGINT NOT NULL"
						+ ", nom VARCHAR(255)  NOT NULL" //
						+ ", qteEnStock BIGINT NOT NULL"
						+ ", PRIMARY KEY (numProduit,numMagasin)"
					    + ");"
					    ;
				updateStatement(sql_query, id);
				
				//LIGNE COMMANDE
				sql_query = "CREATE TABLE LIGNECOMMANDE ("
						+ "  numMagasin  BIGINT  NOT NULL"
						+ ", numCommande BIGINT  NOT NULL"
						+ ", numProduit  BIGINT  NOT NULL" 
						+ ", quantite    BIGINT  NOT NULL"
						+ ", prix        BIGINT  NOT NULL"
						+ ", PRIMARY KEY (numMagasin, numCommande, numProduit)"
					    //+ ", FOREIGN KEY (numCommande) REFERENCES COMMANDE(numCommande)"
					    //+ ", FOREIGN KEY (numProduit)  REFERENCES PRODUIT(numProduit)"
					    + ");"

					;
				updateStatement(sql_query, id);
				

		}
			
	}
	

		/*
		public void action1() throws InstantiationException, IllegalAccessException, ClassNotFoundException{

			  initDB();
			  try {
			  
			  createDb();
			  deleteDb();
			  } catch (Exception e){
				  System.out.println(e.getMessage());
				  //e.printStackTrace();
			  }
		}
		*/

	public Integer getSumOfLingeDeCommands (Integer magasinToConnect, Integer numCommande, Integer numMagasin) throws Exception{
		Integer rvalue = null;
		if (connectionExist(magasinToConnect)){
			String sql_query = "select SUM(prix) FROM LIGNECOMMANDE";
				   sql_query += " where numMagasin=" + numMagasin.toString();
				   sql_query += " and numCommande=" + numCommande.toString();
			
			ResultSet rs = GetSqlResult(sql_query, magasinToConnect);
			if (rs.next()){
				
				System.out.println("GetSqlResult we have next!");
					try {
						rvalue =  rs.getInt("SUM(prix)");
					} catch (SQLException e){ //THIS BLOCK IS NOT UNECESSERY because MAX return always 0(if no rows in table)
						System.out.println("::getSumOfLingeDeCommands Warning SQLException:" + e.getMessage());
						rvalue = -1;
					}
			}
		}
		System.out.println("end getSumOfLingeDeCommands=" + rvalue);
		
		return rvalue;
	}
		public Integer getMaxNumCmdInCommande (Integer magasin) {
			Integer rvalue = null;
			if (connectionExist(magasin)){
				String sql_query = "select MAX(NumCommande) FROM COMMANDE where numMagasin=" + magasin.toString();
				
				ResultSet rs = GetSqlResult(sql_query, magasin);
				try {
					if (rs.next()){
						
						System.out.println("GetSqlResult we have next!");
							try {
								rvalue =  rs.getInt("MAX(NumCommande)");
							} catch (SQLException e){ //THIS BLOCK IS NOT UNECESSERY because MAX return always 0(if no rows in table)
								System.out.println("::getMaxNumCmdInCommande Warning SQLException:" + e.getMessage());
								rvalue = -1;
							}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			System.out.println("end getMaxNumCmdInCommande=" + rvalue);
			
			return rvalue;
		}
		
		public void deleteAllLinesFromLigneCommandeCommande (Integer Id) {
			if (connectionExist(Id)){
				sql_query = "DELETE from lignecommande";
				updateStatement(sql_query, Id);
				sql_query = "DELETE from commande";
				updateStatement(sql_query, Id);
			}
		}
		
		//UPDATE products SET quantity = quantity - 1 WHERE id = '#*$!'; 
		
		public void initProduitTable(){
			

			

			Integer numMagasin = null;
			Integer qte = 100000;
			
			for (int i = 0; i < maxx; i++){
				if (i%100 == 0)
					System.out.printf("produit %5d ...\n", i);
				String orangeName = "orangeN" + i;
				numMagasin = (i > (maxx*0.9)) ? 1 :0; // 10% save to second magasin
				//System.out.printf("produitNum=%d, mag=%d, name=%s, qte=%d\n"
				//		, i, numMagasin, orangeName, qte );
				
				

				
				if (connectionExist(numMagasin)){
					try {
						String q = "INSERT INTO PRODUIT "
								+ "VALUES (" + "" + i
								+ ","        + numMagasin.toString()
								+ ",'"        + orangeName
								+ "',"        + qte.toString()
								+ ")";
						//System.out.println("q=" + q);
						con[numMagasin].createStatement().executeUpdate(q);
					} catch (SQLException e) {
						System.out.println("cant do update, exception:" + e.getMessage());
					}
				}	
			}
			
			
		}
		public void transactionCommandFull ( Integer numMagasin	, Integer client) {
			Integer secondMagasin = 1;
			
			if (connectionExist(numMagasin)){
				try {
				con[numMagasin].setAutoCommit(false);
				
				
				
				
				Integer Qte = 5; //constant
				
				//get max commande
				Integer numCommande = getMaxNumCmdInCommande(numMagasin);
				numCommande++;
				System.out.println("dao::transactionCommandFull numCommande=" + numCommande);
				

				//int end = 1000;
				Random r = new Random();
				
				
				
				 
				//Integer iterator = new Integer(0);
//				while (iterator < end){
					Integer rInt = r.nextInt(maxx);
					System.out.println("random=" + rInt);

					
					Integer prix = 5; //constant
					
					if ( rInt > (maxx*0.9)) { //LAST PRODUIT CASE
						System.out.println("second magasin transaction");


						try {
						con[secondMagasin].setAutoCommit(false);
						

						//add ligne commande
						con[secondMagasin].createStatement().executeUpdate(
								"INSERT INTO LIGNECOMMANDE "
								+ "VALUES (" + numMagasin.toString()
								+ ","        + numCommande.toString()
								+ ","        + rInt.toString()
								+ ","        + Qte.toString()
								+ ","        + prix.toString()
								+ ")"
								);
						
						//change produit
						con[secondMagasin].createStatement().executeUpdate(
								"UPDATE PRODUIT SET qteEnStock=qteEnStock - " + Qte
								+ " WHERE numProduit=" + rInt
								);
						
						con[secondMagasin].commit();
						
						} catch (Exception e){
							try { con[secondMagasin].rollback();
							} catch (Exception c2e) {
								System.out.println("dao::transactionCommandFull 9 produit exception: " + c2e.getMessage());
							}
						}

						
					} else {
						addLigneCommande(numMagasin, numCommande, rInt, Qte, prix);
						updateProduit(numMagasin, numCommande, rInt, Qte);
					}
						

					//iterator++;
//				}
	
				Integer prixTotalPourCommandeMag2  = getSumOfLingeDeCommands(secondMagasin, numCommande, numMagasin);
				Integer prixTotalPourCommande  = getSumOfLingeDeCommands(numMagasin, numCommande, numMagasin);
				addCommande(numMagasin
				, numCommande
				, null 
				, prixTotalPourCommande + prixTotalPourCommandeMag2
				, client
				);
				
				
				
				con[numMagasin].commit();
				
				
				//exceptions ....
				} catch (Exception e){
					System.out.println("transactionCommandFull exception:" + e.getMessage());
					try {
						con[numMagasin].rollback(); 
						} catch (SQLException eSql) {
						System.out.println("transactionCommandFull sql exception:" + eSql.getMessage());
					}
				} finally {
					System.out.println("transactionCommandFull finally");
					
					try {
						con[numMagasin].setAutoCommit(true);
					} catch (SQLException e) {
						System.out.println("setAutoCommit true exception:" + e.getMessage());
					}
				}
				
			}
		}
		
		public void updateProduit (
				  Integer numMagasin
				, Integer numCommande
				, Integer numProduit
				, Integer Qte
				) {
			if (connectionExist(numMagasin)){
				sql_query = "UPDATE PRODUIT SET qteEnStock=qteEnStock - " + Qte
						+ " WHERE numProduit=" + numProduit
						;
				updateStatement(sql_query, numMagasin);
			}
		}
		
		public void addCommande (
				  Integer numMagasin
				, Integer numCommande
				, Date dateCommande
				, Integer prixTotal
				, Integer ClientId
				) {
			if (connectionExist(numMagasin)){
				sql_query = "INSERT INTO COMMANDE "
						+ "VALUES (" + numMagasin.toString()
						+ ","        + numCommande.toString()
						+ ", " + ClientId.toString()
						+ ", CURDATE()"//        + dateCommande.toString()
						+ ","  + prixTotal.toString()
						+ ")";
				
				updateStatement(sql_query, numMagasin);
			}
		}
		
		public void addLigneCommande (
				  Integer numMagasin
				, Integer numCommande
				, Integer numProduit
				, Integer quantite
				, Integer prix
				) throws SQLException{
			if (connectionExist(numMagasin)){
				sql_query = "INSERT INTO LIGNECOMMANDE "
						+ "VALUES (" + numMagasin.toString()
						+ ","        + numCommande.toString()
						+ ","        + numProduit.toString()
						+ ","        + quantite.toString()
						+ ","        + prix.toString()
						+ ")";
				
				updateStatement(sql_query, numMagasin);
			}
		}
		
		public void addRecordToProduit(
				  Integer numPruduit
				, Integer numMagasin
				, String nomProduit
				, Integer qteEnStock
				) {
			if (connectionExist(numMagasin)){
				sql_query = "INSERT INTO PRODUIT "
						+ "VALUES (" + numPruduit.toString()
						+ ","        + numMagasin.toString()
						+ ",'"       + nomProduit
						+ "',"       + qteEnStock
						+ ")";
				
				updateStatement(sql_query, numMagasin);
				

			}
		}
		
		public void updateRecordToProduit(
				  Integer numProduit
				, Integer qteEnStock
				, Integer numMagasin
				) {
			if (connectionExist(numMagasin)){
//				UPDATE Table1 SET Author='Joe' WHERE ID=3 
//              | numProduit | numMagasin | nom          | qteEnStock |
				
				sql_query = "UPDATE PRODUIT SET "
						+ "qteEnStock=" + qteEnStock
						+ " WHERE numProduit=" + numProduit
						;
				
				updateStatement(sql_query, numMagasin);
				

			}
		}
		public ResultSet GetSqlResult(String sql_query, Integer Id)  {
			System.out.println("::GetSqlResult query to execute: " + sql_query );
			ResultSet rs = null;
			try{
				Statement st =  con[Id].createStatement();
				rs =  st.executeQuery(sql_query);
			} catch (Exception e) {
				System.out.println("GetSqlResult exception: " + e.getMessage());
			}
			return rs;
		}
		public ResultSet getProduits(Integer Id) throws SQLException{
			ResultSet rvalue = null;
			if (connectionExist(Id)){
				
				String sql_query = "SELECT * FROM PRODUIT";
				rvalue = GetSqlResult(sql_query, Id);
		}
			return rvalue;
		}
		
		


}
