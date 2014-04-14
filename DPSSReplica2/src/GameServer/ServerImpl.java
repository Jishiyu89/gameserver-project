package GameServer;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;


public class ServerImpl implements Runnable {
	Hashtable<Integer,List<Player>> hash;
	int onlinePlayerNumber, playerNumber;
	int portNumber;
	BufferedWriter bw;
	SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String name;
	DatagramSocket aSocket;
	
	ServerImpl(String Name,int portnumber)throws Exception{
		List<Player> templ;
		this.portNumber=portnumber;
		name=Name;
		playerNumber=0;
		onlinePlayerNumber=0;
		hash=new Hashtable<Integer,List<Player>>();
		hash.clear();
		aSocket= new DatagramSocket(portNumber);
		try{
			bw=new BufferedWriter( new FileWriter("log/"+name+".txt",true));
			for(int i=0;i<26;i++){
				templ=new ArrayList<Player>();
				templ.clear();
				this.hash.put(i,templ)	;	
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		System.out.println("Server "+name+" is up and running!");
	}
	//thread which deal with the UDP request and reply
	public synchronized void run() {
		
		 try{
			 
			 DatagramPacket request;
			 DatagramPacket reply;
			 //...
			 byte[] bufferRequest = new byte[1000];
			 byte[] bufferReply = new byte[1000];
			 while(true){
				
				 //receive request form other server
				request = new DatagramPacket(bufferRequest, bufferRequest.length);
				aSocket.receive(request);
				
				//reply
				if(new String(request.getData()).substring(0,request.getLength()).equals("Status"))
				{
					
					String response=getLocalStatus();
					bufferReply=response.getBytes();
					reply= new DatagramPacket(bufferReply,response.length(), request.getAddress(),request.getPort());
					
					aSocket.send(reply);
				}
				else if(new String(request.getData()).substring(0,request.getLength()).equals("Transfer"))
				{
					aSocket.receive(request);
					String[] playerInformation=new String[10];
					playerInformation=new String(request.getData()).substring(0,request.getLength()).split("->");
					
					Player tranPlayer=new Player(playerInformation[0],playerInformation[1],playerInformation[2],
							Integer.parseInt(playerInformation[4]),playerInformation[3],playerInformation[6]);
					
					int index=playerInformation[2].charAt(0)-'a';
					synchronized(hash.get(index)){
						hash.get(index).add(tranPlayer);
						playerNumber++;
					}
					
					
				}
			}
		 }catch(SocketException e){ System.out.println("Socket 1" + e.getMessage());
		 }catch(IOException e){ System.out.println("IO" + e.getMessage());
		 }finally{ if(aSocket != null) aSocket.close();}
	 }
	
	//get the information of the number of players
	private String getLocalStatus() {
		
		//String reply=""+name+": "+onlinePlayerNumber+" online, "+(playerNumber-onlinePlayerNumber)+" offline. ";
		String reply="Server " + name + ":"+ "Total players:" + playerNumber + " Online:" + onlinePlayerNumber + " Offline: " + (playerNumber-onlinePlayerNumber);
		return reply;
	}
	
	public String createPlayerAccount(String firstName,
			String lastName, String userName, String password,
			short age, String IP) {
		String reply="";
		try
		{
			bw.write("["+dateFormat.format(new Date())+"]Server["+name+"]:Get the request for Create Player Account from PlayerClient["+IP+"]."); 
			bw.newLine();
		
			Player player=new Player(firstName,lastName,userName,age,password,IP);
			
			//Get the index of list
			int index=userName.charAt(0)-'a';
			
			//check the player exists or not
			if (hash.get(index).contains(player))
				reply= "Username [" + player.userName+ "] already exists on the game server!";
			else {
				
				//add player to the list
				List<Player> tempL=hash.get(index);
				synchronized (tempL){
					tempL.add(player);
					playerNumber++;
				}
				
				reply= "Username [" + player.userName+"] successfully created on the requested game server!";
				
			}
			bw.write("["+dateFormat.format(new Date())+"]Server["+name+"] Reply to:"+reply );
			bw.newLine();
		 	bw.flush();
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		return reply;
	}

	
	public String playerSignIn(String userName, String password,
			String IP) {
		String reply="";
		 try
		 {
			bw.write("["+dateFormat.format(new Date())+"]Server["+name+"]:Get the request for Sign In from PlayerClient["+IP+"]."); 
			bw.newLine();
			
			//Find out which list this user belongs to
			int listIndex= userName.charAt(0)-'a';
			Player player=new Player(userName);
		 
			// Check the list whether contains the play which has same userName
			if (!hash.get(listIndex).contains(player))
				reply= "Username ["+ player.userName + "] does not exist on game server!";					
			
			else{
				int playIndex=hash.get(listIndex).indexOf(player);
				//get the player
				Player tempplay=hash.get(listIndex).get(playIndex);
				// check the password 
				if(!tempplay.getPSW().equals(password))
					reply="Password for username ["+ userName + "] is incorrect!";
				else {
					if(tempplay.getStatus())
						reply= "User ["+ userName + "] is already online on game server!";
					else
					{
						synchronized(tempplay)
						{
							//Change status
							tempplay.setStatus(true);
							
							onlinePlayerNumber++;
						}

						reply="User ["+ userName + "] successfully signed in on game server!";
					}
				}
		 	}
		 	bw.write("["+dateFormat.format(new Date())+"]Server["+name+"] Reply:"+reply );
		 	bw.newLine();
		 	bw.flush();
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		return reply;
	}

	public String playerSignOut(String userName, String IP) {
		 String reply="";
		 try
		 {
			bw.write("["+dateFormat.format(new Date())+"]Server["+name+"]:Get the request for Sign In from PlayerClient["+IP+"]."); 
			bw.newLine();
			
			// get the index of the lists
			int index=userName.charAt(0)-'a';
			
			// check the list whether contains the user account
			Player player=new Player(userName);
			if (!hash.get(index).contains(player))
				reply= "Username ["+ player.userName + "] does not exist on game server!";
			else{
				
				//get index of player 
				int tempIndex=hash.get(index).indexOf(player);
				
				//change the status
				Player tempp=hash.get(index).get(tempIndex);
				if(!tempp.getStatus())
					reply= "User ["+ player.userName + "] is not online on game server!";
				else {
								  
					synchronized(tempp){
						onlinePlayerNumber--;
						tempp.setStatus(false);
					}			  
					reply= "Username ["+ userName + "] successfully signed out of game server!";
				}
			}
			bw.write("["+dateFormat.format(new Date())+"]Server["+name+"] Reply:"+reply );
			bw.newLine();
		 	bw.flush();
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		return reply;
	}
	
	//transfer Account function which return string
	public String transferAccount (String userName, String password,String oldIP,String newIP)
	{
		String reply="";
		 try
		 {
			bw.write("["+dateFormat.format(new Date())+"]Server["+name+"]:Get the request for Transfer Account from PlayerClient["+oldIP+"]."); 
			bw.newLine();
		
			// get the index of the lists
			int listIndex=userName.charAt(0)-'a';
			
			// check the list whether contains the user account
			Player player=new Player(userName);
			
			if (!hash.get(listIndex).contains(player))
				reply= "Username ["+ player.userName + "] does not exists on game server!";
			else{
				
				//get index of player 
				int playerIndex=hash.get(listIndex).indexOf(player);
				
				Player tempplay=hash.get(listIndex).get(playerIndex);
				// check the password 
				if(!tempplay.getPSW().equals(password))
					reply= "Password for username ["+ player.userName + "] is incorrect!";
				else {
					
					synchronized(hash.get(listIndex))
					{
						
						int ServerPortNum=0;
						DatagramPacket request;
						
						aSocket = new DatagramSocket();
						InetAddress aHost = InetAddress.getByName("localhost");
						
						//figure out the port number of other 2 severs
						String[] IPpart=new String[6];
						IPpart=newIP.split("\\.");
						
						switch(Integer.parseInt(IPpart[0])){
						case 93:
							ServerPortNum=1000;
							
							break;
						case 132:
							ServerPortNum=2000;
							
							break;
						case 182:
							ServerPortNum=3000;
							
							break;
						}
						
						//send request to other servers
						request= new DatagramPacket("Transfer".getBytes(),"Transfer".length(),aHost, ServerPortNum);
						aSocket.send(request);
						
						String message=tempplay.firstName+"->"+tempplay.lastName+"->"+tempplay.userName+"->"+tempplay.psw+"->"
								+tempplay.age+"->"+oldIP+"->"+newIP;
						
						request= new DatagramPacket(message.getBytes(),message.length(),aHost, ServerPortNum);
						aSocket.send(request);
						
						bw.write("["+dateFormat.format(new Date())+"] Transfer User ["+tempplay.userName+"] sucessfully.");
						
						//remove the player from the list
						if(tempplay.getStatus())onlinePlayerNumber--;
						playerNumber--;
						hash.get(listIndex).remove(playerIndex);
						reply="Username ["+ tempplay.userName + "] successfully transferred from game server!";
												
					}
				}
			}
			
		 	bw.flush();
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		return reply;
		
	}

	//get player status function which return string
	public String getPlayerStatus(String adminName,	String adminPassword, String IP) {
		String response="";
		 try
			{
				bw.write("["+dateFormat.format(new Date())+"]Server["+name+"]:Get the request for Create Player Account from PlayerClient["+IP+"]."); 
				bw.newLine();
				DatagramSocket Socket = null;
				Socket = new DatagramSocket();
				//Check the name and password
				if((!adminName.equals("Admin")) || (!adminPassword.equals("Admin")))
					response= "Invalid Administator user name or password!";
				//else if(!adminPassword.equals("Admin"))
				//	response= "Wrong Password.";
				else{
					
					
					int adminServerPortNum1=0,adminServerPortNum2=0;
					DatagramPacket request1,request2;
					
					
					InetAddress aHost = InetAddress.getByName("localhost");
					
					//figure out the port number of other 2 severs
					switch(portNumber){
					case 1000:
						adminServerPortNum1=2000;
						adminServerPortNum2=3000;
						break;
					case 2000:
						adminServerPortNum1=1000;
						adminServerPortNum2=3000;
						break;
					case 3000:
						adminServerPortNum1=1000;
						adminServerPortNum2=2000;
						break;
					}
					
					//send request to other servers
					request1= new DatagramPacket("Status".getBytes(),"Status".length(),aHost, adminServerPortNum1);//Send the userName of Administer
					Socket.send(request1);
					
					request2= new DatagramPacket("Status".getBytes(),"Status".length(),aHost, adminServerPortNum2);// Send the Password of Administer
					Socket.send(request2);
					
					//receive reply
					byte[] buffer = new byte[1000];
					DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
					Socket.receive(reply);
					bw.write("["+dateFormat.format(new Date())+"] Get response from Server"+new String(reply.getData()).substring(0,reply.getLength()));
					
					//response=""+name+": "+onlinePlayerNumber+" online, "+(playerNumber-onlinePlayerNumber)+" offline "+new String(reply.getData()).substring(0,reply.getLength());
					response="Server " + name + ":"+ "Total players:" + playerNumber + " Online:" + onlinePlayerNumber + " Offline: " + (playerNumber-onlinePlayerNumber) + "#" +new String(reply.getData()).substring(0,reply.getLength());
					
					Socket.receive(reply);
					bw.write("["+dateFormat.format(new Date())+"] Get response from Server"+new String(reply.getData()).substring(0,reply.getLength()));

					response=response+"#"+new String(reply.getData()).substring(0,reply.getLength());
					
				}
			bw.write("["+dateFormat.format(new Date())+"] Server ["+name+"]Send reply to Administer["+IP+"]:"+response);
			bw.flush();
			Socket.close();
			}catch(SocketException e){ System.out.println("Socket " + e.getMessage());
			}catch(IOException e){ System.out.println("IO" + e.getMessage());
			}
		 	
			return response;
	}
	
	//suspend Account function which return string
	public String suspendAccount(String adminUsername, String adminPassword, String IP, String userName){
		String reply="";
		 try
		 {
			bw.write("["+dateFormat.format(new Date())+"]Server["+name+"]:Get the request for Suspend Account from AdminClient["+IP+"]."); 
			bw.newLine();
			
			if((!adminUsername.equals("Admin")) || (!adminPassword.equals("Admin")))
				reply= "Invalid Administator user name or password!";
			else{
				
				
				//Find out which list this user belongs to
				int listIndex= userName.charAt(0)-'a';
				Player player=new Player(userName);
			 
				// Check the list whether contains the play which has same userName
				if (!hash.get(listIndex).contains(player))
					reply="Username ["+ player.userName + "] does not exist on game server!";
				else{
					int playerIndex=hash.get(listIndex).indexOf(player);
					//get the player
					Player tempplay=hash.get(listIndex).get(playerIndex);
							synchronized(hash.get(listIndex))
							{
								//remove the player from the list
								if(tempplay.getStatus())onlinePlayerNumber--;
								playerNumber--;
								hash.get(listIndex).remove(playerIndex);					
								
							}
							reply= "Username ["+ userName + "] successfully removed from game server!";
					
			 	}
			 	bw.write("["+dateFormat.format(new Date())+"]Server["+name+"] Reply:"+reply );
			 	bw.newLine();
			 	bw.flush();
			 	
			}
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		return reply;
	}
	public void stop(){
		this.aSocket.close();
	}
}
