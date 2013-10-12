package ursuppe;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;

import ch.unibe.jexample.JExample;

@RunWith(JExample.class)
public class FoodTest {

	@Test
	public Food foodShouldHaveCorrectColor() {
		Food food1 = new Food("green");
		assertEquals("green", food1.getColor());
		food1.setColor("blue");
		assertEquals("blue", food1.getColor());
		return food1;
	}

	@Test
	public Food foodShouldBeAtCorrectPosition() {
		Food food2 = new Food("red");
		food2.setPosition(10);
		assertEquals(10, food2.getPosition());
		return food2;
	}

	@Test
	public Food foodShouldHaveCorrectLabel() {
		Food food3 = new Food("blue");
		assertEquals("#b#", food3.toString());
		food3.setColor("red");
		assertEquals("#r#", food3.toString());
		return food3;
	}
}
