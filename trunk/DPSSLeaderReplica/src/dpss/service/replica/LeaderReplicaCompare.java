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

		synchronized (reqList) {

			if (reqList.size() > 0) {

				//Analyzing the first entry in the FIFO Queue
				Request oldestReq = reqList.getFirst();

				//Returning the result to FE//
				if (oldestReq.getAllReplies()){				

					System.out.println("All results collected!");

					auxMessage = oldestReq.getVotedReply();			
					fESender.send(auxMessage);		

					//Informing RM about the issue//
					if (oldestReq.diffResponse > 0){				

						System.out.println("Informing RM about results mismatch on Replica:" + oldestReq.diffResponse);

						auxMessage = Integer.toString(oldestReq.diffResponse);
						rMSender.send(auxMessage);
					}

					//Delete analyzed request from FIFO Queue			
					reqList.removeFirst();
					System.out.println("First elemented removed");
				}

				else
					System.out.println("First element analyzied. Nothing to compare yet!");		
			}
		}

	}
}
