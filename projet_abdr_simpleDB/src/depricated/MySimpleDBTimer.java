package depricated;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//
//public class MySimpleDBTimer {
//
//	long timeStart;
//	long timeLimit;
//	int counter;
//	SimpleDateFormat dformat =  new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
//	
//	public MySimpleDBTimer (long tL /* seconds */) {
//		System.out.println("S3Timer time start=" + dformat.format(new Date()));
//		timeStart = System.currentTimeMillis(); //msec
//		timeLimit = tL; // in sec
//		counter = 0;
//	}
//	
//	// while (!isTimeIsOut()) { ...
//	public boolean isTimeIsOut(){
//		long execTime = (System.currentTimeMillis() - timeStart )/1000; //get sec passed from start
//		boolean isTimeIsOut = (execTime > timeLimit);
//		
//		if (isTimeIsOut)
//			System.out.println("S3Timer time end=" + dformat.format(new Date()) + ", number of cycles=" + counter);
//		
//		counter++;
//		return isTimeIsOut;
//	}
//	
//	public long getStartTime(){
//		return timeStart;
//	}
//	public int getCounter(){
//		return counter;
//	}
//	
//
//	
//
//}
