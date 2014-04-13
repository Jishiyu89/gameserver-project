package GameServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;




public class Replica extends Thread {
	DatagramSocket socketReply=null;
	InetAddress hostR;
	ServerImpl serverEU=null ;
	ServerImpl serverNA=null ;
	ServerImpl serverAS=null ;

		public static void main(String[] args) {
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
			Replica R = new Replica();	
			R.start();
			while(true){
							
						DatagramPacket message = new DatagramPacket(buf, buf.length);
						s.receive(message);
						String str=new String(message.getData()).substring(0,message.getLength());
						new Thread(new Process(R.socketReply,R.serverNA,R.serverEU,R.serverAS,str)).start();
						
			}
			
			
		} catch (Exception e) {			
			e.printStackTrace();
			s.close();
		}
	}
	
	public void run(){
		byte[] bufferReply=new byte[1000];
		DatagramPacket messageRM=new DatagramPacket(bufferReply, bufferReply.length, hostR,7000);
		while(true){
			try {
				socketReply.receive(messageRM);
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			String strRM=new String(messageRM.getData()).substring(0, messageRM.getLength());
			if(strRM.equals("Restart"))
				restart();
		}
	}
	public Replica() {
		
		
		System.out.println("factory created");		
		try {
			serverEU= new ServerImpl("EU",1000);
			serverNA= new ServerImpl("NA",2000);
			serverAS= new ServerImpl("AS",3000);	
			socketReply=new DatagramSocket(1012);
		
			hostR = InetAddress.getByName("localhost");	
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
	
	
	public void restart(){
		socketReply.close();
		socketReply=null;
		//serverNA.finalize();
		serverNA=null;
		//serverEU.finalize();
		serverEU=null;
		//serverAS.finalize();
		serverAS=null;
		try {
			System.out.println("Replica 2 restarts!");
			serverEU= new ServerImpl("EU",1000);
			serverNA= new ServerImpl("NA",2000);
			serverAS= new ServerImpl("AS",3000);
			socketReply=new DatagramSocket(1012);
			
			//hostR = InetAddress.getByName("localhost");	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
			
	}

}
