package enemies;

import java.awt.Rectangle;
import static helperMethods.Constants.Direction.*;

public abstract class Enemy {
	
	protected float x, y;
	protected Rectangle bounds;
	protected int health;
	protected int maxHealth;
	protected int id;
	protected int pathId = 0;
	protected int enemyType;
	protected int lastDir;
	protected boolean alive = true;
	protected int slowTickMax = 2 * 60;
	protected int slowTick = slowTickMax;
	
	public Enemy(float x, float y, int id, int enemyType) {
		this.x = x;
		this.y = y;
		this.id = id;
		this.enemyType = enemyType;
		bounds = new Rectangle((int) x, (int) y, 32, 32);
		lastDir = -1;
		setStartingHealth();
	}
	
	private void setStartingHealth() {
		health = helperMethods.Constants.Enemies.GetStartingHealth(enemyType);
		maxHealth = health;
	}
	
	public void damage(int dmg) {
		this.health -= dmg;
		if(health <= 0 && alive) 
			alive = false;
	}
	
	public void slow() {
		slowTick = 0;
	}
	
	public void move(float speed, int dir) {
		lastDir = dir;
		if(slowTick < slowTickMax) {
			slowTick++;
			speed *= 0.5f;
		}
		switch(dir) {
		case LEFT:
			this.x -= speed;
			break;
		case UP:
			this.y -= speed;
			break;
		case RIGHT:
			this.x += speed;
			break;
		case DOWN:
			this.y += speed;
			break;
		}
		
		updateBounds();
		
	}
	
	public void kill() {
		alive = false;
		health = 0;
	}
	
	private void updateBounds() {
		bounds.x = (int) x;
		bounds.y = (int) y;
	}

	public void setPos(int x, int y) {
		//A pozicio fixelesere van csak hasznalva, nem mozgatasra
		this.x = x;
		this.y = y;
	}
	
	public float getHealthBarF() {
		return health / (float) maxHealth;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public int getHealth() {
		return health;
	}

	public int getId() {
		return id;
	}

	public int getEnemyType() {
		return enemyType;
	}
	
	public int getLastDir() {
		return lastDir;
	}
	
	public int getPathId() {
		return pathId;
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public boolean isSlowed() {
		return slowTick < slowTickMax;
	}
	
	public void increasePathId() {
		pathId += 1;
	}

}
