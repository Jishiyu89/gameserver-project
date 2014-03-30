package dpss.service;

/**  Responsible for creating and initializing the ORB, references and servants for game servers NA, EU and AS.
 * 	 The current implementation relies on the CORBA Naming Service to create the references to the distributed objects
 *   A specific UDP port is configured for each game server - to be used by account transfers and administrative queries.
 *   @class GameServerFactory
 *   
 * */

import java.io.PrintWriter;
import java.util.Properties;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import dpss.service.corba.GameServer;
import dpss.service.corba.GameServerHelper;

public class GameServerFactory {

	ORB orb;
	POA rootpoa;
	org.omg.CORBA.Object objRef;
	NamingContextExt ncRef;
	String[] args;
	
	public GameServerFactory() { 
			
		try{ 

			 orb = ORB.init(args, null); 
			 POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			 
			 GameServerImpl servantNA = new GameServerImpl("NA",1500);
			 GameServerImpl servantEU = new GameServerImpl("EU",2500);
			 GameServerImpl servantAS = new GameServerImpl("AS",3500);
			 
			byte[] idNA = rootPOA.activate_object(servantNA);
			byte[] idEU = rootPOA.activate_object(servantEU);
			byte[] idAS = rootPOA.activate_object(servantAS);
	
			org.omg.CORBA.Object refNA = rootPOA.id_to_reference(idNA);
			org.omg.CORBA.Object refEU = rootPOA.id_to_reference(idEU);
			org.omg.CORBA.Object refAS = rootPOA.id_to_reference(idAS);	
			
			String iorNA = orb.object_to_string(refNA);
			String iorEU = orb.object_to_string(refEU);
			String iorAS = orb.object_to_string(refAS);
			
			PrintWriter fileNA = new PrintWriter("G:\\workspace\\iorNA.txt");
			PrintWriter fileEU = new PrintWriter("G:\\workspace\\iorEU.txt");
			PrintWriter fileAS = new PrintWriter("G:\\workspace\\iorAS.txt");
			fileNA.println(iorNA);
			fileEU.println(iorEU);
			fileAS.println(iorAS);
			fileNA.close();	
			fileEU.close();
			fileAS.close();			
					
			rootPOA.the_POAManager().activate();
			orb.run();
			 
		}
		catch(Exception e){
			e.printStackTrace();
		}		
		
	}

}
