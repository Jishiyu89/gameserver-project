package dpss.service.replica;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class LeaderReplicaFESender {

	///Socket to send to FE
	
	String message=null;
	DatagramPacket UDPMessage=null;
	DatagramSocket socketFE=null;
	InetAddress hostFE;
	int portFE=9000;
	int portFES=9001;

	public LeaderReplicaFESender(String messageParam){
		try {

			System.out.println("Sending Reply to FE:" + messageParam );
			
			this.message = messageParam;
			socketFE=new DatagramSocket(portFES);
			hostFE = InetAddress.getByName("localhost");

		} catch (UnknownHostException e) {		
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		}	
	}

	public void send() { 

		try {

			UDPMessage=new DatagramPacket(message.getBytes(),message.length(),hostFE,portFE);	
			socketFE.send(UDPMessage);			
		} catch (IOException e) {		
			e.printStackTrace();
		}
		
		//Closing quietly
		try {
			socketFE.close();
		}
		catch (Exception e) {}
	}	

}
