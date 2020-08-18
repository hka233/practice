package Validation;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKeyFactory;
/**
 * 
 * @author Hamza Ahmed
 * Creating class to salt + hash password for a more secure system
 * Using the PBKDF2 hash function
 */

public class PassHash {

	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String password = "password";
        byte[] salt = getSalt();
        String hashedPassword = hashPassword(password , salt);
        System.out.println(String.valueOf(hashedPassword));
    }
	
	//method to has the password
	public static String hashPassword(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        int iterations = 1000; //
        int keyLength = 128;
        char[] chars = password.toCharArray();
         
        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, keyLength);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return toStr(hash);
    }
	
	//method for generating salt values
	public static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG"); //SHA1PRNG algorithm is used to generate strong pseudo-random number
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        System.out.println(toStr(salt));
        return salt;
    }
	
	private static String toStr(byte[] array) throws NoSuchAlgorithmException
    {
        BigInteger bi = new BigInteger(1, array);
        String str = bi.toString(16);
        
        return str;
    }
}
