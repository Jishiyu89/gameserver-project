package dpss.view; 

import dpss.service.GameServerFactory;;

/**  Single entry point of the application (Server side) <BR>
 *   Game servers NA, EU and AS are created here by invocating the class GameServerFactory
 *   that initializes CORBA objects/services and creates server objects 
 *   @class DPSSServer
 *   
 * */

public class DPSSServer {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		GameServerFactory gameServers = new GameServerFactory();

	}

}
 
 
 