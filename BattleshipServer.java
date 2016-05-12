/* BattleshipServer.java
*  
*  This class acts as the server for the game
*  It will collect an arraylist from each player on where their ships are located.
*  Then, it will check if the player's guess is in the arraylist.
*  It will send its results back to the clients.
*
*  Editors: Julien Fournell, Austin Ayers
*/

import java.io.*;
import java.net.*;
import java.util.*;

class BattleshipServer
{
	public static void main(String args[]) throws Exception
	{
		// Variables
		DatagramSocket serverSocket = null;

		int port = 0, port1 = 0, port2 = 0;
		InetAddress IPAddress=null, IPAddress1=null, IPAddress2=null;

		DatagramPacket receivePacket = null;
    	DatagramPacket sendPacket1 = null;
	    DatagramPacket sendPacket2 = null;

    	String message = "";
    	String response = "";

        ArrayList<String> playerBoard1 = new ArrayList<String>();
        ArrayList<String> playerBoard2 = new ArrayList<String>();

    	int state = 0;
        boolean firstClientTurn = true;

    	byte[] receiveData = new byte[1024];
    	byte[] sendData  = new byte[1024];
    	byte[] messageBytes = new byte[1024];

    	// Trying to open the server socket
    	try
    	{
	   		serverSocket = new DatagramSocket(9876);
      		System.out.println("Starting Server");
    	}

    	catch(Exception e)
		{
			System.out.println("Failed to open UDP socket");
      		System.out.println("Closing Server");
			System.exit(0);
		}

		// Main Code
    	while(state<3)
    	{
    		receiveData = new byte[1024];
    		sendData  = new byte[1024];
    		switch(state)
    		{
    			case 0: // Wait for the first connection
    				receivePacket = new DatagramPacket(receiveData, receiveData.length);
    				serverSocket.receive(receivePacket);
    				message = new String(receivePacket.getData());
    				message = message.trim();
    				if (message.contains("HELLO SERVER"))
    				{

                        boolean getData = true;
                        while(getData)
                        {
                            if(playerBoard1.size() < 17)
                            {
                                receivePacket = new DatagramPacket(receiveData, receiveData.length);
                                serverSocket.receive(receivePacket);
                                message = new String(receivePacket.getData());
                                message = message.trim();
                                playerBoard1.add(message.substring(0,message.indexOf("L"))); 
                            }
                            else
                            {
                                getData = false;
                            }  
                        }
    					
                        System.out.println("Connection 1");
    					response = "100";
    					sendData = response.getBytes();
    					IPAddress1 = receivePacket.getAddress();
    					port1 = receivePacket.getPort();
    					sendPacket1 = new DatagramPacket(sendData, sendData.length, IPAddress1, port1);
    					serverSocket.send(sendPacket1);
    					state = 1;
    				}
    				break;

    			case 1: // Wait for second connection
    				receivePacket = new DatagramPacket(receiveData, receiveData.length);
    				serverSocket.receive(receivePacket);
    				message = new String(receivePacket.getData());
    				message = message.trim();
    				if (message.contains("HELLO SERVER"))
    				{
                        boolean getData = true;
                        while(getData)
                        {
                            if(playerBoard2.size() < 17)
                            {
                                receivePacket = new DatagramPacket(receiveData, receiveData.length);
                                serverSocket.receive(receivePacket);
                                message = new String(receivePacket.getData());
                                message = message.trim();
                                playerBoard2.add(message.substring(0,message.indexOf("L"))); 
                            }
                            else
                            {
                                getData = false;
                            }  
                        }

                        System.out.println("Connection 2");
    					response = "200";
    					sendData = response.getBytes();
    					IPAddress2 = receivePacket.getAddress();
    					port2 = receivePacket.getPort();
    					sendPacket1 = new DatagramPacket(sendData, sendData.length, IPAddress1, port1);
    					serverSocket.send(sendPacket1);
    					sendPacket2 = new DatagramPacket(sendData, sendData.length, IPAddress2, port2);
    					serverSocket.send(sendPacket2);
    					state = 2;
    				}
    				break;

    			case 2: // Send messages to and from the clients

                    // String arr1 = "";
                    // String arr2 = "";
                    // for (int a = 0; a < playerBoard1.size(); a++) 
                    // {
                    //         arr1 += (playerBoard1.get(a) + " . ");
                    //         arr2 += (playerBoard2.get(a) + " . ");
                    // }

                    // System.out.println(arr1);
                    // System.out.println(arr2);

                    String r1 = "";
                    String r2 = "";

    				receivePacket = new DatagramPacket(receiveData, receiveData.length);
    				serverSocket.receive(receivePacket);
    				message = new String(receivePacket.getData());
    				message = message.trim();

    				// Check to see if either client sent "Goodbye"
    				// if (message.toUpperCase().contains("GOODBYE"))
    				// {
    				// 	state = 3;
    				// 	break;
    				// }

    				IPAddress = receivePacket.getAddress();
    				port = receivePacket.getPort();

                    if(firstClientTurn)
                    {
                        if(playerBoard2.indexOf(message) > -1)
                        {
                            r1 = "1";
                            sendData = r1.getBytes();
                            sendPacket1 = new DatagramPacket(sendData, sendData.length, IPAddress1, port1);
                        }
                        else
                        {
                            r1 = "0";
                            sendData = r1.getBytes();
                            sendPacket1 = new DatagramPacket(sendData, sendData.length, IPAddress1, port1);
                        }

                        r2 = message;
                        sendData = r2.getBytes();
                        sendPacket2 = new DatagramPacket(sendData, sendData.length, IPAddress2, port2);
                        serverSocket.send(sendPacket1);
                        serverSocket.send(sendPacket2);
                        firstClientTurn = false;
                    }
                    else
                    {
                        if(playerBoard1.indexOf(message) > -1)
                        {
                            r2 = "1";
                            sendData = r2.getBytes();
                            sendPacket2 = new DatagramPacket(sendData, sendData.length, IPAddress2, port2);
                        }
                        else
                        {
                            r2 = "0";
                            sendData = r2.getBytes();
                            sendPacket2 = new DatagramPacket(sendData, sendData.length, IPAddress2, port2);
                        }
                        
                        r1 = message;
                        sendData = r1.getBytes();
                        sendPacket1 = new DatagramPacket(sendData, sendData.length, IPAddress1, port1);
                        serverSocket.send(sendPacket1);
                        serverSocket.send(sendPacket2);
                        firstClientTurn = true;
                    }

    				break;
    			default:
    				break;
    		}
    	}
    	sendData = "Goodbye".getBytes();
    	sendPacket1 = new DatagramPacket(sendData, sendData.length, IPAddress1, port1);
    	serverSocket.send(sendPacket1);
    	sendPacket2 = new DatagramPacket(sendData, sendData.length, IPAddress2, port2);
    	serverSocket.send(sendPacket2);
    	serverSocket.close();
	}
}