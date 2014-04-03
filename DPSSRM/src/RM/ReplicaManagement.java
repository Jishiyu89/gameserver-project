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

public class ReplicaManagement extends Thread{
	DatagramSocket socket=null;
	int count1,count2,count3;
	
	ReplicaManagement()
	{ 
		try {
			DatagramSocket socket=new DatagramSocket(7000);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	public void run(){
		byte[] buffer = new byte[1000];
		byte[] bufferReply = new byte[1000];
		DatagramPacket receive = new DatagramPacket(buffer, buffer.length);
		DatagramPacket controlor=null;
		try {
			 while(true){
				 synchronized(this){
					 socket.receive(receive);
					 String correctness=new String(receive.getData()).substring(0,receive.getLength());
					 if (correctness.equals("1") )
						 count1++;
					 else if (correctness.equals("2") )
						 count2++;
					 else if (correctness.equals("3") )
						 count3++;
					 bufferReply="Restart".getBytes();
					 if (count1>2)
					 {
						 controlor=new DatagramPacket(bufferReply,bufferReply.length, receive.getAddress(),receive.getPort());
						 count1=0;
					 }
					 else if (count2>2)
					 {
						 controlor=new DatagramPacket(bufferReply,bufferReply.length, receive.getAddress(),receive.getPort());
						 count2=0;
					 }
					 else if (count3>2)
					 {
						 controlor=new DatagramPacket(bufferReply,bufferReply.length, receive.getAddress(),receive.getPort());
						 count3=0;
					 }
				 }
			 }
			
		} catch (IOException e) {
			e.printStackTrace();
		};
	}
	public void main(String arg[]){
		ReplicaManagement RM=new ReplicaManagement();
		RM.start();
	}
}
