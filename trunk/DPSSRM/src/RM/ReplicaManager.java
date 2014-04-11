package RM;

/**
 * Replica Manager.
 * Receives from Replica Leader the ID of the replica with the wrong result and increment its counter. 
 * When the counter reaches the threshold "3" it sends an UDP message to the Replica asking it to restart its services. 
 * It uses 1 socket and receives on port 7000. For the responses it uses ports 7001 (Leader Replica), 7002 (Replica 2) and 7003 (Replica 3) 
 * 
 */


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ReplicaManager extends Thread{
	
	DatagramSocket socketRM=null;
	int count1,count2,count3;
	int portRM=7000;
	
	
	public ReplicaManager()
	{ 
		try {
			
			socketRM=new DatagramSocket(portRM);
			
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		
		byte[] buffer = new byte[1000];
		byte[] bufferReply = new byte[1000];
		
		DatagramPacket receiveMsg = new DatagramPacket(buffer, buffer.length);
		DatagramPacket replyMsg=null;
		
		try {
			 while(true){
				 synchronized(this){
					 socketRM.receive(receiveMsg);
					 String correctness=new String(receiveMsg.getData()).substring(0,receiveMsg.getLength());
					 if (correctness.equals("1") )
						 count1++;
					 else if (correctness.equals("2") )
						 count2++;
					 else if (correctness.equals("3") )
						 count3++;
					 bufferReply="Restart".getBytes();
					 if (count1>2)
					 {
						 replyMsg=new DatagramPacket(bufferReply,bufferReply.length, receiveMsg.getAddress(),7001);
						 count1=0;
						 socketRM.send(replyMsg);
					 }
					 else if (count2>2)
					 {
						 replyMsg=new DatagramPacket(bufferReply,bufferReply.length, receiveMsg.getAddress(),7002);
						 count2=0;
						 socketRM.send(replyMsg);
					 }
					 else if (count3>2)
					 {
						 replyMsg=new DatagramPacket(bufferReply,bufferReply.length, receiveMsg.getAddress(),7003);
						 count3=0;
						 socketRM.send(replyMsg);
					 }
				 }
			 }
			
		} catch (IOException e) {
			e.printStackTrace();
		};
	}
	
	public static void main(String arg[]){
		ReplicaManager RM=new ReplicaManager();
		RM.start();
		System.out.println("Replica Manager (RM) running!");
	}
}
