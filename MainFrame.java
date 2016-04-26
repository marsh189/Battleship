import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

class MainFrame extends JFrame
{
	static JFrame window;
	static JPanel mainPanel = new JPanel();	//Puts Grid Panels into One Panel
	static JPanel topBoard = new JPanel();		//Stores top grid in a square
	static JPanel bottomBoard = new JPanel();	//Stores Bottom Grid in a square
	static JPanel commandPanel = new JPanel();	//Makes Panel on side where commands will show
	static JLabel directions;					//Tells what the player must do/ What is going on
	static String commandPanelText;	

	final static JPanel titlePanel = new JPanel();	//title of Game is stored here		

	public MainFrame()
	{
		super("***BATTLESHIP***");
		setLayout(new BorderLayout(0,0));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public static void main(String[] args) throws Exception
	{
		SetupBoard();
		PlaceShips();
		ConnectToServer();
	}

	//Sets up the beginning look of the board
	public static void SetupBoard()
	{
		window = new MainFrame();
		window.setSize(1000,700);
		topBoard.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		bottomBoard.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		commandPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		commandPanel.setLayout(new BorderLayout(0,0));

		JLabel cP = new JLabel("****** This is the Command Panel ******", JLabel.CENTER);
		directions = new JLabel("", JLabel.CENTER);

		commandPanel.add(cP, BorderLayout.NORTH);
		commandPanel.add(directions, BorderLayout.CENTER);

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
	}

	//Player will place their ship pieces on board
	public static void PlaceShips()
	{

	}

	//Connects the client to the server
	public static void ConnectToServer()
	{
		try
		{
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

			while (state < 3)
			{
				sendData = new byte[1024];
				receiveData = new byte[1024];
				switch (state)
				{
					case 0:
						// send initial message to server and wait for response
						sendData = message.getBytes();
						sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress,9876);
						clientSocket.send(sendPacket);
						


						receivePacket = new DatagramPacket(receiveData, receiveData.length);
						clientSocket.receive(receivePacket);
						response = new String(receivePacket.getData());
	          			response = response.trim();

						if (response.substring(0,3).equals("100")) 
						{
							state = 1; //You are first client. wait for second client to connect

	            			System.out.println("You are the first to connect to the server.");
	            			System.out.println("Waiting for Secong Player");
	            			commandPanelText = "Waiting for Second Player";
	            			directions.setText(commandPanelText);
	            			talk = true;
	            			window.revalidate();
						}

						else if (response.substring(0,3).equals("200")) 
						{
							state = 2; //you are second client. Wait for message from first client
							
							commandPanelText = "Welcome Second Player";
							System.out.println("You were the second to connect to the sever.");

	            			directions.setText(commandPanelText);
	            			window.revalidate();
						}

						break;

					case 1:

						// Waiting for notification that the second client is ready
						receivePacket = new DatagramPacket(receiveData, receiveData.length);
						clientSocket.receive(receivePacket);
						response = new String(receivePacket.getData());
	          			response = response.trim();


						if (response.substring(0,3).equals("200")) 
						{
							//get message from user and send it to server
							
							commandPanelText = "Another player has connected";
	            			directions.setText(commandPanelText);
	           				state = 2;
	           				window.revalidate();
						}

						//state = 2; //transition to state 2: chat mode
						break;

					case 2:
	          			break;

	          		default:
	          			break;
				}
			}
			
			//close the socket
			clientSocket.close();
		}

		catch(Exception e)
		{
			System.out.println("Error: " + e.toString());
			System.exit(0);
		}
	}
}