package dpss.service;

/**  Specific class for listening and responding to UDP requests generated among other servers.
 *   Runs in a separate thread that is triggered by the object GameServerImpl.
     @class GameServerUDP   
 * */



import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;

import dpss.model.Player;

public class GameServerUDP  extends Thread {

	GameServerImpl gameServerSrc;
	DatagramSocket uDPSocket = null;
	DatagramPacket request;
	
	public GameServerUDP( DatagramSocket uDPSocketParam, GameServerImpl gameServerSrcParam){
		this.gameServerSrc = gameServerSrcParam;
		this.uDPSocket = uDPSocketParam;
	}
	
	public void run(){
		
		try{
			
						
			byte[] buffer = new byte[1000];
			String requestMsg;
			
			while(true){
				
				//Receiving Request 01 - Beginning of Protocol
				request = new DatagramPacket(buffer, buffer.length);
				uDPSocket.receive(request);
				requestMsg = new String(request.getData()).substring(0,request.getLength());
				
				if(requestMsg.equals("getPlayerStatus")) 
					 getPlayerStatusProtocol();	
				else if (requestMsg.equals("transferAccount"))
					transferAccountProtocol();								
			}
			
		}catch(SocketException e){ System.out.println("Socket" + e.getMessage());
		}catch(IOException e){ System.out.println("IO" + e.getMessage());
		}finally{ if(uDPSocket != null) uDPSocket.close();}
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void getPlayerStatusProtocol() throws SocketException, IOException{
		
		int playersOnline = 0;
		int playersOnServer = 0;
		Enumeration hashLetters = gameServerSrc.hashPlayers.keys();
		String response;
		
		while(hashLetters.hasMoreElements()) { 
			
			char auxHashIndex = (char)hashLetters.nextElement(); 
			ArrayList<Player> auxArrPlayer = (ArrayList<Player>) gameServerSrc.hashPlayers.get(auxHashIndex);
						
			Iterator<Player> itr = auxArrPlayer.iterator();
		    while (itr.hasNext()) {
		      Player auxPlayer =  itr.next();			      
		      if (auxPlayer.getStatus())
		    	  playersOnline++;			      		      
		    }			    
		    playersOnServer = playersOnServer + auxArrPlayer.size();		  			
		} 	
		
		response = "Server " + gameServerSrc.serverName + ":"+ "Total players:" + playersOnServer + " Online:" + playersOnline + " Offline: " + (playersOnServer-playersOnline); 
						
		DatagramPacket reply = new DatagramPacket(response.getBytes(),response.length(), request.getAddress(),request.getPort());
		uDPSocket.send(reply);		
		
	}
	
	private void transferAccountProtocol() throws SocketException, IOException{
		
		String requestMsg;
		String response = "OK";
		String[] playerData = new String[6];
		byte[] buffer = new byte[1000];
		DatagramPacket reply;

		//Sending Reply 01
		reply = new DatagramPacket(response.getBytes(),response.length(), request.getAddress(),request.getPort());
		uDPSocket.send(reply);		
		
		//Receiving Request 02 - Player's data -> Unmarshaling
		request = new DatagramPacket(buffer, buffer.length);
		uDPSocket.receive(request);
		requestMsg = new String(request.getData()).substring(0,request.getLength());
		playerData = requestMsg.split(";");		
		
		response = gameServerSrc.createPlayerAccountViaUDP(playerData[0], playerData[1], playerData[2], playerData[3],playerData[4],playerData[5]);

		//Sending Reply 02 (OK, NOK)
		reply = new DatagramPacket(response.getBytes(),response.length(), request.getAddress(),request.getPort());
		uDPSocket.send(reply);				
		
	}
	
}
