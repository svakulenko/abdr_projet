package common;
import java.io.*;
public class IdGenerator {
	private IdGenerator(){}
	static String idfilename = "file_ClientId.txt";
	synchronized public static Integer getUniqueId(){

		
		Integer rvalue = null;
		File f = new File(idfilename);
		if (!f.exists())
			createIdFile(0);
		
			try {
				FileReader fr = new FileReader(idfilename);
				BufferedReader br = new BufferedReader(fr);
				String line = null;
				line = br.readLine();
				

				
				Integer id = Integer.parseInt(line);
				br.close();
				
				//System.out.println("id from file=" + sb.toString());
				
				
				id++;
				System.out.println("get id=" + id);
				
				createIdFile(id);
				rvalue = id;
					
			} catch (Exception e) {
				System.out.println("open file exception: " + e.getMessage());
			}
			
			
			return rvalue;
	}
	
	synchronized static void createIdFile(Integer id) {
		//System.out.println("createIdFile id=" + id.toString());
		try {
			FileWriter fw = new FileWriter(idfilename,false);
			fw.write("" + id.toString());
			fw.close();
		} catch (IOException e) {
			System.out.println("create file exception: " + e.getMessage());
		}
	}
	
}
