package dpss.service.replica;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class LeaderReplicaFESender {
	public static String NameFE="localhost";
	///Socket to send to FE
	
	String message=null;
	DatagramPacket UDPMessage=null;
	DatagramSocket socketFE=null;
	InetAddress hostFE;
	int portFE=9000;
	int portFES=9001;

	public LeaderReplicaFESender(){
		try {			
		
			socketFE=new DatagramSocket(portFES);
			hostFE = InetAddress.getByName(NameFE);

		} catch (UnknownHostException e) {		
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		}	
	}

	public void send(String messageParam) { 

		try {
			
			System.out.println("Sending message back to FE...");
			UDPMessage=new DatagramPacket(messageParam.getBytes(),messageParam.length(),hostFE,portFE);	
			
			synchronized (socketFE) {			
				socketFE.send(UDPMessage);	
			}			
			
		} catch (IOException e) {		
			e.printStackTrace();
		}		
		
	}	

}

