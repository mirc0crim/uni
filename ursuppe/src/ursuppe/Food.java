package ursuppe;

/**
 * Food creates all the food on the game board with a color and position.
 * 
 * @author Mirco Kocher
 * @author Patricia Schwab
 * 
 */
public class Food {
	private int position;
	private String color;

	public Food(String color) {
		position = 100;
		this.color = color;
	}

	@ForTestingOnly
	public void setColor(String color) {
		this.color = color;
	}

	public String getColor() {
		return color;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getPosition() {
		return position;
	}

	protected String label() {
		String label;
		if (color.equals("blue"))
			label = "#b#";
		else if (color.equals("green"))
			label = "#g#";
		else
			label = "#r#";
		return label;
	}

	@Override
	public String toString() {
		return label();
	}
}
