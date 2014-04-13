package dpss.service.replica;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class LeaderReplicaRMSender {

	///Socket to send to RM	
	DatagramPacket UDPMessage=null;
	DatagramSocket socketRM=null;
	InetAddress hostRM;
	int portRM=7000;
	int portRMS=7002;
	

	public LeaderReplicaRMSender(){
		try {			
			socketRM=new DatagramSocket(portRMS);
			hostRM = InetAddress.getByName("localhost");

		} catch (UnknownHostException e) {		
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		}	
	}

	public void send(String messageParam) { 

		try {
			
			UDPMessage=new DatagramPacket(messageParam.getBytes(),messageParam.length(),hostRM,portRM);	
			
			synchronized (socketRM) {			
				socketRM.send(UDPMessage);	
			}
			
		} catch (IOException e) {		
			e.printStackTrace();
		}		
	
	}	

}
