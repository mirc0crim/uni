package ursuppe;

/**
 * Square creates all the squares and each of them knows where it is and what
 * squares surround it and whether there is some food or not.
 * 
 * @author Mirco Kocher
 * @author Patricia Schwab
 * 
 */
public class Square {
	private int position;
	protected Board board;

	public Square(Board board, int position) {
		this.board = board;
		this.position = position;
		assert invariant();
	}

	private boolean invariant() {
		return board != null && board.isValidPosition(position);
	}

	protected int rightSquare() {
		if (position == 4 || position == 9 || position == 14 || position == 19
				|| position == 20)
			return position;
		else
			return position + 1;
	}

	protected int leftSquare() {
		if (position == 1 || position == 5 || position == 10 || position == 15
				|| position == 20)
			return position;
		else
			return position - 1;
	}

	protected int upSquare() {
		if (position <= 5 || position == 17)
			return position;
		else if (position == 20)
			return 18;
		else
			return position - 5;
	}

	protected int downSquare() {
		if (position == 7 || position == 15 || position == 16 || position == 17
				|| position >= 19)
			return position;
		else if (position == 18)
			return 20;
		else
			return position + 5;

	}

	@Override
	public String toString() {
		return "[" + squareLabel() + "]";
	}

	public String squareLabel() {
		String label;
		if (position == 12)
			label = "[                  ]";
		else {
			label = "[ ";
			for (int i = 0; i < board.food.size(); i++)
				if (board.food.get(i).getPosition() == position)
					label = label + board.food.get(i).toString();
			for (int j = 0; j < board.amoebas.size(); j++)
				if (board.amoebas.get(j).getPosition() == position)
					label = label + board.amoebas.get(j).toString();
			label = label + " ] ";
		}
		return label;
	}

	public boolean enoughFood(Amoeba amoeba) {
		boolean result = false;
		int count1 = 0;
		for (int i = 0; i < board.food.size(); i++)
			if (board.food.get(i).getColor().equals(amoeba.getColor()) == false
					&& board.food.get(i).getPosition() == position)
				count1++;
		if (count1 > 2)
			result = true;
		else
			amoeba.setBiologicalPoint();
		return result;
	}

	public void eat(Amoeba amoeba) {
		if (enoughFood(amoeba) == true) {
			int j = 0, i = 0;
			while (j < 3 && i < board.food.size()) {
				if (board.food.get(i).getColor().equals(amoeba.getColor()) == false
						&& board.food.get(i).getPosition() == position) {
					board.food.get(i).setPosition(100);
					j++;
				}
				i++;
			}
			int k = 0;
			for (int l = 0; l < board.food.size(); l++) {
				if (board.food.get(l).getPosition() == 100
						&& board.food.get(l).getColor()
								.equals(amoeba.getColor())) {
					board.food.get(l).setPosition(position);
					k++;
				}
				if (k == 2)
					break;
			}
		}

	}
}
