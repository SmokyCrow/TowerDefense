package scenes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import enemies.Enemy;
import game.Game;
import handlers.EnemyHandler;
import handlers.ProjectileHandler;
import handlers.TileHandler;
import handlers.TowerHandler;
import handlers.WaveHandler;
import helperMethods.LoadSave;
import obj.Tile;
import obj.Tower;
import ui.BottomBar;
import static helperMethods.Constants.Tiles.*;
import static game.GameStates.*;

public class Playing extends GameScene implements SceneMethods{
	
	private int [][] lvl;
	private int lvlId;
	private ArrayList<Point> path = new ArrayList<>();
	private Tile selectedTile;
	private Tower selectedTower;
	private BottomBar bottomBar;
	private int mouseX, mouseY;
	private int money;
	private int lives;
	private boolean drawSelect;
	private boolean canEdit = true;
	private int animIdx;
	private int tick;
	private EnemyHandler enemyHandler;
	private TowerHandler towerHandler;
	private ProjectileHandler projectileHandler;
	private WaveHandler waveHandler;
	
	
	public Playing(Game game, int lvlId) {
		super(game);
		this.lvlId = lvlId;
		LoadSave.getData("level" + lvlId, this);
		bottomBar = new BottomBar(0, 640, 640, 200, this);
		enemyHandler = new EnemyHandler(this);
		projectileHandler = new ProjectileHandler(this);
	}
	
	public void saveLevel() {
		LoadSave.saveEverything("level" + lvlId, lvl, path, towerHandler.getTowers(), waveHandler.getWaveIdx(), money, lives);
	}

	public void loadLevel(int[][] lvl, ArrayList<Point> path, ArrayList<Tower> towers, int wave, int money, int lives) {
		this.lvl = lvl;
		this.path = path;
		towerHandler = new TowerHandler(this);
		towerHandler.setTowers(towers);
		waveHandler = new WaveHandler(this);
		waveHandler.setWaveIdx(wave);
		this.money = money;
		this.lives = lives;
	}

	@Override
	public void render(Graphics g) {
		updateTick();
		drawLevel(g);
		bottomBar.draw(g);
		drawSelectedTile(g);
		drawSelectedTower(g);
		enemyHandler.draw(g);
		towerHandler.draw(g);
		projectileHandler.draw(g);
	}
	
	private void updateTick() {
		tick++;
		if(tick >= 20) {
			tick = 0;
			animIdx++;
			if(animIdx >= 4)
				animIdx = 0;
		}
	}
	
	public void update() {
		if(!canEdit) {
			waveHandler.update();
			
			if(areAllEnemiesDead()) {
				if(!isLastWave()) {
					canEdit = true;
					enemyHandler.getEnemies().clear();
				}else {
					setGameState(GAMEWON);
					game.setLvlsDone(lvlId);
				}
			}
		}

		
		if(!canEdit && isTimeForSpawn()) {
			spawnEnemy();
		}
		
		enemyHandler.update();
		towerHandler.update();
		projectileHandler.update();
	}

	private boolean isLastWave() {
		return waveHandler.isLastWave();
	}

	private boolean areAllEnemiesDead() {
		if(!waveHandler.isEnemyListEmpty()) {
			return false;
		}
		for(Enemy e : enemyHandler.getEnemies()) {
			if(e.isAlive())
				return false;
		}
		return true;
	}
	
	public void nextRound() {
		bottomBar.updateTileButtons();
		canEdit = false;
		waveHandler.nextWave();
		if(money < 10)
			money += 1;
		else if(money < 20)
			money += 2;
		else if(money < 30)
			money += 3;
		else
			money += 4;
	}
	
	public void removeLife() {
		lives--;
		if(lives <= 0) {
			setGameState(GAMEOVER);
		}
	}

	private void drawLevel(Graphics g) {
		for(int y = 0; y < lvl.length; y++) {
			for(int x = 0; x < lvl[y].length; x++) {
				int id = lvl[y][x];
				if(isAnim(id)) {
					g.drawImage(getSprite(id, animIdx), x*32, y*32, null);
				}else {
					g.drawImage(getSprite(id), x*32, y*32, null);
					Point control = new Point(x, y);
					if(control.equals(path.get(0))) {
						g.setColor(Color.GREEN);
						g.fillRect(x*32 + 11, y*32 + 11, 10, 10);
					}
					else if(control.equals(path.get(path.size() - 1))) {
						g.setColor(Color.RED);
						g.fillRect(x*32 + 11, y*32 + 11, 10, 10);
					}
						
				}
			}
		}
	}
	
	private boolean isAnim(int id) {
		return game.getTileHandler().isSpriteAnim(id);
	}
	
	private BufferedImage getSprite(int id) {
		return game.getTileHandler().getSprite(id);
	}
	
	private BufferedImage getSprite(int id, int animIdx) {
		return game.getTileHandler().getAnimSprite(id, animIdx);
	}
	
	private void drawSelectedTile(Graphics g) {
		if(selectedTile != null && drawSelect) {
			g.drawImage(selectedTile.getSprite(), mouseX, mouseY, 32, 32, null);
			if(!placedRoadRight(mouseX, mouseY))
				g.drawImage(game.getTileHandler().atlas.getSubimage(6*32, 0, 32, 32), mouseX, mouseY, 32, 32, game);
		}
	}
	
	private void drawSelectedTower(Graphics g) {
		if(selectedTower != null && drawSelect) {
			int range = (int) selectedTower.getRange();
			g.setColor(new Color(255, 204, 229));
			g.drawOval(mouseX + 16 - range, mouseY + 16 - range, 2 * range, 2 * range);
			g.drawImage(towerHandler.getTowerImg(selectedTower.getTowerType()), mouseX, mouseY, 32, 32, null);
			if(!placedTowerRight(mouseX, mouseY) || getTowerAt(mouseX, mouseY) != null)
				g.drawImage(game.getTileHandler().atlas.getSubimage(6*32, 0, 32, 32), mouseX, mouseY, 32, 32, game);
		}
	}

	@Override
	public void mouseClicked(int x, int y) {
		if (y >= 640) {
			bottomBar.mouseClicked(x, y);
		}
		else if(selectedTile != null && selectedTile.getId() != lvl[y/32][x/32]){
			if(placedRoadRight(mouseX, mouseY)) {
				changeTile(mouseX, mouseY);
				selectedTile = null;
				bottomBar.tilePlaced();
			}
		}
		else if(selectedTower != null){
			if(placedTowerRight(mouseX, mouseY) && getTowerAt(mouseX, mouseY) == null) {
				towerHandler.addTower(selectedTower, mouseX, mouseY);
				selectedTower = null;
				bottomBar.tilePlaced();
			}
			
		}else {
			Tower t = getTowerAt(mouseX, mouseY);
			bottomBar.displayTowerInfo(t);
		}
	}

	private void changeTile(int x, int y) {
		if(selectedTile != null) {
			int tileX = x / 32;
			int tileY = y / 32;
					
			lvl[tileY][tileX] = selectedTile.getId();
			if(selectedTile.getId() == ROAD_TILE) {
				path.add(new Point(tileX, tileY));
				enemyHandler.updateEnd();
			}
		}
	}

	private boolean placedRoadRight(int x, int y) {
		int lastX = (int) path.get(path.size()-1).getX();
		int lastY = (int) path.get(path.size()-1).getY();
		int tileX = x / 32;
		int tileY = y / 32;
		//vizre nem lehet utat rakni
		if(lvl[tileY][tileX] == WATER_TILE)
			return false;
		//az utolso path elem utan lehet csak utat rakni
		if(tileX - 1 == lastX && tileY == lastY)
			return true;
		else if(tileX + 1 == lastX && tileY == lastY)
			return true;
		else if(tileX == lastX && tileY - 1 == lastY )
			return true;
		else if(tileX == lastX && tileY + 1 == lastY)
			return true;
		
		return false;
	}
	
	private boolean placedTowerRight(int x, int y) {
		int tileX = x / 32;
		int tileY = y / 32;
		//vizre nem lehet towert rakni
		if(lvl[tileY][tileX] == WATER_TILE)
			return false;
		//utra nem lehet towert rakni
		if(lvl[tileY][tileX] == ROAD_TILE)
			return false;
		if(tileX > 0 && lvl[tileY][tileX - 1] == ROAD_TILE)
			return true;
		else if(tileX < 19 && lvl[tileY][tileX + 1] == ROAD_TILE)
			return true;
		else if(tileY > 0 && lvl[tileY - 1][tileX] == ROAD_TILE)
			return true;
		else if(tileY < 19 && lvl[tileY + 1][tileX] == ROAD_TILE)
			return true;
		
		return false;
	}
	
	private Tower getTowerAt(int x, int y) {
		return towerHandler.getTowerAt(x, y);
	}
	
	public void shootEnemy(Tower t, Enemy e) {
		projectileHandler.newProjectile(t, e);
	}
	
	private void spawnEnemy() {
		enemyHandler.spawnEnemy(waveHandler.getNextEnemy());
	}
	
	private boolean isTimeForSpawn() {
		if(waveHandler.isTimeForSpawn()) {
			if(!waveHandler.isEnemyListEmpty())
				return true;
		}
		return false;
	}

	@Override
	public void mouseMoved(int x, int y) {
		if (y >= 640) {
			bottomBar.mouseMoved(x, y);
			drawSelect = false;
		}else {
			drawSelect = true;
			mouseX = (x / 32) * 32;
			mouseY = (y / 32) * 32;
		}
	}

	@Override
	public void mousePressed(int x, int y) {
		if (y >= 640) {
			bottomBar.mousePressed(x, y);
		}
	}

	@Override
	public void mouseReleased(int x, int y) {
		bottomBar.mouseReleased(x, y);
	}
	
	public TileHandler getTileHandler() {
		return game.getTileHandler();
	}
	
	public TowerHandler getTowerHandler() {
		return towerHandler;
	}
	
	public EnemyHandler getEnemyHandler() {
		return enemyHandler;
	}
	
	public WaveHandler getWaveHandler() {
		return waveHandler;
	}

	public int getTileType(int x, int y) {
		int xCord = x / 32;
		int yCord = y / 32;
		if(xCord < 0 || xCord > 19)
			return 0;
		if(yCord < 0 || yCord > 19)
			return 0;
		int id = lvl[yCord][xCord];
		return game.getTileHandler().getTile(id).getTileType();
	}
	
	public ArrayList<Point> getPath(){
		return path;
	}
	
	public int getMoney() {
		return money;
	}
	
	public void addMoney(int m) {
		money += m;
	}
	
	public int getLives() {
		return lives;
	}
	
	public boolean canEdit() {
		return canEdit;
	}
	
	public void setSelectedTile(Tile tile) {
		this.selectedTile = tile;
		drawSelect = true;
	} 
	
	public void setSelectedTower(Tower tower) {
		this.selectedTower = tower;
		drawSelect = true;
	}

	public void resetLevel() {
		bottomBar.resetEverything();
		resetPath();
		enemyHandler.reset();
		towerHandler.reset();
		projectileHandler.reset();
		waveHandler.reset();
		money = 1;
		lives = 10;
		mouseX = 0;
		mouseY = 0;
		selectedTile = null;
		selectedTower = null;
		canEdit = true;
		saveLevel();
	}

	private void resetPath() {
		for(int i = 5; i < path.size(); i++) {
			int x = path.get(i).x;
			int y = path.get(i).y;
			lvl[y][x] = GRASS_TILE;
		}
		while(path.size() > 5) {
			path.remove(5);
		}
	}

}
