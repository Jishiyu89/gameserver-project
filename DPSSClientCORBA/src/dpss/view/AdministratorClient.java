package dpss.view;

import java.util.Properties;
import java.util.Scanner;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;


import dpss.service.WriteLog;
import dpss.service.corba.GameServer;
import dpss.service.corba.GameServerHelper;

/**  Administrator point of access (Client side)
 *   It has the menu that collects admin's inputs and calls the getPlayerStatus and suspendAccount methods on the game server. 
 *   Every new instance of the AdministratorClient object generates a random virtual IP that redirects the CORBA calls to the server it belongs to.
  *  @class AdministratorClient
 *   
 * */
public class AdministratorClient {

	String adminUserName;
	String adminPwd;
	String usernameToSuspend;
	String adminIP;
	String adminServer;
	String adminServerName;
	String[] adminIPRange = {"132.xxx.xxx.xxx","93.xxx.xxx.xxx","182.xxx.xxx.xxx"};
	String[] adminrServerRange = {"rmi://localhost:1000/gameserver","rmi://localhost:2000/gameserver","rmi://localhost:3000/gameserver"};
	String[] adminrServerNameRange = {"NA","EU","AS"};
	WriteLog Logger = new WriteLog(); 
	GameServer gameServer;
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {		
		AdministratorClient admin =  new AdministratorClient();		
	}

	public AdministratorClient(){ 
		
		selectRandomIP();	 
		connectToServer();
		showMenu();		
		
		try{
		
		int userChoice=0;
		Scanner keyboard = new Scanner(System.in);
		
		while(true)
		{
			Boolean valid = false;
			
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
			
			switch(userChoice)
			{
			case 1: 
				playerStatusMenu();	
				playerStatus();
				showMenu();
				break;
			case 2:
				suspendAccountMenu();	
				suspendAccount();
				showMenu();
				break;			
			case 3:
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
	
	public void showMenu()
	{
		System.out.println("\n**** Welcome to Admin Client application ****");
		System.out.println("IP detected: "  + adminIP + " - Game server [" + adminServerName + "] \n");
		System.out.println("Please select an option (1-2)");
		System.out.println("1. Player Status");
		System.out.println("2. Suspend Account");
		System.out.println("3. Exit");
	}

	private void selectRandomIP() {
		int auxRandom = (int)(Math.random()*3);		
		adminIP = adminIPRange[auxRandom];
		adminServer = adminrServerRange[auxRandom];	
		adminServerName = adminrServerNameRange[auxRandom];		
	}	
	
	private void connectToServer(){
		
		try{ 

			Properties properties = System.getProperties( );
				
			properties.put( "org.omg.CORBA.ORBInitialHost", "localhost" );
			properties.put( "org.omg.CORBA.ORBInitialPort", Integer.toString(900));
			 
			ORB orb = ORB.init((String[])null,properties);			
			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService"); 
			
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef); 
			this.gameServer = GameServerHelper.narrow(ncRef.resolve_str(adminServerName));
			
			//System.out.println("Connected to Server " + playerServerName + "!");
			
		} catch(Exception e){
			e.printStackTrace();
		}
		
	}	

	@SuppressWarnings("resource")
	private void playerStatusMenu() {
				
		Scanner keyboard = new Scanner(System.in);		
			
		System.out.println("Please enter admin's user name:");
		adminUserName=keyboard.next();
		System.out.println("Please enter admin's password:");
		adminPwd=keyboard.next();
		
	}
	
	private void playerStatus(){
		String auxReturn;
		try{
			auxReturn = gameServer.getPlayerStatus(adminUserName,adminPwd,adminIP);			
			Logger.write("admin." + adminUserName+adminIP, auxReturn);
			System.out.println(auxReturn); 				
		} catch (Exception e){
			e.printStackTrace();
		}	
	}	

	@SuppressWarnings("resource")
	private void suspendAccountMenu() {
		Scanner keyboard = new Scanner(System.in);		
		
		System.out.println("Please enter admin's user name:");
		adminUserName=keyboard.next();
		System.out.println("Please enter admin's password:");
		adminPwd=keyboard.next();
		System.out.println("Please enter the username to suspend:");
		usernameToSuspend=keyboard.next();		
	}
	
	private void suspendAccount() {
		String auxReturn;
		try{
			auxReturn = gameServer.suspendAccount(adminUserName,adminPwd,adminIP, usernameToSuspend);			
			Logger.write("admin." + adminUserName+adminIP, auxReturn);
			System.out.println(auxReturn); 				
		} catch (Exception e){
			e.printStackTrace();
		}	
	}	
	
}