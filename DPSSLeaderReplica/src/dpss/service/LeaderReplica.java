package dpss.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.LinkedList;

import org.omg.CORBA.ORB;

import dpss.model.*;
import dpss.service.corba.GameServer;
import dpss.service.corba.GameServerHelper;

public class LeaderReplica extends Thread{
	DatagramSocket socketA=null;
	int portA=1010;
	InetAddress hostLR;
	org.omg.CORBA.Object oNA,oEU,oAS;
	GameServerImpl gameServer;
	int Seq;
	GameServerFactory gameServers;
	
	LinkedList<Request> reqList;
	
	public LeaderReplica()	{
		try {
			
			gameServers = new GameServerFactory();
		
			System.out.println("servers created");
			
			socketA=new DatagramSocket(portA);
			hostLR = InetAddress.getByName("localhost");
			reqList=new LinkedList<Request>();
			Seq=0;
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
	
	
	private GameServerImpl IPConvert(String s){
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
					// return GameServerHelper.narrow(oNA);					 
					return gameServers.servantNA;
			case 93:System.out.println("93 here!");
					//return  GameServerHelper.narrow(oEU);
					return gameServers.servantEU;
			default:System.out.println("wte here!");
					//return GameServerHelper.narrow(oAS);
					return gameServers.servantAS;
			}
		}
		
	}
	public void run(){
		
		System.out.println("Running run() from Leader Replica");
		
		byte[] bufferRequest=new byte[1000];
		DatagramPacket UDPRequest=new DatagramPacket(bufferRequest, bufferRequest.length);
		
		DatagramPacket UDPReply=null;
		RequestType type;
		String reply=null;
		
		while(true){
			try {
				socketA.receive(UDPRequest);
				String[] requestInformation=new String[10];
				requestInformation=new String(UDPRequest.getData()).substring(0,UDPRequest.getLength()).split("->");
				type=RequestType.valueOf(requestInformation[0]);
				switch(type){
				case CreatePlayerAccount:
					System.out.println("CreatPlayerAccount");
					reqList.add(new Request(Seq,RequestType.CreatePlayerAccount));
					
					gameServer=IPConvert(requestInformation[6]);
					if(gameServer!=null)
					{
						reply=gameServer.createPlayerAccount(requestInformation[1], requestInformation[2], requestInformation[3], requestInformation[4], Integer.parseInt(requestInformation[5]), requestInformation[6]);
						System.out.println("reply = >>>" + reply );
						Request temp=reqList.get(Seq);
						Seq++;
						temp.setStatus(1, reply);
					}
					System.out.println(reply);		
					UDPReply=new DatagramPacket(reply.getBytes(),reply.length(),hostLR,9000);
					break;
				
				case PlayerSignIn:
					System.out.println("PlayerSignIn");
					
					gameServer=IPConvert(requestInformation[3]);
					if(gameServer!=null)
						reply=gameServer.playerSignIn(requestInformation[1], requestInformation[2], requestInformation[3]);
					System.out.println(reply);		
					UDPReply=new DatagramPacket(reply.getBytes(),reply.length(),hostLR,9000);
					break;
				case PlayerSignOut:
					System.out.println("PlayerSignOut");

					gameServer=IPConvert(requestInformation[2]);
					if(gameServer!=null)
						reply=gameServer.playerSignOut(requestInformation[1], requestInformation[2]);
					System.out.println(reply);		
					UDPReply=new DatagramPacket(reply.getBytes(),reply.length(),hostLR,9000);
					
					break;
				case TransferAccount:
					System.out.println("TransferAccount");

					gameServer=IPConvert(requestInformation[3]);
					if(gameServer!=null)
						reply=gameServer.transferAccount(requestInformation[1], requestInformation[2],requestInformation[3], requestInformation[4]);
					System.out.println(reply);		
					UDPReply=new DatagramPacket(reply.getBytes(),reply.length(),hostLR,9000);
					
					break;
				case GetPlayerStatus:
					System.out.println("GetPlayerStatus");

					gameServer=IPConvert(requestInformation[3]);
					if(gameServer!=null)
						reply=gameServer.getPlayerStatus(requestInformation[1], requestInformation[2],requestInformation[3]);
					System.out.println(reply);		
					UDPReply=new DatagramPacket(reply.getBytes(),reply.length(),hostLR,9000);
					
					break;
				case SuspendAccount:					
					//UDPReply=new DatagramPacket("SuspendAccount".getBytes(),"CreatPlayerAccount".length(),hostLR,9000);
				
					System.out.println("SuspendAccount");

					gameServer=IPConvert(requestInformation[3]);
					if(gameServer!=null)
						reply=gameServer.suspendAccount(requestInformation[1], requestInformation[2],requestInformation[3],requestInformation[4]);
					System.out.println(reply);		
					UDPReply=new DatagramPacket(reply.getBytes(),reply.length(),hostLR,9000);
					
					break;
					
				}
				
				compare();
				
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
	
}
