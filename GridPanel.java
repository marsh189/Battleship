import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class GridPanel extends JPanel
{
	public MyButton[][] btnArr = new MyButton[10][10];
	public int count = 0;
	public String clickedTag;

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
				btn.Tag = i + "," + j;
				btn.myGrid = this;
				btn.setVisible(true);	
				btnArr[i][j] = btn;
				this.add(btn);
			}
		}
	}
}