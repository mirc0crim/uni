package ursuppe;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;

import ch.unibe.jexample.JExample;

@RunWith(JExample.class)
public class BoardTest {

	@Test
	public Board newBoardShouldBeCorrectInitialized() {
		Board board = new Board(20);
		assertEquals(3, board.players.size());
		assertEquals(3 * 55, board.food.size());
		return board;
	}

	@Test
	public Board boardShouldDriftAmoebasToCorrectPosition() throws IOException {
		Board board1 = new Board(20);
		Amoeba amoeba = new Amoeba("blue");
		amoeba.setPosition(11);
		board1.drift(amoeba, 1);
		assertEquals(amoeba.getPosition(), 12);
		board1.drift(amoeba, 2);
		assertEquals(amoeba.getPosition(), 17);
		board1.drift(amoeba, 3);
		assertEquals(amoeba.getPosition(), 16);
		board1.drift(amoeba, 4);
		assertEquals(amoeba.getPosition(), 16);
		board1.drift(amoeba, 6);
		assertEquals(amoeba.getPosition(), 16);
		return board1;
	}

	@Test
	public Board boardShouldLetAmoebasEat() {
		// DR This throws an AssertionError in Square#init
		// MIKO All test are green when I run them. see screenshot2.jpg
		Board board2 = new Board(20);
		Amoeba amoeba2 = new Amoeba("red");
		amoeba2.setPosition(11);
		board2.getSquare(11).eat(amoeba2);
		int count = 0;
		for (int i = 0; i < board2.food.size(); i++)
			if (board2.food.get(i).getPosition() == 11)
				count++;
		assertEquals(count, 5);
		return board2;
	}

	@Test
	public Board boardShouldBeOverInTheEnd() {
		Board board = new Board(20);
		assertEquals(board.gameIsOver(), false);
		new Player("red", "john");
		Player.move(41);
		assertEquals(board.gameIsOver(), true);
		return board;
	}

}
