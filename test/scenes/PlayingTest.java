package scenes;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import game.Game;

public class PlayingTest {
	
	Playing playing;

	@Before
	public void setUp() {
		Game game = new Game();
		game.setSelectedLevel(1);
		playing = game.getPlaying();
	}
	
	@Test
	public void testCanEdit() {
		boolean can = playing.canEdit();
		Assert.assertTrue(can);
	}
	
	@Test
	public void testResetLevel() {
		ArrayList<Point> p = new ArrayList<>();
		for(int i = 0; i < 5; i++) {
			p.add(playing.getPath().get(i));
		}
		playing.resetLevel();
		Assert.assertEquals(1, playing.getMoney());
		Assert.assertEquals(10, playing.getLives());
		Assert.assertEquals(p, playing.getPath());
	}
	
	@Test
	public void testGetTileType() {
		int tile = playing.getTileType(0, 0);
		Assert.assertEquals(tile, 1);
	}

}
