import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

class MainFrame extends JFrame
{
	public MainFrame()
	{
		super("***BATTLESHIP***");
		setLayout(new BorderLayout(0,0));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public static void main(String[] args) throws Exception
	{
		// Game Setup
		MainFrame window = new MainFrame();
		window.setSize(800,700);

		JPanel topBoard = new JPanel();		//Stores top grid in a square
		topBoard.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));

		JPanel bottomBoard = new JPanel();	//Stores Bottom Grid in a square
		bottomBoard.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));

		JPanel commandPanel = new JPanel();	//Makes Panel on side where commands will show
		commandPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		String commandPanelText = "****** This is the Command Panel ******";
		JLabel cP = new JLabel(commandPanelText, JLabel.CENTER);
		commandPanel.add(cP);

		JPanel titlePanel = new JPanel();
		JLabel title = new JLabel("WELCOME TO BATTLESHIP!", JLabel.CENTER);
		titlePanel.add(title);

		GridPanel attackPanel = new GridPanel(10,10); //makes grid to choose where to attack
		topBoard.add(attackPanel);
		for (int i = 0; i < 10; i++) 
		{
			for (int j = 0; j < 10; j++) 
			{
					attackPanel.btnArr[i][j].setEnabled(false); //Sets buttons on Top board to unclickable
			}	
		}
		GridPanel choosingPanel = new GridPanel(10,10); //makes grid to place ships
		bottomBoard.add(choosingPanel);

		JPanel mainPanel = new JPanel();	//Puts Grid Panels into One Panel
		mainPanel.add(topBoard,BorderLayout.NORTH);
		mainPanel.add(bottomBoard,BorderLayout.SOUTH);

		window.add(titlePanel, BorderLayout.NORTH);
		window.add(commandPanel, BorderLayout.EAST);
		window.add(mainPanel, BorderLayout.CENTER);
		window.setVisible(true);

		// Networking
		boolean talk = false;
		int state = 0;
		String response="";

		DatagramSocket clientSocket = new DatagramSocket();
 		InetAddress IPAddress = InetAddress.getByName("localhost");

 		String message = "HELLO SERVER";
 		byte[] sendData = new byte[1024];
 		sendData = message.getBytes();
		DatagramPacket sendPacket=null;

 		byte[] receiveData = new byte[1024];
		DatagramPacket receivePacket = null;
		String receivedMessge;

		while (state < 3){
			sendData = new byte[1024];
			receiveData = new byte[1024];
			switch (state){
				case 0:
					// send initial message to server and wait for response
					sendData = message.getBytes();
					sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress,9876);
					clientSocket.send(sendPacket);

					commandPanel.remove(cP);
					commandPanelText = "\nAttempting to connect to server.";
					cP = new JLabel(commandPanelText, JLabel.CENTER);
					commandPanel.add(cP);
            		window.revalidate();


					receivePacket = new DatagramPacket(receiveData, receiveData.length);
					clientSocket.receive(receivePacket);
					response = new String(receivePacket.getData());
          			response = response.trim();

          			//System.out.println(response); //either 100 or 200

					if (response.substring(0,3).equals("100")) {
						state = 1; //You are first client. wait for second client to connect
						commandPanel.remove(cP);
            			commandPanelText = "\nYou are the first to\n connect to the server.";
            			cP = new JLabel(commandPanelText, JLabel.CENTER);
            			talk = true;
            			commandPanel.add(cP);
            			window.revalidate();
					}

					else if (response.substring(0,3).equals("200")) {
						state = 2; //you are second client. Wait for message from first client
						commandPanel.remove(cP);
						commandPanelText = "\nYou are the second to\n connect to the server.";
            			cP = new JLabel(commandPanelText, JLabel.CENTER);
            			commandPanel.add(cP);
            			window.revalidate();
					}

					break;

				case 1:

					// Waiting for notification that the second client is ready
					receivePacket = new DatagramPacket(receiveData, receiveData.length);
					clientSocket.receive(receivePacket);
					response = new String(receivePacket.getData());
          			response = response.trim();

          			//System.out.println(response); // prints either 100 or 200

					if (response.substring(0,3).equals("200")) 
					{
						//get message from user and send it to server
						commandPanel.remove(cP);
						commandPanelText = "\nAnother player has connected";
            			cP = new JLabel(commandPanelText, JLabel.CENTER);
           				state = 2;
           				commandPanel.add(cP);
           				window.revalidate();
					}

					//state = 2; //transition to state 2: chat mode
					break;

				case 2:
					//Chat mode
					// if (talk) {
					// 	System.out.print(clientName);
					// 	response = clientName + inFromUser.readLine();
					// 	sendData = response.getBytes();
					// 	sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
					// 	clientSocket.send(sendPacket);
					// 	talk = false;
					// }

					// else {
					// 	//receive message from other client
					// 	receivePacket = new DatagramPacket(receiveData, receiveData.length);
					// 	clientSocket.receive(receivePacket);
					// 	response = new String(receivePacket.getData());
					// 	response = response.trim();

					// 	if (response.length()>= 7 && response.toLowerCase().contains("goodbye")) {
					// 		state = 3; //prepare to exit the while loop
					// 		System.out.println("goodbye");
					//   		System.out.println("*** chat has been ended ***");
					// 		break;
					// 	}

					// 	else {
					//  		System.out.println(response);
					//   		talk = true;
					// 	}
     //      			}
          			break;

          		default:
          			break;
			}
		}
		//close the socket
		clientSocket.close();
	}
}