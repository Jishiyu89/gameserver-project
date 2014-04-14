package system;


public class SystemInterfacePlay {

public String Username;

public String FirstName;

public String LastName;

public String Password;

public String Age;

public String IPaddress;
	
	public boolean isSignedIn = false;

	static SystemInterfaceImpl Asia,Europe,NAmerica;

		public String get_loc(String IPaddress)
		{
			String IP = IPaddress.substring(0, IPaddress.indexOf("."));
			if (IP.equals("132")) 	return "NORTHAMERICA";
			 
			if (IP.equals("93"))	return "EUROPE";
			
			if (IP.equals("182"))	return "ASIA";
			
			return "";
		
		}
		
}
