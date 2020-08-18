package Validation;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

/**
 * 
 * @author Hamza Ahmed
 * Creating class to salt + hash password for a more secure system
 * Using the PBKDF2 hash function
 */

public class PassHash {

	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        String  originalPassword = "password";
       
    }
	
	//method for generating salt values
	private static byte[] getSalt() throws NoSuchAlgorithmException
    {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG"); //SHA1PRNG algorithm is used to generate strong pseudo-random number
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }
}
