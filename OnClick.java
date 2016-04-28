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