package ursuppe;

import org.junit.Test;
import org.junit.runner.RunWith;

import ch.unibe.jexample.JExample;

@RunWith(JExample.class)
public class GameTest {
	@Test
	public Game newGameShouldBeInitialized() {
		Game game = new Game();
		return game;
	}

}