package ui;

import static game.GameStates.MENU;
import static game.GameStates.setGameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import helperMethods.Constants.Towers;
import obj.Tile;
import obj.Tower;
import scenes.Playing;

public class BottomBar {
	
	private int x, y, width, height;
	private Playing playing;
	private CustomButton bMenu, bSave, bNextRound, bNextTile, bCancel;
	
	private ArrayList<CustomButton> tileButtons = new ArrayList<>();
	
	private Tile selectedTile;
	private Tower selectedTower;
	private Tower displayedTower;
	private Random random;
	
	public BottomBar(int x, int y, int width, int height, Playing playing) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.playing = playing;
		this.random = new Random();
		
		initButtons();
	}
	
	private void initButtons() {
		bMenu = new CustomButton(4, 644, 60, 20, "Menu");
		bSave = new CustomButton(4, 668, 60, 20, "Save");
		bNextRound = new CustomButton(546, 644, 90, 30, "Next round");
		bNextTile = new CustomButton(546, 678, 90, 30, "Next tile");
		bCancel = new CustomButton(354, 680, 60, 20, "Cancel");
		
		int w = 50;
		int h = 50;
		int xStart = 110;
		int yStart = 665;
		int xOffset = (int) (w * 1.1f);
		
		tileButtons.add(new CustomButton(xStart, yStart, w, h, 4, ""));
		tileButtons.add(new CustomButton(xStart + xOffset, yStart, w, h, randomInt(), ""));
	}
	
	public void updateTileButtons() {
		tileButtons.get(0).setId(tileButtons.get(1).getId());
		if(randomInt() % 2 == 0) {
			tileButtons.get(1).setId(4);
		}else
			tileButtons.get(1).setId(randomInt());
	}
	
	private void drawButtons(Graphics g) {
		bMenu.draw(g);
		bSave.draw(g);
		bNextRound.draw(g);
		bNextTile.draw(g);
		drawTileButtons(g);
	}

	private void drawTileButtons(Graphics g) {
		for(CustomButton b : tileButtons) {
			g.drawImage(getButtonImg(b.getId()),b.x, b.y, b.width, b.height, null);
			drawButtonFeedback(g, b);
		}
		g.drawImage(playing.getTileHandler().atlas.getSubimage(5*32, 0, 32, 32),
				115 + (int) (50*1.1f), 670, 40, 40, null);
	}
	
	private void drawButtonFeedback(Graphics g, CustomButton b) {
		if (b.isMouseOver())
			g.setColor(Color.white);
		else
			g.setColor(Color.BLACK);

		g.drawRect(b.x, b.y, b.width, b.height);

		if (b.isMousePressed()) {
			g.drawRect(b.x + 1, b.y + 1, b.width - 2, b.height - 2);
			g.drawRect(b.x + 2, b.y + 2, b.width - 4, b.height - 4);
		}
	}
	
	public BufferedImage getButtonImg(int id) {
		if(id == 4)
			return playing.getTileHandler().getSprite(2);
		else if(id == -1)
			return null;
		else
			return playing.getTowerHandler().getTowerImg(id);
	}

	public void draw(Graphics g) {
		g.setColor(new Color(255, 203, 149));
		g.fillRect(x, y, width, height);
		
		drawButtons(g);
		drawSelectedTile(g);
		drawMoney(g);
		drawLivesInfo(g);
		drawDisplayedTower(g);
		drawWaveInfo(g);
	}
	
	private void drawLivesInfo(Graphics g) {
		g.setColor(new Color(255, 153, 204));
		g.fillRect(452, 678, 90, 30);
		g.setColor(Color.BLACK);
		g.drawRect(452, 678, 90, 30);
		String text = "Lives: " + playing.getLives() + " / " + 10;
		drawText(g, text, 452, 678, 90, 30);
	}

	private void drawWaveInfo(Graphics g) {
		g.setColor(Color.BLACK);
		g.setFont(new Font("DialogInput", Font.BOLD, 15));
		drawWavesLeftInfo(g);
		if(playing.canEdit()) {
			drawText(g, "You can place tiles!", 220, 790, 200, 50);
		}else {
			drawText(g, "You can't place tiles while the wave is going!", 220, 790, 200, 50);
			drawEnemiesLeftInfo(g);
		}
	}

	private void drawWavesLeftInfo(Graphics g) {
		int current = playing.getWaveHandler().getWaveIdx() + 1;
		int size = playing.getWaveHandler().getWaves().size();
		String waveInfo = new String("Wave " + current + " / " + size);
		drawText(g, waveInfo, 546, 712, 90, 30);
	}

	private void drawEnemiesLeftInfo(Graphics g) {
		int remaining = playing.getEnemyHandler().getAmountOfEnemiesLeft();
		String enemiesLeft = new String("Enemies left: " + remaining);
		drawText(g, enemiesLeft, 270, 640, 100, 30);
	}

	private void drawDisplayedTower(Graphics g) {
		if(displayedTower != null) {
			g.setColor(new Color(153, 255, 255));
			g.fillRect(230, 720, 180, 70);
			g.setColor(Color.BLACK);
			g.drawRect(230, 720, 180, 70);
			g.drawImage(playing.getTowerHandler().getTowerImg(displayedTower.getTowerType()), 236, 726, 50, 50, null);
			g.drawRect(236, 726, 50, 50);
			g.setFont(new Font("DialogInput", Font.BOLD, 15));
			g.drawString("" + Towers.GetName(displayedTower.getTowerType()), 290, 740);
			g.drawString("ID: " + displayedTower.getId(), 290, 755);
			drawDisplayedTowerRange(g);
		}
	}
	
	private void drawDisplayedTowerRange(Graphics g) {
		g.setColor(new Color(255, 204, 229));
		int range = (int) displayedTower.getRange();
		g.drawOval(displayedTower.getX() + 16 - range, displayedTower.getY() + 16 - range, range * 2, range * 2);
	}

	private void drawMoney(Graphics g) {
		g.setColor(new Color(255, 255, 51));
		g.fillRect(452, 644, 90, 30);
		g.setColor(Color.BLACK);
		g.drawRect(452, 644, 90, 30);
		String text = "Money: " + playing.getMoney();
		drawText(g, text, 452, 644, 90, 30);
	}
	
	public void displayTowerInfo(Tower t) {
		displayedTower = t;
	}
	
	private void drawText(Graphics g, String text, int x, int y, int width, int height) {
		int w = g.getFontMetrics().stringWidth(text);
		int h = g.getFontMetrics().getHeight();
		int a = g.getFontMetrics().getAscent();
		g.drawString(text, x + (width - w)/2, y + ((height - h)/2) + a);
	}

	private int randomInt() {
		return random.nextInt(3);
	}
	
	private void drawSelectedTile(Graphics g) {
		if(selectedTile != null) {
			g.drawImage(selectedTile.getSprite(), 300, 665, 50, 50, null);
			g.setColor(Color.BLACK);
			g.drawRect(300, 665, 50, 50);
			
			bCancel.draw(g);
		}
		else if(selectedTower != null) {
			g.drawImage(playing.getTowerHandler().getTowerImg(selectedTower.getTowerType()), 300, 665, 50, 50, null);
			g.setColor(Color.BLACK);
			g.drawRect(300, 665, 50, 50);
			
			bCancel.draw(g);
		}
	}

	public void mouseClicked(int x, int y) {
		if(bMenu.getBounds().contains(x, y)) {
			setGameState(MENU);
		}
		else if(bSave.getBounds().contains(x, y) && playing.canEdit())
			saveLevel();
		else if(bNextRound.getBounds().contains(x, y) && playing.canEdit()) {
			playing.nextRound();
			cancelTile();
		}
		else if(bNextTile.getBounds().contains(x, y) && playing.canEdit()) {
			nextTile();
			cancelTile();
		}
		else if(bCancel.getBounds().contains(x, y) && playing.canEdit()) {
			if(selectedTower != null || selectedTile != null)
				cancelTile();
		}
		else {
			CustomButton b = tileButtons.get(0);
			if(b.getBounds().contains(x, y) && b.getId() != -1 && playing.canEdit()) {
				if(b.getId() == 4) {
					selectedTile = playing.getTileHandler().getTile(2);
					playing.setSelectedTile(selectedTile);
					return;
				}
				else {
					selectedTower = new Tower(0, 0, -1, b.getId());
					playing.setSelectedTower(selectedTower);
					return;
				}
			}
		}
	}

	private void cancelTile() {
		selectedTile = null;
		selectedTower = null;
		playing.setSelectedTile(selectedTile);
		playing.setSelectedTower(selectedTower);
	}
	
	public void tilePlaced() {
		selectedTile = null;
		selectedTower = null;
		playing.setSelectedTile(selectedTile);
		playing.setSelectedTower(selectedTower);
		tileButtons.get(0).setId(-1);
	}

	private void nextTile() {
		if(playing.getMoney() >= 2) {
			updateTileButtons();
			playing.addMoney(-2);;
		}
	}

	private void saveLevel() {
		playing.saveLevel();
	}

	public void mouseMoved(int x, int y) {
		bMenu.setMouseOver(false);
		bSave.setMouseOver(false);
		bNextRound.setMouseOver(false);
		bNextTile.setMouseOver(false);
		bCancel.setMouseOver(false);
		for(CustomButton b : tileButtons)
			b.setMouseOver(false);
		if(bMenu.getBounds().contains(x, y)) {
			bMenu.setMouseOver(true);
		}
		else if(bSave.getBounds().contains(x, y))
			bSave.setMouseOver(true);
		else if(bNextRound.getBounds().contains(x, y))
			bNextRound.setMouseOver(true);
		else if(bNextTile.getBounds().contains(x, y))
			bNextTile.setMouseOver(true);
		else if(bCancel.getBounds().contains(x, y)) {
			if(selectedTower != null || selectedTile != null)
				bCancel.setMouseOver(true);
		}
		else {
			CustomButton b = tileButtons.get(0);
			if(b.getBounds().contains(x, y) && b.getId() != -1) {
				b.setMouseOver(true);
				return;
			}
		}
	}

	public void mousePressed(int x, int y) {
		if(bMenu.getBounds().contains(x, y)) {
			bMenu.setMousePressed(true);
		}
		else if(bSave.getBounds().contains(x, y))
			bSave.setMousePressed(true);
		else if(bNextRound.getBounds().contains(x, y))
			bNextRound.setMousePressed(true);
		else if(bNextTile.getBounds().contains(x, y))
			bNextTile.setMousePressed(true);
		else if(bCancel.getBounds().contains(x, y)) {
			if(selectedTower != null || selectedTile != null)
				bCancel.setMousePressed(true);
		}
		else {
			CustomButton b = tileButtons.get(0);
			if(b.getBounds().contains(x, y) && b.getId() != -1) {
				b.setMousePressed(true);
				return;
			}
		}
	}

	public void mouseReleased(int x, int y) {
		bMenu.resetBooleans();
		bSave.resetBooleans();
		bNextRound.resetBooleans();
		bNextTile.resetBooleans();
		bCancel.resetBooleans();
		tileButtons.get(0).resetBooleans();
	}

	public void resetEverything() {
		selectedTower = null;
		displayedTower = null;
		selectedTile = null;
	}
	
}
