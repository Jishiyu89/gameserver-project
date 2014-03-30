package dpss.service;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.*;

/**  Auxiliar class (Server side) 
 * 	 Used to write server logs to the disk
 *   @class WriteLog
 *   
 * */

public class WriteLog {
	
	BufferedWriter writer = null; 
	public void write(String filenameParam, String messageParam){		
		
		filenameParam = "log/"+filenameParam+".txt";
		SimpleDateFormat now = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String content = "["+ now.format(new Date()) + "] " + messageParam;
		
		try{			
				writer = new BufferedWriter(new FileWriter(filenameParam, true));	
				writer.write(content);
				writer.newLine();
				writer.close();			

		}
		catch(Exception e) {
			e.printStackTrace();
		}}
	
}



