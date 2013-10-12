package ursuppe;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * CardBank is the bank for all genes and the environment card.
 * 
 * @author Mirco Kocher
 * @author Patricia Schwab
 * 
 */
public class CardBank implements ICardBank {

	private static Random generator = new Random();
	private static int direction = generator.nextInt(4);
	private static int ozone = generator.nextInt(4) + 3;
	public static List freeGenes = new ArrayList();

	protected CardBank() {
		freeGenes.add("intelligence");
		freeGenes.add("speed");
		freeGenes.add("defence");
	}

	@Override
	public int getDriftDirection() {
		direction = generator.nextInt(4);
		return direction;
	}

	@Override
	public int getOzoneLayerThikness() {
		ozone = generator.nextInt(4) + 3;
		return ozone;
	}

	public static boolean buysIntelligence() {
		if (Player.getBP() > 1 && freeGenes.remove("intelligence")) {
			Player.setMP(Player.getMP() + 3);
			Player.setBP(Player.getBP() - 2);
			return Player.addGen("intelligence");
		} else
			return false;
	}

	public static boolean buysSpeed() {
		if (Player.getBP() > 2 && freeGenes.remove("speed")) {
			Player.setMP(Player.getMP() + 4);
			Player.setBP(Player.getBP() - 3);
			return Player.addGen("speed");
		} else
			return false;
	}

	public static boolean buysDefense() {
		if (Player.getBP() > 3 && freeGenes.remove("defence")) {
			Player.setMP(Player.getMP() + 4);
			Player.setBP(Player.getBP() - 4);
			return Player.addGen("defence");
		} else
			return false;
	}

}