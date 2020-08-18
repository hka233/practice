package jTest;

import org.junit.Test;

import Validation.CheckAuthentication;

import static org.junit.Assert.assertEquals;

public class JUnitTest {
	
	String user = "testuser";
	String pass = "testpass";
	String output = "welcome-page";
	String redirect = "welcome-page.xhtml";
	CheckAuthentication checkAuth = new CheckAuthentication();
	
	@Test
	public void testCheckAuthentication() {	  
	      //assertEquals(output,checkAuth.checkAuth(user, pass));
	   }
	
	@Test
	public void testDoRedirect() {
		
		//assertEquals("welcome-page.xhtml", checkAuth.doRedirect(redirect));
	}

}
