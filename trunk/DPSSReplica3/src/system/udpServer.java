package system;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class udpServer {
	
		
		public static void main(String[] args) 
		{
			
			DatagramSocket socket = null;
			
			try 
			{
				
				socket = new DatagramSocket(1500);
				byte[] buffer = new byte[15000];
				
				while(true)
				{
					DatagramPacket request = new DatagramPacket(buffer,buffer.length);
					socket.receive(request);
					
					DatagramPacket reply = new DatagramPacket(request.getData(), request.getLength(), request.getAddress(), request.getPort());
					socket.send(reply);
				}
			}
			 
			catch (Exception e) 
			 {
				System.out.println("Warning -  " + e.getMessage());
			}

		}
	}

