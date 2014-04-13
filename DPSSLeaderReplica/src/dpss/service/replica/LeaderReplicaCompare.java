package dpss.service.replica;

import java.util.LinkedList;

import dpss.model.Request;

public class LeaderReplicaCompare extends Thread {

	LinkedList<Request> reqList;
	LeaderReplicaFESender fESender;
	LeaderReplicaRMSender rMSender;
	
	public LeaderReplicaCompare(LinkedList<Request> reqListParam, LeaderReplicaFESender fESenderParam, LeaderReplicaRMSender rMSenderParam) {
		this.reqList = reqListParam;
		this.fESender = fESenderParam;
		this.rMSender = rMSenderParam;
	}
	
	public void run() {
		
		System.out.println("Compare function called!");
		
		String auxMessage=null;
		
		//Analyzing the first entry in the FIFO Queue
		Request oldestReq = reqList.getFirst();

		//Returning the result to FE//
		if (oldestReq.getAllReplies()){				
			
			System.out.println("All results collected!");
			
			auxMessage = oldestReq.getVotedReply();			
			fESender.send(auxMessage);		
		
			//Informing RM about the issue//
			if (oldestReq.diffResponse > 0){				
				
				System.out.println("Informing RM about results mismatch!");
				
				auxMessage = Integer.toString(oldestReq.diffResponse);
				rMSender.send(auxMessage);
			}
			
			//Delete analyzed request from FIFO Queue
			synchronized (reqList) {
				reqList.removeFirst();
			}
			
		}
		else
			System.out.println("Nothing to compare yet!");
		
	}
	
}
