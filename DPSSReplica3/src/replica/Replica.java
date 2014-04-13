package replica;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;



import system.SystemInterfaceImpl;



public class Replica extends Thread{
	
	static DatagramSocket socketReply=null;
	InetAddress hostR;
	SystemInterfaceImpl serverEU=null ;
	SystemInterfaceImpl serverNA=null ;
	SystemInterfaceImpl serverAS=null ;

		public static void main(String[] args) {
			
		System.out.println("Replica3 Running...");
			
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
	
	
	public Replica() {
		
		
				
		try {
			
			serverNA = new SystemInterfaceImpl();			
			serverEU= new SystemInterfaceImpl();								
			serverAS = new SystemInterfaceImpl();
			socketReply=new DatagramSocket(1013);
		
			hostR = InetAddress.getByName("localhost");	
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
			serverEU= new SystemInterfaceImpl();
			serverNA= new SystemInterfaceImpl();
			serverAS= new SystemInterfaceImpl();
			socketReply=new DatagramSocket(1013);
			
			//hostR = InetAddress.getByName("localhost");	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
			
	}

}
