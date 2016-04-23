import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class MainFrame extends JFrame
{
	public MainFrame()
	{
		super("***BATTLESHIP***");
		setLayout(new BorderLayout(0,0));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public static void main(String[] args)
	{
		MainFrame window = new MainFrame();
		window.setSize(800,700);

		JPanel topBoard = new JPanel();		//Stores top grid in a square
		topBoard.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));

		JPanel bottomBoard = new JPanel();	//Stores Bottom Grid in a square
		bottomBoard.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));

		JPanel commandPanel = new JPanel();	//Makes Panel on side where commands will show
		commandPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		JLabel cP = new JLabel("****** This is the Command Panel ******", JLabel.CENTER);
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



	}
}