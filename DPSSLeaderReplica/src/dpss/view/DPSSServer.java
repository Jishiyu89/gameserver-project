package dpss.view; 

import dpss.service.GameServerFactory;
import dpss.service.LeaderReplica;

/**  Single entry point of the application (Server side) <BR>
 *   Game servers NA, EU and AS are created here by invocating the class GameServerFactory
 *   that initializes CORBA objects/services and creates server objects 
 *   @class DPSSServer
 *   
 * */

public class DPSSServer {

	public static void main(String[] args) {
		
		LeaderReplica lR=new LeaderReplica();
		lR.run();		

	}

}
 
 
 