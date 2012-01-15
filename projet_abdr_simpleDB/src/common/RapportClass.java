package common;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;



public class RapportClass extends Thread {
	
	static boolean isFirstTimeCalled = true;
	public static List<logStruct> l = new ArrayList<logStruct>();
	public static List<logStruct> lFirstProc = new ArrayList<logStruct>();
	
	public static void resetToInit(){
		isFirstTimeCalled = true;
		l = new ArrayList<logStruct>();
		lFirstProc = new ArrayList<logStruct>();
	}
	public static void showLog(List<logStruct> lst){
		for (logStruct ls : lst) {
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
	public static void saveLogToFile(Integer threadsNum, String sufix, List<logStruct> col){
		loggerClass log = new loggerClass();
		String namef = threadsNum > 1? "_clients" : "_client";
		namef = namef + sufix ;
		String threadsNumStr = threadsNum < 10 ? "0" + threadsNum : "" + threadsNum; 
		log.openLogStream(threadsNumStr + namef /* .txt */);
		
		
		 
		Iterator<logStruct> it = col.iterator() ;
		while(it.hasNext()){
			logStruct ls = it.next();
			log.saveBuffer(String.format("%1.1f %d", ls.time,ls.trans) + '\n');
		}
		
		log.closeLogStream();

	}
	
	synchronized public void saveLogs(Map<Float, Integer> m){
		
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
				System.out.println("added isFirstTimeCalled=" + isFirstTimeCalled);
				
			} else if (elIndex != -1){
				ls = l.get(elIndex);
				ls.called += 1;
				ls.trans = ls.trans + new_v;
			}

			if(isFirstTimeCalled)
				l.add(ls);
			
			
		}
			 
		 if(isFirstTimeCalled){
	
			 for (logStruct itt : l) {
				 logStruct ls = new logStruct();
				 ls.trans = itt.trans;
				 ls.time = itt.time;
				 lFirstProc.add(ls);
				 
				 
			}
			 //showLog(lFirstProc);
			 //System.exit(0);
			 //lFirstProc = Arrays.asList(new logStruct[l.size()]);
			 //Collections.copy(lFirstProc,l);
		 }
			 
		 
		 isFirstTimeCalled = false;
		 
		  
		
	}
}
