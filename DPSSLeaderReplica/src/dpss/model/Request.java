package dpss.model;

public class Request {
	
	int seqNumber;
	String response1=null,response2=null,response3=null;
	RequestType type;
	public int diffResponse = 1;
	
	
	public Request(int seq,RequestType type){
		seqNumber=seq;
		this.type=type;
	}
	public Request(int seq){
		seqNumber=seq;
	}
	
	public void setStatus(int number,String s){
		switch(number){
		case 1:response1=s;break;
		case 2:response2=s;break;
		case 3:response3=s;break;
		}
	}
	
	public boolean getAllReplies(){
		
		if (response1!=null /*&& response2 != null && response3!=null*/)
			return true;
		else
			return false;
	}
	
	
	public String getVotedReply(){		
		
		return response1;
						
			/*if (response1.equals(response2)  && response1.equals(response3)){				
				diffResponse = -1;
				return response1;				
			}
			else if (response1.equals(response2) && !response1.equals(response3)){
				diffResponse = 3;
				return response1;
			}	
			else if (!response1.equals(response2) && response1.equals(response3)){
				diffResponse = 2;
				return response1;
			}
			else {
				diffResponse = 1;
				return response2;
			}		*/
			
	}
	@Override 
	public boolean equals(Object obj){
		final Request p=(Request) obj;
		if(this.seqNumber==p.seqNumber)
			return true;
		else return false;
	}
}
