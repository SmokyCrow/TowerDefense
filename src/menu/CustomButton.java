package menu;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CustomButton extends JButton implements MouseListener {
	private Dimension size = new Dimension(250, 150);
	
	private boolean hover = false;
	private boolean click = false;
	
	private String text = "";
	
	public CustomButton(String text) {
		setVisible(true);
		setFocusable(true);
		setContentAreaFilled(false);
		setBorderPainted(false);
		
		this.text = text;
		
		addMouseListener(this);
	}
}
