package tests;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

//import tests.Test_SingleProcess.logStruct;

import common.Conf;
import common.logStruct;
import db.loggerClass;

public class Test_MultyProcess {

	
	

	public void runMultyTache(Integer threadsNum){
		System.out.println("runMulty threadsNum=" + threadsNum);
		Test_SingleProcess[] threads = new Test_SingleProcess[threadsNum];
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Test_SingleProcess();
//			threads[i].threadsNum = i+1;
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
		List<logStruct>  coll = null;
		Test_SingleProcess.filterList();
		coll =Test_SingleProcess.l;
		
		System.out.println("full log");
		Test_SingleProcess.showLog(coll);
		Test_SingleProcess.saveLogToFile(threadsNum, "", coll);
		
		coll =Test_SingleProcess.lFirstProc;
		Test_SingleProcess.saveLogToFile(threadsNum, "_", coll);
		Test_SingleProcess.resetToInit();

		
		
		




		
	}


	public static void main(String[] args) {

		
		new Test_MultyProcess().runMultyTache(Conf.threadsNum);

	}

}
