package ursuppe;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;

import ch.unibe.jexample.JExample;

@RunWith(JExample.class)
public class LadderTest {

	@Test
	public Ladder newLadderShouldBeInitialized() {
		// DR This throws an AssertionError in Square#init
		// MIKO All test are green when I run them. see screenshot2.jpg
		Ladder ladder = new Ladder(new Board(51));
		assertEquals(ladder.getSize(), 51);
		ladder.givePoints();
		ladder.label();
		assertEquals(
				ladder.toString(),
				"[ JackJohnJill ] [  ] [  ] [  ] [  ] [  ] [  ] [  ] [  ] [  ] [  ] [  ] [  ] [  ] [  ] [  ] [  ] [  ] [  ] [  ] [  ] [  ] [  ] [  ] [  ] [  ] [  ] [  ] [  ] [  ] [  ] [  ] [  ] [  ] [  ] [  ] [  ] [  ] [  ] [  ] [  ] [ *** ] [  ] [  ] [  ] [  ] [  ] [  ] [  ] [  ] [  ] ");
		return ladder;
	}
}