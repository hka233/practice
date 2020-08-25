package Validation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class DbConnect{
	
	private String password;
	private byte[] salt;
	
	public String getPassword() {
		return password;
	}
	
	public byte[] getsalt() {
		return salt;
	}

	private static final String url = "jdbc:mysql://localhost:3306/testdb"; 
	private static final String user = "testUser"; 
	private static final String dbPass = "password";

	public void getPass(String username)  {
		
		//Basic JDBC code to connect to the testdb dataset
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			myConn = DriverManager.getConnection(url, user, dbPass);
			
			String sql = "SELECT * FROM testdb.account_info WHERE username = ?;";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, username);
			
			myRs = myStmt.executeQuery();
			
			//while loop to list out the user names
			while (myRs.next()) {
				password = myRs.getString("password_name");
				String dbsalt = myRs.getString("salt");
				salt = PassHash.toByteArr(dbsalt);
			}
			
			
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}
	
	

	

}
