import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class MyButton extends JButton {

    private Color hoverBackgroundColor;
    private Color pressedBackgroundColor;
    public GridPanel myGrid;   //stores a reference to the grid the button is on
    public String Tag;

    public MyButton() {
        this(null);
    }

    public MyButton(String text) {
        super(text);
        super.setContentAreaFilled(false);
        setForeground(new Color(0, 135, 200).brighter());
        setBackground(new Color(3, 59, 90));
        setHoverBackgroundColor(new Color(3, 59, 90).brighter());
        setPressedBackgroundColor(Color.GREEN);
    }

    //Paints button to specified color on hover and on click
    @Override
    protected void paintComponent(Graphics g) 
    {
        if (getModel().isPressed()) 
        {
            g.setColor(pressedBackgroundColor);
            myGrid.count++;

            if(myGrid.count % 2 == 0)
            {
                myGrid.clickedTag = this.Tag;
            }
        }
        else if (getModel().isRollover())
        {
            g.setColor(hoverBackgroundColor);
        } 
        else 
        {
            g.setColor(getBackground());
        }
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

    @Override
    public void setContentAreaFilled(boolean b) 
    {
    }

    public Color getHoverBackgroundColor() 
    {
        return hoverBackgroundColor;
    }

    public void setHoverBackgroundColor(Color hoverBackgroundColor) 
    {
        this.hoverBackgroundColor = hoverBackgroundColor;
    }

    public Color getPressedBackgroundColor() 
    {
        return pressedBackgroundColor;
    }

    public void setPressedBackgroundColor(Color pressedBackgroundColor) 
    {
        this.pressedBackgroundColor = pressedBackgroundColor;
    }
}
