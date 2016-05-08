/* OnClick.java
*  Acts as the Button's AcionListener
*  Each Buttton will have 1 of these objects
*  Once a button is clicked, it will change the color to green and non-clickable
*
*  Editor: Matt Marshall
*/ 

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class OnClick implements ActionListener {

  OnClick() {
  }

  public void actionPerformed(ActionEvent ae) 
  {
	MyButton but = (MyButton) ae.getSource();
	but.setForeground(Color.GREEN);
	but.setBackground(Color.GREEN);
	but.setEnabled(false);
  }
}