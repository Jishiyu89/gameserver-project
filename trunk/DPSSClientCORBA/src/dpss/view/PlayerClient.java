package dpss.view;
 
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.Scanner;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

 
import dpss.service.WriteLog;
import dpss.service.corba.GameServer;
import dpss.service.corba.GameServerHelper;

/**  Players point of access (Client side)
 *   It has the menu that collects user's inputs and calls the operations on the game server. 
 *   Every new instance of the playerClient object generates a random virtual IP that redirects the CORBA calls to the server it belongs to.
  *  @class PlayerClient
 *   
 * */

public class PlayerClient {
	
	String playerFirstName;
	String playerLastName;
	String playerUserName;
	String playerAge;
	String playerPwd;
	String playerIP;
	String playerNewIP;
	String playerServer;
	String playerServerName;
	String[] playerIPRange = {"132.xxx.xxx.xxx","93.xxx.xxx.xxx","182.xxx.xxx.xxx"};
	String[] playerServerRange = {"rmi://localhost:1000/gameserver","rmi://localhost:2000/gameserver","rmi://localhost:3000/gameserver"};
	String[] playerServerNameRange = {"NA","EU","AS"};
	WriteLog Logger = new WriteLog(); 
	GameServer gameServer;
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {		
		PlayerClient client =  new PlayerClient();		
	}
	
	public PlayerClient(){ 
		
		selectRandomIP();
		connectToServer();
		showMenu();		
		
		try{
		
		int userChoice=0;
		Scanner keyboard = new Scanner(System.in);
		
		while(true)
		{
			Boolean valid = false;
			
			// Enforces a valid integer input.
			while(!valid)
			{
				try{
					userChoice=keyboard.nextInt();
					valid=true;
				}
				catch(Exception e)
				{
					System.out.println("Invalid Input, please enter an Integer");
					valid=false;
					keyboard.nextLine();
				}
			}
			
			
			// Select an action based on user input from menu
			switch(userChoice)
			{
			case 1: 
				createPlayerMenu();
				if(validateCreatePlayer()){
					createPlayer();
				}	
				showMenu();
				break;
			case 2:
				signInPlayerMenu();
				if(validateSignInPlayer()){
					signInPlayer();
				}	
				showMenu();
				break;
			case 3:
				signOutPlayerMenu();
				if(validateSignOutPlayer()){
					signOutPlayer();
				}					
				showMenu();
				break;
			case 4:
				transferAccountMenu();
				if(validateTransferAccount()){
					transferAccount();
				}					
				showMenu();	
				break;
			case 5:
				System.out.println("Quitting the application...");
				keyboard.close();
				System.exit(0);
			default:
				System.out.println("Invalid Input, please try again.");
			}
		}
		
	} catch (Exception e){
		e.printStackTrace();
	}
}

	public PlayerClient(String playerFirstNameParam,String playerLastNameParam,String playerUserNameParam,String playerPwdParam,String playerAgeParam){
		this.playerFirstName = playerFirstNameParam;
		this.playerLastName = playerLastNameParam;
		this.playerUserName = playerUserNameParam;
		this.playerPwd = playerPwdParam;
		this.playerAge = playerAgeParam;
		selectRandomIP();
		connectToServer();
	}	

	public void showMenu()
	{
		System.out.println("\n**** Welcome to Player Client application ****");
		System.out.println("IP detected: "  + playerIP + " - Game server [" + playerServerName + "] \n");
		System.out.println("Please select an option (1-4)");
		System.out.println("1. Create Player Account");
		System.out.println("2. Player Sign In");
		System.out.println("3. Player Sign Out");
		System.out.println("4. Transfer Account");		
		System.out.println("5. Exit");
	}
	
	private void selectRandomIP() {
		int auxRandom = (int)(Math.random()*3);		
		playerIP = playerIPRange[auxRandom];
		playerServer = playerServerRange[auxRandom];	
		playerServerName = playerServerNameRange[auxRandom];
	}
	
	private void connectToServer(){
		
		try{ 

			/*Properties properties = System.getProperties( );
				
			properties.put( "org.omg.CORBA.ORBInitialHost", "localhost" );
			properties.put( "org.omg.CORBA.ORBInitialPort", Integer.toString(900));
			 
			ORB orb = ORB.init((String[])null,properties);			
			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService"); 
			
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef); 
			this.gameServer = GameServerHelper.narrow(ncRef.resolve_str(playerServerName));
			*/
			
			ORB orb = ORB.init((String[])null,null);	
			
//			BufferedReader brNA = new BufferedReader (new FileReader("G:\\workspace\\iorNA.txt"));
//			String iorNA = brNA.readLine();
//			brNA.close();
//
//			BufferedReader brEU = new BufferedReader (new FileReader("G:\\workspace\\iorEU.txt"));
//			String iorEU = brEU.readLine();
//			brEU.close();
//			
//			BufferedReader brAS = new BufferedReader (new FileReader("G:\\workspace\\iorAS.txt"));
//			String iorAS = brAS.readLine();
//			brAS.close();		
//
//			org.omg.CORBA.Object oNA = orb.string_to_object(iorNA);
//			org.omg.CORBA.Object oEU = orb.string_to_object(iorEU);
//			org.omg.CORBA.Object oAS = orb.string_to_object(iorAS);			
//			
//
//						
//			if (playerServerName.equals("NA")){ 
//			
//				this.gameServer = GameServerHelper.narrow(oNA);
//			}
//			else if (playerServerName.equals("EU")) {
//			
//				this.gameServer = GameServerHelper.narrow(oEU);
//			}
//			else if (playerServerName.equals("AS")){
//			
//				this.gameServer = GameServerHelper.narrow(oAS);			
//			}
//			
			BufferedReader brFE = new BufferedReader (new FileReader("..\\iorFE.txt"));
			String iorFE = brFE.readLine();
			brFE.close();
			org.omg.CORBA.Object oFE = orb.string_to_object(iorFE);
			this.gameServer=GameServerHelper.narrow(oFE);
		} catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	@SuppressWarnings("resource")
	private void createPlayerMenu() {
		
		Scanner keyboard = new Scanner(System.in);		
		
		// Get User information
		
		System.out.println("Please enter player's first name:");
		playerFirstName=keyboard.next();
		System.out.println("Please enter player's last name:");
		playerLastName=keyboard.next();
		System.out.println("Please enter player's user name:");
		playerUserName=keyboard.next();
		System.out.println("Please enter player's password:");
		playerPwd=keyboard.next();
		System.out.println("Please enter player's age:");
		playerAge=keyboard.next();		
		 
	}	
	
	public void createPlayer(){
		
		String auxReturn;
		
		try{			
			//System.setSecurityManager(new RMISecurityManager());
			//Game gameServer = (Game) Naming.lookup(playerServer);		
			//auxReturn = gameServer.createPlayerAccount(playerFirstName,playerLastName,playerUserName,playerPwd,Integer.parseInt(playerAge),playerIP);
			auxReturn = gameServer.createPlayerAccount(playerFirstName,playerLastName,playerUserName,playerPwd,Integer.parseInt(playerAge),playerIP);
			Logger.write(playerUserName+playerIP, auxReturn);
			System.out.println(auxReturn);			
			
		} catch (Exception e){
			e.printStackTrace();
		}	
	}	

	@SuppressWarnings("resource")
	private void signInPlayerMenu() {
		
		Scanner keyboard = new Scanner(System.in);
		
		System.out.println("Please enter player's user name:");
		playerUserName=keyboard.next();
		System.out.println("Please enter player's password:");
		playerPwd=keyboard.next();		
		 
	}	
		
	public void signInPlayer(){
		String auxReturn;
		try{
			auxReturn = gameServer.playerSignIn(playerUserName,playerPwd,playerIP);			
			Logger.write(playerUserName+playerIP, auxReturn);
			System.out.println(auxReturn);	
			
		} catch (Exception e){
			e.printStackTrace();
		}			
		
	}
	
	@SuppressWarnings("resource")
	private void signOutPlayerMenu() {
	
		Scanner keyboard = new Scanner(System.in);

		System.out.println("Please enter player's user name:");
		playerUserName=keyboard.next();
		 
	}
	
	public void signOutPlayer(){
		
		String auxReturn;
		
		try{
			auxReturn = gameServer.playerSignOut(playerUserName, playerIP);
			Logger.write(playerUserName+playerIP, auxReturn );
			System.out.println(auxReturn);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	@SuppressWarnings("resource")
	private void transferAccountMenu() {
		
		Scanner keyboard = new Scanner(System.in);
		
		System.out.println("Please enter player's user name:");
		playerUserName=keyboard.next();
		System.out.println("Please enter player's password:");
		playerPwd=keyboard.next();	
		System.out.println("Please enter the new server (0=NA,1=EU,2=AS):");
		playerNewIP=keyboard.next();				
		 
	}	
		
	public void transferAccount(){
		String auxReturn;
		try{
			auxReturn = gameServer.transferAccount(playerUserName,playerPwd,playerIP, playerIPRange[Integer.parseInt(playerNewIP)]);			
			Logger.write(playerUserName+playerIP, auxReturn);
			System.out.println(auxReturn);	
			
		} catch (Exception e){
			e.printStackTrace();
		}			
		
	}
	
	private boolean validateCreatePlayer(){		
		
		// Test cases for creating player
		
		if ((this.playerUserName.length() > 15) || (this.playerUserName.length() < 6)){
			System.out.println("Sorry! Username typed doesn't meet the minimum requirements! (at least 6 and at most 15 characters )");
			return false;
		}

		if ((this.playerPwd.length() < 6)){
			System.out.println("Sorry! Password typed doesn't meet the minimum requirements! (at least 6 characters)");
			return false;
		}	
		
		try { 
	        Integer.parseInt(this.playerAge); 
	    } catch(NumberFormatException e) { 
	    	System.out.println("Sorry! Invalid age!");
	        return false; 
	    }
		
		return true;
	}
	
	private boolean validateSignInPlayer(){		
		// Test cases to validate the sign-in operation
		
		if ((this.playerUserName.length() > 15) || (this.playerUserName.length() < 6)){
			System.out.println("Sorry! Username typed doesn't meet the minimum requirements! (at least 6 and at most 15 characters )");
			return false;
		}

		if ((this.playerPwd.length() < 6)){
			System.out.println("Sorry! Password typed doesn't meet the minimum requirements! (at least 6 characters)");
			return false;
		}	
		return true;
	}
	
	private boolean validateSignOutPlayer(){
		// Test case to validate the sign-out operation
		if ((this.playerUserName.length() > 15) || (this.playerUserName.length() < 6)){
			System.out.println("Sorry! Username typed doesn't meet the minimum requirements! (at least 6 and at most 15 characters )");
			return false;
		}	
		return true;		
	}

	private boolean validateTransferAccount(){		
		// Test cases to check for before transfering the player's account
		
		if ((this.playerUserName.length() > 15) || (this.playerUserName.length() < 6)){
			System.out.println("Sorry! Username typed doesn't meet the minimum requirements! (at least 6 and at most 15 characters )");
			return false;
		}

		if ((this.playerPwd.length() < 6)){
			System.out.println("Sorry! Password typed doesn't meet the minimum requirements! (at least 6 characters)");
			return false;
		}	
		
		if ((!this.playerNewIP.equals("0")) &&  (!this.playerNewIP.equals("1")) && (!this.playerNewIP.equals("2"))){
			System.out.println("Sorry! Invalid Server! (0=NA,1=EU,2=AS)");
			return false;
		}	
		
		if (playerServer.equals(playerServerRange[Integer.parseInt(playerNewIP)])){
			System.out.println("Operation not allowed! You are trying to transfer one account to the same server!");
			return false;
		}					
		
		return true;
	}
}