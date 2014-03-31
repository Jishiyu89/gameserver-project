package dpss.model;

public class Request {
	int seqNumber;
	String response1=null,response2=null,response3=null;
	RequestType type;
	Request(int seq,RequestType type){
		seqNumber=seq;
		this.type=type;
	}
}
