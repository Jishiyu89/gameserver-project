package GameServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.LinkedList;



public class Replica extends Thread {
	
	ServerImpl gameServer;
	GameServer gameServers;
	DatagramSocket socketA=null;
	int portA=1012;
	InetAddress hostR;
	
	public static void main(String[] args) {
				
		
		try {
			Replica R = new Replica();
			R.run();	
			
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}
	
	
	public Replica() throws Exception{
		gameServers = new GameServer();		
		System.out.println("factory created");		
		socketA=new DatagramSocket(portA);
		hostR = InetAddress.getByName("localhost");	
	}
	
	public void run(){
		
		System.out.println("Running run() from Replica");
		
		byte[] bufferRequest=new byte[1000];
		DatagramPacket UDPRequest=new DatagramPacket(bufferRequest, bufferRequest.length);
		
		DatagramPacket UDPReply=null;
		RequestType type;
		String reply=null;


		/* Receiver  - Multicast
		
		int mPort = 6789
		InetAddress ipGroup = null;
		MulticastSocket s=null;
		
		//Group members
		ipGroup = InetAddress.getByName("localhost");
		
		//Replica 2 //Replica 3
		s = new MulticastSocket(mPort);
		s.joinGroup(ipGroup);
		
		//Receiving
		byte[] buf = new byte[1000];
		while (true) {
		
		DatagramPacket messageIn = new DatagramPacket(buf, buf.length);
		
		try {
		    s.setSoTimeout(120000)
		    s.receive(messageIn);
		} catch (SocketTimeoutException e) {
		break;
		} catch (IOException e) {}
		
		    String received = new String(messageIn.getData());
		    System.out.println(received);
		}
		//leaving the group and closing socket
		s.leaveGroup(ipGroup);if (s!=null) 
		 s.close();
		
		*/
				
		
		while(true){
			try {
				socketA.receive(UDPRequest);
				String[] requestInformation=new String[10];
				requestInformation=new String(UDPRequest.getData()).substring(0,UDPRequest.getLength()).split("->");
				type=RequestType.valueOf(requestInformation[0]);
				switch(type){
				case CreatePlayerAccount:
					System.out.println("CreatPlayerAccount");
					
					gameServer=IPConvert(requestInformation[6]);
					if(gameServer!=null)
					{
						reply=gameServer.createPlayerAccount(requestInformation[1], requestInformation[2], requestInformation[3], requestInformation[4], Short.parseShort(requestInformation[5]), requestInformation[6]);
						System.out.println("reply = >>>" + reply );
					}
					System.out.println(reply);		
					UDPReply=new DatagramPacket(reply.getBytes(),reply.length(),UDPRequest.getAddress(),9000);
					break;
				
				case PlayerSignIn:
					System.out.println("PlayerSignIn");
					
					gameServer=IPConvert(requestInformation[3]);
					if(gameServer!=null)
						reply=gameServer.playerSignIn(requestInformation[1], requestInformation[2], requestInformation[3]);
					System.out.println(reply);		
					UDPReply=new DatagramPacket(reply.getBytes(),reply.length(),UDPRequest.getAddress(),9000);
					break;
				case PlayerSignOut:
					System.out.println("PlayerSignOut");

					gameServer=IPConvert(requestInformation[2]);
					if(gameServer!=null)
						reply=gameServer.playerSignOut(requestInformation[1], requestInformation[2]);
					System.out.println(reply);		
					UDPReply=new DatagramPacket(reply.getBytes(),reply.length(),UDPRequest.getAddress(),9000);
					
					break;
				case TransferAccount:
					System.out.println("TransferAccount");

					gameServer=IPConvert(requestInformation[3]);
					if(gameServer!=null)
						reply=gameServer.transferAccount(requestInformation[1], requestInformation[2],requestInformation[3], requestInformation[4]);
					System.out.println(reply);		
					UDPReply=new DatagramPacket(reply.getBytes(),reply.length(),UDPRequest.getAddress(),9000);
					
					break;
				case GetPlayerStatus:
					System.out.println("GetPlayerStatus");

					gameServer=IPConvert(requestInformation[3]);
					if(gameServer!=null)
						reply=gameServer.getPlayerStatus(requestInformation[1], requestInformation[2],requestInformation[3]);
					System.out.println(reply);		
					UDPReply=new DatagramPacket(reply.getBytes(),reply.length(),UDPRequest.getAddress(),9000);
					
					break;
				case SuspendAccount:					
					//UDPReply=new DatagramPacket("SuspendAccount".getBytes(),"CreatPlayerAccount".length(),hostLR,9000);
				
					System.out.println("SuspendAccount");

					gameServer=IPConvert(requestInformation[3]);
					if(gameServer!=null)
						reply=gameServer.suspendAccount(requestInformation[1], requestInformation[2],requestInformation[3],requestInformation[4]);
					System.out.println(reply);		
					UDPReply=new DatagramPacket(reply.getBytes(),reply.length(),UDPRequest.getAddress(),9000);
					
					break;
					
				}
				socketA.send(UDPReply);
				
				//to RM
				socketA.send(new DatagramPacket("1".getBytes(),"1".length(),UDPRequest.getAddress(),7000));
				
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
	}

	
	private ServerImpl IPConvert(String s){
		String[] IP=s.split("\\.");
		System.out.println(s);
		System.out.println(IP[0]);
		if (IP[0]==null) {
			System.out.println("null>");
			return null;
		}		
		else{ 
			switch(Integer.parseInt(IP[0])){
			case 132:System.out.println("132 here!");			 
					return gameServers.serverNA;
			case 93:System.out.println("93 here!");
					return gameServers.serverEU;
			default:System.out.println("wte here!");
					return gameServers.serverAS;
			}
		}
		
	}

}
