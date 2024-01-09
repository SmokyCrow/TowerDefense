package enemies;

import static org.junit.Assert.*;

import java.awt.Rectangle;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static helperMethods.Constants.Enemies.*;

public class EnemyTest {
	
	Enemy b, t, p;
	
	@Before
	public void setUp() {
		b = new Blob(1, 1, 0);
		t = new TrashCan(2, 2, 1);
		p = new Plasticine(3, 3, 2);
	}

	@Test
	public void testBounds() {
		Rectangle r1 = new Rectangle(1, 1, 32, 32);
		Rectangle r2 = new Rectangle(2, 2, 32, 32);
		Rectangle r3 = new Rectangle(3, 3, 32, 32);
		Assert.assertEquals(r1, b.getBounds());
		Assert.assertEquals(r2, t.getBounds());
		Assert.assertEquals(r3, p.getBounds());
	}
	
	@Test
	public void testHealth() {
		Assert.assertEquals(100, b.getHealth());
		Assert.assertEquals(250, t.getHealth());
		Assert.assertEquals(85, p.getHealth());
	}
	
	@Test
	public void testDamage() {
		b.damage(5);
		t.damage(5);
		p.damage(5);
		Assert.assertEquals(95, b.getHealth());
		Assert.assertEquals(245, t.getHealth());
		Assert.assertEquals(80, p.getHealth());
	}
	
	@Test
	public void testKill() {
		b.kill();
		t.kill();
		p.kill();
		Assert.assertFalse(b.isAlive());
		Assert.assertFalse(t.isAlive());
		Assert.assertFalse(p.isAlive());
	}
	
	@Test
	public void testType() {
		Assert.assertEquals(BLOB, b.getEnemyType());
		Assert.assertEquals(TRASHCAN, t.getEnemyType());
		Assert.assertEquals(PLASTICINE, p.getEnemyType());
	}

}
