package jTest;

import java.security.NoSuchAlgorithmException;

import Validation.DbConnect;

public class DbConnectTest {

	public static void main(String[] args) throws NoSuchAlgorithmException {
		DbConnect pass = new DbConnect();
		String str = pass.getPass("test2");
		System.out.println(str);
	}
}
