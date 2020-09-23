package Validation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class DbConnect{
	
	public static void main(String[] args) {
		DbConnect dbConnect = new DbConnect();
		boolean str = dbConnect.getPass("testuser", "testpass");
		System.out.println(str);
		
		
	}
	private String url = "jdbc:mysql://localhost:3306/testdb"; 
	private String user = "testUser"; 
	private String pass = "password";

	public boolean getPass(String username, String password)  {
		
		//Basic JDBC code to connect to the testdb dataset
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		String dbPass = "";
		String dbsalt = "";
		boolean valid = false;
		
		try {
			Class.forName("com.mysql.jdbc.Driver"); //used to force apache to use this driver
			myConn = DriverManager.getConnection(url, user, pass);
			
			String sql = "SELECT * FROM testdb.account_info WHERE username = ?;"; //query to database
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, username);
			
			myRs = myStmt.executeQuery();
			
			//while loop to list out the user names
			while (myRs.next()) {
				dbPass = myRs.getString("password_name");
				dbsalt = myRs.getString("salt");
				//this.salt = PassHash.toByteArr(dbsalt);
			}
			byte[] salt = {};
			String pass = "no";
			if (dbsalt != "") {
				salt = PassHash.toByteArr(dbsalt);  //turning salt String to byte array to use it for hashing
				pass = PassHash.hashPassword(password, salt);
			}
			
			valid = dbPass.equals(pass); //after hashing user given password and database password, it is compared here
			
			
			return valid;
			
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return valid; 
	}
	
	

	

}
