package common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MyS3Timer {

	String logBeginTime = null;

	String logEndTime   = null;

	List l;
	Map<Long,Integer> SecondsPerTransList = new HashMap<Long, Integer>(); 
	
	
	long timeStart;
	long timeLimit;
	int counter;
	
	SimpleDateFormat dformat =  new SimpleDateFormat("yyyy-MM-dd-hh:mm:ss");
	
	public MyS3Timer (long tL /* seconds */) {
		logBeginTime = "S3Timer time start=" + dformat.format(new Date());
		System.out.println(logBeginTime);
		timeStart = System.currentTimeMillis(); //msec
		timeLimit = tL; // in sec
		counter = 0;
	}
	
	// while (!isTimeIsOut()) { ..your thing... }
	public boolean isTimeIsOut(){
		long execTime = (System.currentTimeMillis() - timeStart )/1000; //get sec passed from start
		
		SecondsPerTransList.put(execTime, counter);
		
		boolean isTimeIsOut = (execTime > timeLimit);
		
		if (isTimeIsOut){
			logEndTime = "S3Timer time   end=" + dformat.format(new Date()) + ", number of cycles=" + counter;
			System.out.println(logEndTime);
		}
		
		counter++;
		return isTimeIsOut;
	}
	
	public long getStartTime(){
		return timeStart;
	}
	public int getCounter(){
		return counter;
	}
	
	public Map<Long, Integer> getStatistiqueList(){
		return SecondsPerTransList;
	}

	public String getLogBeginTime() {
		return logBeginTime;
	}
	public String getLogEndTime() {
		return logEndTime;
	}
	

	

}
