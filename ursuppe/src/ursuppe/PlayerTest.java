package ursuppe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;

import ch.unibe.jexample.JExample;

@RunWith(JExample.class)
public class PlayerTest {
	@Test
	public Player newPlayerShouldBeInitialized() {
		Player player = new Player("blue", "john");
		assertEquals(player.getName(), "john");
		assertEquals(player.getColor(), "blue");
		assertEquals(Player.getPosition(), 0);
		assertEquals(Player.getBP(), 4);
		assertEquals(Player.getMP(), 0);
		Player.move(5);
		assertEquals(5, Player.getPosition());
		Player.setBP(5);
		assertEquals(Player.getBP(), 5);
		Player.setMP(7);
		assertEquals(Player.getMP(), 7);
		return player;
	}

	@Test
	public Player playerShouldBuyTwoGenes() {
		Player player = new Player("blue", "jack");
		player.buyGen("defence");
		assertTrue(player.hasGen("defence"));
		player.buyGen("intelligence");
		assertTrue(player.hasGen("intelligence"));
		return player;
	}

	@Test
	public Player PlayerShouldSellAllThreeGenes() {
		Player player = new Player("blue", "jack");
		player.buyGen("defence");
		player.buyGen("intelligence");
		player.buyGen("speed");
		assertTrue(player.hasGen("speed"));
		assertTrue(player.hasGen("intelligence"));
		assertTrue(player.hasGen("defence"));
		player.sellGen("speed");
		assertFalse(player.hasGen("speed"));
		player.sellGen("intelligence");
		assertFalse(player.hasGen("intelligence"));
		player.sellGen("defence");
		assertFalse(player.hasGen("defence"));
		return player;
	}
}