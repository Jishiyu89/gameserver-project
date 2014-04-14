package replica;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;




import system.SystemInterfaceImpl;



public class Process implements Runnable  {
	String message;
	SystemInterfaceImpl gameServer=null;
	SystemInterfaceImpl sNA=null,sEU=null,sAS=null;
	DatagramSocket s;
	InetAddress hostR;
	int idReplica = 3;
	
	Process(DatagramSocket s,SystemInterfaceImpl NA,SystemInterfaceImpl EU,SystemInterfaceImpl AS,String m){
		message=m;
		this.sNA=NA;
		this.sEU=EU;
		this.sAS=AS;
		this.s=s;
		if(s==null)
			System.out.println("NULL");
		try {
			hostR = InetAddress.getByName("localhost");
		} catch (UnknownHostException e) {
			
			e.printStackTrace();
		}	
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
		case 132:		 
				return sNA;
		case 93:
				return sEU;
		default:
				return sAS;
		}
	}
	
}
public boolean Reply(String str){
	
	DatagramPacket message = new DatagramPacket(str.getBytes(), str.length(),hostR,1300);
	
	synchronized(s){
		try {
			s.send(message);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	return true;
}

}
