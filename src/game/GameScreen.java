package game;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.*;

import inputs.MyMouseListener;

public class GameScreen extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private Game game;
	private Dimension size;
	
	private MyMouseListener myMouseListener;
	
	public GameScreen(Game game) {
		this.game = game;
		
		setPanelSize();
	}
	
	public void initInputs() {
		myMouseListener = new MyMouseListener(game);
		
		addMouseListener(myMouseListener);
		addMouseMotionListener(myMouseListener);
		
		requestFocus();
	}
	
	private void setPanelSize() {
		size = new Dimension(640, 840);
		setMinimumSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
	}

	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		game.getRender().render(g);
	}
	
	
	
}
