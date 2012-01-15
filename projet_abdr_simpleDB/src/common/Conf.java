package common;

import java.io.File;
import java.io.IOException;

public class Conf {
	private Conf(){}
	

    
	public static Integer threadsNum = 1;
	public static Integer MyS3Time = 5;
	public static String [] domainsName = {"D11","D12"};
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static String getCredentialFilePath(){
		
    	File f = new File(".");
    	String path = null;
		try {
			path = f.getCanonicalPath() + "\\" + "src" + "\\" + "AwsCredentials.properties";
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}
    
}
