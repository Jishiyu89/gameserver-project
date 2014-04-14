package system;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;



public class SystemInterfaceImpl  {

	

	private static final String Username = null;
	public Hashtable<String, ArrayList<SystemInterfacePlay>> hash_data = new Hashtable<String, ArrayList<SystemInterfacePlay>>();
	SystemInterfacePlay cl;
	String OldIPAddress;
	
	
	public String createPlayerAccount(String FirstName, String LastName,
			String Age, String Username, String Password, String IPaddress) {
		
		
		SystemInterfacePlay Play1 = new SystemInterfacePlay();
		
		Play1.FirstName = FirstName;
		Play1.LastName = LastName;
		Play1.Age = Age;
		Play1.Username = Username;
		Play1.Password = Password;
		Play1.IPaddress = IPaddress;
		
		
		
		ArrayList<SystemInterfacePlay> list = new ArrayList<SystemInterfacePlay>();
		int input = 0;
		int length = 0;
		boolean entry = true;
	
		
		
		
		String str1 = Username.substring(0, 1);
		String region = new SystemInterfacePlay().get_loc(IPaddress);
		
		
		if (region.equalsIgnoreCase("NORTHAMERICA")) 
		{
			//Create Logs
			
			Logger log1 = Logger.getLogger("Logs Created Successfully !!");
			log1.setUseParentHandlers(false);
			
			
			
			FileHandler f_handle = null;
			
			
			String log_Name = region +  "server.log";
			
			try 
			{
				File file1 = new File(log_Name);
				
				//Check if file exists
				if (file1.exists()) 
					f_handle = new FileHandler(log_Name, true);
				 
				else f_handle = new FileHandler(log_Name);
				

				log1.addHandler(f_handle);
				
				SimpleFormatter f = new SimpleFormatter();
				
				f_handle.setFormatter(f);
				
				// log entry
				
				log1.info("Player created  ");
			} 
			
			catch (Exception e)
			{
				log1.info("Error is " + e.getMessage());
				e.printStackTrace();
			}
			
			finally 
			{
				f_handle.close();
			}
			
		} 
		
		else if (region.equalsIgnoreCase("EUROPE")) 
		{
			
			

			Logger log1 = Logger.getLogger("Logs written :");
			log1.setUseParentHandlers(false);
			FileHandler f_handle = null;
			String log_Name = region + "server.log";
			
			try 
			{
				
				File file1 = new File(log_Name);
				
				if (file1.exists()) 
					f_handle = new FileHandler(log_Name, true);
				
				else 
					f_handle = new FileHandler(log_Name);


				log1.addHandler(f_handle);

				SimpleFormatter f = new SimpleFormatter();
				f_handle.setFormatter(f);
				
				log1.info("Player created ");
			} 
			
			catch (Exception e)
			{
				log1.info("Error is " + e.getMessage());
				e.printStackTrace();
			} 
			finally 
			{
				f_handle.close();
			}
			
		} 
		
		if (region.equalsIgnoreCase("ASIA")) 
		{
			

			Logger log1 = Logger.getLogger("Logs created");
			log1.setUseParentHandlers(false);
			FileHandler file2 = null;
			String log_Name = region + "server.log";
			
			try
			{
				File file1 = new File(log_Name);
				if (file1.exists()) 
				   file2 = new FileHandler(log_Name, true);
				
				else 
				   file2 = new FileHandler(log_Name);
				

				log1.addHandler(file2);
				SimpleFormatter f = new SimpleFormatter();
				file2.setFormatter(f);
				log1.info("Player created  ");
			
			} 
			catch (Exception e)
			{
				log1.info("Error is " + e.getMessage());
				e.printStackTrace();
			} 
			finally 
			{
				file2.close();
			}
		}

		
		
		
		
		
		
		
		
		
		// Check Hash table
		if (hash_data.containsKey(str1)) 
		{
			length = hash_data.get(str1).size();
			
			if (length > 0) 
			{
				for (input = 0; input < length; input++) 
				{
					if (Play1.Username.equalsIgnoreCase(hash_data.get(str1).get(input).Username)) 
					{
						entry = false;
						break;
					}
				}
			}
		}

		if (entry) {

			Play1.isSignedIn = false;
			synchronized (hash_data) 		
			{
			
				if (hash_data.containsKey(str1)) 
				{
					length = hash_data.get(str1).size();
					
					for (int k = 0; k < length; k++) 
					{
						list.add(hash_data.get(str1).get(k)); 
					}
				}

				list.add(Play1);
				hash_data.put(str1, list);

				Logger log1 = Logger.getLogger("Logs");	
				log1.setUseParentHandlers(false);
				FileHandler f_handle = null;
				String log_Name = Play1.Username +".log";
				
				try 
				{
					File file1 = new File(log_Name);
					
					if (file1.exists()) 
						f_handle = new FileHandler(log_Name, true);
					 
					else 
						f_handle = new FileHandler(log_Name);
	
					log1.addHandler(f_handle);
					SimpleFormatter f = new SimpleFormatter();
					f_handle.setFormatter(f);
					log1.info(" Account Created ");
					
				} 
				
				catch (Exception e)
				{
					log1.info("Exception Occured " + e.getMessage());
					e.printStackTrace();
				} 
				finally 
				{
					f_handle.close();
				}
				
				
				Logger log11 = Logger.getLogger("Logs");
				log1.setUseParentHandlers(false);
				FileHandler file3 = null;
				String log_Name1 = region + "server.log";
				
				try 
				{
					File file1 = new File(log_Name1);
					
					if (file1.exists()) 
						file3 = new FileHandler(log_Name, true);
					
					else 					
						file3 = new FileHandler(log_Name);
					

					log11.addHandler(file3);
					SimpleFormatter f1 = new SimpleFormatter();
					file3.setFormatter(f1);
					log11.info("Player created  ");
				}
				catch (Exception e)
				{
					log1.info("Exception  : " + e.getMessage());
					e.printStackTrace();
				}
				finally 
				{
					file3.close();
				}
				return "Username [" +Username + "] successfully created on the requested game server!";
			}

		} 
		
		else
		{

			Logger log1 = Logger.getLogger("Logs");
			log1.setUseParentHandlers(false);
			FileHandler file2 = null;
			String log_Name = region + "server.log";
			
			try 
			{
				File file1 = new File(log_Name);
				
				if (file1.exists()) 
					file2 = new FileHandler(log_Name, true);
				
				else 
					file2 = new FileHandler(log_Name);
				

				log1.addHandler(file2);
				SimpleFormatter f = new SimpleFormatter();
				file2.setFormatter(f);
				log1.info("Player created ");
			} 
			catch (Exception e)
			{
				log1.info("Exception  " + e.getMessage());
				e.printStackTrace();
			}
			finally
			{
				file2.close();
			}
			return "Username [" +Username + "] successfully created on the requested game server!";
		}

	}
	


		
		
	public String playerSignIn(String Username, String Password,String IPaddress) 
	{
		
		
        SystemInterfacePlay Play1 = new SystemInterfacePlay();
		
		
		Play1.Username = Username;
		Play1.Password = Password;
		Play1.IPaddress = IPaddress;
		
		
		String str2 = IPaddress.substring(0, IPaddress.indexOf("."));
		String region = "";
		
		
		
		
		
		if (str2.equals("132")) 
		{

			region = "NORTHAMERICA";
			
			Logger log1 = Logger.getLogger("Log for server");
			log1.setUseParentHandlers(false);
			FileHandler file1 = null;
			String log_Name = region + "server.log";
			try 
			{
				File new_file = new File(log_Name);
				
				if (new_file.exists()) 
			
					file1 = new FileHandler(log_Name, true);
			
				else 
			
					file1 = new FileHandler(log_Name);
			

				log1.addHandler(file1);
				SimpleFormatter f = new SimpleFormatter();
				file1.setFormatter(f);
				log1.info("sign-in ");
			} 
			catch (Exception e)
			{
				log1.info("Exception occured " + e.getMessage());
				e.printStackTrace();
			} 
			finally 
			{
				file1.close();
			}
		} 
		else if (str2.equals("93")) 
		{
			region = "EUROPE";
			Logger log1 = Logger.getLogger("Log for creation of server :");
			log1.setUseParentHandlers(false);
			FileHandler file1 = null;
			String log_Name = region + "server.log";
			
			try {
				File new_file = new File(log_Name);
				if (new_file.exists()) 
					file1 = new FileHandler(log_Name, true);
				else 
					file1 = new FileHandler(log_Name);
			

				log1.addHandler(file1);
				SimpleFormatter f = new SimpleFormatter();
				file1.setFormatter(f);
				log1.info("sign-in : ");
			} 
			catch (Exception e)
			{
				log1.info("Exception occured " + e.getMessage());
				e.printStackTrace();
			}
			finally 
			{
				file1.close();
			}
		} 
		
		else if (str2.equals("182")) 
		{
;
			region = "ASIA";
			Logger log1 = Logger.getLogger("Log for server :");
			log1.setUseParentHandlers(false);
			FileHandler file1 = null;
			String log_Name = region + "on server.log";
			
			try 
			{
				File new_file = new File(log_Name);
				
				if (new_file.exists()) 
			
					file1 = new FileHandler(log_Name, true);
			
				else 
	
					file1 = new FileHandler(log_Name);
	

				log1.addHandler(file1);
				SimpleFormatter f = new SimpleFormatter();
				file1.setFormatter(f);
				log1.info("sign-in ");
			} 
			catch (Exception e)
			{
				log1.info(" Exception occured " + e.getMessage());
				e.printStackTrace();
			} 
			finally 
			{
				file1.close();
			}
		}
		
		String str1 = Username.substring(0, 1);
		int status = 0;
		if (hash_data.containsKey(str1)) 
		{

			for (int i = 0; i < hash_data.get(str1).size(); i++) 
			{
				SystemInterfacePlay Play2 = hash_data.get(str1).get(i);

				if (Username.equalsIgnoreCase(Play2.Username)) 
				{
					if (Password.equalsIgnoreCase(Play2.Password)) 
					{
						Play1 = Play2;
						
						if (!Play2.isSignedIn) 
						{
							hash_data.get(str1).get(i).isSignedIn = true;
	
							status = 10;
							break;
						} 
						else 
						{
			
							status = 20;
							break;
						}

					} 
		
				} 
			
			}

		}

		if (status == 0) 
		{
			return "Username ["+ Username + "] does not exist on game server!";
		} 
		
		// Sign-in 
		else if (status == 10) 
		{
			Logger log1 = Logger.getLogger("Sign In");
			log1.setUseParentHandlers(false);
			FileHandler file4 = null;
			String log_Name = Play1.Username +".log";

			try 
			{
				File new_file = new File(log_Name);
				
				if (new_file.exists()) 
				
					file4 = new FileHandler(log_Name, true);
				
				else file4 = new FileHandler(log_Name);
				

				log1.addHandler(file4);
				SimpleFormatter f = new SimpleFormatter();
				file4.setFormatter(f);
				log1.info("Sign In");
			} 
			catch (Exception e)
			{
				log1.info("Exception occured " + e.getMessage());
				e.printStackTrace();
			}  
			finally 
			{
				file4.close();
			}
			
			return "User ["+ Username + "] successfully signed in on game server! ";
		} 
		
		
		else if (status == 20) 
		{
			
			Logger log1 = Logger.getLogger("You are already Signed In");
			log1.setUseParentHandlers(false);
			FileHandler file4 = null;
			String log_Name = Play1.Username + ".log";
			
			try
			{
				File new_file = new File(log_Name);
			
				if (new_file.exists()) 
			
					file4 = new FileHandler(log_Name, true);
			
				else file4 = new FileHandler(log_Name);
				

				log1.addHandler(file4);
				SimpleFormatter f = new SimpleFormatter();
				file4.setFormatter(f);
				log1.info("Already Signed-In :");
			} 
			catch (Exception e)
			{
				log1.info("Exception occured " + e.getMessage());
				e.printStackTrace();
			}
			finally
			{
				file4.close();
			}
			return "User ["+ Username + "] is already online on game server!";
		} 
		
		return "";

		
		
		
		
	}

	
	public String playerSignOut(String Username, String IPaddress) {
		
		
		
		SystemInterfacePlay Play1 = new SystemInterfacePlay();
		Play1.Username = Username;
		Play1.IPaddress = IPaddress;
	
			String IP = IPaddress.substring(0, IPaddress.indexOf("."));

			String region = "";
			
			if (IP.equals("132")) 
			{
		
				region = "NORTHAMERICA";
				Logger log = Logger.getLogger("Log for server :");
				log.setUseParentHandlers(false);
				FileHandler file1 = null;
				String log_Name =  region + "server.log";
				
				try 
				{
					File new_file = new File(log_Name);
					
					if (new_file.exists())
						file1 = new FileHandler(log_Name, true);
					
					else file1 = new FileHandler(log_Name);
					

					log.addHandler(file1);
					SimpleFormatter formatter = new SimpleFormatter();
					file1.setFormatter(formatter);
					log.info("Player signed Out : ");
				} 
				catch (Exception e)
				{
					log.info("Exception occured " + e.getMessage());
					e.printStackTrace();
				}
				finally 
				{
					file1.close();
				}
			} 
			
			else if (IP.equals("93")) 
			{
				
	
				region = "EUROPE";
				Logger log = Logger.getLogger("Log for server :");
				log.setUseParentHandlers(false);
				FileHandler file1 = null;
				String log_Name = region + " server.log";
				
				try 
				{
					File new_file = new File(log_Name);
					
					if (new_file.exists()) 
						file1 = new FileHandler(log_Name, true);
					
					else file1 = new FileHandler(log_Name);
					

					log.addHandler(file1);
					SimpleFormatter formatter = new SimpleFormatter();
					file1.setFormatter(formatter);
					log.info("Player successfully signed Out !! ");
				} 
				catch (Exception e)
				{
					log.info("Exception occured " + e.getMessage());
					e.printStackTrace();
				}
				finally 
				{	file1.close();
				}
			} 
			if (IP.equals("182")) 
			{

				region = "ASIA";
				Logger log = Logger.getLogger("Log for server :");
				log.setUseParentHandlers(false);
				FileHandler file1 = null;
				String log_Name = region + " server.log";
				
				try 
				{
					File new_file = new File(log_Name);
					
					if (new_file.exists()) 
					
						file1 = new FileHandler(log_Name, true);
					
					else file1 = new FileHandler(log_Name);
					

					log.addHandler(file1);
					SimpleFormatter f = new SimpleFormatter();
					file1.setFormatter(f);
					log.info("Player Signed Out successfully !! ");
				} 
				catch (Exception e)
				{
					log.info(" Exception occured " + e.getMessage());
					e.printStackTrace();
				}
				finally 
				{
					file1.close();
				}
			}
			
			String str1 = Username.substring(0, 1);
			int status = 0;
			
			if (hash_data.containsKey(str1)) 
			{

				for (int i = 0; i < hash_data.get(str1).size(); i++) 
				{
					SystemInterfacePlay Play2 = hash_data.get(str1).get(i);

					if (Username.equalsIgnoreCase(Play2.Username))
					{
						Play1 = Play2;
						
						if (Play2.isSignedIn) 
						{
							hash_data.get(str1).get(i).isSignedIn = false;
							//  return you have signed-out;
							status = 10;
							break;
						} 
						else 
						{
							// player already signed out 
							status = 20;
							break;
						}

					} 
					
				}

			}

			else if (status == 0) 
			{
				return "Username ["+ Username + "] does not exist on game server!";
			} 
			
			else if (status == 10) 		
			{
				
				Logger log = Logger.getLogger("Creation Log");
				log.setUseParentHandlers(false);
				FileHandler file4 = null;
				String log_Name = Play1.Username +".log";
				
				try 
				{
					File new_file = new File(log_Name);
					
					if (new_file.exists())
					
						file4 = new FileHandler(log_Name, true);
					
					else file4 = new FileHandler(log_Name);
					

					log.addHandler(file4);
					SimpleFormatter f = new SimpleFormatter();
					file4.setFormatter(f);
					log.info("User Signed Out");
				} 
				catch (Exception e)
				{
					log.info(" Exception occured " + e.getMessage());
					e.printStackTrace();
				}
				finally 
				{
					file4.close();
				}
				
				return "Username ["+ Username + "] successfully signed out of game server!";
			} 
			
			else if (status == 20) 
			{
				
				Logger log = Logger.getLogger(" Log");
				log.setUseParentHandlers(false);
				FileHandler file4 = null;
				String log_Name = Play1.Username + ".log";
				
				try
				{
					File new_file = new File(log_Name);
					
					if (new_file.exists()) 
					
						file4 = new FileHandler(log_Name, true);
					 
					else file4 = new FileHandler(log_Name);
					

					log.addHandler(file4);
					SimpleFormatter f = new SimpleFormatter();
					file4.setFormatter(f);
					log.info("Already Signed Out");
				} 
				catch (Exception e)
				{
					log.info("Exception occured " + e.getMessage());
					e.printStackTrace();
				}
				finally 
				{
					file4.close();
				}
				return "User ["+ Username + "] is not online on game server!";
			} 
			
						
			return "";

		
		
	}

	public String getPlayerStatus(String AdminUsername, String AdminPassword,
			String IPaddress) {
		
		
		return getStatus(hash_data);
		
		
	}
	
	

	 String getStatus(Hashtable<String, ArrayList<SystemInterfacePlay>> hash_data) 
	 {
		int online = 0;
		int offline = 0;
		Enumeration<ArrayList<SystemInterfacePlay>> enumerate = hash_data.elements();
		
		while (enumerate.hasMoreElements()) 
		{
			ArrayList<SystemInterfacePlay> list = (ArrayList<SystemInterfacePlay>) enumerate.nextElement();
			
			for (int i = 0; i < list.size(); i++) 
			{
				
				if (list.get(i).isSignedIn) 
					online++;
				
				else offline++;
				
			}
		}
		
		return online + " online player(s) " + offline + " offline player(s)";
	}




	
	public String transferAccount(String Username, String Password,	String OldIPAddress, String NewIPAddress) 
	{
			
			SystemInterfacePlay Play1 = new SystemInterfacePlay();
			Play1.Username = Username;
			Play1.Password = Password;
			
			
			
			String IP = OldIPAddress.substring(0, OldIPAddress.indexOf("."));
			String region = "";
			
			if (IP.equals("132")) 
			{

				region = "NORTHAMERICA";
				Logger log1 = Logger.getLogger("Log for creation of server :");
				log1.setUseParentHandlers(false);
				FileHandler file1 = null;
				String logname = "Log : " + region + "on server.log";
				try 
				{
					File new_file = new File(logname);
					
					if (new_file.exists()) 
						file1 = new FileHandler(logname, true);
				
					else  file1 = new FileHandler(logname);
					

					log1.addHandler(file1);
					SimpleFormatter f = new SimpleFormatter();
					file1.setFormatter(f);
					log1.info("Request for account suspention : ");
				} 
				catch (Exception e)
				{
					log1.info("Error : Exception : " + e.getMessage());
					e.printStackTrace();
				} 
				finally 
				{
					file1.close();
				}
			} 
			else if (IP.equals("93")) 
			{
				region = "EUROPE";
				Logger log1 = Logger.getLogger("Log for creation of server :");
				log1.setUseParentHandlers(false);
				FileHandler file3 = null;
				String logname = "Log : " + region + " on server.log";
				
				try {
					File new_file = new File(logname);
					if (new_file.exists()) {
						file3 = new FileHandler(logname, true);
					} else {
						file3 = new FileHandler(logname);
					}

					log1.addHandler(file3);
					SimpleFormatter f = new SimpleFormatter();
					file3.setFormatter(f);
					log1.info("Request for account suspention : ");
				} 
				catch (Exception e)
				{
					log1.info("Error : Exception : " + e.getMessage());
					e.printStackTrace();
				}
				finally 
				{
					file3.close();
				}
			} 
			
			else if (IP.equals("182")) 
			{
	;
				region = "ASIA";
				Logger log1 = Logger.getLogger("Log for creation of server :");
				log1.setUseParentHandlers(false);
				FileHandler file1 = null;
				String logname = "Log : " + region + "on server.log";
				
				try 
				{
					File new_file = new File(logname);
					
					if (new_file.exists()) 
					{
						file1 = new FileHandler(logname, true);
					} 
					else 
					{
						file1 = new FileHandler(logname);
					}

					log1.addHandler(file1);
					SimpleFormatter f = new SimpleFormatter();
					file1.setFormatter(f);
					log1.info("Request for account suspention : ");
				} 
				catch (Exception e)
				{
					log1.info("Error : Exception : " + e.getMessage());
					e.printStackTrace();
				} 
				finally 
				{
					file1.close();
				}
			}
			
			String str1 = Username.substring(0, 1);
			int status = 0;
			if (hash_data.containsKey(str1)) 
			{

				for (int i = 0; i < hash_data.get(str1).size(); i++) 
				{
					SystemInterfacePlay Play = hash_data.get(str1).get(i);

					if (Username.equalsIgnoreCase(Play.Username)) 
					{
						
						status=10;
						hash_data.get(str1).remove(i);
						break;
						
					}
							else 
							{
								status = 30;
							}
								
				} 
					
			}
			
			if (status == 10) 
			{
				Logger log1 = Logger.getLogger("Suspention Log :");
				log1.setUseParentHandlers(false);
				FileHandler file4 = null;
				String logname = Username +"on "+ region + ".log";

				try 
				{
					File f = new File(logname);
					
					if (f.exists()) 
						file4 = new FileHandler(logname, true);
					
					else
					file4 = new FileHandler(logname);
					

					log1.addHandler(file4);
					SimpleFormatter f1 = new SimpleFormatter();
					file4.setFormatter(f1);
					log1.info("Suspend ");
				} 
				catch (Exception e)
				{
					log1.info("Error : Exception : " + e.getMessage());
					e.printStackTrace();
				}
				 
				finally 
				{
					file4.close();
				}
				
				return "User suspended";
			} 

			else if (status == 30) 
				return "Invalid Username";

			return "Player not found";
		}


		






		


	
	public String suspendAccount(String AdminUsername, String AdminPassword,
			String AdminIP, String UsernameToSuspend) {
		
		
		
		String IP = AdminIP.substring(0, AdminIP.indexOf("."));
		String region = "";
		
		if (IP.equals("132")) 
		{

			region = "NORTHAMERICA";
			Logger log = Logger.getLogger("Log for creation of server :");
			log.setUseParentHandlers(false);
			FileHandler file1 = null;
			String log_Name = "Log : " + region + "on server.log";
			try 
			{
				File new_file = new File(log_Name);
				
				if (new_file.exists()) 
				
					file1 = new FileHandler(log_Name, true);
				 
				else 
				
					file1 = new FileHandler(log_Name);
				

				log.addHandler(file1);
				SimpleFormatter f = new SimpleFormatter();
				file1.setFormatter(f);
				log.info("Player Suspended ");
			} 
			catch (Exception e)
			{
				log.info("Exception occured" + e.getMessage());
				e.printStackTrace();
			} 
			finally 
			{
				file1.close();
			}
		} 
		else if (IP.equals("93")) 
		{
			region = "EUROPE";
			Logger log = Logger.getLogger("Log for server :");
			log.setUseParentHandlers(false);
			FileHandler file3 = null;
			String log_Name =  region + " on server.log";
			
			try {
				File new_file = new File(log_Name);
				if (new_file.exists()) 
					file3 = new FileHandler(log_Name, true);
				else 
					file3 = new FileHandler(log_Name);
				

				log.addHandler(file3);
				SimpleFormatter f = new SimpleFormatter();
				file3.setFormatter(f);
				log.info("Player suspended ");
			} 
			catch (Exception e)
			{
				log.info("Exception occured " + e.getMessage());
				e.printStackTrace();
			}
			finally 
			{
				file3.close();
			}
		} 
		
		else if (IP.equals("182")) 
		{

			region = "ASIA";
			Logger log = Logger.getLogger("Log for server :");
			log.setUseParentHandlers(false);
			FileHandler file1 = null;
			String log_Name = region + " on server.log";
			
			try 
			{
				File new_file = new File(log_Name);
				
				if (new_file.exists()) 
				
					file1 = new FileHandler(log_Name, true);
				
				else file1 = new FileHandler(log_Name);
				

				log.addHandler(file1);
				SimpleFormatter f = new SimpleFormatter();
				file1.setFormatter(f);
				log.info("Suspend player ");
			} 
	    catch (Exception e)
			{
				log.info("Exception occured " + e.getMessage());
				e.printStackTrace();
			} 
			finally 
			{
				file1.close();
			}
		}
		
		String str1 = UsernameToSuspend.substring(0, 1);
		int status = 0;
		if (hash_data.containsKey(str1)) 
		{

			for (int i = 0; i < hash_data.get(str1).size(); i++) 
			{
				SystemInterfacePlay Play2 = hash_data.get(str1).get(i);

				if (UsernameToSuspend.equalsIgnoreCase(Play2.Username)) 
				{
					
					status=10;
					hash_data.get(str1).remove(i);			
					break;
					
				}
									
			} 
				
		}
		
		if (status == 10) 
		{
			Logger log = Logger.getLogger("Suspend Log ");
			log.setUseParentHandlers(false);
			FileHandler file4 = null;
			String log_Name = region + ".log";

			try 
			{
				File files = new File(log_Name);
				
				if (files.exists()) 
				
					file4 = new FileHandler(log_Name, true);
				
				else
				
					file4 = new FileHandler(log_Name);
				

				log.addHandler(file4);
				SimpleFormatter f = new SimpleFormatter();
				file4.setFormatter(f);
				log.info("Suspend player ");
			} 
			catch (Exception e)
			{
				log.info("Exception occured " + e.getMessage());
				e.printStackTrace();
			}
			 
			finally 
			{
				file4.close();
			}
			
			return "Username ["+ Username + "] successfully removed from game server!";
		} 

		return "Username ["+ Username + "] does not exist on game server!";
	}

}
