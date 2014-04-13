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
			
			//Starting RM/FE Senders in the main thread
			LeaderReplicaFESender fESender = new LeaderReplicaFESender();
			LeaderReplicaRMSender rMSender = new LeaderReplicaRMSender();
			
			//Starting Compare()
			LeaderReplicaCompare compareFIFO = new LeaderReplicaCompare(reqList, fESender, rMSender);				
			System.out.println("3.Compare() created");
			
			//Starting FE Receiver
			new Thread(new LeaderReplicaFEReceiver(gameServers, reqList, compareFIFO)).start();		
			System.out.println("4.FE Receiver created");
			
			//Starting Replica Receiver
			new Thread(new LeaderReplicaReplicasReceiver(reqList, compareFIFO)).start();	
			System.out.println("5.Replica Receiver created");
		
			//Starting RM Receiver
			new Thread(new LeaderReplicaRMReceiver(gameServers)).start();	
			System.out.println("6.RM Receiver created");
			
			
			
			
		}catch(Exception e) {}
		}
	
}
