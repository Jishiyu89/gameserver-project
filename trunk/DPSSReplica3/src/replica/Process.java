package replica;

import system.SystemInterfaceImpl;



public class Process  extends Replica implements Runnable  {
	
	String message;
	SystemInterfaceImpl gameServer=null;
	int idReplica = 3;
	
	Process(String m){
		message=m;
		
	}
public void run(){
		
		RequestType type;
		String reply=null;

		int seq=0;
		
			try {
				System.out.println(message);
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
						reply=seq+"->"+idReplica+"->"+gameServer.createPlayerAccount(requestInformation[2], requestInformation[3], requestInformation[4], requestInformation[5], requestInformation[6], requestInformation[7]);
					}
					
					System.out.println(reply);		
					Reply(reply);
					
					break;
				
				case PlayerSignIn:
					System.out.println("PlayerSignIn");
					
					gameServer=IPConvert(requestInformation[4]);
					if(gameServer!=null)
						reply=seq+"->"+idReplica+"->"+gameServer.playerSignIn(requestInformation[2], requestInformation[3], requestInformation[4]);
					System.out.println(reply);		
					Reply(reply);
					break;
				case PlayerSignOut:
					System.out.println("PlayerSignOut");

					gameServer=IPConvert(requestInformation[3]);
					if(gameServer!=null)
						reply=seq+"->"+idReplica+"->"+gameServer.playerSignOut(requestInformation[2], requestInformation[3]);
					System.out.println(reply);		
					Reply(reply);
					break;
				case TransferAccount:
					System.out.println("TransferAccount");

					gameServer=IPConvert(requestInformation[4]);
					if(gameServer!=null)
						reply=seq+"->"+idReplica+"->"+gameServer.transferAccount(requestInformation[2], requestInformation[3],requestInformation[4], requestInformation[5]);
					System.out.println(reply);		
					Reply(reply);
					break;
				case GetPlayerStatus:
					System.out.println("GetPlayerStatus");

					gameServer=IPConvert(requestInformation[4]);
					if(gameServer!=null)
						reply=seq+"->"+idReplica+"->"+gameServer.getPlayerStatus(requestInformation[2], requestInformation[3],requestInformation[4]);
					System.out.println(reply);		
					Reply(reply);
					break;
				case SuspendAccount:					
				
					System.out.println("SuspendAccount");

					gameServer=IPConvert(requestInformation[4]);
					if(gameServer!=null)
						reply=seq+"->"+idReplica+"->"+gameServer.suspendAccount(requestInformation[2], requestInformation[3],requestInformation[4],requestInformation[5]);
					System.out.println(reply);		
					Reply(reply);
					break;
					
				}
				
				
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		
	}

	
	
	private SystemInterfaceImpl IPConvert(String s){
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
					return serverNA;
			case 93:System.out.println("93 here!");
					return serverEU;
			default:System.out.println("wte here!");
					return serverAS;
			}
		}
		
	}

}
