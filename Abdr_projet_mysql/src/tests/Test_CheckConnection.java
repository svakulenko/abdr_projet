package tests;
import db.*;
public class Test_CheckConnection {

	
	public void checkConnect(){
		new DbHandler().initDB();
	}
	
	public static void main(String[] args){
		
		new Test_CheckConnection().checkConnect();
	}
}
