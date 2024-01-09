package handlers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import enemies.Enemy;
import enemies.TrashCan;
import enemies.Blob;
import enemies.Plasticine;
import helperMethods.LoadSave;
import scenes.Playing;

import static helperMethods.Constants.Direction.*;
import static helperMethods.Constants.Tiles.*;
import static helperMethods.Constants.Enemies.*;

public class EnemyHandler {
	
	private Playing playing;
	private BufferedImage[] enemyImgs;
	private BufferedImage slowEffect;
	private ArrayList<Enemy> enemies = new ArrayList<>();
	private ArrayList<Point> path = new ArrayList<>();
	private int startX, startY, endX, endY;
	private int HealthBarWidth = 28;
	
	public EnemyHandler(Playing playing) {
		this.playing = playing;
		enemyImgs = new BufferedImage[3];
		path = playing.getPath();
		initStart();
		updateEnd();
		loadEffectImg();
		loadEnemyImgs();
	}
	
	private void loadEffectImg() {
		slowEffect = LoadSave.getSpriteAtlas().getSubimage(3 * 32, 2 * 32, 32, 32);
	}
	
	private void loadEnemyImgs() {
		BufferedImage atlas = LoadSave.getSpriteAtlas();
		for(int i = 0; i < 3; i++) {
			enemyImgs[i] = atlas.getSubimage((4 + i) * 32, 2 * 32, 32, 32);
		}
	}

	public void initStart() {
		startX = path.get(0).x * 32;
		startY = path.get(0).y * 32;
	}
	
	public void updateEnd() {
		endX = path.get(path.size() - 1).x * 32;
		endY = path.get(path.size() - 1).y * 32;
	}

	public void update() {
		for (Enemy e : enemies) {
			if(e.isAlive())
				updateEnemyMove(e);
		}
	}
	
	public void spawnEnemy(int nextEnemy) {
		addEnemy(nextEnemy);
	}

	public void updateEnemyMove(Enemy e) {
		if(e.getLastDir() == -1)
			setNewDirAndMove(e);
		
		int newX = (int) (e.getX() + getSpeedAndWidth(e.getLastDir(), e.getEnemyType()));
		int newY = (int) (e.getY() + getSpeedAndHeight(e.getLastDir(), e.getEnemyType()));
		
		if(getTileType(newX, newY) == ROAD_TILE && isNextPath(newX, newY, e)) {
			e.move(GetSpeed(e.getEnemyType()), e.getLastDir());
		}
		else if(isAtEnd(e)){
			e.kill();
			playing.removeLife();
		}else {
			setNewDirAndMove(e);
		}
	}

	private void setNewDirAndMove(Enemy e) {
		int dir = e.getLastDir();
		
		int xCord = (int) e.getX() / 32;
		int yCord = (int) e.getY() / 32;
		
		fixEnemyTile(e, dir, xCord, yCord);
		
		if(isAtEnd(e))
			return;
		
		if(dir == -1) {
			int secondX = (int) path.get(1).getX();
			int secondY = (int) path.get(1).getY();
			if(xCord - 1 == secondX && yCord == secondY)
				e.move(GetSpeed(e.getEnemyType()), LEFT);
			else if(xCord + 1 == secondX && yCord == secondY)
				e.move(GetSpeed(e.getEnemyType()), RIGHT);
			else if(xCord == secondX && yCord - 1 == secondY )
				e.move(GetSpeed(e.getEnemyType()), UP);
			else if(xCord == secondX && yCord + 1 == secondY)
				e.move(GetSpeed(e.getEnemyType()), DOWN);
		}
		else if(dir == LEFT || dir == RIGHT) {
			int newY = (int) (e.getY() + getSpeedAndHeight(UP, e.getEnemyType()));
			if(getTileType((int)e.getX(), newY) == ROAD_TILE  && isNextPath((int)e.getX(), newY, e))
				e.move(GetSpeed(e.getEnemyType()), UP);
			else
				e.move(GetSpeed(e.getEnemyType()), DOWN);
		} else {
			int newX = (int) (e.getX() + getSpeedAndWidth(RIGHT, e.getEnemyType()));
			if(getTileType(newX, (int)e.getY()) == ROAD_TILE && isNextPath(newX, (int)e.getY(), e))
				e.move(GetSpeed(e.getEnemyType()), RIGHT);
			else
				e.move(GetSpeed(e.getEnemyType()), LEFT);
		}
	}

	private boolean isNextPath(int x, int y, Enemy e) {
		int xCord = x / 32;
		int yCord = y / 32;
		int id = e.getPathId()+1;
		Point nextPath = new Point(xCord, yCord);
		if(id < path.size() && path.get(id).equals(nextPath))
			return true;
		else if(id + 1 < path.size() && path.get(id + 1).equals(nextPath)) {
			e.increasePathId();
			return true;
		}
		return false;
	}

	private void fixEnemyTile(Enemy e, int dir, int xCord, int yCord) {
		switch(dir) {
		case RIGHT:
			if(xCord < 19)
				xCord++;
			break;
		case DOWN:
			if(yCord < 19)
				yCord++;
			break;
		}
		
		e.setPos(xCord * 32, yCord * 32);
	}

	public boolean isAtEnd(Enemy e) {
		if (e.getX() == endX) {
			if(e.getY() == endY)
				return true;
		}
		return false;
	}

	private int getTileType(int x, int y) {
		return playing.getTileType(x, y);
	}

	private float getSpeedAndWidth(int dir, int enemyType) {
		if(dir == LEFT)
			return -GetSpeed(enemyType);
		else if(dir == RIGHT)
			return GetSpeed(enemyType) + 32;
		return 0;
	}

	private float getSpeedAndHeight(int dir, int enemyType) {
		if(dir == UP)
			return -GetSpeed(enemyType);
		else if(dir == DOWN)
			return GetSpeed(enemyType) + 32;
		return 0;
	}

	public void addEnemy(int enemyType) {
		int x = startX;
		int y = startY;
		
		switch(enemyType) {
		case BLOB:
			enemies.add(new Blob(x, y, 0));
			break;
		case TRASHCAN:
			enemies.add(new TrashCan(x, y, 0));
			break;
		case PLASTICINE:
			enemies.add(new Plasticine(x, y, 0));
			break;
		}
	}
	
	public void draw(Graphics g) {
		for (Enemy e : enemies) {
			if(e.isAlive()) {
				drawEnemy(e, g);
				drawHealthBar(e, g);
				drawEffects(e, g);
			}
		}
	}

	private void drawEffects(Enemy e, Graphics g) {
		if(e.isSlowed()) {
			g.drawImage(slowEffect, (int) e.getX(), (int) e.getY(), null);
		}
	}

	private void drawHealthBar(Enemy e, Graphics g) {
		g.setColor(Color.red);
		g.fillRect((int) e.getX() + 2 , (int) e.getY() - 8, getNewHealthBarWidth(e), 4);
		g.setColor(Color.black);
		g.drawRect((int) e.getX() + 2, (int) e.getY() - 8, 28, 4);
	}
	
	private int getNewHealthBarWidth(Enemy e) {
		return (int) (HealthBarWidth * e.getHealthBarF());
	}

	private void drawEnemy(Enemy e, Graphics g) {
		g.drawImage(enemyImgs[e.getEnemyType()], (int) e.getX(), (int) e.getY(), null);
	}
	
	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}

	public int getAmountOfEnemiesLeft() {
		int size = 0;
		for(Enemy e : enemies)
			if(e.isAlive())
				size++;
		return size;
	}
	
	public void reset() {
		enemies.clear();
		initStart();
		updateEnd();
	}
	
}
