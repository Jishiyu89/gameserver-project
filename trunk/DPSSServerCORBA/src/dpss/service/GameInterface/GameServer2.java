package dpss.service.GameInterface;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import GameInterface.GamePOA;

import dpss.model.Player;

public class GameServer2 extends GamePOA {
	
	private String serverName;
	
	
	@SuppressWarnings("rawtypes")
	private Hashtable hashPlayers;
	@SuppressWarnings("rawtypes")
	private Enumeration hashLetters;

	private WriteLog Logger = new WriteLog(); 	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public GameServer2 (String serverNameParam){
		 
		this.serverName = serverNameParam;
	
		hashPlayers = new Hashtable();

		for (char c = 'A'; c <= 'Z'; c++){
			hashPlayers.put(c, new ArrayList<Player>());
		}
		
		try{
				
			Logger.write(serverName,"Game server " + serverName +  " is up and running!");
			System.out.println("Game server " + serverName +  " is up and running!");	
						
		}catch(Exception e){
			e.printStackTrace();
		}		
	 }
	
	
	public String createPlayerAccount(String firstNameParam, String lastNameParam, String usernameParam, String passwordParam, int ageParam, String iPAdressParam) {
		
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

	@Override
	public String getPlayerStatus(String adminUsernameParam,
			String adminPasswordParam, String iPAdressParam) {
		// TODO Auto-generated method stub
		return "d";
	}


}
