package dpss.service;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import dpss.model.*;

public class LeaderReplica extends Thread{
	DatagramSocket socketA=null;
	int portA=1010;
	InetAddress hostLR;
	LeaderReplica()	{
		try {
			socketA=new DatagramSocket(portA);
			hostLR = InetAddress.getByName("localhost");
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
	public void run(){
		byte[] bufferRequest=new byte[1000];
		DatagramPacket UDPRequest=new DatagramPacket(bufferRequest, bufferRequest.length);
		byte[] bufferReply=new byte[1000];
		DatagramPacket UDPReply=null;
		RequestType type;
		while(true){
			try {
				socketA.receive(UDPRequest);
				String[] requestInformation=new String[10];
				requestInformation=new String(UDPRequest.getData()).substring(0,UDPRequest.getLength()).split("->");
				type=RequestType.valueOf(requestInformation[0]);
				switch(type){
				case CreatePlayerAccount:
					System.out.println("CreatPlayerAccount");
					UDPReply=new DatagramPacket("CreatPlayerAccount".getBytes(),"CreatPlayerAccount".length(),hostLR,9000);
					break;
				case PlayerSignIn:
					System.out.println("PlayerSignIn");
					UDPReply=new DatagramPacket("PlayerSignIn".getBytes(),"CreatPlayerAccount".length(),hostLR,9000);

					break;
				case PlayerSignOut:
					System.out.println("PlayerSignOut");
					UDPReply=new DatagramPacket("PlayerSignOut".getBytes(),"CreatPlayerAccount".length(),hostLR,9000);

					break;
				case TransferAccount:
					System.out.println("TransferAccount");
					UDPReply=new DatagramPacket("TransferAccount".getBytes(),"CreatPlayerAccount".length(),hostLR,9000);

					break;
				case GetPlayerStatus:
					System.out.println("GetPlayerStatus");
					UDPReply=new DatagramPacket("GetPlayerStatus".getBytes(),"CreatPlayerAccount".length(),hostLR,9000);

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
