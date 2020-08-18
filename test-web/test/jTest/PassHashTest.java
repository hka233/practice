package jTest;

import org.junit.Before;
import org.junit.Test;

import Validation.PassHash;

import static org.junit.Assert.*;


public class PassHashTest {

	private PassHash passHash;

	  @Before
	  public void setUp() throws Exception {
		  passHash = new PassHash();
	  }

	  @Test
	  public void sameSaltSamePasswordTest() throws Exception {
	    String testpass = "password123";
	    String testpass2 = "password123";
	    byte[] salt = PassHash.getSalt();
	    String hash1 = PassHash.hashPassword(testpass , salt);
	    String hash2 = PassHash.hashPassword(testpass2 , salt);
	    assertTrue(hash1.equals(hash2)); //Since we are using the same salt, it should be equal

	  }

	  @Test
	  public void diffSaltSamePasswordTest() throws Exception {
	    String testpass = "password123";
	    String testpass2 = "password123";
	    byte[] salt = PassHash.getSalt();
	    byte[] salt2 = PassHash.getSalt();
	    String hash1 = PassHash.hashPassword(testpass , salt);
	    String hash2 = PassHash.hashPassword(testpass2 , salt2);
	    assertTrue(!hash1.equals(hash2)); //Different salt would make different output

	  }
	  
}
