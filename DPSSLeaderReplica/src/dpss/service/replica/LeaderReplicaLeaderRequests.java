package dpss.service.replica;

import java.util.LinkedList;

import dpss.model.Request;
import dpss.model.RequestType;
import dpss.service.GameServerFactory;
import dpss.service.GameServerImpl;
import dpss.service.WriteLog;

public class LeaderReplicaLeaderRequests implements Runnable {

	GameServerFactory gameServers;
	GameServerImpl gameServer=null;
	LinkedList<Request> reqList;
	String[] requestMessageArray = new String[10];
	RequestType type;
	String reply;
	String requestMessage=null;
	int seqFIFO;
	LeaderReplicaCompare compareFIFO;	
	WriteLog Logger = new WriteLog(); 	
	
	// Auxiliar class to handle Leader's requests
	public LeaderReplicaLeaderRequests(GameServerFactory gameServersParam, LinkedList<Request> reqListParam, Integer seqFIFOParam, String requestMessageParam) {

		this.gameServers=gameServersParam;
		this.reqList=reqListParam;
		this.requestMessage= requestMessageParam;
		this.seqFIFO = seqFIFOParam;
	}

	public void run() {

		requestMessageArray = requestMessage.split("->");
		type = RequestType.valueOf(requestMessageArray[0]);

		switch (type) {
		case CreatePlayerAccount:

			gameServer = IPConvert(requestMessageArray[6]);
			
			if (gameServer != null) {
				reply = gameServer.createPlayerAccount(requestMessageArray[1],
						requestMessageArray[2], requestMessageArray[3],
						requestMessageArray[4],
						Integer.parseInt(requestMessageArray[5]),
						requestMessageArray[6]);
				
				System.out.println("reply = >>>" + reply);

			}			
			break;

		case PlayerSignIn:
			
			System.out.println("PlayerSignIn");

			gameServer = IPConvert(requestMessageArray[3]);
			
			if (gameServer != null)
				reply = gameServer.playerSignIn(requestMessageArray[1],
						requestMessageArray[2], requestMessageArray[3]);
			System.out.println(reply);
			
			break;
		case PlayerSignOut:
			System.out.println("PlayerSignOut");

			gameServer = IPConvert(requestMessageArray[2]);
			if (gameServer != null)
				reply = gameServer.playerSignOut(requestMessageArray[1],
						requestMessageArray[2]);
			System.out.println(reply);
		

			break;
		case TransferAccount:
			System.out.println("TransferAccount");

			gameServer = IPConvert(requestMessageArray[3]);
			if (gameServer != null)
				reply = gameServer.transferAccount(requestMessageArray[1],
						requestMessageArray[2], requestMessageArray[3],
						requestMessageArray[4]);
			System.out.println(reply);
			

			break;
		case GetPlayerStatus:
			System.out.println("GetPlayerStatus");

			gameServer = IPConvert(requestMessageArray[3]);
			if (gameServer != null)
				reply = gameServer.getPlayerStatus(requestMessageArray[1],
						requestMessageArray[2], requestMessageArray[3]);
			System.out.println(reply);
		

			break;
		case SuspendAccount:
		
			System.out.println("SuspendAccount");

			gameServer = IPConvert(requestMessageArray[3]);
			if (gameServer != null)
				reply = gameServer.suspendAccount(requestMessageArray[1],
						requestMessageArray[2], requestMessageArray[3],
						requestMessageArray[4]);
			System.out.println(reply);
		

			break;

		}
		
		//After receiving the reply from local game servers > update FIFO queue
		Request auxRequest = reqList.get(reqList.indexOf(new Request(seqFIFO)));		
		try {
			
			synchronized (auxRequest) {
				auxRequest.setStatus(1, reply);
			}	
			
			Logger.write("LeaderReplica", "Reply from Leader Replica to request["+seqFIFO+"]:" + reply);
			
			//COMPARE AN REPLY
			compareFIFO = new LeaderReplicaCompare(reqList);
			compareFIFO.compare();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private GameServerImpl IPConvert(String s) {
		String[] IP = s.split("\\.");
		System.out.println(s);
		System.out.println(IP[0]);
		if (IP[0] == null) {
			System.out.println("null>");
			return null;
		} else {
			switch (Integer.parseInt(IP[0])) {
			case 132:
				System.out.println("132 here!");
				return gameServers.servantNA;
			case 93:
				System.out.println("93 here!");
				return gameServers.servantEU;
			default:
				System.out.println("wte here!");
				return gameServers.servantAS;
			}
		}

	}
}
