package common;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Set;

import java.util.*;


public class MyS3Timer {

	String logBeginTime = null;

	String logEndTime   = null;


	Map<Float,Integer> SecondsPerTransList = new TreeMap<Float, Integer>(); 
	
	
	long timeStart = 0;
	long timeLimit = 0;
	int counter = 0;
	long spanTimeStart = 0;
	long spanTimeEnd = 0;
	
	
	SimpleDateFormat dformat =  new SimpleDateFormat("yyyy-MM-dd-hh:mm:ss");
	
	public MyS3Timer (long tL /* seconds */) {

		SecondsPerTransList.put(new Float(0.0), 0); // init 0 0
		
		logBeginTime = "S3Timer time start=" + dformat.format(new Date());
		System.out.println(logBeginTime);
		timeStart = System.currentTimeMillis(); //msec
		timeLimit = tL; // in sec
		counter = 0;
	}
	
	// while (!isTimeIsOut()) { ..your thing... }
	public boolean isTimeIsOut(){
		Long transactionTime = new Long(0);
		if (spanTimeStart != 0)
			transactionTime = System.currentTimeMillis() - spanTimeStart;
		Long purTime  = System.currentTimeMillis() - timeStart;
		Long execTime = purTime/1000; //get sec passed from start
		
		System.out.println("transactionTime=" + transactionTime);
		//String s =String.format("%1.1f", purTime.floatValue()/1000).replace(',', '.');
		//Float val = Float.parseFloat(s);
		Float val = execTime.floatValue();
		//if(!s.equals("0,0")){
		
		if(val != 0.0)
			SecondsPerTransList.put(val, counter);
		//}
		
		boolean isTimeIsOut = (execTime > timeLimit);
		
		if (isTimeIsOut){
			logEndTime = "S3Timer time   end=" + dformat.format(new Date()) + ", number of cycles=" + counter;
			System.out.println(logEndTime);
		}
		
		counter++;
		spanTimeStart = System.currentTimeMillis();
		return isTimeIsOut;
	}
	public void showFullLog(){
		Set<Float> s = SecondsPerTransList.keySet();
		for (Float long1 : s) {
			System.out.printf("time=%5.1f , transactions = %3d\n", long1, SecondsPerTransList.get(long1));
		}
	}
	
	public long getStartTime(){
		return timeStart;
	}
	public int getCounter(){
		return counter;
	}
	
	public Map<Float, Integer> getStatistiqueList(){
		return SecondsPerTransList;
	}

	public String getLogBeginTime() {
		return logBeginTime;
	}
	public String getLogEndTime() {
		return logEndTime;
	}
	

	

}
