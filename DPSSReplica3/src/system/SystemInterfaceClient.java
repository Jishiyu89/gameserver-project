package system;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import org.omg.CORBA.ORB;

public class SystemInterfaceClient {

public String Username;

public String FirstName;

public String LastName;

public String Password;

public String Age;

public String IPaddress;
	
	public boolean isSignedIn = false;

	static SystemInterface Asia,Europe,NAmerica;

	
	
		public static void main(String[] args) throws IOException {
		
		
		ORB orb = ORB.init(args,null);
		
		BufferedReader br1 = new BufferedReader (new FileReader("IOR_North_America.txt"));
		String IOR_NA = br1.readLine();
		br1.close();
		
		org.omg.CORBA.Object objNA = orb.string_to_object(IOR_NA);
		
		NAmerica = SystemInterfaceHelper.narrow(objNA);
		
		//-----------------------------------------------------------------------------------
		
		ORB orb_Europe = ORB.init(args,null);
		
		BufferedReader br2 = new BufferedReader (new FileReader("IOR_EUROPE.txt"));
		String IOR_EU = br2.readLine();
		br2.close();
		
		org.omg.CORBA.Object objEU = orb_Europe.string_to_object(IOR_EU);
		
		Europe = SystemInterfaceHelper.narrow(objEU);
		
		//------------------------------------------------------------------------------------
		
		ORB orb_Asia = ORB.init(args,null);
						
		BufferedReader br3 = new BufferedReader (new FileReader("IOR_ASIA.txt"));
		String IOR_AS = br3.readLine();
		br3.close();
					
		org.omg.CORBA.Object objASIA = orb_Asia.string_to_object(IOR_AS);
			
		Asia = SystemInterfaceHelper.narrow(objASIA);
		
		//-----------------------------------------------------------------------------------
		
		
Scanner scan = new Scanner(System.in);
		
		try 
		{
			System.out.println("Client is Running.......");
			
			
			
			String user_value  = "Y";
			
			while (user_value.equalsIgnoreCase("Y")) 
			{

				show_options(); // Display Options available / operations user can perform
				
				
				int user_input = scan.nextInt();
				
				if (user_input == 1) 
				  new SystemInterfaceClient().createPlayerAccount();
 
				//If user input is 1, call createPlayerAccount method 
				
				
				if (user_input == 2) 
				  new SystemInterfaceClient().playerSignIn();

				//If user input is 2, call playerSignIn method
				
				if (user_input == 3) 
				  new SystemInterfaceClient().playerSignOut();

				//If user input is 3, call playerSignOut method
				
				if (user_input == 4) 
					  new SystemInterfaceClient().transferAccount();
				
				if (user_input == 5)
				  break;

				//If user input is 4, quit
				
				System.out.println("\n Press 'Y' To Perform Next Operation ");
				user_value = scan.next();
				
				//Ask user if he wants to perform another operation, if yes then show the menu again
			}

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
	

	}

	//Method to display Menu or operations available to user
		public static void show_options() 
		{
			System.out.println("\n What You Want To Do ? ");
			System.out.println(" 1 - Create New Account");
			System.out.println(" 2 - Sign-in In Your Account");
			System.out.println(" 3 - Sign-out Of Your Account");
			System.out.println(" 4 - Transfer Your Account");
			System.out.println(" 5 - Quit");
		} 
	
		
		
		
		// Method to create player		
		public void createPlayerAccount()
		{
		
			Scanner temp = new Scanner(System.in);
			System.out.println("What Is Your Location ? ");
			System.out.println("\n 1 - North America \n 2 - Europe \n 3 - Asia ");
			int input = temp.nextInt();
			//input => assign the player's location
			
			this.IPaddress = createIP(input);
            // call method createIP to determine IP address based on Location
			
			System.out.println(" Please Enter Your First Name :- ");
			this.FirstName = temp.next();
			
			System.out.println(" Please Enter Your Last Name :- ");
			this.LastName = temp.next();
			
			// Get all required information from player for creating account
			
			System.out.println(" Please Enter Your User Name :- ");
			
			this.Username = temp.next();
			
			System.out.println(" Please Enter Your Password :- ");
			this.Password = temp.next();
			
			System.out.println("Please Enter Your Age :- ");
			this.Age = temp.next();
			
			
			String str = "";
			String NA_data, EU_data, ASIA_data;
			// If input is 1, i.e. if IP starts with 132, then create account on North America Server
			if (input == 1) 
			{ 
				NA_data  = NAmerica.createPlayerAccount(this.FirstName, this.LastName, this.Age, this.Username, this.Password, this.IPaddress);
				str = "NORTHAMERICA :  " + NA_data;
				
			}
			
			// If input is 2 i.e. if IP starts with 93, then create account on Europe Server
			if (input == 2) 
			{ 
				EU_data  = Europe.createPlayerAccount(this.FirstName, this.LastName, this.Age, this.Username, this.Password, this.IPaddress);
				str = "EUROPE :  " + EU_data;
			}
			
			// If input is 3, i.e. if IP starts with 182, then create account on Asia Server
			if (input == 3) 
			{ 
				ASIA_data  = Asia.createPlayerAccount(this.FirstName, this.LastName, this.Age, this.Username, this.Password, this.IPaddress);
				str = "ASIA :  " + ASIA_data;
			}
			System.out.println(str);

		}
			
			
		
		// Method to determine IP address based on the location user selects
		public String createIP(int location) {
			String prefix = "";
			
			if (location == 1) 	prefix = "132";
			 //132 for North America
			if (location == 2) 	prefix = "93";
			 //93 for Europe
			if (location == 3) 	prefix = "182";
			 //182 for Asia
			for (int i = 0; i < 3; i++) 
			{
				prefix = prefix + "." + new Random().nextInt(255);
			}
			return prefix.substring(0, prefix.length() - 1);
		}
		
		
		// Method for Player sign in
		public void playerSignIn() 
		{
			Scanner temp1 = new Scanner(System.in);
			System.out.println("What Is Your Location ? ");
			System.out.println("\n 1 - North America \n 2 - Europe \n 3 - Asia ");
			
			int input = temp1.nextInt();
			String IPaddress = createIP(input);
			String location = get_loc(IPaddress);
			// get_loc method determines location or region based on IP address
			
			System.out.println(" Region :" + location);

			System.out.println(" Please Enter Your User Name :- ");
			this.Username = temp1.next();
			
			System.out.println(" Please Enter Your Password :- ");
			this.Password = temp1.next();
		
			
			String i="";
			String NA_data;
			String EU_data;
			String ASIA_data;
				if (input == 1) 
				{ 	
					NA_data  = NAmerica.playerSignIn(Username, Password, IPaddress);
					i = "NORTHAMERICA :  " + NA_data;
					
				}
				
				if (input == 2) 
				{ 
					EU_data  = Europe.playerSignIn(Username, Password, IPaddress);
					i = "EUROPE :  " + EU_data;
				}
				
				if (input == 3) 
				{ 
					ASIA_data  = Asia.playerSignIn(Username, Password, IPaddress);
					i = "ASIA :  " + ASIA_data;
				}
				System.out.println(i);
			
		
		}
		
		public String get_loc(String IPaddress)
		{
			String IP = IPaddress.substring(0, IPaddress.indexOf("."));
			if (IP.equals("132")) 	return "NORTHAMERICA";
			 
			if (IP.equals("93"))	return "EUROPE";
			
			if (IP.equals("182"))	return "ASIA";
			
			return "";
		
		}
		
		
		// Method to sign out user
		public void playerSignOut() 
		{
			Scanner in = new Scanner(System.in);
			System.out.println("What Is Your Location ? ");
			System.out.println("\n 1 - North America \n 2 - Europe \n 3 - Asia ");
			int input = in.nextInt();
			
			String IPaddress= createIP(input);
			
			String region = get_loc(IPaddress);
			
			System.out.println("IP Address is  " + IPaddress );
			

			System.out.println("Please Enter your username - ");
			this.Username = in.next();
			
			String answer="";
			
			//Based on the location and the username, sign out the user from appropriate server
			
			if (input == 1) 
			{ 	
				String NA_data  = NAmerica.playerSignOut(IPaddress, Username);
				answer = "NORTHAMERICA :  " + NA_data;
				
			}
			
			else if (input == 2) 
			{ 
				String EU_data  = Europe.playerSignOut(IPaddress, Username);
				answer = "EUROPE :  " + EU_data;
			}
			
			else if (input == 3) 
			{ 
				String ASIA_data  = Asia.playerSignOut(Username,IPaddress);
				answer = "ASIA :  " + ASIA_data;
			}
			System.out.println(answer);
		
		}
		
		public String transferAccount()
		{
			Scanner temp = new Scanner(System.in);
			
			Scanner choice=new Scanner(System.in);
			
			int input = temp.nextInt();
		
    		System.out.println("What Is Your Location ? ");
			System.out.println("\n 1 - North America \n 2 - Europe \n 3 - Asia ");
			// Get Location
			
			String OldIPAddress = createIP(input);
			String location = get_loc(OldIPAddress);
			// Determine IP
			
			System.out.println(" Please  Enter your username :");
			this.Username  = temp.next();
			System.out.println("Please Enter your password :");
			this.Password = temp.next();
			
			
			
				
			System.out.println(">> Enter your prefered region to transfer player account :");
			String NewIPAddress = createIP(input);
			String region2 = get_loc(NewIPAddress);
			System.out.println("New ip-address : " + NewIPAddress + " | "+ " Region :" + region2);								String reply_in="";
				
			String NA_data;
			String EU_data, ASIA_data;
			String i = "";
			if (input == 1) 
			{ 	
					
					NA_data =  NAmerica.transferAccount(Username,Password,OldIPAddress,NewIPAddress);
					i = "NORTHAMERICA :  " + NA_data;
				
			}
				
			else if (input == 2) 
				{ 
					EU_data  = Europe.transferAccount(Username,Password,OldIPAddress,NewIPAddress);
					i = "EUROPE :  " + EU_data;
				}
				
				else if (input == 3) 
				{ 
					ASIA_data  = Asia.suspendAccount(Username,Password,OldIPAddress,NewIPAddress);
					i = "ASIA :  " + ASIA_data;
				}
 			System.out.println(i);
				return i;
			
			
			
		}

}
