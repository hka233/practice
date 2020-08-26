package Validation;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKeyFactory;
/**
 * 
 * @author Hamza Ahmed
 * Creating class to salt + hash password for a more secure system
 * Using the PBKDF2 hash function
 */

public class PassHash {
		//created to get the salt and hash for the password
	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String password = "testpass";
        byte[] salt = getSalt(); //47f91894ffbdcecea8b95ba77b034c37
        String hashedPassword = hashPassword(password , salt);
        System.out.println("salt: " + toStr(salt));
        System.out.println(String.valueOf(hashedPassword)); //ba21bc89db1c5fea264cea2d0d087b8f
    }
	
	//method to hash the password
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
        return salt;
    }
	
	public static String toStr(byte[] array) throws NoSuchAlgorithmException
    {
		
        BigInteger bi = new BigInteger(1, array);
        String str = bi.toString(16);
        //System.out.println("byte array " + Arrays.toString(array));
        //System.out.println("original " + str);
        return str;
    }
	
	public static byte[] toByteArr(String hexString) {
	    byte[] byteArray = new BigInteger(hexString, 16)
	      .toByteArray();
	    if (byteArray[0] == 0) {
	        byte[] output = new byte[byteArray.length - 1];
	        System.arraycopy(
	          byteArray, 1, output, 
	          0, output.length);
	        return output;
	    }
	    return byteArray;
	}
	

}
