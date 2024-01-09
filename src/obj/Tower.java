package obj;

public class Tower {
	
	private int x, y, id, towerType, cdwnTick, dmg;
	private float range, cooldown;
	
	public Tower(int x, int y, int id, int towerType) {
		this.x = x;
		this.y = y;
		this.id = id;
		this.towerType = towerType;
		setDefaultDamage();
		setDefaultRange();
		setDefaultCooldown();
	}
	
	public void update() {
		cdwnTick++;
	}
	
	public boolean isCooldownOver() {
		return cdwnTick >= cooldown;
	}

	public void resetCooldown() {
		cdwnTick = 0;
	}

	private void setDefaultCooldown() {
		cooldown = helperMethods.Constants.Towers.GetDefaultCooldown(towerType);
	}

	private void setDefaultRange() {
		range = helperMethods.Constants.Towers.GetDefaultRange(towerType);		
	}

	private void setDefaultDamage() {
		dmg = helperMethods.Constants.Towers.GetDefaultDamage(towerType);		
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getId() {
		return id;
	}

	public int getTowerType() {
		return towerType;
	}

	public int getDmg() {
		return dmg;
	}

	public float getRange() {
		return range;
	}

	public float getCooldown() {
		return cooldown;
	}
	
}
