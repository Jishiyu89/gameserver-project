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
			new Thread(new LeaderReplicaFEReceiver(gameServers, reqList)).start();		
			System.out.println("3.FE Receiver created");
			
			//Starting Replica Receiver
			new Thread(new LeaderReplicaReplicasReceiver(reqList)).start();	
			System.out.println("4.Replica Receiver created");
		
			//Starting RM Receiver
			new Thread(new LeaderReplicaRMReceiver()).start();	
			System.out.println("5.RM Receiver created");
			
			
		}catch(Exception e) {}
		}
	
}
