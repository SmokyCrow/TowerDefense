package handlers;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import enemies.Enemy;
import helperMethods.LoadSave;
import obj.Projectile;
import obj.Tower;
import scenes.Playing;

import static helperMethods.Constants.Towers.*;
import static helperMethods.Constants.Projectiles.*;

public class ProjectileHandler {
	
	private Playing playing;
	private ArrayList<Projectile> projectiles = new ArrayList<>();
	private ArrayList<Explosion> explosions = new ArrayList<>();
	private BufferedImage[] projImgs, explosionImgs;
	private int projId = 0;
	private Random random;
	
	public ProjectileHandler(Playing playing) {
		this.playing = playing;
		this.random = new Random();
		importImgs();
	}
	
	private void importImgs() {
		projImgs = new BufferedImage[3];
		BufferedImage atlas = LoadSave.getSpriteAtlas();
		for(int i = 0; i < 3; i++) {
			projImgs[i] = atlas.getSubimage((7 + i) *32, 2*32, 32, 32);
		}
		importExplosion(atlas);
	}
	
	private void importExplosion(BufferedImage atlas) {
		explosionImgs = new BufferedImage[7];
		for(int i = 0; i < 7; i ++) {
			explosionImgs[i] = atlas.getSubimage(i * 32, 1 * 32, 32, 32);
		}
	}
	
	private int randomInt() {
		return random.nextInt(100);
	}

	public void newProjectile(Tower t, Enemy e) {
		int type = getProjectileType(t);
		
		int xDist = (int) (t.getX() - e.getX());
		int yDist = (int) (t.getY() - e.getY());
		int total = Math.abs(xDist) + Math.abs(yDist);
		float xPercentage = (float) Math.abs(xDist) / total;
		float yPercentage = 1.0f - xPercentage;
		
		float xSpeed = xPercentage * helperMethods.Constants.Projectiles.GetSpeed(type);
		float ySpeed = yPercentage * helperMethods.Constants.Projectiles.GetSpeed(type);
		
		if(t.getX() > e.getX()) {
			xSpeed *= -1;
		}
		if(t.getY() > e.getY()) {
			ySpeed *= -1;
		}
		
		float rotate = 0;
		if(type == ARROW) {
			float atanValue = (float) Math.atan((float) yDist / xDist);
			rotate = (float) Math.toDegrees(atanValue);
		
			if(xDist < 0)
				rotate += 180;
		}
		
		projectiles.add(new Projectile(t.getX() + 16, t.getY() + 16, xSpeed, ySpeed, t.getDmg(), rotate, projId++, type));
		
	}

	public void update() {
		for(Projectile p : projectiles) {
			if(p.isActive()) {
				p.move();
				if(hitEnemy(p)) {
					p.setActive(false);
					if(p.getProjectileType() == BOMB) {
						explosions.add(new Explosion(p.getPos()));
						explodeAOE(p);
					}
				}
				else if(isProjectileOutsideBounds(p)) {
					p.setActive(false);
				}
			}
		}
		for(Explosion e : explosions) {
			if(e.getIdx() < 7)
				e.update();
		}
	}

	private void explodeAOE(Projectile p) {
		for(Enemy e : playing.getEnemyHandler().getEnemies()) {
			if(e.isAlive()) {
				float radius = 40.0f;
				float xDist = Math.abs(p.getPos().x - e.getX());
				float yDist = Math.abs(p.getPos().y - e.getY());
				float dist = (float) Math.hypot(xDist, yDist);
				
				if(dist <= radius) {
					e.damage(p.getDamage());
				}
			}
		}
	}

	private boolean hitEnemy(Projectile p) {
		for(Enemy e : playing.getEnemyHandler().getEnemies()) {
			if(e.isAlive())
				if(e.getBounds().contains(p.getPos())) {
					e.damage(p.getDamage());
					if(!e.isAlive() && randomInt() % 4 == 0) {
						playing.addMoney(1);
					}
					else if(p.getProjectileType() == SNOW) {
						e.slow();
					}
					return true;
				}
		}
		return false;
	}
	
	private boolean isProjectileOutsideBounds(Projectile p) {
		if(p.getPos().x >= 0)
			if(p.getPos().x <= 640)
				if(p.getPos().y >= 0)
					if(p.getPos().y <= 640)
						return false;
		return true;
	}

	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		for(Projectile p : projectiles) {
			if(p.isActive()) {
				if(p.getProjectileType() == ARROW) {
					g2d.translate(p.getPos().x, p.getPos().y);
					g2d.rotate(Math.toRadians(p.getRotation()));
					g2d.drawImage(projImgs[p.getProjectileType()], -16, -16, null);
					g2d.rotate(-Math.toRadians(p.getRotation()));
					g2d.translate(-p.getPos().x, -p.getPos().y);
				}else {
					g2d.drawImage(projImgs[p.getProjectileType()], (int) p.getPos().x - 16, (int) p.getPos().y - 16, null);
				}
			}
		}
		
		drawExplosion(g2d);
	}
	
	private void drawExplosion(Graphics2D g2d) {
		for(Explosion e : explosions) {
			if(e.getIdx() < 7) {
				g2d.drawImage(explosionImgs[e.getIdx()], (int) e.getPos().x - 16, (int) e.getPos().y - 16, null);
			}
		}
	}

	private int getProjectileType(Tower t) {
		switch(t.getTowerType()) {
		case CANNON:
			return BOMB;
		case ARCHER:
			return ARROW;
		case ICE:
			return SNOW;
		}
		return -1;
	}
	
	public class Explosion{
		
		private Point2D.Float pos;
		private int explosionTick = 0, explosionIndex = 0;
		
		public Explosion(Point2D.Float pos) {
			this.pos = pos;
		}
		
		public void update() {
			explosionTick++;
			if(explosionTick >= 10) {
				explosionTick = 0;
				explosionIndex++;
			}
		}
		
		public Point2D.Float getPos(){
			return pos;
		}
		
		public int getIdx() {
			return explosionIndex;
		}
		
	}

	public void reset() {
		projectiles.clear();
		explosions.clear();
		projId = 0;
	}
	
}
