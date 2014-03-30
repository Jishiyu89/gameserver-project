package dpss.service;

/**  Implementation of the Front End which contains the code of available features.
 *      
 *   @class FrontEndImpl   
 *   
 * 	 For every clients' or adminclients' CORBA invocation,FR send UDP request to Leader Replica
 * 	 Format: type of request +information from clients or adminclients.
 * 	 Port number: 1010
 * 
 *   Receive reply from Leader Replica
 *   Port number: 9000
 *    
 * */

import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import dpss.service.corba.GameServerPOA;

public class FrontEndImpl extends GameServerPOA {
	
	ORB orb; 
	String name="FrontEnd";
	int portUDP=9000;		
	DatagramSocket socketFE=null;
	@SuppressWarnings("rawtypes")
	Enumeration hashLetters;
	WriteLog Logger = new WriteLog(); 		
	InetAddress hostLR;
	int portLR;
	public void setORB(ORB orb_val) { 
		 this.orb = orb_val; 
	 }	 
		 
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public FrontEndImpl (){
				
		try{
			socketFE=new DatagramSocket(portUDP);
			Logger.write(name,"Front End " + name +  " is up and running!");
			System.out.println("Front End " + name +  " is up and running!");	
			hostLR = InetAddress.getByName("localhost");
			portLR=1010;
		}catch(Exception e){
			e.printStackTrace();
		}		
	 }
		
	@SuppressWarnings("unchecked")
	public String createPlayerAccount(String firstNameParam, String lastNameParam, String usernameParam, String passwordParam, int ageParam, String iPAdressParam) {
		String request="CreatPlayAccount"+"->"+firstNameParam+"->"+lastNameParam+"->"+usernameParam+"->"+passwordParam +"->"+ ageParam+"->"+iPAdressParam;
		byte[] bufferRequest=new byte[1000];
		DatagramPacket UDPRequest=null;
		byte[] bufferReply=new byte[1000];
		DatagramPacket UDPReply=new DatagramPacket(bufferReply, bufferReply.length);;
		bufferRequest=request.getBytes();
		String reply;
		
		UDPRequest=new DatagramPacket(bufferRequest,bufferRequest.length,hostLR,portLR);
		try {
			synchronized (socketFE) {
		
				socketFE.send(UDPRequest);
	
				Logger.write(name,"Front End send Create Account request to Leader Replica from Client[" + iPAdressParam + "]!");
				System.out.println("Front End send Create Account request to Leader Replica from Client[" + iPAdressParam + "]!");			
			}
			synchronized (socketFE) {
				
				socketFE.receive (UDPReply);
				
				reply=new String(UDPReply.getData()).substring(0,UDPReply.getLength());
				Logger.write(name,"Front End recieve Create Account reply["+reply+"] from Leader Replica to Client[" + iPAdressParam + "]!");
				System.out.println("Front End recieve Create Account reply["+reply+"] from Leader Replica to Client[" + iPAdressParam + "]!");			
			}
			return reply;
		} catch (IOException e) {
			e.printStackTrace();
			return "Error in Front End";
		}	
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public String playerSignIn(String usernameParam, String passwordParam, String iPAdressParam){
			
		String request="PlayerSignIn"+"->"+usernameParam+"->"+passwordParam +"->"+iPAdressParam;
		DatagramPacket UDPRequest=null;
		byte[] bufferRequest=new byte[1000];
		byte[] bufferReply=new byte[1000];
		DatagramPacket UDPReply=new DatagramPacket(bufferReply, bufferReply.length);;
		bufferRequest=request.getBytes();
		String reply;
		
		UDPRequest=new DatagramPacket(bufferRequest,bufferRequest.length,hostLR,portLR);
		try {
			synchronized (socketFE) {
		
				socketFE.send(UDPRequest);
	
				Logger.write(name,"Front End send Sign In request to Leader Replica from Client[" + iPAdressParam + "]!");
				System.out.println("Front End send Sign In request to Leader Replica from Client[" + iPAdressParam + "]!");			
			}
			synchronized (socketFE) {
				
				socketFE.receive (UDPReply);
				
				reply=new String(UDPReply.getData()).substring(0,UDPReply.getLength());
				Logger.write(name,"Front End recieve Sign In reply["+reply+"] from Leader Replica to Client[" + iPAdressParam + "]!");
				System.out.println("Front End recieve Sign In reply["+reply+"] from Leader Replica to Client[" + iPAdressParam + "]!");			
			}
			return reply;
		} catch (IOException e) {
			e.printStackTrace();
			return "Error in Front End";
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public String playerSignOut(String usernameParam, String iPAdressParam) {
		
		String request="PlayerSignOut"+"->"+usernameParam+"->"+iPAdressParam;
		DatagramPacket UDPRequest=null;
		byte[] bufferRequest=new byte[1000];
		byte[] bufferReply=new byte[1000];
		DatagramPacket UDPReply=new DatagramPacket(bufferReply, bufferReply.length);;
		bufferRequest=request.getBytes();
		String reply;
		
		UDPRequest=new DatagramPacket(bufferRequest,bufferRequest.length,hostLR,portLR);
		try {
			synchronized (socketFE) {
		
				socketFE.send(UDPRequest);
	
				Logger.write(name,"Front End send Sign Out request to Leader Replica from Client[" + iPAdressParam + "]!");
				System.out.println("Front End send Sign Out request to Leader Replica from Client[" + iPAdressParam + "]!");			
			}
			synchronized (socketFE) {
				
				socketFE.receive (UDPReply);
				
				reply=new String(UDPReply.getData()).substring(0,UDPReply.getLength());
				Logger.write(name,"Front End recieve Sign Out reply["+reply+"] from Leader Replica to Client[" + iPAdressParam + "]!");
				System.out.println("Front End recieve Sign Out reply["+reply+"] from Leader Replica to Client[" + iPAdressParam + "]!");			
			}
			return reply;
		} catch (IOException e) {
			e.printStackTrace();
			return "Error in Front End";
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public String transferAccount(String usernameParam, String passwordParam, String oldIPAddressParam, String newIPAddressParam) {
		String request="TransferAccount"+"->"+usernameParam+"->"+passwordParam +"->"+ oldIPAddressParam+"->"+newIPAddressParam;
		byte[] bufferRequest=new byte[1000];
		DatagramPacket UDPRequest=null;
		byte[] bufferReply=new byte[1000];
		DatagramPacket UDPReply=new DatagramPacket(bufferReply, bufferReply.length);;
		bufferRequest=request.getBytes();
		String reply;
		
		UDPRequest=new DatagramPacket(bufferRequest,bufferRequest.length,hostLR,portLR);
		try {
			synchronized (socketFE) {
		
				socketFE.send(UDPRequest);
	
				Logger.write(name,"Front End send Transfer Account request to Leader Replica from Client[" + oldIPAddressParam + "]!");
				System.out.println("Front End send Transfer Account request to Leader Replica from Client[" + oldIPAddressParam + "]!");			
			}
			synchronized (socketFE) {
				
				socketFE.receive (UDPReply);
				
				reply=new String(UDPReply.getData()).substring(0,UDPReply.getLength());
				Logger.write(name,"Front End recieve Transfer Account reply["+reply+"] from Leader Replica to Client[" + oldIPAddressParam + "]!");
				System.out.println("Front End recieve Transfer Account reply["+reply+"] from Leader Replica to Client[" + oldIPAddressParam + "]!");			
			}
			return reply;
		} catch (IOException e) {
			e.printStackTrace();
			return "Error in Front End";
		}
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public String getPlayerStatus(String adminUsernameParam, String adminPasswordParam, String iPAdressParam)  {
	
		String request="GetPlayerStatus"+"->"+adminUsernameParam+"->"+adminPasswordParam +"->"+iPAdressParam;
		byte[] bufferRequest=new byte[1000];
		DatagramPacket UDPRequest=null;
		byte[] bufferReply=new byte[1000];
		DatagramPacket UDPReply=new DatagramPacket(bufferReply, bufferReply.length);;
		bufferRequest=request.getBytes();
		String reply;
		
		UDPRequest=new DatagramPacket(bufferRequest,bufferRequest.length,hostLR,portLR);
		try {
			synchronized (socketFE) {
		
				socketFE.send(UDPRequest);
	
				Logger.write(name,"Front End send Get Status request to Leader Replica from AdminClient[" + iPAdressParam + "]!");
				System.out.println("Front End send Get Stutas request to Leader Replica from AdminClient[" + iPAdressParam + "]!");			
			}
			synchronized (socketFE) {
				
				socketFE.receive (UDPReply);
				
				reply=new String(UDPReply.getData()).substring(0,UDPReply.getLength());
				Logger.write(name,"Front End recieve Get Status reply["+reply+"] from Leader Replica to AdminClient[" + iPAdressParam + "]!");
				System.out.println("Front End recieve Get Status reply["+reply+"] from Leader Replica to AdminClient[" + iPAdressParam + "]!");			
			}
			return reply;
		} catch (IOException e) {
			e.printStackTrace();
			return "Error in Front End";
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public String suspendAccount(String adminUsernameParam, String adminPasswordParam, String iPAdressParam, String usernameSuspendParam) {
		String request="SuspendAccount"+"->"+adminUsernameParam+"->"+adminPasswordParam +"->"+iPAdressParam+"->"+usernameSuspendParam;
		byte[] bufferRequest=new byte[1000];
		DatagramPacket UDPRequest=null;
		byte[] bufferReply=new byte[1000];
		DatagramPacket UDPReply=new DatagramPacket(bufferReply, bufferReply.length);;
		bufferRequest=request.getBytes();
		String reply;
		
		UDPRequest=new DatagramPacket(bufferRequest,bufferRequest.length,hostLR,portLR);
		try {
			synchronized (socketFE) {
		
				socketFE.send(UDPRequest);
	
				Logger.write(name,"Front End send Suspend Account request to Leader Replica from AdminClient[" + iPAdressParam + "]!");
				System.out.println("Front End send Suspend Account request to Leader Replica from AdminClient[" + iPAdressParam + "]!");			
			}
			synchronized (socketFE) {
				
				socketFE.receive (UDPReply);
				
				reply=new String(UDPReply.getData()).substring(0,UDPReply.getLength());
				Logger.write(name,"Front End recieve Suspend Account reply["+reply+"] from Leader Replica to AdminClient[" + iPAdressParam + "]!");
				System.out.println("Front End recieve Suspend Account reply["+reply+"] from Leader Replica to AdminClient[" + iPAdressParam + "]!");			
			}
			return reply;
		} catch (IOException e) {
			e.printStackTrace();
			return "Error in Front End";
		}
	}	
	
	public void main(String args[]){
		
		try{ 
			 orb = ORB.init(args, null); 
			 POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			 
			 FrontEndImpl frontEnd = new FrontEndImpl();
					 
			byte[] idFE = rootPOA.activate_object(frontEnd);
				
			org.omg.CORBA.Object refFE = rootPOA.id_to_reference(idFE);
			
			String iorFE = orb.object_to_string(refFE);
			
			
			PrintWriter fileFE = new PrintWriter("..\\iorFE.txt");
			
			fileFE.println(iorFE);
			fileFE.close();	
								
			// wait for invocations from clients 
			rootPOA.the_POAManager().activate();
			orb.run();
			 
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}
