package ursuppe;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;

import ch.unibe.jexample.JExample;

@RunWith(JExample.class)
public class CardsTest {

	CardBank bank = new CardBank();
	
	@Test
	public ICardBank cardIntelligenceShouldBeSold() {
		new CardBank();
		new Player("red", "joe");
		assertTrue(CardBank.buysIntelligence());
		CardBank.buysIntelligence();
		assertFalse(CardBank.buysIntelligence());
		return null;
	}

	@Test
	public ICardBank cardDefenseShouldBeSold() {
		new CardBank();
		new Player("blue", "jack");
		assertTrue(CardBank.buysDefense());
		assertFalse(CardBank.buysDefense());
		return null;
	}

	@Test
	public ICardBank cardSpeedShouldBeSold() {
		new CardBank();
		new Player("green", "jill");
		assertTrue(CardBank.buysSpeed());
		assertFalse(CardBank.buysSpeed());
		return null;
	}

	@Test
	public ICardBank environmentCardShouldHaveValidDirection() {
		assertTrue(bank.getDriftDirection() >= 0);
		assertTrue(bank.getDriftDirection() < 4);
		return null;
	}

	@Test
	public ICardBank environmentCardShouldHaveValidOzone() {
		assertTrue(bank.getOzoneLayerThikness() > 2);
		assertTrue(bank.getOzoneLayerThikness() < 7);
		return null;
	}

}
