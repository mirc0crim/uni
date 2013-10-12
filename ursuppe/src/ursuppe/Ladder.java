package ursuppe;

/**
 * Ladder creates the board tiles and gives Points.
 * 
 * @author Mirco Kocher
 * @author Patricia Schwab
 * 
 */
public class Ladder {
	private final int size;
	private final Board board;

	public Ladder(Board board) {
		this.board = board;
		size = 51;
	}

	@ForTestingOnly
	public int getSize() {
		return size;
	}

	public void givePoints() {
		int count = 0;
		for (int i = 0; i < board.players.size(); i++) {
			for (int j = 0; j < board.amoebas.size(); j++)
				if (board.amoebas.get(j).getPosition() != 100
						&& board.amoebas.get(j).getColor()
								.equals(board.players.get(i).getColor()))
					count++;
			if (count >= 5) {
				board.players.get(i);
				Player.move(count - 1);
			} else if (count == 4 || count == 3) {
				board.players.get(i);
				Player.move(count - 2);
			}
			count = 0;
		}
	}

	public String label() {
		String label = "";
		for (int i = 0; i < size; i++) {
			label = label + "[ ";
			if (i == 41)
				label = label + "***";
			for (int j = 0; j < board.players.size(); j++) {
				board.players.get(j);
				if (Player.getPosition() == i)
					label = label + board.players.get(j).toString();
			}
			label = label + " ] ";
		}
		return label;
	}

	@Override
	public String toString() {
		String s;
		s = label();
		return s;
	}
}
