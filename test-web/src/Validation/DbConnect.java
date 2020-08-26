package Validation;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class DbConnect{
	
	public static void main(String[] args) throws NoSuchAlgorithmException {
		DbConnect pass = new DbConnect();
		pass.getPass("testuser");
		//byte[] salt = pass.getsalt();
		//String str = PassHash.toStr(salt);
		//System.out.println(str);
	}
	/*
	private String password;
	private byte[] salt;
	
	public String getPassword() {
		return password;
	}
	
	public byte[] getsalt() {
		return salt;
	}
*/
	private static final String url = "jdbc:mysql://localhost:3306/testdb"; 
	private static final String user = "testUser"; 
	private static final String dbPass = "password";

	public static String getPass(String username)  {
		
		//Basic JDBC code to connect to the testdb dataset
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		String password = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			myConn = DriverManager.getConnection(url, user, dbPass);
			
			String sql = "SELECT * FROM testdb.account_info WHERE username = ?;";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, username);
			
			myRs = myStmt.executeQuery();
			
			//while loop to list out the user names
			while (myRs.next()) {
				password = myRs.getString("password_name");
				String dbsalt = myRs.getString("salt");
				//this.salt = PassHash.toByteArr(dbsalt);
			}
			return password;
			
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return password; 
	}
	
	

	

}
