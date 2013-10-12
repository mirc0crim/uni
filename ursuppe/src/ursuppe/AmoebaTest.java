package ursuppe;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;

import ch.unibe.jexample.JExample;

@RunWith(JExample.class)
public class AmoebaTest {
	@Test
	public Amoeba newAmoebasShouldHaveCorrectColorPositionAndBP() {
		Amoeba amoeba = new Amoeba("blue");
		assertEquals("blue", amoeba.getColor());
		assertEquals(100, amoeba.getPosition());
		assertEquals(0, amoeba.getBiologicalPoint());
		return amoeba;
	}

	@Test
	public Amoeba amoebasShouldHaveCorrectLabel() {
		Amoeba amoebaRed = new Amoeba("red");
		assertEquals("<r>", amoebaRed.toString());
		Amoeba amoebaBlue = new Amoeba("blue");
		assertEquals("<b>", amoebaBlue.toString());
		Amoeba amoebaGreen = new Amoeba("green");
		assertEquals("<g>", amoebaGreen.toString());
		return amoebaGreen;
	}

	@Test
	public Amoeba amoebasShouldIncreasePoints() {
		Amoeba amoeba = new Amoeba("red");
		int points = amoeba.getBiologicalPoint();
		amoeba.setBiologicalPoint();
		assertEquals(points + 1, amoeba.getBiologicalPoint());
		return amoeba;
	}

	@Test
	public Amoeba amoebasShouldGetCorrectPoints() {
		Amoeba amoeba = new Amoeba("red");
		int points = amoeba.getBiologicalPoint();
		amoeba.addBiologicalPoint(5);
		assertEquals(points + 5, amoeba.getBiologicalPoint());
		return amoeba;
	}

	@Test
	public Amoeba amoebasShouldBeAtCorrectPosition() {
		Amoeba amoeba = new Amoeba("red");
		amoeba.setPosition(5);
		assertEquals(5, amoeba.getPosition());
		amoeba.setPosition(10);
		assertEquals(10, amoeba.getPosition());
		return amoeba;
	}

}