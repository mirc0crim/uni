package ursuppe;

import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.Expectations;
import org.jmock.Mockery;

import org.junit.Test;
import org.junit.runner.RunWith;





@RunWith(JMock.class)
public class RandomTest {
	Mockery mockObject = new JUnit4Mockery();
	final ICardBank bank = mockObject.mock(ICardBank.class);
	
	
	@Test
	public void driftDirectionIsValid() {
        // set up
        final int number1 =3;
        // expectations
        mockObject.checking(new Expectations() {{
        	allowing (bank).getDriftDirection();will(returnValue(number1));
        }});

        // execute
        bank.getDriftDirection();
    }
	
	@Test
	public void ozonThicknessIsValid() {
		//set up
		final int number2 = 5;
        // expectations
        mockObject.checking(new Expectations() {{
        	allowing (bank).getOzoneLayerThikness();will(returnValue(number2));
        }});

        // execute
        bank.getOzoneLayerThikness();
	}
}