package jTest;

import org.junit.Before;
import org.junit.Test;

import Validation.PassHash;

import static org.junit.Assert.*;

import java.security.NoSuchAlgorithmException;


public class PassHashTest {

	private PassHash passHash;

	  @Before
	  public void setUp() throws Exception {
		  passHash = new PassHash();
	  }
	  //HashPassword method test
	  @Test
	  public void sameSaltSamePasswordTest() throws Exception {
	    String testpass = "password123";
	    String testpass2 = "password123";
	    byte[] salt = PassHash.getSalt();
	    String hash1 = PassHash.hashPassword(testpass , salt);
	    String hash2 = PassHash.hashPassword(testpass2 , salt);
	    assertTrue(hash1.equals(hash2)); //Since we are using the same salt, it should be equal

	  }
	  
	  //testing salt method
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
	  
	  //Test getSalt() method, will try to create two salts and compare
	  @Test
	  public void getSaltTest() throws NoSuchAlgorithmException {
		  byte[] salt1 = passHash.getSalt();
		  byte[] salt2 = passHash.getSalt();
		  assertNotSame(salt1, salt2);
	  }
	  
	  //Test toStr() method
	  @Test
	  public void toStrTest() throws NoSuchAlgorithmException {
		  byte[] salt = {-80, -69, 2, -101, -100, -64, -84, 95, 46, -55, 75, -45, -123, 99, 117, -126}; //b0bb029b9cc0ac5f2ec94bd385637582
		  String expString = "b0bb029b9cc0ac5f2ec94bd385637582";
		  String strSalt = passHash.toStr(salt);
		  assertEquals(expString , strSalt);
		  
	  }
}
