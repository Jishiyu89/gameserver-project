package dpss.service.replica;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.LinkedList;

import dpss.model.Request;
import dpss.model.RequestType;
import dpss.service.GameServerFactory;
import dpss.service.GameServerImpl;

public class LeaderReplicaFEReceiver {

	DatagramSocket socketA=null;
	int portA=1010;
	int Seq;
	InetAddress hostLR;
	
	GameServerFactory gameServers;
	GameServerImpl gameServer;
	LinkedList<Request> reqList;
	LeaderReplicaLeaderRequests LeaderRequests;
	
	
	/* MULTICAST - Sender */			
	int mPort = 6789;
	InetAddress ipGroup = null;
	MulticastSocket s=null;
	
	
	public LeaderReplicaFEReceiver(GameServerFactory gameServersParam, LinkedList<Request> reqListParam)	{
		try {
							
				Seq = 0;
				this.gameServers = gameServersParam;
				this.reqList = reqListParam;
				socketA=new DatagramSocket(portA);
				hostLR = InetAddress.getByName("localhost");	
				
				/* MULTICAST - Sender */
				ipGroup = InetAddress.getByName("228.5.6.1");
				s = new MulticastSocket(mPort);
				s.joinGroup(ipGroup);
				
				//Leader Request Handler
				
				
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			
		}
		
//		
//		private GameServerImpl IPConvert(String s){
//			String[] IP=s.split("\\.");
//			System.out.println(s);
//			System.out.println(IP[0]);
//			if (IP[0]==null) {
//				System.out.println("null>");
//				return null;
//			}		
//			else{ 
//				switch(Integer.parseInt(IP[0])){
//				case 132:System.out.println("132 here!");				 
//						return gameServers.servantNA;
//				case 93:System.out.println("93 here!");
//						return gameServers.servantEU;
//				default:System.out.println("wte here!");
//						return gameServers.servantAS;
//				}
//			}
//			
//		}
		
		public void run1(){
			
			System.out.println("Running run() from Leader Replica FE Receiver");
			
			byte[] bufferRequest=new byte[1000];
			DatagramPacket UDPRequest=new DatagramPacket(bufferRequest, bufferRequest.length);
			
			//DatagramPacket UDPReply=null;
			RequestType type;
			//String reply=null;		
			
			
			while(true){
				try {
					
					socketA.receive(UDPRequest);
					String[] requestInformation=new String[10];
					String s=new String(UDPRequest.getData()).substring(0,UDPRequest.getLength());
					requestInformation=s.split("->");
					type=RequestType.valueOf(requestInformation[0]);
					
					System.out.println("s is supposed to be = "+s);
					
					reqList.add(new Request(Seq,RequestType.CreatePlayerAccount));
					
					//Multicast request
					System.out.println("multicasting to group");
					multicastGroup(Seq + "->"+ s);
					
					//Execute on Leader
					System.out.println("executing on leader");
					if (s!=null)
						new LeaderReplicaLeaderRequests(s).run2();
						//new Thread (new LeaderReplicaLeaderRequests(s)).start();
					s=null;
//					switch(type){
//					case CreatePlayerAccount:
//						
//						System.out.println("CreatPlayerAccount");
//						reqList.add(new Request(Seq,RequestType.CreatePlayerAccount));
//						
//						//Multicast request
//						multicastGroup(Seq + "->"+ s);
//						
//						gameServer=IPConvert(requestInformation[6]);
//						if(gameServer!=null)
//						{							
//							reply=gameServer.createPlayerAccount(requestInformation[1], requestInformation[2], requestInformation[3], requestInformation[4], Integer.parseInt(requestInformation[5]), requestInformation[6]);
//							System.out.println("reply = >>>" + reply );
//							Request temp=reqList.get(Seq);
//							temp.setStatus(1, reply);
//							Seq++;
//						}
//						System.out.println(reply);		
//						UDPReply=new DatagramPacket(reply.getBytes(),reply.length(),hostLR,9000);
//						break;
//					
//					case PlayerSignIn:
//						System.out.println("PlayerSignIn");
//						
//						gameServer=IPConvert(requestInformation[3]);
//						if(gameServer!=null)
//							reply=gameServer.playerSignIn(requestInformation[1], requestInformation[2], requestInformation[3]);
//						System.out.println(reply);		
//						UDPReply=new DatagramPacket(reply.getBytes(),reply.length(),hostLR,9000);
//						break;
//					case PlayerSignOut:
//						System.out.println("PlayerSignOut");
//
//						gameServer=IPConvert(requestInformation[2]);
//						if(gameServer!=null)
//							reply=gameServer.playerSignOut(requestInformation[1], requestInformation[2]);
//						System.out.println(reply);		
//						UDPReply=new DatagramPacket(reply.getBytes(),reply.length(),hostLR,9000);
//						
//						break;
//					case TransferAccount:
//						System.out.println("TransferAccount");
//
//						gameServer=IPConvert(requestInformation[3]);
//						if(gameServer!=null)
//							reply=gameServer.transferAccount(requestInformation[1], requestInformation[2],requestInformation[3], requestInformation[4]);
//						System.out.println(reply);		
//						UDPReply=new DatagramPacket(reply.getBytes(),reply.length(),hostLR,9000);
//						
//						break;
//					case GetPlayerStatus:
//						System.out.println("GetPlayerStatus");
//
//						gameServer=IPConvert(requestInformation[3]);
//						if(gameServer!=null)
//							reply=gameServer.getPlayerStatus(requestInformation[1], requestInformation[2],requestInformation[3]);
//						System.out.println(reply);		
//						UDPReply=new DatagramPacket(reply.getBytes(),reply.length(),hostLR,9000);
//						
//						break;
//					case SuspendAccount:					
//						//UDPReply=new DatagramPacket("SuspendAccount".getBytes(),"CreatPlayerAccount".length(),hostLR,9000);
//					
//						System.out.println("SuspendAccount");
//
//						gameServer=IPConvert(requestInformation[3]);
//						if(gameServer!=null)
//							reply=gameServer.suspendAccount(requestInformation[1], requestInformation[2],requestInformation[3],requestInformation[4]);
//						System.out.println(reply);		
//						UDPReply=new DatagramPacket(reply.getBytes(),reply.length(),hostLR,9000);
//						
//						break;
//						
//					}
					
					//compare();
					
					//socketA.send(UDPReply);
					
					//to RM
					//socketA.send(new DatagramPacket("1".getBytes(),"1".length(),hostLR,7000));
					
					
				} catch (Exception e) {
					
					e.printStackTrace();
				}
			}
		}
		
		public void compare() throws Exception {
			
			String reply=null;
			DatagramPacket UDPResult=null;
			
			Request oldestReq = reqList.getFirst();

			//Returning the result to FE//
			if (oldestReq.getAllReplies()){			
				DatagramPacket UDPReply=null;
				reply = oldestReq.getVotedReply();
				UDPResult=new DatagramPacket(reply.getBytes(),reply.length(),hostLR,9000);	
				socketA.send(UDPReply);
			}
			
			//Informing RM about the issue//
			if (oldestReq.diffResponse > 0){
				reply = Integer.toString(oldestReq.diffResponse);
				UDPResult = (new DatagramPacket(reply.getBytes(),reply.length(),hostLR,7000));
				socketA.send(UDPResult);
			}
		}
		
		
		public void multicastGroup(String message) {
		
			//Group members
			try {			

				// message contents & destination multicast group (e.g. "228.5.6.7")
				byte [] m = message.getBytes();
				System.out.println("message:" + message);
				DatagramPacket messageOut = new DatagramPacket(m, m.length, ipGroup, mPort);
				s.send(messageOut);
		
				//leaving the group and closing socket
				//s.leaveGroup(ipGroup);
		
				//if (s!=null) 
				// s.close();
			
			} catch (IOException e1) {			
				e1.printStackTrace();
			}	
			
		}	
		
		
	}

	

