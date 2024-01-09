package handlers;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import helperMethods.LoadSave;
import obj.Tile;
import static helperMethods.Constants.Tiles.*;

public class TileHandler {
	
	public Tile WATER,GRASS,ROAD;
	public BufferedImage atlas;
	public ArrayList<Tile> tiles = new ArrayList<>();
	
	public TileHandler() {
		
		loadAtlas();
		createTiles();
		
	}

	private void createTiles() {
		
		int id = 0;
		tiles.add(WATER = new Tile(getAnimSprites(0, 0), id++, WATER_TILE));
		tiles.add(GRASS = new Tile(getSprite(7, 0), id++, GRASS_TILE));
		tiles.add(ROAD = new Tile(getSprite(4, 0), id++, ROAD_TILE));
		
	}

	private void loadAtlas() {
		atlas = LoadSave.getSpriteAtlas();
	}
	
	public Tile getTile(int id) {
		return tiles.get(id);
	}
	
	public BufferedImage getSprite(int id) {
		return tiles.get(id).getSprite();
	}
	
	public BufferedImage getAnimSprite(int id, int animIdx) {
		return tiles.get(id).getSprite(animIdx);
	}
	
	private BufferedImage[] getAnimSprites(int x, int y) {
		BufferedImage[] arr = new BufferedImage[4];
		for(int i = 0; i < 4; i++) {
			arr[i] = getSprite(x + i, y);
		}
		return arr;
	}
	
	private BufferedImage getSprite(int x, int y) {
		return atlas.getSubimage(x*32, y*32, 32, 32);
	}
	
	public boolean isSpriteAnim(int id) {
		return tiles.get(id).isAnim();
	}
	
}
