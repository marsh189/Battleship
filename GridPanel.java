import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class GridPanel extends JPanel
{
	public JButton[][] btnArr = new JButton[10][10];

	//Makes Grid of 100 buttons in a JPanels
	public GridPanel(int x, int y)
	{
		setLayout(new GridLayout(x,y,2,2));
		setGrid(x,y);
	}

	public void setGrid(int col, int row)
	{
		for (int i = 0; i < 10; i++) 
		{
			for (int j = 0; j < 10; j++) 
			{
				MyButton btn = new MyButton(i + "," + j);
				btn.setVisible(true);	
				btnArr[i][j] = btn;
				btn.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent ae)
                    {
                        MyButton but = (MyButton) ae.getSource();
                        but.setForeground(Color.GREEN);
                        but.setBackground(Color.GREEN);
                       	but.setEnabled(false);
                    }
                });
				this.add(btn);
			}
		}
	}

	// public void paint(Graphics g)
	// {
	// 	g.fillRect(0,0,5,5);
	// }
}