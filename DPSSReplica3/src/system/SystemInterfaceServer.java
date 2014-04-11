package system;

//import java.io.FileNotFoundException;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.ObjectNotActive;
import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

public class SystemInterfaceServer {

	public static void main(String[] args) throws InvalidName, ServantAlreadyActive, WrongPolicy, ObjectNotActive, AdapterInactive, FileNotFoundException 
	{
	
		//----------------------------------------------------------------------
		
		ORB orb = ORB.init(args,null);
		POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA")); 
		
		SystemInterfaceImpl NA = new SystemInterfaceImpl();
		byte[] id_NA= rootPOA.activate_object(NA);
		org.omg.CORBA.Object ref_NA = rootPOA.id_to_reference(id_NA);
		
		String IOR_NA = orb.object_to_string(ref_NA);
		System.out.println(IOR_NA);
		
		
		PrintWriter File_NA = new PrintWriter("IOR_North_America.txt");
		File_NA.println(IOR_NA);
		File_NA.close();
		
		
		//------------------------------------------------------------------------
		
		
		
		
		SystemInterfaceImpl EU = new SystemInterfaceImpl();
		byte[] id_Europe= rootPOA.activate_object(EU);
		org.omg.CORBA.Object ref_EU = rootPOA.id_to_reference(id_Europe);
		
		String IOR_EU = orb.object_to_string(ref_EU);
		System.out.println(IOR_EU);
		PrintWriter File_EU = new PrintWriter("IOR_EUROPE.txt");
		File_EU.println(IOR_EU);
		File_EU.close();
		
		
		
		
		//------------------------------------------------------------------------
		
		
		
		
		SystemInterfaceImpl AS = new SystemInterfaceImpl();
		byte[] id_Asia= rootPOA.activate_object(AS);
		org.omg.CORBA.Object ref_AS = rootPOA.id_to_reference(id_Asia);
		String IOR_AS = orb.object_to_string(ref_AS);
		System.out.println(IOR_AS);
		PrintWriter File_AS = new PrintWriter("IOR_ASIA.txt");
		File_AS.println(IOR_AS);
		File_AS.close();
		
		rootPOA.the_POAManager().activate();
		orb.run();

	}


}
