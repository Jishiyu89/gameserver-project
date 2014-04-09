package dpss.service.replica;

import java.util.LinkedList;

import dpss.model.Request;
import dpss.service.GameServerFactory;

public class LeaderReplicaMain {

	//Run in main thread
	GameServerFactory gameServers;
	LinkedList<Request> reqList;
	
	public LeaderReplicaMain()	{
		
		try {
			
			//Creating Game servers in the main thread
			gameServers = new GameServerFactory();		
			System.out.println("servers created");
			
			reqList=new LinkedList<Request>();
			
			
		}catch(Exception e) {}
		}
	
}
