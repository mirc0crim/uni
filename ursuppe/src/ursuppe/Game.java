package ursuppe;

import java.io.IOException;

/**
 * Game is the main class creating the board and starting the game.
 * 
 * @author Mirco Kocher
 * @author Patricia Schwab
 * 
 */
public class Game {

	public static void main(String[] args) throws IOException {
		Board board = new Board(20);
		new CardBank();
		board.play();
	}

}