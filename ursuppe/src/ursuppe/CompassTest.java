package ursuppe;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;

import ch.unibe.jexample.JExample;

@RunWith(JExample.class)
public class CompassTest {

	@Test
	public ICardBank compassShouldSetCorrectDirection() {
		CompassSquare.setDriftDirection(2);
		assertEquals(CompassSquare.getDriftDirection(), 2);
		CompassSquare.setDriftDirection(0);
		assertEquals(CompassSquare.getDriftDirection(), 0);
		return null;
	}

	@Test
	public ICardBank compassShouldSetCorrectOzone() {
		CompassSquare.setOzoneLayerThikness(3);
		assertEquals(CompassSquare.getOzoneLayerThikness(), 3);
		CompassSquare.setOzoneLayerThikness(5);
		assertEquals(CompassSquare.getOzoneLayerThikness(), 5);
		return null;
	}

}
