package GameServer;



public class Process  extends Replica implements Runnable  {
	String message;
	ServerImpl gameServer=null;
	Process(String m){
		message=m;
		
	}
public void run(){
		
//		byte[] bufferRequest=new byte[1000];
//		DatagramPacket UDPRequest=new DatagramPacket(bufferRequest, bufferRequest.length);
//		
//		DatagramPacket UDPReply=null;
		RequestType type;
		String reply=null;

		int seq=0;
		
			try {
				
				String[] requestInformation=new String[10];
				requestInformation=message.split("->");
				seq=Integer.parseInt(requestInformation[0]);
				type=RequestType.valueOf(requestInformation[1]);
				switch(type){
				case CreatePlayerAccount:
					System.out.println("CreatPlayerAccount");
					
					gameServer=IPConvert(requestInformation[7]);
					if(gameServer!=null)
					{
						reply=seq+"->"+2+"->"+gameServer.createPlayerAccount(requestInformation[2], requestInformation[3], requestInformation[4], requestInformation[5], Short.parseShort(requestInformation[6]), requestInformation[7]);
					}
					
					System.out.println(reply);		
					Reply(reply);
					//UDPReply=new DatagramPacket(reply.getBytes(),reply.length(),UDPRequest.getAddress(),9000);
					break;
				
				case PlayerSignIn:
					System.out.println("PlayerSignIn");
					
					gameServer=IPConvert(requestInformation[4]);
					if(gameServer!=null)
						reply=seq+"->"+2+"->"+gameServer.playerSignIn(requestInformation[2], requestInformation[3], requestInformation[4]);
					System.out.println(reply);		
					//UDPReply=new DatagramPacket(reply.getBytes(),reply.length(),UDPRequest.getAddress(),9000);
					Reply(reply);
					break;
				case PlayerSignOut:
					System.out.println("PlayerSignOut");

					gameServer=IPConvert(requestInformation[3]);
					if(gameServer!=null)
						reply=seq+"->"+2+"->"+gameServer.playerSignOut(requestInformation[2], requestInformation[3]);
					System.out.println(reply);		
					//UDPReply=new DatagramPacket(reply.getBytes(),reply.length(),UDPRequest.getAddress(),9000);
					Reply(reply);
					break;
				case TransferAccount:
					System.out.println("TransferAccount");

					gameServer=IPConvert(requestInformation[4]);
					if(gameServer!=null)
						reply=seq+"->"+2+"->"+gameServer.transferAccount(requestInformation[2], requestInformation[3],requestInformation[4], requestInformation[5]);
					System.out.println(reply);		
					//UDPReply=new DatagramPacket(reply.getBytes(),reply.length(),UDPRequest.getAddress(),9000);
					Reply(reply);
					break;
				case GetPlayerStatus:
					System.out.println("GetPlayerStatus");

					gameServer=IPConvert(requestInformation[4]);
					if(gameServer!=null)
						reply=seq+"->"+2+"->"+gameServer.getPlayerStatus(requestInformation[2], requestInformation[3],requestInformation[4]);
					System.out.println(reply);		
					//UDPReply=new DatagramPacket(reply.getBytes(),reply.length(),UDPRequest.getAddress(),9000);
					Reply(reply);
					break;
				case SuspendAccount:					
					//UDPReply=new DatagramPacket("SuspendAccount".getBytes(),"CreatPlayerAccount".length(),hostLR,9000);
				
					System.out.println("SuspendAccount");

					gameServer=IPConvert(requestInformation[4]);
					if(gameServer!=null)
						reply=seq+"->"+2+"->"+gameServer.suspendAccount(requestInformation[2], requestInformation[3],requestInformation[4],requestInformation[5]);
					System.out.println(reply);		
					//UDPReply=new DatagramPacket(reply.getBytes(),reply.length(),UDPRequest.getAddress(),9000);
					Reply(reply);
					break;
					
				}
				
				
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		
	}

	
	
	private ServerImpl IPConvert(String s){
		String[] IP=s.split("\\.");
		System.out.println(s);
		System.out.println(IP[0]);
		if (IP[0]==null) {
			System.out.println("null>");
			return null;
		}		
		else{ 
			switch(Integer.parseInt(IP[0])){
			case 132:System.out.println("132 here!");			 
					return super.serverNA;
			case 93:System.out.println("93 here!");
					return super.serverEU;
			default:System.out.println("wte here!");
					return super.serverAS;
			}
		}
		
	}

}
