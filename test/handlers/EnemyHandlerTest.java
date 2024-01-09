package handlers;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import enemies.Blob;
import enemies.Enemy;
import enemies.TrashCan;
import game.Game;
import scenes.Playing;

public class EnemyHandlerTest {
	
	EnemyHandler eh;
	
	@Before
	public void setUp() {
		Game game = new Game();
		Playing playing = new Playing(game, 1);
		eh = playing.getEnemyHandler();
	}

	@Test
	public void testIsAtEnd() {
		Enemy e1 = new Blob(352, 288, 0);
		Enemy e2 = new Blob(4, 4, 1);
		Assert.assertTrue(eh.isAtEnd(e1));
		Assert.assertFalse(eh.isAtEnd(e2));
	}
	
	@Test
	public void testAddEnemy() {
		eh.addEnemy(0);
		Assert.assertEquals(0, eh.getEnemies().get(0).getEnemyType());
	}
	
	@Test
	public void testDefaultEnemiesLeft() {
		Assert.assertEquals(0, eh.getAmountOfEnemiesLeft());
	}

}
