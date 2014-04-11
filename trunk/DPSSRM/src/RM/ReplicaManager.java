package RM;
/*
 * Replica Management
 * 
 * Receive correctness report 
 * format: replica number;  port number : 7000
 * 
 * send control message to the replica which is wrong over 2 time
 * format: Restart;  ?????port number of replica address of replica?????
 * 
 * @author Shu Liu ID: 6855466
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
						 replyMsg=new DatagramPacket(bufferReply,bufferReply.length, receiveMsg.getAddress(),receiveMsg.getPort());
						 count1=0;
						 socketRM.send(replyMsg);
					 }
					 else if (count2>2)
					 {
						 replyMsg=new DatagramPacket(bufferReply,bufferReply.length, receiveMsg.getAddress(),receiveMsg.getPort());
						 count2=0;
						 socketRM.send(replyMsg);
					 }
					 else if (count3>2)
					 {
						 replyMsg=new DatagramPacket(bufferReply,bufferReply.length, receiveMsg.getAddress(),receiveMsg.getPort());
						 count3=0;
						 socketRM.send(replyMsg);
					 }
				 }
			 }
			
		} catch (IOException e) {
			e.printStackTrace();
		};
	}
	public void main(String arg[]){
		ReplicaManager RM=new ReplicaManager();
		RM.start();
	}
}
