package dpss.service.replica;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import dpss.service.GameServerFactory;
import dpss.service.WriteLog;

public class LeaderReplicaRMReceiver extends Thread {
	
	GameServerFactory gameServers;
	
	String message=null;
	DatagramPacket UDPMessage=null;
	DatagramSocket socketRM=null;
	int portRM=7000;
	WriteLog Logger = new WriteLog(); 	
	
	public LeaderReplicaRMReceiver(GameServerFactory gameServersParam){
		try {
			this.gameServers = gameServersParam;
			socketRM=new DatagramSocket(portRM);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void run(){
		
		byte[] bufferRequest=new byte[1000];
		DatagramPacket UDPRequest=new DatagramPacket(bufferRequest, bufferRequest.length);

		while(true){
			try {
				
				socketRM.receive(UDPRequest);
				String s=new String(UDPRequest.getData()).substring(0,UDPRequest.getLength());
				if (s.equals("Restart")){
					
					gameServers.serversReset();
					
					Logger.write("LeaderReplica", "Restart request from Replica Manager(RM) successfully executed!");					
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
	
		}
	}
}