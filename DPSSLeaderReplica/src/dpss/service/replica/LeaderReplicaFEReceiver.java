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

public class LeaderReplicaFEReceiver extends Thread {

	DatagramSocket socketA=null;
	int portA=1010;
	int seqFIFO;
	InetAddress hostLR;
	
	GameServerFactory gameServers;
	GameServerImpl gameServer;
	LinkedList<Request> reqList;
	LeaderReplicaLeaderRequests LeaderRequests;
	LeaderReplicaCompare compareFIFO;	
	
	/* MULTICAST - Sender */			
	int mPort = 6789;
	InetAddress ipGroup = null;
	MulticastSocket s=null;
	
	
	public LeaderReplicaFEReceiver(GameServerFactory gameServersParam, LinkedList<Request> reqListParam, LeaderReplicaCompare compareFIFOParam )	{
		try {
							
				seqFIFO = 0;
				this.gameServers = gameServersParam;
				this.reqList = reqListParam;
				this.compareFIFO = compareFIFOParam; 
				socketA=new DatagramSocket(portA);
				hostLR = InetAddress.getByName("localhost");	
				
				/* MULTICAST - Sender */
				ipGroup = InetAddress.getByName("228.6.3.21");
				s = new MulticastSocket(mPort);
				
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			
		}
		
		public void run(){
			
			byte[] bufferRequest=new byte[1000];
			DatagramPacket UDPRequest=new DatagramPacket(bufferRequest, bufferRequest.length);
			RequestType typeReq;
						
			while(true){
				try {
					
					socketA.receive(UDPRequest);
					String[] requestInformation=new String[10];
					String s=new String(UDPRequest.getData()).substring(0,UDPRequest.getLength());
					requestInformation=s.split("->");
					typeReq=RequestType.valueOf(requestInformation[0]);
					
					//Add request into FIFO Queue
					System.out.println("1.LeaderReplicaFEReceiver> Add request into FIFO Queue");
					reqList.add(new Request(seqFIFO,typeReq));
					
					//Multicast request
					System.out.println("2.LeaderReplicaFEReceiver> Multicasting to group");
					multicastGroup(seqFIFO + "->"+ s);
					
					//Execute on Leader
					System.out.println("3.LeaderReplicaFEReceiver> Executing on leader");
					new Thread(new LeaderReplicaLeaderRequests(gameServers,reqList, seqFIFO, s, compareFIFO)).start();

					//FIFO++
					seqFIFO++;							
					
				} catch (Exception e) {
					
					e.printStackTrace();
				}
			}
		}
		

		
		public void multicastGroup(String message) {
		
			//Group members
			try {			

				byte [] m = message.getBytes();
				System.out.println("Message:" + message);
				DatagramPacket messageOut = new DatagramPacket(m, m.length, ipGroup, mPort);
				s.send(messageOut);		
			
			} catch (IOException e1) {			
				e1.printStackTrace();
			}				
		}			
		
	}

	

