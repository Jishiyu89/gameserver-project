package dpss.service;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.omg.CORBA.ORB;

import GameInterface.*;
import GameInterface.GameHelper;

public class BehaviorClient {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		ORB orb = ORB.init(args,null);
		
		BufferedReader br = new BufferedReader(new FileReader("../ior.txt"));
		String ior = br.readLine();
		br.close();

		org.omg.CORBA.Object o = orb.string_to_object(ior);
		
		Game aBehavior = GameHelper.narrow(o);
		
		System.out.println("Client calling the server now...");
		System.out.println(aBehavior.createPlayerAccount("a", "a", "a", "a", (short)1, "a"));
		
	}

}

/* TIPS for #ASSGN#2
 * -Do not change the interface
 * -For transfer you can use TCP or UDP
 * -3 different IORs
 * 
 * */
 