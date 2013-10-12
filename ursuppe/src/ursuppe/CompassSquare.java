package ursuppe;

/**
 * CompassSquare calculates the new direction and ozone.
 * 
 * @author Mirco Kocher
 * @author Patricia Schwab
 * 
 */
public class CompassSquare {

	private static int direction = 1;
	private static int ozone = 1;

	public static void setDriftDirection(int newDirection) {
		direction = newDirection;
	}

	public static int getDriftDirection() {
		return direction;
	}

	public static void setOzoneLayerThikness(int newOzone) {
		ozone = newOzone;
	}

	public static int getOzoneLayerThikness() {
		return ozone;
	}

}
