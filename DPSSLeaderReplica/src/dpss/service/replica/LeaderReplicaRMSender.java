package dpss.service.replica;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class LeaderReplicaRMSender {

	///Socket to send to RM
	
	String message=null;
	DatagramPacket UDPMessage=null;
	DatagramSocket socketRM=null;
	InetAddress hostRM;
	int portRM=7000;

	public LeaderReplicaRMSender(String messageParam){
		try {

			this.message = messageParam;
			socketRM=new DatagramSocket(portRM);
			hostRM = InetAddress.getByName("localhost");

		} catch (UnknownHostException e) {		
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		}	
	}

	public void send() { 

		try {

			UDPMessage=new DatagramPacket(message.getBytes(),message.length(),hostRM,portRM);	
			socketRM.send(UDPMessage);			
		} catch (IOException e) {		
			e.printStackTrace();
		}
		
		//Closing quietly
		try {
			socketRM.close();
		}
		catch (Exception e) {}
	}	

}
