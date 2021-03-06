package jTest;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import Validation.DbConnect;

public class DbConnectTest {
	
	private DbConnect dbConnect; 
	
	@Before
	  public void setUp() throws Exception {
		dbConnect = new DbConnect();
	  }
	
	//testing with correct username and password
	@Test
	public void correctUserPassTest() {
		String username = "testuser";
		String password = "testpass";
		
		boolean authenticate = dbConnect.getPass(username, password);
		assertTrue(authenticate);
	}
	
	//testing with wrong username and password
	@Test
	public void correctUserWrongPassTest() {
		String username = "testuser";
		String password = "wrongpass";
		
		boolean authenticate = dbConnect.getPass(username, password);
		assertFalse(authenticate);
	}
}
