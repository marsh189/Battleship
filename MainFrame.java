/* MainFrame.java
*
*  This class acts as the main window of the game.
*  It first sets up the look of the board and gets user to add ships.
*  This class also will connect the player to the server and play the game.
*  
*   Editors: Matt Marshall, Austin Ayers, Julien Fournell
*/
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

class MainFrame extends JFrame
{
	static JFrame window;
	static JPanel mainPanel = new JPanel();	//Puts Grid Panels into One Panel
	static JPanel topBoard = new JPanel();		//Stores top grid in a square
	static JPanel bottomBoard = new JPanel();	//Stores Bottom Grid in a square
	static JPanel commandPanel = new JPanel();	//Makes Panel on side where commands will show
	static GridPanel attackPanel = new GridPanel(10,10); //makes grid to choose where to attack
	static GridPanel choosingPanel = new GridPanel(10,10); //makes grid to place ships
	static JLabel directions;					//Tells what the player must do/ What is going on
	static String commandPanelText;
	static ArrayList<String> locations = new ArrayList<String>();	//Will store the locations of ships
	static int numPlaced = 0;					//How many pieces have been placed

	static DatagramSocket clientSocket;
	static InetAddress IPAddress;
	static byte[] sendData = new byte[1024];
	static byte[] receiveData = new byte[1024];

	public static boolean myTurn = false;

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

	//Sets up the beginning look of the board (implemented by Matt)
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

		topBoard.add(attackPanel);
		for (int i = 0; i < 10; i++) 
		{
			for (int j = 0; j < 10; j++) 
			{
					attackPanel.btnArr[i][j].setEnabled(false); //Sets buttons on Top board to unclickable
			}
		}

		bottomBoard.add(choosingPanel);

		//Puts Grid Panels into One Panel
		mainPanel.add(topBoard,BorderLayout.NORTH);
		mainPanel.add(bottomBoard,BorderLayout.SOUTH);

		window.add(titlePanel, BorderLayout.NORTH);
		window.add(commandPanel, BorderLayout.EAST);
		window.add(mainPanel, BorderLayout.CENTER);
		window.setVisible(true);
	}

	//Player will place their ship pieces on board (implemented by Matt)
	public static void PlaceShips()
	{

		for (int x = 0; x < 10; x++) 
		{
			for (int y = 0; y < 10; y++) 
			{
				choosingPanel.btnArr[x][y].addActionListener(new OnClick());
			}
		}

		String firstClicked = null;
		String secondClicked = null;

		while(numPlaced < 5)
		{
			if(choosingPanel.count == 0)
			{
				directions.setText("Select ONE Button.");
				if(choosingPanel.clickedTag == null)
				{
					firstClicked = choosingPanel.clickedTag;
				}
			}
			else if(choosingPanel.count == 2)
			{
				if(numPlaced == 0)
				{
					directions.setText("Now Choose a Button 4 spaces away");
					firstClicked = choosingPanel.clickedTag;
				}
				else if(numPlaced == 1)
				{
					directions.setText("Now Choose a Button 3 spaces away.");
					firstClicked = choosingPanel.clickedTag;
				}
				else if(numPlaced == 2 || numPlaced == 3)
				{
					directions.setText("Now Choose a Button 2 spaces away.");
					firstClicked = choosingPanel.clickedTag;
				}
				else if(numPlaced == 4)
				{
					directions.setText("Now Choose a Button 1 space away.");
					firstClicked = choosingPanel.clickedTag;
					choosingPanel.count = 3;
				}
			}
			else if(choosingPanel.count == 4)
			{
				secondClicked = choosingPanel.clickedTag;

				if(numPlaced == 0)
				{
					Ship(firstClicked, secondClicked, 4);
				}
				else if(numPlaced == 1)
				{
					Ship(firstClicked,secondClicked, 3);
				}
				else if(numPlaced == 2 || numPlaced == 3)
				{
					Ship(firstClicked, secondClicked, 2);
				}
				else if(numPlaced == 4)
				{
					System.out.println(secondClicked);
					int index = secondClicked.indexOf(",");
					int f =  Integer.parseInt(secondClicked.substring(0,index));
					int s = Integer.parseInt(secondClicked.substring(index + 1));
					choosingPanel.btnArr[f][s].setForeground(Color.GREEN);
					choosingPanel.btnArr[f][s].setBackground(Color.GREEN);
					choosingPanel.btnArr[f][s].setEnabled(false);


					if(!choosingPanel.btnArr[f][s].isEnabled())
					{

						numPlaced = 5;
					}
				}
			}

			System.out.println(choosingPanel.count + "," + numPlaced);  //Will not work correctly without this
		}
		directions.setText("All Ships have been set!");

		for (int x = 0; x < 10; x++) 
		{
			for (int y = 0; y < 10; y++) 
			{
				if(!choosingPanel.btnArr[x][y].isEnabled())
				{
					locations.add(choosingPanel.btnArr[x][y].Tag);
					System.out.println(choosingPanel.btnArr[x][y].Tag);
				}
				choosingPanel.btnArr[x][y].setEnabled(false);
			}
		}
	}

	//Will make the areas between the two button clicks green (implemented by Matt)
	public static void Ship(String f, String s, int dif)
	{
		int index = f.indexOf(",");
		int firstNum_firstClicked = Integer.parseInt(f.substring(0,index));
		int firstNum_secondClicked = Integer.parseInt(s.substring(0,index));

		int secondNum_firstClicked = Integer.parseInt(f.substring(index + 1));
		int secondNum_secondClicked = Integer.parseInt(s.substring(index + 1));

		//check if buttons are horizontal to each other
		if(firstNum_firstClicked == firstNum_secondClicked)
		{
			if(secondNum_firstClicked > secondNum_secondClicked)
			{
				if(secondNum_firstClicked - secondNum_secondClicked == dif)
				{
					for(int num = secondNum_secondClicked + 1; num <= secondNum_firstClicked; num++)
					{
						MyButton btn = choosingPanel.btnArr[firstNum_firstClicked][num];
						btn.setForeground(Color.GREEN);
						btn.setBackground(Color.GREEN);
						btn.setEnabled(false);
					}
					numPlaced++;
					choosingPanel.count = 0;
				}
			}
			else if(secondNum_secondClicked > secondNum_firstClicked)
			{
				if(secondNum_secondClicked - secondNum_firstClicked == dif)
				{
					for(int num = secondNum_firstClicked + 1; num <= secondNum_secondClicked; num++)
					{
						MyButton btn = choosingPanel.btnArr[firstNum_firstClicked][num];
						btn.setForeground(Color.GREEN);
						btn.setBackground(Color.GREEN);
						btn.setEnabled(false);
					}
					numPlaced++;
					choosingPanel.count = 0;
				}
			}
		}

		//check if buttons are vertical to each other
		else if(secondNum_firstClicked == secondNum_secondClicked)
		{
			if(firstNum_firstClicked > firstNum_secondClicked)
			{
				if(firstNum_firstClicked - firstNum_secondClicked == dif)
				{
					for(int num = firstNum_secondClicked + 1; num <= firstNum_firstClicked; num++)
					{
						MyButton btn = choosingPanel.btnArr[num][secondNum_firstClicked];
						btn.setForeground(Color.GREEN);
						btn.setBackground(Color.GREEN);
						btn.setEnabled(false);
					}
					numPlaced++;
					choosingPanel.count = 0;
				}
			}
			else if(firstNum_secondClicked > firstNum_firstClicked)
			{
				if(firstNum_secondClicked - firstNum_firstClicked == dif)
				{
					for(int num = firstNum_firstClicked + 1; num <= firstNum_secondClicked; num++)
					{
						MyButton btn = choosingPanel.btnArr[num][secondNum_firstClicked];
						btn.setForeground(Color.GREEN);
						btn.setBackground(Color.GREEN);
						btn.setEnabled(false);
					}
					numPlaced++;
					choosingPanel.count = 0;
				}
			}
		}
	}

	//Connects the client to the server (implemented by Austin)
	public static void ConnectToServer()
	{
		try
		{
			// Networking
			boolean talk = false;
			int state = 0;
			String response="";

			IPAddress = InetAddress.getByName("localhost");
			clientSocket = new DatagramSocket();

	 		String message = "HELLO SERVER";

	 		sendData = message.getBytes();
			DatagramPacket sendPacket=null;

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

						boolean sendArr = true;
						sendData = message.getBytes();
						sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress,9876);
						clientSocket.send(sendPacket);

						for (int x = 0; x < locations.size(); x++) 
						{
							message = locations.get(x);
							sendData = message.getBytes();
							sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress,9876);
							clientSocket.send(sendPacket);
						}
						
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
							System.out.println("Game Has Started");

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
							System.out.println("Game Has Started");
	            			directions.setText(commandPanelText);
	           				state = 2;
	           				myTurn = true;
	           				window.revalidate();
						}

						//state = 2; //transition to state 2: chat mode
						break;

					case 2:
						PlayGame();
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

	//look for button press and see if it is correct
	public static void PlayGame() throws Exception
	{
		boolean gameOver = false;
		boolean check = false;

		String message = "";
 		sendData = message.getBytes();
		DatagramPacket sendPacket=null;


		DatagramPacket receivePacket = null;
		String receivedMessge;

		while(!gameOver)
		{
			sendData = new byte[1024];
			receiveData = new byte[1024];

			if(myTurn)
			{
				for (int x = 0; x < 10; x++)
				{
					for (int y = 0; y < 10; y++)
					{
						attackPanel.btnArr[x][y].setEnabled(true);
						attackPanel.btnArr[x][y].addActionListener(new OnClick());
					}
				}

				while(attackPanel.count == 0)
				{
					directions.setText("Select an area to attack on the top board.");
				}

				if(attackPanel.count > 0)
				{
					for (int x = 0; x < 10; x++)
					{
						for (int y = 0; y < 10; y++)
						{
							attackPanel.btnArr[x][y].setEnabled(false);
						}
					}
					message = attackPanel.clickedTag;
					sendData = message.getBytes();
					sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress,9876);
					clientSocket.send(sendPacket);
					check = true;
					String response = "";

					while(check)
					{
						response = ReceiveMessage();

						if(Integer.parseInt(response) == 1)
						{
							//hit
							int index = attackPanel.clickedTag.indexOf(",");
							int first = Integer.parseInt(attackPanel.clickedTag.substring(0,index));
							int second = Integer.parseInt(attackPanel.clickedTag.substring(index + 1));
							attackPanel.btnArr[first][second].setForeground(Color.RED);
							attackPanel.btnArr[first][second].setBackground(Color.RED);
							check = false;
						}
						else if(Integer.parseInt(response) == 0)
						{
							//miss
							int index = attackPanel.clickedTag.indexOf(",");
							int first = Integer.parseInt(attackPanel.clickedTag.substring(0,index));
							int second = Integer.parseInt(attackPanel.clickedTag.substring(index + 1));
							attackPanel.btnArr[first][second].setForeground(new Color(128,128,128));
							attackPanel.btnArr[first][second].setBackground(new Color(128,128,128));
							check = false;
						}
					}
					myTurn = false;
					attackPanel.count = 0;
					attackPanel.clickedTag = null;
					response = null;
				}
			}

			else
			{
				directions.setText("Waiting on other Player");
				check = true;
				while(check)
				{
					String r = ReceiveMessage();
					boolean hit = false;
					for (int i = 0; i < locations.size(); i++) 
					{
						if(r.equals(locations.get(i)))
						{
							hit = true;
							break;
						}
						else
						{
							hit = false;
						}
					}
					if(hit)
					{
						//hit
						int index = r.indexOf(",");
						int first = Integer.parseInt(r.substring(0,index));
						int second = Integer.parseInt(r.substring(index + 1));
						choosingPanel.btnArr[first][second].setForeground(Color.RED);
						choosingPanel.btnArr[first][second].setBackground(Color.RED);
						check = false;
					}
					else
					{
						//miss
						int index = r.indexOf(",");
						int first = Integer.parseInt(r.substring(0,index));
						int second = Integer.parseInt(r.substring(index + 1));
						choosingPanel.btnArr[first][second].setForeground(new Color(128,128,128));
						choosingPanel.btnArr[first][second].setBackground(new Color(128,128,128));
						check = false;
					}
				}
				myTurn = true;
			}
		}
	}

	public static String ReceiveMessage() throws Exception
	{
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

		clientSocket.receive(receivePacket);
		String receivedSentece = new String(receivePacket.getData());
		String returnStr = receivedSentece.trim();
		return returnStr;
	}
}