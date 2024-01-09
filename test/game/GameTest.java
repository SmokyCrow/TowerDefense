package game;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GameTest {
	
	Game game;
	
	@Before
	public void setUp() {
		game = new Game();
	}
	
	@Test
	public void testTitle() {
		Assert.assertEquals("DIY Tower Defense", game.getTitle());
	}

	@Test
	public void testDefaultSelectedLevel() {
		Assert.assertEquals(-1, game.getSelectedLevel());
	}
}
