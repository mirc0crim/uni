package ursuppe;

//DR Probably you could do problemset10 (it's so easy and rly fast done ^^)

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * Board creates a new game board GUI and knows about the game phases.
 * 
 * @author Mirco Kocher
 * @author Patricia Schwab
 * 
 */
public class Board {
	String yesText = "Yes";
	String noText = "no";
	String notEnoughText = "You don't have enought BP's";
	String sellAnyText = "You don't have enough Biological Points, which means you have to sell some Gene Cards";
	String defText = "defence";
	String speText = "speed";
	String intText = "intelligence";
	String sellYourText = "Do you want to sell your";
	String buyTheText = "Do you want to buy the";
	String boughtText = "You bought a Gen";
	private final List<Square> squares = new ArrayList<Square>();
	public List<Amoeba> amoebas = new ArrayList<Amoeba>();
	public List<Food> food = new ArrayList<Food>();
	public List<Player> players = new ArrayList<Player>();
	private final int size;
	public final Ladder ladder;
	public final CardBank bank;
	private final static JTextArea ladderString = new JTextArea();
	private final static JTextArea boardString = new JTextArea();
	private final static JTextArea optionString = new JTextArea();
	private final static JTextArea playerString = new JTextArea();
	private final JButton button1 = new JButton(yesText);
	private final JButton button2 = new JButton(noText);
	private final JButton button3 = new JButton("-");
	private final JButton button4 = new JButton("-");
	private final JButton button5 = new JButton("-");
	private int selectedOption = 0;
	JFrame f = new JFrame("Ursuppe");
	JPanel p = new JPanel();

	public Board(int size) {
		this.size = size;
		addSquares(size);
		addFood();
		addPlayers();
		addAmoebas();
		assert invariant();
		ladder = new Ladder(this);
		bank = new CardBank();

		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(1000, 500);
		f.setVisible(true);
		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedOption = 1;
			}
		});
		button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedOption = 2;
			}
		});
		button3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedOption = 3;
			}
		});
		button4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedOption = 4;
			}
		});
		button5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedOption = 5;
			}
		});
		p.add(boardString);
		p.add(ladderString);
		p.add(optionString);
		p.add(playerString);
		p.add(button1);
		p.add(button2);
		f.setContentPane(p);
	}

	private void addPlayers() {
		Player jack = new Player("green", "Jack");
		players.add(jack);
		Player john = new Player("blue", "John");
		players.add(john);
		Player jill = new Player("red", "Jill");
		players.add(jill);
	}

	private void addAmoebas() {
		for (int i = 0; i < players.size(); i++) {
			for (int j = 0; j < 7; j++) {
				Amoeba amoeba = new Amoeba(players.get(i).getColor());
				amoebas.add(amoeba);
			}
			amoebas.get(i * 7).setPosition(3 * i + 1);
		}
	}

	public boolean gameIsOver() {
		boolean isOver = false;
		for (int i = 0; i < players.size(); i++) {
			players.get(i);
			if (Player.getPosition() >= 41)
				isOver = true;
		}
		return isOver;
	}

	private void addFood() {
		for (int i = 0; i < 55; i++) {
			Food food1 = new Food("blue");
			food.add(food1);
		}
		for (int i = 0; i < 55; i++) {
			Food food1 = new Food("green");
			food.add(food1);
		}
		for (int i = 0; i < 55; i++) {
			Food food1 = new Food("red");
			food.add(food1);
		}
		int j = 1;
		while (j < 21) {
			if (j != 12) {
				food.get(j).setPosition(j);
				food.get(j + 20).setPosition(j);
				food.get(j + 55).setPosition(j);
				food.get(j + 55 + 20).setPosition(j);
				food.get(j + 110).setPosition(j);
				food.get(j + 110 + 20).setPosition(j);
			}
			j++;
		}
	}

	private boolean invariant() {
		return squares.size() > 3 && size == squares.size();
	}

	public boolean isValidPosition(int position) {
		return position >= 1 && position < size;
	}

	public void setSquare(int position, Square square) {
		initSquare(position, square);
		assert invariant();
	}

	public Square getSquare(int position) {
		assert isValidPosition(position);
		return squares.get(position - 1);
	}

	@Override
	public String toString() {
		String label;
		label = "                      ";
		for (int i = 0; i < 4; i++)
			label = label + squares.get(i).squareLabel();
		label = label + "\n";
		for (int j = 4; j < 9; j++)
			label = label + squares.get(j).squareLabel();
		label = label + "\n";
		for (int j = 9; j < 14; j++)
			label = label + squares.get(j).squareLabel();
		label = label + "\n";
		for (int j = 14; j < 19; j++)
			label = label + squares.get(j).squareLabel();
		label = label + "\n ";
		label = label + "			                                             ";
		label = label + squares.get(19).squareLabel();
		return label;
	}

	private void addSquares(int size) {
		for (int i = 0; i < size; i++) {
			Square square = new Square(this, i + 1);
			squares.add(square);
		}
	}

	private void initSquare(int position, Square square) {
		assert isValidPosition(position) && square != null;
		squares.set(position - 1, square);
	}

	public void play() throws IOException {
		boardString.setText(toString());
		ladderString.setText(ladder.toString());
		optionString.setText("New Round");
		while (!gameIsOver()) {
			phase1();
			phase2();
			phase3();
			phase4();
			phase5();
			phase6();
			boardString.setText(toString());
			ladderString.setText(ladder.toString());
		}
		System.out.println(getWinner());
	}

	private String getWinner() {
		String winner;
		players.get(0);
		players.get(1);
		if (Player.getPosition() >= Player.getPosition())
			winner = players.get(0).toString();
		else {
			players.get(1);
			players.get(2);
			if (Player.getPosition() >= Player.getPosition())
				winner = players.get(1).toString();
			else
				winner = players.get(2).toString();
		}
		return winner;
	}

	public void phase4() {
		for (int i = 0; i < players.size(); i++) {
			players.get(i);
			Player.setBP((Player.getBP() + 10));
			for (int j = 0; j < amoebas.size(); j++)
				if (amoebas.get(j).getPosition() == 100
						&& amoebas.get(j).getColor()
								.equals(players.get(i).getColor())) {
					amoebas.get(j).setPosition(1);
					players.get(i);
					Player.setBP((Player.getBP() - 6));
					break;
				}
		}
	}

	private void phase6() {
		ladder.givePoints();
	}

	public void phase5() {
		int count = 0;
		int pos;
		Amoeba amoeba;
		for (int i = 0; i < amoebas.size(); i++) {
			amoeba = amoebas.get(i);
			pos = amoeba.getPosition();
			if (amoeba.getBiologicalPoint() >= 2) {
				amoeba.setPosition(100);
				amoeba.addBiologicalPoint(-amoeba.getBiologicalPoint());
				for (int j = 0; j < 55; j++)
					for (int k = 1; k < 4; k++) {
						if (food.get(k * j).getPosition() == 100) {
							food.get(k * j).setPosition(pos);
							count++;
						}
						if (count == 2) {
							count = 0;
							k++;
						}
					}
			}
		}

	}

	private void phase3() throws IOException {
		for (int i = 0; i < players.size(); i++) {
			playerString.setText("Player: " + players.get(i).getName());
			// DR you could probably add a timer here to wait because if you buy
			// the interlgene it jumps forward to buyDefens gene
			// MIKO No, it doesn't do that for me. I can buy the Speed Gene if a
			// player had enough BP's. see screenshot1.jpg
			buyIntelligence();
			buySpeed();
			buyDefense();
		}
	}

	public void phase2() throws IOException {
		CompassSquare.setDriftDirection(bank.getDriftDirection());
		CompassSquare.setOzoneLayerThikness(bank.getOzoneLayerThikness());
		int ozone = CompassSquare.getOzoneLayerThikness();
		for (int i = 0; i < players.size(); i++) {
			Player player = players.get(i);
			if (Player.getMP() > ozone) {
				optionString.setText(optionString.getText() + "\n"
						+ players.get(i).getName() + ", you have to pay "
						+ (ozone - Player.getMP()) + " points");
				boolean removedPlayer = false;
				while (Player.getBP() > ozone - Player.getMP()) {
					sellIntelligence(player);
					sellSpeed(player);
					sellDefense(player);
					if (!player.hasGen(defText) && !player.hasGen(intText)
							&& !player.hasGen(speText)) {
						optionString
								.setText("No Cards nor BP's left. You die..");
						removedPlayer = players.remove(players.get(i));
						break;
					}
				}
				if (!removedPlayer) {
					Player.setBP(ozone);
					optionString.setText(optionString.getText() + "\n"
							+ "You now have " + ozone + " points");
				}
			}
		}
	}

	private void sellIntelligence(Player player) throws IOException {
		if (player.hasGen(intText)) {
			String text = sellYourText + " Intelligence Card?";
			optionString.setText(optionString.getText() + "\n" + sellAnyText
					+ "\n" + text);
			button1.setText(yesText);
			button2.setText(noText);
			while (selectedOption == 0)
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			if (selectedOption == 1)
				player.sellGen(intText);
		}
		selectedOption = 0;
	}

	private void sellSpeed(Player player) throws IOException {
		if (player.hasGen(speText)) {
			String text = sellYourText + " Speed Card?";
			optionString.setText(optionString.getText() + "\n" + sellAnyText
					+ "\n" + text);
			button1.setText(yesText);
			button2.setText(noText);
			while (selectedOption == 0)
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			if (selectedOption == 1)
				player.sellGen(speText);
		}
		selectedOption = 0;
	}

	private void sellDefense(Player player) throws IOException {
		if (player.hasGen(defText)) {
			String text = sellYourText + " Defense Card?";
			optionString.setText(optionString.getText() + "\n" + sellAnyText
					+ "\n" + text);
			button1.setText(yesText);
			button2.setText(noText);
			while (selectedOption == 0)
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			if (selectedOption == 1)
				player.sellGen(defText);
		}
		selectedOption = 0;
	}

	private void buyIntelligence() throws IOException {
		if (CardBank.freeGenes.contains(intText)) {
			optionString.setText(optionString.getText() + "\n" + buyTheText
					+ " Intelligence Gen?");
			button1.setText(yesText);
			button2.setText(noText);
			while (selectedOption == 0)
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			if (selectedOption == 1)
				if (CardBank.buysIntelligence())
					optionString.setText(optionString.getText() + "\n"
							+ boughtText);
				else
					optionString.setText(optionString.getText() + "\n"
							+ notEnoughText);
		}
		selectedOption = 0;
	}

	private void buySpeed() throws IOException {
		if (CardBank.freeGenes.contains(speText)) {
			optionString.setText(optionString.getText() + "\n" + buyTheText
					+ " Speed Gen?");
			button1.setText(yesText);
			button2.setText(noText);
			while (selectedOption == 0)
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			if (selectedOption == 1)
				if (CardBank.buysSpeed())
					optionString.setText(optionString.getText() + "\n"
							+ boughtText);
				else
					optionString.setText(optionString.getText() + "\n"
							+ notEnoughText);
		}
		selectedOption = 0;
	}

	private void buyDefense() throws IOException {
		if (CardBank.freeGenes.contains(defText)) {
			optionString.setText(optionString.getText() + "\n" + buyTheText
					+ " Defense Gen?");
			button1.setText(yesText);
			button2.setText(noText);
			while (selectedOption == 0)
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			if (selectedOption == 1)
				if (CardBank.buysDefense())
					optionString.setText(optionString.getText() + "\n"
							+ boughtText);
				else
					optionString.setText(optionString.getText() + "\n"
							+ notEnoughText);
		}
		selectedOption = 0;
	}

	public void phase1() throws IOException {
		int DriftDirection = CompassSquare.getDriftDirection();
		Player player = null;
		for (int j = 0; j < amoebas.size(); j++) {
			for (int i = 0; i < players.size(); i++)
				if (amoebas.get(j).getColor().equals(players.get(i).getColor()))
					player = players.get(i);
			if (amoebas.get(j).getPosition() != 100) {
				playerString.setText("Player: " + player.getName());
				optionString.setText(optionString.getText() + "\n"
						+ player.getName() + ", do you wand to drift or move?");
				button1.setText("Drift");
				button2.setText("Move");
				while (selectedOption == 0)
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				if (selectedOption == 1)
					drift(amoebas.get(j), DriftDirection);
				else {
					Player.setBP(Player.getBP()
							- (player.hasGen(speText) ? 1 : 0));
					Random generator = new Random();
					int moveDirection = generator.nextInt(6);
					move(amoebas.get(j), moveDirection);
				}
				selectedOption = 0;
				squares.get(amoebas.get(j).getPosition() - 1).eat(
						amoebas.get(j));
			}
		}
	}

	public void drift(Amoeba amoeba, int Driftdirection) throws IOException {
		int DriftDirection;
		DriftDirection = Driftdirection;
		if (DriftDirection == 5) {
			addMoveButtons();
			optionString
					.setText("Do you want to move to the Nord (n), East (e), South (s), West (w) or stay (0) here?");
			while (selectedOption == 0)
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			DriftDirection = getDriftDirection();
			removeMoveButtons();
		}
		driftToNewPosition(amoeba, DriftDirection);
		selectedOption = 0;
	}

	private void addMoveButtons() {
		p.add(button3);
		p.add(button4);
		p.add(button5);
		button1.setText("Nord");
		button2.setText("East");
		button3.setText("South");
		button4.setText("West");
		button5.setText("Stay");
	}

	private void removeMoveButtons() {
		p.remove(button3);
		p.remove(button4);
		p.remove(button5);
	}

	private void driftToNewPosition(Amoeba amoeba, int DriftDirection) {
		int newPosition;
		if (DriftDirection == 0)
			newPosition = getSquare(amoeba.getPosition()).upSquare();
		else if (DriftDirection == 1)
			newPosition = getSquare(amoeba.getPosition()).rightSquare();
		else if (DriftDirection == 2)
			newPosition = getSquare(amoeba.getPosition()).downSquare();
		else if (DriftDirection == 3)
			newPosition = getSquare(amoeba.getPosition()).leftSquare();
		else
			newPosition = amoeba.getPosition();
		amoeba.setPosition(newPosition);
	}

	private int getDriftDirection() {
		int DriftDirection;
		switch (selectedOption) {
		case 1:
			DriftDirection = 0;
			break;
		case 2:
			DriftDirection = 1;
			break;
		case 3:
			DriftDirection = 2;
			break;
		case 4:
			DriftDirection = 3;
			break;
		default:
			DriftDirection = 4;
		}
		return DriftDirection;
	}

	/**
	 * Move is basically the same as drift, but it makes the code more readable
	 * this way.
	 * 
	 * @param amoeba
	 * @param Driftdirection
	 * @throws IOException
	 */
	private void move(Amoeba amoeba, int Driftdirection) throws IOException {
		drift(amoeba, Driftdirection);
	}

}
