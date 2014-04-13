package replica;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.LinkedList;

import system.SystemInterfaceImpl;



public class Replica {
	
	static DatagramSocket socketReply=null;
	InetAddress hostR;
	static SystemInterfaceImpl serverEU=null ;
	static SystemInterfaceImpl serverNA=null ;
	static SystemInterfaceImpl serverAS=null ;

		public static void main(String[] args) {
			
		System.out.println("Replica3 Running...");
			
		//DatagramSocket socketA=null;	
		int mPort = 6789;
		InetAddress ipGroup = null;
		MulticastSocket s=null;

		//Receiving
		byte[] buf = new byte[1000];
		try {
			//Group members
			ipGroup = InetAddress.getByName("228.6.3.21");
					
			//Replica 2 
			s = new MulticastSocket(mPort);
			s.joinGroup(ipGroup);
			//Replica R = new Replica();	
			//R.start();
			while(true){
							
						DatagramPacket message = new DatagramPacket(buf, buf.length);
						s.receive(message);
						String str=new String(message.getData()).substring(0,message.getLength());
						new Thread(new Process(str)).start();
						
			}
			
			
		} catch (Exception e) {			
			e.printStackTrace();
			s.close();
		}
	}
	
	
	public Replica() {
		
		
				
		try {
			
			serverNA = new SystemInterfaceImpl();			
			serverEU= new SystemInterfaceImpl();								
			serverAS = new SystemInterfaceImpl();
			socketReply=new DatagramSocket(1012);
		
		hostR = InetAddress.getByName("localhost");	
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public boolean Reply(String s){
		
		DatagramPacket message = new DatagramPacket(s.getBytes(), s.length(),hostR,1300);
		synchronized(socketReply){
			try {
				socketReply.send(message);
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		
		return true;
	}
	
	public void finalize(){
		socketReply.close();
		socketReply=null;
		//serverNA.finalize();
		serverNA=null;
		//serverEU.finalize();
		serverEU=null;
		//serverAS.finalize();
			
	}

}