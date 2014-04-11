package dpss.service.replica;

import java.util.LinkedList;

import dpss.model.Request;

public class LeaderReplicaCompare {

	LinkedList<Request> reqList;
	LeaderReplicaRMSender rMSender;
	LeaderReplicaFESender fESender;
	
	public LeaderReplicaCompare(LinkedList<Request> reqListParam){
		this.reqList = reqListParam;
	}
	
	public void compare() throws Exception {
		
		System.out.println("Compare function called!");
		
		String auxMessage=null;
		
		Request oldestReq = reqList.getFirst();

		//Returning the result to FE//
		if (oldestReq.getAllReplies()){				
			
			auxMessage = oldestReq.getVotedReply();			
			auxMessage = Integer.toString(oldestReq.diffResponse);
			fESender = new LeaderReplicaFESender(auxMessage);
			fESender.send();		
		
			//Informing RM about the issue//
			if (oldestReq.diffResponse > 0){
				
				auxMessage = Integer.toString(oldestReq.diffResponse);
				rMSender = new LeaderReplicaRMSender(auxMessage);
				rMSender.send();
			}
		}
		else
			System.out.println("Nothing to compare yet!");
		
	}
	
}
