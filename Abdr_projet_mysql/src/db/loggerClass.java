package db;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class loggerClass {
	
	BufferedWriter bw = null;
	
	public loggerClass(){
		
	}
	
	public void openLogStream(String file){
		System.out.println("openLogStream");
		try {
			String datePrefix = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss_").format(new Date());
			bw = new BufferedWriter(new FileWriter(datePrefix+ file,true));
		} catch (IOException e) {
			System.out.println("loggerClass::openLogStream exception:" + e.getMessage());
		}
	}
	public void closeLogStream(){
		try {
			if (bw != null)
				bw.close();
			
			bw = null;

		} catch (IOException e) {
			System.out.println("loggerClass::openLogStream exception:" + e.getMessage());
		}
	}
	

	public void saveBufferWithN(List<String> l){
		String str = "";
		for (String s : l)
			str += s + '\n';
		saveBuffer(str);
	}
	public void saveBuffer(List<String> l){
		String str = "";
		for (String s : l)
			str += s;
		
		saveBuffer(str);
	}
	public void saveBufferWithN(String str) {
		saveBuffer(str + '\n');
	}
	public void saveBuffer(String str) {
		if (bw == null){
			System.out.println("saveBuffer file not opened, skip action...");
			return;
			}

		try {
			bw.write(str);
		} catch (IOException e) {
			System.out.println("loggerClass::saveBuffer exception:" + e.getMessage());
		}
	}
	
}
