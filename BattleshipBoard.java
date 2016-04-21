import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class BattleshipBoard extends JFrame
{
    private static final String INITIAL_TEXT = "Nothing Pressed";
    private static final String ADDED_TEXT = " was Pressed";
    private JLabel positionLabel_attack;
    private JLabel positionLabel_myBoard;
    private JButton resetButton;
    private static int gridSize = 10;

    public BattleshipBoard()
    {
        super("***BATTLESHIP***");
    }

    private void createAndDisplayGUI()
    {       
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //once window is closed, program will stop

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        contentPane.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));

        JPanel myShipsPanel = new JPanel();
        myShipsPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        myShipsPanel.setLayout(new BoxLayout(myShipsPanel, BoxLayout.Y_AXIS));    
        
        JPanel labelPanel_myBoard = new JPanel();
        positionLabel_myBoard = new JLabel(INITIAL_TEXT, JLabel.CENTER);

        JPanel myBoard = new JPanel();
        myBoard.setLayout(new GridLayout(gridSize, gridSize, 10, 10));
        for (int i = 0; i < gridSize; i++)
        {
            for (int j = 0; j < gridSize; j++)
            {
                JButton button = new JButton("(" + i + ", " + j + ")");
                button.setActionCommand("(" + i + ", " + j + ")");
                button.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent ae)
                    {
                        JButton but = (JButton) ae.getSource();
                        positionLabel_myBoard.setText(but.getActionCommand() + ADDED_TEXT);  
                        but.setVisible(false);                         
                    }
                });
                myBoard.add(button);
  			}
  		}
  		labelPanel_myBoard.add(positionLabel_myBoard);
  		myShipsPanel.add(labelPanel_myBoard);
        myShipsPanel.add(myBoard);
        contentPane.add(myShipsPanel);

        JPanel attackPanel = new JPanel();
        attackPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        attackPanel.setLayout(new BoxLayout(attackPanel, BoxLayout.Y_AXIS));

        JPanel labelPanel_attack = new JPanel();
        positionLabel_attack = new JLabel(INITIAL_TEXT, JLabel.CENTER);

        JPanel other = new JPanel();
        other.setLayout(new GridLayout(gridSize, gridSize, 10, 10));
        for (int i = 0; i < gridSize; i++)
        {
            for (int j = 0; j < gridSize; j++)
            {
                JButton button = new JButton("(" + i + ", " + j + ")");
                button.setActionCommand("(" + i + ", " + j + ")");
                button.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent ae)
                    {
                        JButton but = (JButton) ae.getSource();
                        positionLabel_attack.setText(but.getActionCommand() + ADDED_TEXT);                           
                    }
                });
                other.add(button);
            }
        }
        labelPanel_attack.add(positionLabel_attack);
        attackPanel.add(labelPanel_attack);
        attackPanel.add(other);
        contentPane.add(attackPanel);

        setContentPane(contentPane);
        pack();
        setLocationByPlatform(true);
        setVisible(true);
    }

    public static void main(String[] args)
    {
        BattleshipBoard board = new BattleshipBoard();
        board.createAndDisplayGUI();
    }
}