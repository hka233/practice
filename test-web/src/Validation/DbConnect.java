package Validation;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class DbConnect{
	
	public static void main(String[] args) throws NoSuchAlgorithmException {
			DbConnect pass = new DbConnect();
			boolean str = pass.getPass("testuser" , "testpass");
			System.out.println(str);
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
	private static final String pass = "password";

	public boolean getPass(String username, String password)  {
		
		//Basic JDBC code to connect to the testdb dataset
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		String dbPass = "";
		String dbsalt = "";
		boolean valid = false;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			myConn = DriverManager.getConnection(url, user, pass);
			
			String sql = "SELECT * FROM testdb.account_info WHERE username = ?;";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, username);
			
			myRs = myStmt.executeQuery();
			
			//while loop to list out the user names
			while (myRs.next()) {
				dbPass = myRs.getString("password_name");
				dbsalt = myRs.getString("salt");
				//this.salt = PassHash.toByteArr(dbsalt);
			}
			
			byte[] salt = PassHash.toByteArr(dbsalt);
			String pass = PassHash.hashPassword(password, salt);
			valid = dbPass.equals(pass);
			
			
			return valid;
			
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return valid; 
	}
	
	

	

}
