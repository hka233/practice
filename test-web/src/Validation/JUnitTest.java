package Validation;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class JUnitTest {
	
	String user = "testuser";
	String pass = "testpass";
	String output = "welcome-page";
	CheckAuthentication checkAuth = new CheckAuthentication();
	
	@Test
	public void testPrintMessage() {	  
	      //assertEquals(output,checkAuth.checkAuth(user, pass));
	   }

}
