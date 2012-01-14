package tests;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import tests.Test_SingleProcess.logStruct;

import common.Conf;
import db.loggerClass;

public class Test_MultyProcess {

	
	

	public void runMultyTache(Integer threadsNum){
		System.out.println("runMulty threadsNum=" + threadsNum);
		Test_SingleProcess[] threads = new Test_SingleProcess[threadsNum];
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Test_SingleProcess();
			threads[i].start();
		}
		System.out.println("DEBUG#!");
		for (int i = 0; i < threads.length; i++) {
			try {
				threads[i].join();
				System.out.println("Test_MultyProcess::Join after");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		//FINAL log show and save it to 'rapports' dir
		
		
		loggerClass log = new loggerClass();
		log.openLogStream("rapport" /* .txt */);
		
		Test_SingleProcess.filterList();
		Test_SingleProcess.showFullLog();
		List<logStruct>  coll =Test_SingleProcess.l; 
		Iterator<logStruct> it = coll.iterator() ;
		while(it.hasNext()){
			logStruct ls = it.next();
			log.saveBuffer(String.format("%1.1f %d", ls.time,ls.trans) + '\n');
		}
		
		log.closeLogStream();




		
	}

	public static void main(String[] args) {

		
		new Test_MultyProcess().runMultyTache(Conf.threadsNum);

	}

}
