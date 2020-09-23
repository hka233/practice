package CreateUser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Validation.PassHash;

public class Connect2DB {

	private String url = "jdbc:mysql://localhost:3306/testdb"; 
	private String user = "testUser"; 
	private String pass = "password";

	public boolean getPass(RegisterForm userinfo)  {
		
		//Basic JDBC code to connect to the testdb dataset
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		byte[] salt = PassHash.toByteArr(userinfo.getPwd());
		try {
			Class.forName("com.mysql.jdbc.Driver"); //used to force apache to use this driver
			myConn = DriverManager.getConnection(url, user, pass);
			
			String sql = "INSERT INTO testdb.account_info (username, first_name, last_name, email, salt, password_name) VALUES ( ?, ?, ?, ?, ?, ?);"; //query to database
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, userinfo.getUname());
			myStmt.setString(2, userinfo.getFirstname());
			myStmt.setString(3, userinfo.getLastname());
			myStmt.setString(4, userinfo.getEmail());
			myStmt.setString(5, salt);
			myStmt.setString(6, username);
			
			myRs = myStmt.executeQuery();
			
			return valid;
			
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return valid; 
	}
	
}
