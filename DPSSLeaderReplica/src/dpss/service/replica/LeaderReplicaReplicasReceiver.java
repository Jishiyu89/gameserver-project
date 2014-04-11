package dpss.service.replica;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.LinkedList;

import dpss.model.Request;
import dpss.service.WriteLog;

public class LeaderReplicaReplicasReceiver extends Thread {
	
	DatagramSocket socketR=null;
	int portR=1300;
	LinkedList<Request> reqList;
	WriteLog Logger = new WriteLog(); 	
	LeaderReplicaCompare compareFIFO;
	
	public LeaderReplicaReplicasReceiver(LinkedList<Request> reqListParam)	{
				
		try {
			
			this.reqList = reqListParam;
			socketR=new DatagramSocket(portR);
		} 
		
		catch (SocketException e) {			
			e.printStackTrace();
		}		
	}
	
	public void run(){
		
		byte[] bufferRequest=new byte[1000];
		DatagramPacket UDPRequest=new DatagramPacket(bufferRequest, bufferRequest.length);

		int seqFIFO;
		int codReplica;
		String replyReplica;
					
		while(true){
			try {
				
				socketR.receive(UDPRequest);
				String[] requestInformation=new String[3];
				String s=new String(UDPRequest.getData()).substring(0,UDPRequest.getLength());
				requestInformation=s.split("->");
				
				//1st position = SEQ FIFO
				seqFIFO = Integer.parseInt(requestInformation[0]);
				//2nd position = COD Replica
				codReplica = Integer.parseInt(requestInformation[1]);
				//3rd position = Reply from Replica 
				replyReplica = requestInformation[2];
						
				Request auxRequest = reqList.get(reqList.indexOf(new Request(seqFIFO)));	
				
				synchronized (auxRequest) {
					auxRequest.setStatus(codReplica, replyReplica);
				}	
				
				//COMPARE AN REPLY
				compareFIFO = new LeaderReplicaCompare(reqList);
				compareFIFO.compare();
				
				Logger.write("LeaderReplica", "Reply from Replica" + codReplica + " to request["+seqFIFO+"]:" + replyReplica);				
				
				
			}catch(Exception e){
				Logger.write("LeaderReplica", "Unexpected error receiving reply from Replicas!");		
			}
			
		
	}

	}
	
}
