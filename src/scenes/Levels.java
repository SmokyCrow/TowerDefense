package scenes;

import static game.GameStates.*;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import game.Game;
import ui.CustomButton;

public class Levels extends GameScene implements SceneMethods{

	private ArrayList<CustomButton> levelButtons = new ArrayList<>();
	private CustomButton menuButton;
	
	public Levels(Game game) {
		super(game);
		initButtons();
	}

	private void initButtons() {
		menuButton = new CustomButton(4, 4, 60, 20, "Menu");
		int w = 150;
		int h = w/3;
		int x = 640/2 - w/2;
		int y = 120;
		int yOffset = h + 10;
		
		for(int i = 0; i < 10; i ++) {
			levelButtons.add(new CustomButton(x, y + i*yOffset, w, h, i, "Level " + (i + 1)));
		}
	}

	@Override
	public void render(Graphics g) {
		drawButtons(g);
	}
	
	private void drawButtons(Graphics g) {
		menuButton.draw(g);
		for(CustomButton b : levelButtons) {
			b.draw(g);
			if(b.getId() > game.getLvlsDone()) {
				g.setColor(Color.red);
				g.drawLine(b.getBounds().x + 25, b.getBounds().y + 25, b.getBounds().x + 125, b.getBounds().y + 25);
			}
		}
	}

	@Override
	public void mouseClicked(int x, int y) {
		if(menuButton.getBounds().contains(x, y)) {
			setGameState(MENU);
		}else {
			for(int i = 0; i < 10; i++) {
				if(levelButtons.get(i).getBounds().contains(x, y) && i <= game.getLvlsDone()) {
					setGameState(PLAYING);
					game.setSelectedLevel(i);
				}
			}
		}
	}

	@Override
	public void mouseMoved(int x, int y) {
		for(CustomButton b : levelButtons)
			b.setMouseOver(false);
		menuButton.setMouseOver(false);
		if(menuButton.getBounds().contains(x, y)) {
			menuButton.setMouseOver(true);
		}else {
			for(CustomButton b : levelButtons) {
				if(b.getBounds().contains(x, y) && b.getId() <= game.getLvlsDone()) {
					b.setMouseOver(true);
					return;
				}
			}
		}
	}

	@Override
	public void mousePressed(int x, int y) {
		if(menuButton.getBounds().contains(x, y)) {
			menuButton.setMousePressed(true);
		}else{
			for(CustomButton b : levelButtons) {
				if(b.getBounds().contains(x, y) && b.getId() <= game.getLvlsDone()) {
					b.setMousePressed(true);
					return;
				}
			}
		}
	}

	@Override
	public void mouseReleased(int x, int y) {
		resetButtons();
	}

	private void resetButtons() {
		menuButton.resetBooleans();
		for(CustomButton b : levelButtons)
			b.resetBooleans();
	}
	
}
