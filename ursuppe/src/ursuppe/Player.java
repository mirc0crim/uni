package ursuppe;

import java.util.ArrayList;
import java.util.List;

/**
 * Player creates new players with a color, name, position, bP & mP and can buy
 * or sell genes.
 * 
 * @author Mirco Kocher
 * @author Patricia Schwab
 * 
 */
public class Player {
	private static int position;

	private final String name;
	private final String color;
	private static int bP;
	private static int MP;
	private static List<String> genes;

	public Player(String myColor, String myName) {
		color = myColor;
		name = myName;
		position = 0;
		bP = 4;
		MP = 0;
		genes = new ArrayList<String>();
	}

	public boolean hasGen(String gen) {
		return genes.contains(gen);
	}

	public static boolean addGen(String gen) {
		return genes.add(gen);
	}

	public String getName() {
		return name;
	}

	public String getColor() {
		return color;
	}

	public static int getPosition() {
		return position;
	}

	public static void move(int transport) {
		position = position + transport;
	}

	public static int getBP() {
		return bP;
	}

	public static void setBP(int newbP) {
		bP = newbP;
	}

	public static int getMP() {
		return MP;
	}

	public static void setMP(int newMP) {
		MP = newMP;
	}

	public void sellGen(String gen) {
		if (gen != "defence" && gen != "speed" && gen != "intelligence")
			return;
		if (gen == "defence")
			bP += 4;
		if (gen == "speed")
			bP += 4;
		if (gen == "intelligence")
			bP += 3;
		genes.remove(gen);
	}

	public void buyGen(String gen) {
		if (gen == "defence" || gen == "speed" || gen == "intelligence") {
			if (gen == "defence")
				CardBank.buysIntelligence();
			else if (gen == "speed")
				CardBank.buysSpeed();
			else
				CardBank.buysDefense();
			genes.add(gen);
		}
	}

	@Override
	public String toString() {
		String s = getName();
		return s;
	}

}
