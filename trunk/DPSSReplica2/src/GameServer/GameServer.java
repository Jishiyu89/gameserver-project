package GameServer;
/**
 * @author Shu Liu
 * This is Server Class
 * It can reply the request from both AdminClient and Client
 * 
 */

import java.io.PrintWriter;
import org.omg.CORBA.ORB;

import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

//import GameInterface.*;


public class GameServer {

	/**
	 * @param args
	 * @throws Exception 
	 */
	ServerImpl serverEU;
	ServerImpl serverNA;
	ServerImpl serverAS;
	
	public GameServer() throws   Exception {
		// TODO Auto-generated method stub
		
//		ORB orb = ORB.init(args,null);
//		POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));

		//EU server
		ServerImpl serverEU = new ServerImpl("EU",1000);
// 		byte[] idEU = rootPOA.activate_object(serverEU); /* converting to a unique ID*/		
//		org.omg.CORBA.Object refEU = rootPOA.id_to_reference(idEU); /* postal card - our reference */		
//		String iorEU = orb.object_to_string(refEU);
//		System.out.println(iorEU);
//		PrintWriter file = new PrintWriter("../iorEU.txt");
//		file.println(iorEU);
//		file.close();
//		new Thread(serverEU).start();
		
		//NA server
		ServerImpl serverNA = new ServerImpl("NA",2000);
//		byte[] idNA = rootPOA.activate_object(serverNA); /* converting to a unique ID*/		
//		org.omg.CORBA.Object refNA = rootPOA.id_to_reference(idNA); /* postal card - our reference */		
//		String iorNA = orb.object_to_string(refNA);
//		System.out.println(iorNA);
//		file = new PrintWriter("../iorNA.txt");
//		file.println(iorNA);
//		file.close();
//		new Thread(serverNA).start();
		
		//AS server
		ServerImpl serverAS = new ServerImpl("AS",3000);
//		byte[] idAS = rootPOA.activate_object(serverAS); /* converting to a unique ID*/		
//		org.omg.CORBA.Object refAS = rootPOA.id_to_reference(idAS); /* postal card - our reference */		
//		String iorAS = orb.object_to_string(refAS);
//		System.out.println(iorAS);
//		file = new PrintWriter("../iorAS.txt");
//		file.println(iorAS);
//		file.close();
//		new Thread(serverAS).start();
		
		
//		rootPOA.the_POAManager().activate();
//		orb.run();
	}

}
