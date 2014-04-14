package replica;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import system.SystemInterfaceImpl;



public class Replica extends Thread{
	
	static DatagramSocket socketReply=null;
	InetAddress hostR;
	Thread thEU,thNA,thAS;
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
					
			//Replica 3 
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
			
			serverNA = new SystemInterfaceImpl(5051);
			thNA=new Thread(serverNA);
			thNA.start();
			serverEU= new SystemInterfaceImpl(5052);
			thEU=new Thread(serverEU);
			thEU.start();
			serverAS = new SystemInterfaceImpl(5053);
			thAS=new Thread(serverAS);
			thAS.start();
			socketReply=new DatagramSocket(7103);
		
			hostR = InetAddress.getByName("localhost");	
		
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
	}
	
	public void restart(){
		socketReply.close();
		socketReply=null;
		thNA.interrupt();
		serverNA.stop();
		serverNA=null;
		thEU.interrupt();
		serverEU.stop();
		
		serverEU=null;
		thAS.interrupt();
		serverAS.stop();
		serverAS=null;
		
		try {
			System.out.println("Replica 3 restarts!");
			serverEU= new SystemInterfaceImpl(5052);
			thEU=new Thread(serverEU);
			thEU.start();
			serverNA= new SystemInterfaceImpl(5051);
			thNA=new Thread(serverNA);
			thNA.start();
			serverAS= new SystemInterfaceImpl(5053);	
			thAS=new Thread(serverAS);
			thAS.start();
			socketReply=new DatagramSocket(7103);

			
		} catch (Exception e) {			
			e.printStackTrace();
		}	
	}

}
