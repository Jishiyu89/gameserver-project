package dpss.model;

public class Request {
	int seqNumber;
	String response1=null,response2=null,response3=null;
	RequestType type;
	public Request(int seq,RequestType type){
		seqNumber=seq;
		this.type=type;
	}
	public void setStatus(int number,String s){
		switch(number){
		case 1:response1=s;break;
		case 2:response2=s;break;
		case 3:response3=s;break;
		}
	}
}
