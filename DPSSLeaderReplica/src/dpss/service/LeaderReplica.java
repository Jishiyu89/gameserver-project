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
	GameServer gameServer;
	
	LinkedList<Request> reqList;
	LeaderReplica()	{
		try {
			socketA=new DatagramSocket(portA);
			hostLR = InetAddress.getByName("localhost");
			reqList=new LinkedList<Request>();
			
			ORB orb = ORB.init((String[])null,null);	
			
			BufferedReader brNA = new BufferedReader (new FileReader("..\\iorNA.txt"));
			String iorNA = brNA.readLine();
			brNA.close();

			BufferedReader brEU = new BufferedReader (new FileReader("..\\iorEU.txt"));
			String iorEU = brEU.readLine();
			brEU.close();
			
			BufferedReader brAS = new BufferedReader (new FileReader("..\\iorAS.txt"));
			String iorAS = brAS.readLine();
			brAS.close();		

			org.omg.CORBA.Object oNA = orb.string_to_object(iorNA);
			org.omg.CORBA.Object oEU = orb.string_to_object(iorEU);
			org.omg.CORBA.Object oAS = orb.string_to_object(iorAS);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
	
	
	private GameServer IPConvert(String s){
		String[] IP=s.split("\\.");
		if (IP[0]==null)
		return null;
		else
		switch(Integer.parseInt(IP[0])){
		case 132: return GameServerHelper.narrow(oNA);
		case 98:return  GameServerHelper.narrow(oEU);
		default:return GameServerHelper.narrow(oAS);
		}
		
	}
	public void run(){
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
					
					gameServer=IPConvert(requestInformation[6]);
					if(gameServer!=null)
						reply=gameServer.createPlayerAccount(requestInformation[1], requestInformation[2], requestInformation[3], requestInformation[4], Integer.parseInt(requestInformation[5]), requestInformation[6]);
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
						reply=gameServer.suspendAccount(requestInformation[1], requestInformation[2],requestInformation[3], requestInformation[4]);
					System.out.println(reply);		
					UDPReply=new DatagramPacket(reply.getBytes(),reply.length(),hostLR,9000);
					
					break;
				case SuspendAccount:
					System.out.println("SuspendAccount");
					UDPReply=new DatagramPacket("SuspendAccount".getBytes(),"CreatPlayerAccount".length(),hostLR,9000);
				
					break;
				}
				socketA.send(UDPReply);
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
	}
	public static void main(String args[]){
		LeaderReplica lR=new LeaderReplica();
		lR.start();
	}
}
