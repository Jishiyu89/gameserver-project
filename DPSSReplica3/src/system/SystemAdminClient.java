package system;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import org.omg.CORBA.ORB;
import org.omg.CORBA.StringHolder;




public class SystemAdminClient {

	SystemInterfaceClient client1 = new SystemInterfaceClient();
	public String AdminUsername;
	public String AdminPassword;
	static SystemInterface NAmerica, Europe, Asia;
	
	
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

			Scanner sc1 = new Scanner(System.in);
			Scanner sc2=new Scanner(System.in);
			
			System.out.println("Please Enter Your Admin Username - ");
			String AdminUsername = sc2.next();
			
			//Check for admin username
			
			while(!(AdminUsername.equals("admin")))
			{
				System.out.println("Enter Correct Admin Username");
				AdminUsername = sc2.next();
			}
			
			System.out.println("Please Enter Your Admin Password - ");
			String AdminPassword = sc2.next();
			
			//Check for admin password
			
			while(!(AdminPassword.equals("admin")))
			{
				System.out.println("Enter Correct Admin Password");
				AdminPassword = sc2.next();
			}

			// region selection
			Scanner scin = new Scanner(System.in);
			
			try 
			{
				System.out.println("What Is Your Location ? ");
				System.out.println("\n 1 - North America \n 2 - Europe \n 3 - Asia ");
				
				int input1 = sc1.nextInt();
				String IPaddress=new SystemAdminClient().createIP(input1);
				
				String location=new SystemAdminClient().get_loc(IPaddress);
				System.out.println("location is " + location);
				
				
				
				String s1  = "y";
				
				while (s1.equalsIgnoreCase("y")) 
				{
				
					Menu(); //Call method Menu
					int code = scin.nextInt();
					//Based on user input, select appropriate method
					
					if (code == 1) 
						new SystemAdminClient().suspendAccount(code);
					
					if (code == 2) 
						new SystemAdminClient().getPlayerStatus();
					
					if (code == 3)
						break;
					
					
					System.out.println("Do you want to continue ? (y-yes) - ");
					s1 = scin.next();
				
				}
			}
			
			catch (Exception e) 
			{
			
				e.printStackTrace();
			}
			
		}
			
			
			public void getPlayerStatus()
			{
				Scanner sc1 = new Scanner(System.in);
				
				/*System.out.println("What Is Your Location ? ");
				System.out.println("\n 1 - North America \n 2 - Europe \n 3 - Asia ");*/
				
				int input = sc1.nextInt();
				String IPaddress = createIP(input);
				
				SystemInterfaceClient P1 = new SystemInterfaceClient();
				

				StringHolder reply = new StringHolder();
				if (input == 1) 
				{ 
					String NA_data  = NAmerica.getPlayerStatus(AdminUsername, AdminPassword,IPaddress);
					String EU_data  = Europe.getPlayerStatus(AdminUsername, AdminPassword,createIP(2));
					String ASIA_data  = Asia.getPlayerStatus(AdminUsername, AdminPassword,createIP(3));
					reply.value = "NorthAmerica  " + NA_data + "europe " + EU_data + " asia "+ASIA_data; 
					
				}
				
				else if (input == 2) 
				{ 
					
					String NA_data  = NAmerica.getPlayerStatus(AdminUsername, AdminPassword,createIP(2));
					String ASIA_data  = Asia.getPlayerStatus(AdminUsername, AdminPassword,createIP(3));
					String EU_data  = Europe.getPlayerStatus(AdminUsername, AdminPassword,IPaddress);
					reply.value =  "Europe" + EU_data +"NorthAmerica  " + NA_data + "Asia "+ASIA_data; 
				}
				
				else if (input == 3) 
				{ 
					
					String EU_data  = Europe.getPlayerStatus(AdminUsername, AdminPassword,createIP(3));
					String ASIA_data  = Asia.getPlayerStatus(AdminUsername, AdminPassword,IPaddress);
					String NA_data  = NAmerica.getPlayerStatus(AdminUsername, AdminPassword,createIP(2));
					reply.value =  "Asia "+ASIA_data + "Europe -" + EU_data +"NorthAmerica " + NA_data  ; 
				}
				System.out.println(reply.value);

			
			}

			
			public static void Menu() 
			{
				System.out.println("Operations Available to Admin : ");
				System.out.println(" 1 - Suspend Player Account");
				System.out.println(" 2 - Get player status");
				System.out.println(" 3 - Exit");
		
			
			}

		
			public String suspendAccount(int code)
			{
				
				Scanner sc1=new Scanner(System.in);
				Scanner sc2=new Scanner(System.in);
				String AdminIP = createIP(code);
				
				System.out.println(" Please Enter Admin Username : ");
				String adminUsername = sc2.next();
				while(!(adminUsername.equals("admin")))
				{
					System.out.println("Incorrect data....Try Again");
					adminUsername = sc2.next();
				}
				
				System.out.println(" Please Enter Admin Password : ");
				String adminPassword = sc2.next();
			    while (!(adminPassword.equals("admin")))
				{
					System.out.println("Incorrect data....Try Again");
					adminPassword = sc2.next();
				}

			
				
				System.out.println("Operation : Suspend a player account");
				System.out.println("\n Please Enter Username :");
				String UserToSuspend  = sc1.next();
				String i="";
				String NA_data, EU_data, ASIA_data;
				if (code == 1) 
				{ 	
					
					NA_data =  NAmerica.suspendAccount(AdminUsername,AdminPassword,AdminIP,UserToSuspend);
					i = "NORTHAMERICA :  " + NA_data;
					
				}
				
				if (code == 2) 
				{ 
					EU_data  = Europe.suspendAccount(AdminUsername,AdminPassword,AdminIP,UserToSuspend);
					i = "EUROPE :  " + EU_data;
				}
				
				if (code == 3) 
				{ 
					ASIA_data  = Asia.suspendAccount(AdminUsername,AdminPassword,AdminIP,UserToSuspend);
					i = "ASIA :  " + ASIA_data;
				}
				System.out.println(i);
				
				return i;
				
			}
			
			
			public String get_loc(String IPaddress) 
			//Method assigns location based on IP address
			
			{
					String IP = IPaddress.substring(0, IPaddress.indexOf("."));
				 
					if (IP.equals("132"))
						return "NORTHAMERICA";
				    
					
				    if (IP.equals("93"))
						return "EUROPE";
				    					
					if (IP.equals("182"))
						return "ASIA";
				    
					
				return "";
			}
			
			
			
			public String createIP(int location)
			//Method assigns IP address based on location
			
			{
				String prefix = "";
				if (location==1)	prefix = "132";
				
				if (location==2)	prefix = "93";
				
				if (location==3)	prefix = "182";
				
				
				for (int i = 0; i < 3; i++)
				{
					prefix = prefix + "." + new Random().nextInt(255);
				}
				return prefix.substring(0, prefix.length()-1);
				//return "";
			}

	}


