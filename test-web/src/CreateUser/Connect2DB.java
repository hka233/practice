package CreateUser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;


import Validation.PassHash;

public class Connect2DB {

	public static void main(String[] args) {
		RegisterForm registerForm = new RegisterForm("test3", "john", "doe", "john.doe@gmail.com", "TESTpass123");
		Connect2DB connect2DB = new Connect2DB();
		try {
			connect2DB.addUser(registerForm);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String url = "jdbc:mysql://localhost:3306/testdb"; 
	private String user = "testUser"; 
	private String pass = "password";

	public void addUser(RegisterForm userinfo) throws Exception  {
		
		//Basic JDBC code to connect to the testdb dataset
		Connection myConn = null;
		PreparedStatement myStmt = null;

		byte[] salt = PassHash.getSalt();
		String strSalt = PassHash.toStr(salt);
		String hashpassword = PassHash.hashPassword(userinfo.getPassword(), salt);
		try {
			Class.forName("com.mysql.jdbc.Driver"); //used to force apache to use this driver
			myConn = DriverManager.getConnection(url, user, pass);
			
			String sql = "INSERT INTO testdb.account_info (username, first_name, last_name, email, salt, password_name) VALUES ( ?, ?, ?, ?, ?, ?);"; //query to database
			
			myStmt = myConn.prepareStatement(sql);
			
			myStmt.setString(1, userinfo.getUname());
			myStmt.setString(2, userinfo.getFirstname());
			myStmt.setString(3, userinfo.getLastname());
			myStmt.setString(4, userinfo.getEmail());
			myStmt.setString(5, strSalt);
			myStmt.setString(6, hashpassword);
			
			myStmt.execute();
		}
			finally {
				close (myConn, myStmt);
			}
			
		
	}
	
	public boolean searchEmail(RegisterForm userinfo) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		boolean emailExist = false;
		
		try {
			Class.forName("com.mysql.jdbc.Driver"); //used to force apache to use this driver
			myConn = DriverManager.getConnection(url, user, pass);
			
			String sql = "SELECT * FROM testdb.account_info WHERE email = ?;"; //query to database
			
			myStmt = myConn.prepareStatement(sql);
			
			myStmt.setString(1, userinfo.getEmail());
			
			myRs = myStmt.executeQuery();
			
			while (myRs.next()) {
				emailExist = true;
			}
		}
			finally {
				close (myConn, myStmt);
			}
		return emailExist;
	}
	
	public boolean searchUser(RegisterForm userinfo) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		boolean userExist = false;
		
		try {
			Class.forName("com.mysql.jdbc.Driver"); //used to force apache to use this driver
			myConn = DriverManager.getConnection(url, user, pass);
			
			String sql = "SELECT * FROM testdb.account_info WHERE username = ?;"; //query to database
			
			myStmt = myConn.prepareStatement(sql);
			
			myStmt.setString(1, userinfo.getUname());
			
			myRs = myStmt.executeQuery();
			
			while (myRs.next()) {
				userExist = true;
			}
		}
			finally {
				close (myConn, myStmt);
			}
		return userExist;
	}
	
	private void close(Connection theConn, Statement theStmt) {
		close(theConn, theStmt, null);
	}
	
	private void close(Connection theConn, Statement theStmt, ResultSet theRs) {

		try {
			if (theRs != null) {
				theRs.close();
			}

			if (theStmt != null) {
				theStmt.close();
			}

			if (theConn != null) {
				theConn.close();
			}
			
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}	
}
