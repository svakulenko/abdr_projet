package tests;

import db.DbHandler;
import common.*;

public class Test_SingleProcess extends Thread {
	
	public void run(){
//			Thread.sleep(5000);
			runTransaction();
	}

	public void runTransaction(){

		Integer client = IdGenerator.getUniqueId();
		//System.out.println("clientID=" + client);
		
		DbHandler db =new DbHandler(); 
		db.initDB();
		db.log.openLogStream("log" + client + ".txt");
		
		
		
		MyS3Timer timer = new MyS3Timer(3);
		while(!timer.isTimeIsOut()){
			db.transactionCommandFull(0, client);
		}
		db.log.saveBuffer(timer.getLogBeginTime() + '\n');
		db.log.saveBuffer(timer.getLogEndTime() + '\n');
		db.log.closeLogStream();
		
	}
	
	
	public static void main(String[] args){

		new Test_SingleProcess().runTransaction();
	}
}
