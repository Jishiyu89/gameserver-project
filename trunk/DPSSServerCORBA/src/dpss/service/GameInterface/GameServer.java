package dpss.service.GameInterface;

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


public class GameServer {

	/**
	 * @param args
	 * @throws ObjectNotActive 
	 * @throws WrongPolicy 
	 * @throws ServantAlreadyActive 
	 * @throws InvalidName 
	 * @throws FileNotFoundException 
	 * @throws AdapterInactive 
	 */
	public static void main(String[] args) throws   ObjectNotActive, WrongPolicy, ServantAlreadyActive, InvalidName, FileNotFoundException, AdapterInactive {
		// TODO Auto-generated method stub
		
		ORB orb = ORB.init(args,null);
		POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));

		GameServer2 aBehavior = new GameServer2("NA");
		byte[] id = rootPOA.activate_object(aBehavior); /* converting to a unique ID*/		
		org.omg.CORBA.Object ref = rootPOA.id_to_reference(id); /* postal card - our reference */		
		String ior = orb.object_to_string(ref);
		
		GameServer2 aBehavior2 = new GameServer2("EU");
		byte[] id2 = rootPOA.activate_object(aBehavior2); /* converting to a unique ID*/		
		org.omg.CORBA.Object ref2 = rootPOA.id_to_reference(id2); /* postal card - our reference */		
		String ior2 = orb.object_to_string(ref2);
				
		System.out.println(ior);
		PrintWriter file = new PrintWriter("../ior.txt");
		file.println(ior);
		file.println(ior2);
		file.close();
		
		rootPOA.the_POAManager().activate();
		orb.run();
	}

}
