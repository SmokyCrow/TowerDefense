package menu;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.*;

public class Menu extends JPanel{
	public Menu() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		add(Box.createVerticalStrut(280));
		
		CustomButton button = new CustomButton("START");
	}
}
