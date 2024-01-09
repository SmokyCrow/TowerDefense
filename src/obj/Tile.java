package obj;

import java.awt.image.BufferedImage;

public class Tile {
	
	private BufferedImage[] sprite;
	private int id, tileType;
	
	public Tile(BufferedImage sprite, int id, int tileType) {
		this.sprite = new BufferedImage[1];
		this.sprite[0] = sprite;
		this.id = id;
		this.tileType = tileType;
	}
	
	public Tile(BufferedImage[] sprite, int id, int tileType) {
		this.sprite = sprite;
		this.id = id;
		this.tileType = tileType;
	}
	
	public BufferedImage getSprite(int animIdx) {
		return sprite[animIdx];
	}
	
	public BufferedImage getSprite() {
		return sprite[0];
	}
	
	public boolean isAnim() {
		return sprite.length > 1;
	}
	
	public int getTileType() {
		return tileType;
	}

	public int getId() {
		return id;
	}

}
