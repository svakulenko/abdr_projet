package tests;

import java.util.*;

import tests.Test_SingleProcess.logStruct;

import db.DbHandler;
import common.*;

public class Test_SingleProcess extends Thread {
	
	public class logStruct{
		public Float time = new Float(0);
		public Integer trans = new Integer(0);
		public Integer called = new Integer(0);
		
	}
	
	
	// static Map<Long, Integer> fullLogAll = new HashMap<Long, Integer>();
	static Integer numThreadsCalledInThisFunc = 0;
	public static List<logStruct> l = new ArrayList<Test_SingleProcess.logStruct>();
	//public static Map<Float, Integer> fullLog = new TreeMap<Float, Integer>();
	static boolean isFirstTimeCalled = true;
	
	public static void showFullLog(){
		for (logStruct ls : l) {
			System.out.printf("time=%5.1f , transactions = %3d, called=%d\n"
					, ls.time
					, ls.trans
					, ls.called);
		}
	}
	synchronized public static void filterList(){
		
		int max = 0;
		for (logStruct ls : l) {
			if(ls.called > max)
				max = ls.called;
		}
		System.out.println("filterList max=" + max);
		for(Iterator<logStruct> i = l.iterator(); i.hasNext(); ) {
			logStruct ls =  i.next();
			if (ls.called < max){
				i.remove();
			}
		}
	}
	synchronized public void saveLogs(Map<Float, Integer> m){
		
		numThreadsCalledInThisFunc++;
		
		 System.out.println("saveLogs start m size=" + m.size());
		 Iterator<Float> it = m.keySet().iterator();
		 
		 System.out.println("DBG#1");
		 while (it.hasNext()) {
			Float k = (Float) it.next();
			Integer new_v = m.get(k);
			Integer elIndex = -1;
			for (int i = 0; i < l.size(); i++) {
				if(l.get(i).time.equals(k)){
					elIndex= i;
					break;
				}
					
			}
			System.out.println("new_v=" + new_v + ", k=" + k);
			logStruct ls = null;
			if (isFirstTimeCalled){
				ls = new logStruct();
				ls.called += 1;
				ls.time = k;
				ls.trans = new_v;
				
			} else if (elIndex != -1){
				ls = l.get(elIndex);
				ls.called += 1;
				ls.trans = ls.trans + new_v;
			}
			System.out.println("DBG#3");
			if(isFirstTimeCalled)
				l.add(ls);
				
		}
		 System.out.println("DBG#4");
		 isFirstTimeCalled = false;
		
	}
	
	public void run(){
		System.out.println("Test_SingleProcess::run thread runs!");
		runTransaction();
	}
	
	public void runTransaction(){

		Integer client = IdGenerator.getUniqueId();
		//System.out.println("clientID=" + client);
		
		DbHandler db =new DbHandler(); 
		db.initDB();
		//db.log.openLogStream("log" + client + ".txt");
		
		
		
		MyS3Timer timer = new MyS3Timer(Conf.MyS3Time);
		while(!timer.isTimeIsOut()){
			db.transactionCommandFull(0, client);
		}
		timer.showFullLog();
		saveLogs(timer.getStatistiqueList());
		
		
		
		//db.log.saveBuffer(timer.getLogBeginTime() + '\n');
		//db.log.saveBuffer(timer.getLogEndTime() + '\n');
		//db.log.closeLogStream();
		
	}
	
	
	public static void main(String[] args){
		new Test_SingleProcess().runTransaction();
	}
}
