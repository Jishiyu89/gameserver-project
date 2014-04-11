package dpss.service;

/**  Responsible for creating and initializing the ORB, references and servants for game servers NA, EU and AS.
 * 	 The current implementation relies on the CORBA Naming Service to create the references to the distributed objects
 *   A specific UDP port is configured for each game server - to be used by account transfers and administrative queries.
 *   @class GameServerFactory
 *   
 * */

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.PortableServer.POA;

public class GameServerFactory extends Thread {

	ORB orb;
	POA rootpoa;
	org.omg.CORBA.Object objRef;
	NamingContextExt ncRef;
	String[] args;
	
	public GameServerImpl servantNA;
	public GameServerImpl servantEU;
	public GameServerImpl servantAS;
	
	public GameServerFactory() { 			
		
		try{ 
			serversCreation();
		}
		catch(Exception e){
			e.printStackTrace();
		}		
		
	}
	
	
	//TODO: Close Sockets!? 
	//TODO: sync RM sender>> Call from the main thread
	
	public void serversReset() {		
		try{ 
			servantNA = null;
			servantEU = null;
			servantAS = null;		
			serversCreation();
		}
		catch(Exception e){
			e.printStackTrace();
		}			

	}
	
	private void serversCreation() throws Exception {
		servantNA = new GameServerImpl("NA",1500);
		servantEU = new GameServerImpl("EU",2500);
		servantAS = new GameServerImpl("AS",3500);
	}

}
