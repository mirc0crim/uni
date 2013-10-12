package ursuppe;

/**
 * Amoeba creates new amoebas with a color, preset position and
 * biologicalPoints.
 * 
 * @author Mirco Kocher
 * @author Patricia Schwab
 * 
 */
public class Amoeba {
	private int position;
	private final String color;
	private int biologicalPoints;

	public Amoeba(String color) {
		this.color = color;
		position = 100;
		biologicalPoints = 0;
	}

	public int getBiologicalPoint() {
		return biologicalPoints;
	}

	public void setBiologicalPoint() {
		biologicalPoints++;
	}

	public void addBiologicalPoint(int add) {
		biologicalPoints += add;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getColor() {
		return color;
	}

	protected String label() {
		String label;
		if (color.equals("blue"))
			label = "<b>";
		else if (color.equals("green"))
			label = "<g>";
		else
			label = "<r>";
		return label;
	}

	@Override
	public String toString() {
		return label();
	}

}
