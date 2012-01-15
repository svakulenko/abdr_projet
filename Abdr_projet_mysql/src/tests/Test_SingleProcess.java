package tests;

import java.util.*;

//import tests.Test_SingleProcess.logStruct;

import db.DbHandler;
import common.*;



public class Test_SingleProcess extends RapportClass{
	
	public Integer threadsNum;
	public void run(){
		Integer client = IdGenerator.getUniqueId();
		System.out.println("Test_SingleProcess::run thread runs! client=" + client);
		runTransaction(client );
		//Thread.currentThread().stop();
		
		
		System.out.println("not full log");
		//Test_SingleProcess.showLog(lFirstProc);
		
		
	}
	
	public void runTransaction(Integer client){

//		client = IdGenerator.getUniqueId();
		//System.out.println("clientID=" + client);
		
		DbHandler db =new DbHandler(); 
		db.initDB();
		
		MyS3Timer timer = new MyS3Timer(Conf.MyS3Time);
		while(!timer.isTimeIsOut()){
			db.transactionCommandFull(0, client);
		}
		
		timer.showFullLog();
		saveLogs(timer.getStatistiqueList());
		
	}
	
	
	public static void main(String[] args){
		new Test_SingleProcess().runTransaction(999);
	}
}
