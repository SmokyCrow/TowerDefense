package handlers;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import enemies.Enemy;
import helperMethods.LoadSave;
import obj.Tower;
import scenes.Playing;

public class TowerHandler {
	
	private Playing playing;
	private BufferedImage[] towerImgs;
	private ArrayList<Tower> towers = new ArrayList<>();
	private int towerAmount = 0;
	
	public TowerHandler(Playing playing) {
		this.playing = playing;
		loadTowerImgs();
	}

	private void loadTowerImgs() {
		BufferedImage atlas = LoadSave.getSpriteAtlas();
		towerImgs = new BufferedImage[3];
		for(int i = 0; i < 3; i++) {
			towerImgs[i] = atlas.getSubimage((7 + i) *32, 1*32, 32, 32);
		}
	}
	
	public BufferedImage getTowerImg(int id) {
		return towerImgs[id];
	}
	
	public void draw(Graphics g) {
		for(Tower t : towers) {
			g.drawImage(towerImgs[t.getTowerType()], t.getX(), t.getY(), null);
		}
	}
	
	public Tower getTowerAt(int x, int y) {
		for(Tower t : towers)
			if(t.getX() == x)
				if(t.getY() == y)
					return t;
		return null;
	}
	
	public void addTower(Tower selectedTower, int xPos, int yPos) {
		towers.add(new Tower(xPos, yPos, towerAmount++, selectedTower.getTowerType()));
	}
	
	public void update() {
		for(Tower t : towers) {
			t.update();
			attackEnemy(t);
		}
	}
	
	private void attackEnemy(Tower t) {
		for(Enemy e : playing.getEnemyHandler().getEnemies()) {
			if(e.isAlive())
				if(isEnemyInRange(t, e)) {
					if(t.isCooldownOver()) {
						playing.shootEnemy(t, e);
						t.resetCooldown();
					}
				}else {
				
				}
		}
	}

	private boolean isEnemyInRange(Tower t, Enemy e) {
		int range = helperMethods.Utility.GetDistance(t.getX(), t.getY(), e.getX(), e.getY());
		return range < t.getRange();
	}

	public ArrayList<Tower> getTowers(){
		return towers;
	}
	
	public void setTowers(ArrayList<Tower> towers) {
		this.towers = towers;
		towerAmount = towers.size();
	}
	
	public void reset() {
		Tower start = towers.get(0);
		towers.clear();
		towers.add(start);
		towerAmount = 1;
	}
	
}
