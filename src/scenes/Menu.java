package scenes;

import java.awt.Graphics;

import game.Game;
import ui.CustomButton;
import static game.GameStates.*;

public class Menu extends GameScene implements SceneMethods{
	
	private CustomButton buttonPlay, buttonQuit;
	
	public Menu(Game game) {
		super(game);
		initButtons();
	}

	private void initButtons() {
		int w = 150;
		int h = w/3;
		int x = 640/2 - w/2;
		int y = 150;
		int yOffset = 100;
		
		buttonPlay = new CustomButton(x, y, w, h, "Play");
		buttonQuit = new CustomButton(x, y + yOffset, w, h, "Quit");
	}

	@Override
	public void render(Graphics g) {
		drawButtons(g);
	}
	
	private void drawButtons(Graphics g) {
		buttonPlay.draw(g);
		buttonQuit.draw(g);
	}

	@Override
	public void mouseClicked(int x, int y) {
		if(buttonPlay.getBounds().contains(x, y)) {
			setGameState(LEVELS);
		}
		else if(buttonQuit.getBounds().contains(x, y)) {
			System.exit(0);
		}
	}

	@Override
	public void mouseMoved(int x, int y) {
		buttonPlay.setMouseOver(false);
		buttonQuit.setMouseOver(false);
		if(buttonPlay.getBounds().contains(x, y)) {
			buttonPlay.setMouseOver(true);
		}
		else if(buttonQuit.getBounds().contains(x, y)) {
			buttonQuit.setMouseOver(true);
		}
	}

	@Override
	public void mousePressed(int x, int y) {
		if(buttonPlay.getBounds().contains(x, y)) {
			buttonPlay.setMousePressed(true);
		}
		else if(buttonQuit.getBounds().contains(x, y)) {
			buttonQuit.setMousePressed(true);
		}
	}

	@Override
	public void mouseReleased(int x, int y) {
		resetButtons();
	}

	private void resetButtons() {
		buttonPlay.resetBooleans();
		buttonQuit.resetBooleans();
	}

}
