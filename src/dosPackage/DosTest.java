package dosPackage;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Test;
import org.apache.log4j.Logger;

/**
 * The Class DosTest for Unit Test Case
 * @author KannigaNatarajan
 * @version 4.0
 */

class DosTest {
	WelcomeServlet test = new WelcomeServlet();
	final static Logger logger = Logger.getLogger(DosTest.class);
	
	/**
	 * This test case for success by equaling correct value
	 */
	@Test
	void passTest() {
		ArrayList<String> list = new ArrayList<String>();
		logger.debug("Entered success Test case");
		list = test.ProductSearch("Dairy Products","C:\\\\Users\\\\UVIND\\\\eclipse-workspace\\\\DOSAttack\\\\src");
		assertEquals("[Ghee, Butter, Milk, Sweet]", list.toString());
	}
	/**
	 * This test case for failure by equaling wrong value
	 */
	@Test
	void failTest() {
		ArrayList<String> list = new ArrayList<String>();
		logger.error("Entered failure Test case");
		list = test.ProductSearch("Dairy Products","C:\\\\Users\\\\UVIND\\\\eclipse-workspace\\\\DOSAttack\\\\src");
		assertEquals("[Ghee, Butter, Milk, Sweet22333]", list.toString());
	}
	/**
	 * This test case for passing empty value
	 */
	@Test
	void exceptionTest(){
		ArrayList<String> list = new ArrayList<String>();
		logger.debug("Entered passing empty value test case");
		list = test.ProductSearch("not found","C:\\\\Users\\\\UVIND\\\\eclipse-workspace\\\\DOSAttack\\\\src");
		assertEquals("[]", list.toString());
	}

}
