package dpss.service;

/**  Implementation of the Game Server which contains the code of available features.
 *   Main class on Server side. It represents the server itself that holds the database of users and responds to players and admin requests.
 * 	 In addition to methods specified in the JAVA IDL interface (GameInterface.idl), other private methods used in internal  operations can be found.   
 *   @class GameServerImpl   
 * */

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

import org.omg.CORBA.ORB;
import dpss.model.Player;
import dpss.service.corba.GameServerPOA;

public class GameServerImpl extends GameServerPOA {
	
	ORB orb; 
	String serverName;
	int portUDP;		
	@SuppressWarnings("rawtypes")
	Hashtable hashPlayers;
	@SuppressWarnings("rawtypes")
	Enumeration hashLetters;
	WriteLog Logger = new WriteLog(); 		
	
	public void setORB(ORB orb_val) { 
		 this.orb = orb_val; 
	 }	 
		 
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public GameServerImpl (String serverNameParam, int portUDPParam){
		 
		this.serverName = serverNameParam;
		this.portUDP = portUDPParam;
	
		hashPlayers = new Hashtable();

		for (char c = 'A'; c <= 'Z'; c++){
			hashPlayers.put(c, new ArrayList<Player>());
		}
		
		try{
			exportServerUDP();
			Logger.write(serverName,"Game server " + serverName +  " is up and running!");
			System.out.println("Game server " + serverName +  " is up and running!");	
						
		}catch(Exception e){
			e.printStackTrace();
		}		
	 }
		
	public void exportServerUDP(){		
		//(new GameServerUDP(serverName,portUDP,hashPlayers)).start();	
		(new GameServerUDP(this)).start();
	}
	
	@SuppressWarnings("unchecked")
	public String createPlayerAccount(String firstNameParam, String lastNameParam, String usernameParam, String passwordParam, int ageParam, String iPAdressParam) {
		
			System.out.println("entrei!");
		
			char auxHashIndex = ((usernameParam.trim()).toUpperCase()).charAt(0);
			ArrayList<Player> auxArrPlayer = (ArrayList<Player>) hashPlayers.get(auxHashIndex);
			
			if (auxArrPlayer.contains(new Player(usernameParam))){			
				
				Logger.write(serverName,"Username ["+ usernameParam + "] already exists on game server " + serverName + "!");
				return "Username ["+ usernameParam + "] already exists on game server " + serverName + "!";			
			}
			else{			
			
				try {
					
					synchronized (auxArrPlayer) {
						auxArrPlayer.add(new Player(firstNameParam, lastNameParam, usernameParam, ageParam, passwordParam, iPAdressParam));
					}							
					
					Logger.write(serverName, "Username ["+ usernameParam + "] sucessfuly created on game server " + serverName + "!");
					return "Username ["+ usernameParam + "] sucessfuly created on game server " + serverName + "!";	
					
				}catch(Exception e){
					Logger.write(serverName, "Unexpected server error on game server " + serverName + "!");
					return "Unexpected server error on game server " + serverName + "!";	
				}						
			}			
	}
	
	@SuppressWarnings("unchecked")
	public String createPlayerAccountViaUDP(String firstNameParam, String lastNameParam, String usernameParam, String passwordParam, String ageParam, String iPAdressParam) {
		
			char auxHashIndex = ((usernameParam.trim()).toUpperCase()).charAt(0);
			ArrayList<Player> auxArrPlayer = (ArrayList<Player>) hashPlayers.get(auxHashIndex);
			
			if (auxArrPlayer.contains(new Player(usernameParam))){			
				
				Logger.write(serverName,"Username ["+ usernameParam + "] already exists on game server " + serverName + "!");
				return "NOK";			
			}
			else{			
			
				try {
					
					synchronized (auxArrPlayer) {
						auxArrPlayer.add(new Player(firstNameParam, lastNameParam, usernameParam, Integer.parseInt(ageParam), passwordParam, iPAdressParam));
					}							
					
					Logger.write(serverName, "Username ["+ usernameParam + "] sucessfuly created on game server " + serverName + "!");
					return "OK";	
					
				}catch(Exception e){
					Logger.write(serverName, "Unexpected server error on game server " + serverName + "!");
					return "NOK";	
				}						
			}			
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String playerSignIn(String usernameParam, String passwordParam, String iPAdressParam){
			
		char auxHashIndex = ((usernameParam.trim()).toUpperCase()).charAt(0);
		ArrayList<Player> auxArrPlayer = (ArrayList<Player>) hashPlayers.get(auxHashIndex);
		
		
		if (!auxArrPlayer.contains(new Player(usernameParam))){		

			Logger.write(serverName,"Username ["+ usernameParam + "] does not exists on game server " + serverName + "!");
			return "Username ["+ usernameParam + "] does not exists on game server " + serverName + "!";			
		}
		else		
		{
			int auxArrIndex = auxArrPlayer.indexOf(new Player(usernameParam));
			Player auxPlayer = auxArrPlayer.get(auxArrIndex);
			
			if (!auxPlayer.validatePWD(passwordParam)){
				Logger.write(serverName,"Password for username ["+ usernameParam + "] is incorrect on game server " + serverName + "!");
				return "Password for username ["+ usernameParam + "] is incorrect on game server " + serverName + "!";
			}
			else if (auxPlayer.getStatus()){
				Logger.write(serverName,"User ["+ usernameParam + "] is already online on game server " + serverName + "!");
				return "User ["+ usernameParam + "] is already online on game server " + serverName + "!";
			}
			else {

				try {
					
					synchronized (auxPlayer) {
						auxPlayer.setStatus(true);
					}	
					
					Logger.write(serverName, "Username ["+ usernameParam + "] sucessfuly signed in on game server " + serverName + "!");
					return "Username ["+ usernameParam + "] sucessfuly signed in on game server " + serverName + "!";	
					
				}catch(Exception e){
					Logger.write(serverName, "Unexpected server error on game server " + serverName + "!");
					return "Unexpected server error on game server " + serverName + "!";	
				}			
			}			
		}	
	}

	@SuppressWarnings("unchecked")
	@Override
	public String playerSignOut(String usernameParam, String iPAdressParam) {
		
		char auxHashIndex = ((usernameParam.trim()).toUpperCase()).charAt(0);
		ArrayList<Player> auxArrPlayer = (ArrayList<Player>) hashPlayers.get(auxHashIndex);
		
		
		if (!auxArrPlayer.contains(new Player(usernameParam))){			

			Logger.write(serverName,"Username ["+ usernameParam + "] does not exist on game server " + serverName + "!");
			return "Username ["+ usernameParam + "] does not exist on game server " + serverName + "!";			
		}
		else		
		{
			int auxArrIndex = auxArrPlayer.indexOf(new Player(usernameParam));
			Player auxPlayer = auxArrPlayer.get(auxArrIndex);
		
			if (!auxPlayer.getStatus()){
				Logger.write(serverName,"User ["+ usernameParam + "] is not online on game server " + serverName + "!");
				return "User ["+ usernameParam + "] is not online on game server " + serverName + "!";
			}
			else {
				
				try {
					
					synchronized (auxPlayer) {
						auxPlayer.setStatus(false);					
					}	
									
					Logger.write(serverName, "Username ["+ usernameParam + "] sucessfuly signed out on game server " + serverName + "!");
					return "Username ["+ usernameParam + "] sucessfuly signed out on game server " + serverName + "!";	
					
				}catch(Exception e){
					Logger.write(serverName, "Unexpected server error on game server " + serverName + "!");
					return "Unexpected server error on game server " + serverName + "!";	
				}			
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public String transferAccount(String usernameParam, String passwordParam, String oldIPAddressParam, String newIPAddressParam) {

		char auxHashIndex = ((usernameParam.trim()).toUpperCase()).charAt(0);
		ArrayList<Player> auxArrPlayer = (ArrayList<Player>) hashPlayers.get(auxHashIndex);
		
		if (!auxArrPlayer.contains(new Player(usernameParam))){		

			Logger.write(serverName,"Username ["+ usernameParam + "] does not exists on game server " + serverName + "!");
			return "Username ["+ usernameParam + "] does not exists on game server " + serverName + "!";			
		}
		else		
		{
			int auxArrIndex = auxArrPlayer.indexOf(new Player(usernameParam));
			Player auxPlayer = auxArrPlayer.get(auxArrIndex);
			
			if (!auxPlayer.validatePWD(passwordParam)){
				Logger.write(serverName,"Password for username ["+ usernameParam + "] is incorrect on game server " + serverName + "!");
				return "Password for username ["+ usernameParam + "] is incorrect on game server " + serverName + "!";
			}
			else {

				try {
					
					synchronized (auxArrPlayer) {
						auxPlayer.setIPAdress(newIPAddressParam);
						if (accountTransmission(auxPlayer, oldIPAddressParam, newIPAddressParam)){
							auxArrPlayer.remove(auxArrIndex);	
						}
						else { 
							auxPlayer.setIPAdress(oldIPAddressParam);
							throw new Exception();							
						}
					}
					
					Logger.write(serverName, "Username ["+ usernameParam + "] sucessfuly transferred from game server " + serverName + "!");
					return "Username ["+ usernameParam + "] sucessfuly transferred from game server " + serverName + "!";	
					
				}catch(Exception e){
					
					Logger.write(serverName, "Unexpected server error on game server " + serverName + "!");
					return "Unexpected server error on game server " + serverName + "!";	
				}			
			}			
		}
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public String getPlayerStatus(String adminUsernameParam, String adminPasswordParam, String iPAdressParam)  {
	
		int playersOnline = 0;
		int playersOnServer = 0;
		String responseFromOtherServers;
		
		if (adminUsernameParam.equals("Admin") && adminPasswordParam.equals("Admin")) {
		
			hashLetters = hashPlayers.keys();
			
			while(hashLetters.hasMoreElements()) { 
				
				char auxHashIndex = (char)hashLetters.nextElement(); 
				ArrayList<Player> auxArrPlayer = (ArrayList<Player>) hashPlayers.get(auxHashIndex);
							
				Iterator<Player> itr = auxArrPlayer.iterator();
			    while (itr.hasNext()) {
			      Player auxPlayer =  itr.next();			      
			      if (auxPlayer.getStatus())
			    	  playersOnline++;			      		      
			    }			    
			    playersOnServer = playersOnServer + auxArrPlayer.size();		  			
			} 		
			
			
			Logger.write(serverName, "Players status requested by Administator user!");
			
			responseFromOtherServers = getCountsFromOtherServers();
			
			Logger.write(serverName, "Result returned to Administator user:" + "Server " + serverName + ":"+ "Total players:" + playersOnServer + " Online:" + playersOnline + " Offline: " + (playersOnServer-playersOnline) + " * " + responseFromOtherServers);
			return "Server " + serverName + ":"+ "Total players:" + playersOnServer + " Online:" + playersOnline + " Offline: " + (playersOnServer-playersOnline) + " * " + responseFromOtherServers;
		}
		else {
			Logger.write(serverName, "Invalid Administator user name or password on game server " + serverName + "!");
			return "Invalid Administator user name or password on game server " + serverName + "!";
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public String suspendAccount(String adminUsernameParam, String adminPasswordParam, String iPAdressParam, String usernameSuspendParam) {

		char auxHashIndex;
		ArrayList<Player> auxArrPlayer;
		
		if (adminUsernameParam.equals("Admin") && adminPasswordParam.equals("Admin")) {			
		
			auxHashIndex = ((usernameSuspendParam.trim()).toUpperCase()).charAt(0);
			auxArrPlayer = (ArrayList<Player>) hashPlayers.get(auxHashIndex);		
			
			if (!auxArrPlayer.contains(new Player(usernameSuspendParam))){			
	
				Logger.write(serverName,"Username ["+ usernameSuspendParam + "] does not exist on game server " + serverName + " and could not be removed!");
				return "Username ["+ usernameSuspendParam + "] does not exist on game server " + serverName + " and could not be removed!";			
			}
			else		
			{
				int auxArrIndex = auxArrPlayer.indexOf(new Player(usernameSuspendParam));
			
				try {
					
					synchronized (auxArrPlayer) {
						auxArrPlayer.remove(auxArrIndex);						
					}	
									
					Logger.write(serverName, "Username ["+ usernameSuspendParam + "] successfully removed from game server " + serverName + "!");
					return "Username ["+ usernameSuspendParam + "] successfully removed from game server " + serverName + "!";	
					
				}catch(Exception e){
					Logger.write(serverName, "Unexpected server error on game server " + serverName + "!");
					return "Unexpected server error on game server " + serverName + "!";	
				}						
			}
		}
		else {
			Logger.write(serverName, "Invalid Administator user name or password on game server " + serverName + "!");
			return "Invalid Administator user name or password on game server " + serverName + "!";
		}			
		
	}	
	
	private String getCountsFromOtherServers() {
		
			int partner1UDP = 0;
			int partner2UDP = 0;
			String partner1Reponse = "";
			String partner2Reponse = "";
			byte[] buffer = new byte[1000];
			
			DatagramSocket uDPSocket = null;
			try{
				uDPSocket = new DatagramSocket();
				byte [] m = "getPlayerStatus".getBytes();
				InetAddress aHost = InetAddress.getByName("localhost");
				
				switch(this.portUDP){
				case 1500: partner1UDP = 2500; /* EU */
						   partner2UDP = 3500; /* AS */
						   break;
				case 2500: partner1UDP = 1500; /* NA */
						   partner2UDP = 3500; /* AS */
						   break;
				case 3500: partner1UDP = 1500; /* NA */
				   		   partner2UDP = 2500; /* EU */
				   		   break;		   
				}
									
				DatagramPacket request1 = new DatagramPacket(m,m.length,aHost, partner1UDP);
				DatagramPacket request2 = new DatagramPacket(m,m.length,aHost, partner2UDP);
				
				//Sending Request 01 - Beginning of Protocol
				uDPSocket.send(request1);
				uDPSocket.send(request2);				
				
				DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
				uDPSocket.receive(reply);
				partner1Reponse = new String(reply.getData()).substring(0,reply.getLength());
				uDPSocket.receive(reply);
				partner2Reponse = new String(reply.getData()).substring(0,reply.getLength());								
				
			}catch(SocketException e){ System.out.println("Socket" + e.getMessage());
			}catch(IOException e){ System.out.println("IO" + e.getMessage());
			}finally{ if(uDPSocket != null) uDPSocket.close();}
			
			return (partner1Reponse + " * " + partner2Reponse);				
	}
	
	private boolean accountTransmission(Player auxPlayerParam, String oldIPAddressParam, String newIPAddressParam) {

		int partnerPortUDP = 0;		
		int partnerIPPrefix = 0;
		String partnerReponse = "";
		String[] partnerIP =new String[4];
		byte[] buffer = new byte[1000];
		
		partnerIP=newIPAddressParam.split("\\.");		
		partnerIPPrefix = Integer.parseInt(partnerIP[0]);
		
		DatagramSocket uDPSocket = null;
		try{
			uDPSocket = new DatagramSocket();
			byte [] m = "transferAccount".getBytes();
			InetAddress aHost = InetAddress.getByName("localhost");
			
			switch(partnerIPPrefix){
			case 132: partnerPortUDP = 1500; /* NA */
					  break;
			case 93:  partnerPortUDP = 2500; /* EU */
					  break;
			case 182: partnerPortUDP = 3500; /* AS */
			   		  break;		   
			}
			
			//Sending Request 01 - Beginning of Protocol
			DatagramPacket request = new DatagramPacket(m,m.length,aHost, partnerPortUDP);			
			uDPSocket.send(request);			
			
			//Receiving reply 01
			DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
			uDPSocket.receive(reply);
			
			//Sending Player's data embedded in the UDP message > Marshalling
			m = auxPlayerParam.getCSV().getBytes();		
			request = new DatagramPacket(m,m.length,aHost, partnerPortUDP);	
			uDPSocket.send(request);	
			
			//Receiving Reply 02
			reply = new DatagramPacket(buffer, buffer.length);
			uDPSocket.receive(reply);
			partnerReponse = new String(reply.getData()).substring(0,reply.getLength());

			if (partnerReponse.equals("OK")){
				return true;
			}
			else return false;			
			
		}catch(SocketException e){ System.out.println("Socket" + e.getMessage());		}
		catch(IOException e){ System.out.println("IO" + e.getMessage());		}
		finally{ if(uDPSocket != null) uDPSocket.close();}
				
		return false;
	}	
}
