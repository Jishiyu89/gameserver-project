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
			System.out.println("1.Game servers created");
			
			//Starting FIFO Queue
			reqList=new LinkedList<Request>();
			System.out.println("2.FIFO Queue created");
			
			//Starting FE Receiver
			LeaderReplicaFEReceiver lRFER = new LeaderReplicaFEReceiver(gameServers, reqList);
			System.out.println("3.FE Receiver created");
			lRFER.run1();
			
			
			
		}catch(Exception e) {}
		}
	
}
