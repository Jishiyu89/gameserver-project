package dpss.service.replica;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.LinkedList;

import dpss.model.Request;
import dpss.model.RequestType;
import dpss.service.WriteLog;

public class LeaderReplicaReplicasReceiver extends Thread {
	
	DatagramSocket socketR=null;
	int portR=1300;
	LinkedList<Request> reqList;
	LeaderReplicaCompare compareFIFO;
	WriteLog Logger = new WriteLog(); 	
	
	
	
	public LeaderReplicaReplicasReceiver(LinkedList<Request> reqListParam,LeaderReplicaCompare compareFIFOParam)	{
				
		try {
		
			this.reqList = reqListParam;
			this.compareFIFO = compareFIFOParam;
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
				
				System.out.println("received>>" +s );
				
				//1st position = SEQ FIFO
				seqFIFO = Integer.parseInt(requestInformation[0]);
				//2nd position = COD Replica
				codReplica = Integer.parseInt(requestInformation[1]);
				//3rd position = Reply from Replica 
				replyReplica = requestInformation[2];

				Request auxRequest = reqList.get(reqList.indexOf(new Request(seqFIFO)));	
				
				//GetPlayerStatus Unmarshalling
				if (auxRequest.type == RequestType.GetPlayerStatus && replyReplica.indexOf("#")>-1) {
					String[] arrReplyReplica=new String[3];
					arrReplyReplica = replyReplica.split("#");
					Arrays.sort(arrReplyReplica);
					replyReplica = arrReplyReplica[0]+"#"+arrReplyReplica[1]+"#"+arrReplyReplica[2];
					
				}				
				
				synchronized (auxRequest) {
					auxRequest.setStatus(codReplica, replyReplica);
				}	
				
				Logger.write("LeaderReplica", "Reply from Replica" + codReplica + " to request["+seqFIFO+"]:" + replyReplica);	
				
				//COMPARE AND REPLY		
				compareFIFO.run();
				//new Thread(compareFIFO).start();							
				
				
			}catch(Exception e){
				e.printStackTrace();				
				Logger.write("LeaderReplica", "Unexpected error receiving reply from Replicas!");		
			}
			
		
	}

	}
	
}
