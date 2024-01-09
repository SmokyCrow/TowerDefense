package scenes;

import static game.GameStates.MENU;
import static game.GameStates.PLAYING;
import static game.GameStates.setGameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import game.Game;
import ui.CustomButton;

public class GameWon extends GameScene implements SceneMethods{
	private CustomButton replayButton, menuButton;

	public GameWon(Game game) {
		super(game);
		initButtons();
	}

	private void initButtons() {
		int w = 150;
		int h = w/3;
		int x = 145;
		int y = 500;
		int xOffset = 200;
		
		replayButton = new CustomButton(x, y, w, h, "Replay");
		menuButton = new CustomButton(x + xOffset, y, w, h, "Menu");
	}

	@Override
	public void render(Graphics g) {
		drawButtons(g);
		g.setColor(Color.green);
		g.setFont(new Font("DialogInput", Font.BOLD, 60));
		drawText(g, "Game Won!", 150, 150, 340, 110);
	}

	private void drawButtons(Graphics g) {
		replayButton.draw(g);
		menuButton.draw(g);
	}
	
	private void drawText(Graphics g, String text, int x, int y, int width, int height) {
		int w = g.getFontMetrics().stringWidth(text);
		int h = g.getFontMetrics().getHeight();
		int a = g.getFontMetrics().getAscent();
		g.drawString(text, x + (width - w)/2, y + ((height - h)/2) + a);
	}
	
	private void resetGame() {
		game.getPlaying().resetLevel();
	}

	@Override
	public void mouseClicked(int x, int y) {
		if(menuButton.getBounds().contains(x, y)) {
			resetGame();
			setGameState(MENU);
		}
		else if(replayButton.getBounds().contains(x, y)) {
			resetGame();
			setGameState(PLAYING);
		}
	}

	@Override
	public void mouseMoved(int x, int y) {
		replayButton.setMouseOver(false);
		menuButton.setMouseOver(false);
		if(menuButton.getBounds().contains(x, y)) {
			menuButton.setMouseOver(true);
		}
		else if(replayButton.getBounds().contains(x, y)) {
			replayButton.setMouseOver(true);
		}
	}

	@Override
	public void mousePressed(int x, int y) {
		if(menuButton.getBounds().contains(x, y)) {
			menuButton.setMousePressed(true);
		}
		else if(replayButton.getBounds().contains(x, y)) {
			replayButton.setMousePressed(true);
		}
	}

	@Override
	public void mouseReleased(int x, int y) {
		resetButtons();
	}
	
	private void resetButtons() {
		menuButton.resetBooleans();
		replayButton.resetBooleans();
	}

}
