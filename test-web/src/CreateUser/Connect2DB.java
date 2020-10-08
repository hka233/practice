package CreateUser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import DBSource.DsFactory;
import Validation.PassHash;
/**
 * Class created to send information from RegisterForm
 *  sql queries to database
 *
 */
public class Connect2DB {
	//Testing class
	public static void main(String[] args) {
		RegisterForm registerForm = new RegisterForm("test3", "john", "doe", "john.doe@gmail.com", "TESTpass123");
		//Connect2DB connect2DB = new Connect2DB();
		try {
			//connect2DB.addUser(registerForm);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Using datasource to hide the url, username, password of database
	//information about the database is stored in db.properties under WEB-INF
	DataSource ds = DsFactory.getDataSource();
	
	//Method used to add new user info to database
	public void addUser(RegisterForm userinfo) throws Exception  {
		
		//Basic JDBC code to connect to the testdb dataset
		Connection myConn = null;
		PreparedStatement myStmt = null;

		//salting and hashing created password
		byte[] salt = PassHash.getSalt();
		String strSalt = PassHash.toStr(salt);
		String hashpassword = PassHash.hashPassword(userinfo.getPassword(), salt);
		
		try {
			Class.forName("com.mysql.jdbc.Driver"); //used to force apache to use this driver
			myConn = ds.getConnection();
			
			String sql = "INSERT INTO testdb.account_info (username, first_name, last_name, email, salt, password_name, password) VALUES ( ?, ?, ?, ?, ?, ?, ?);"; //query to database
			
			myStmt = myConn.prepareStatement(sql);
			
			//setting values to be stored in DB
			myStmt.setString(1, userinfo.getUname());
			myStmt.setString(2, userinfo.getFirstname());
			myStmt.setString(3, userinfo.getLastname());
			myStmt.setString(4, userinfo.getEmail());
			myStmt.setString(5, strSalt);
			myStmt.setString(6, hashpassword);
			myStmt.setString(7, userinfo.getPassword()); //added for testing
			
			myStmt.execute();
		}
			finally {
				close (myConn, myStmt);
			}
			
		
	}
	
	//Method to see if email is already used for another account
	public boolean searchEmail(RegisterForm userinfo) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		boolean emailExist = false;
		
		try {
			Class.forName("com.mysql.jdbc.Driver"); //used to force apache to use this driver
			myConn = ds.getConnection();
			
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
	
	//Method to check if username is used
	public boolean searchUser(RegisterForm userinfo) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		boolean userExist = false;
		
		try {
			Class.forName("com.mysql.jdbc.Driver"); //used to force apache to use this driver
			myConn = ds.getConnection();
			
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
	
	//Method primarily for testing addUser, it retrieve user info from db, may be used for future function
	public RegisterForm retrieve(String user) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver"); //used to force apache to use this driver
			myConn = ds.getConnection();
			
			String sql = "SELECT * FROM testdb.account_info WHERE username = ?;"; //query to database
			
			myStmt = myConn.prepareStatement(sql);
			
			myStmt.setString(1, user);
			myRs = myStmt.executeQuery();
			
			RegisterForm rform = new RegisterForm();
			while (myRs.next()) {
			rform.setUname(myRs.getString(2));
			rform.setFirstname(myRs.getString(3));
			rform.setLastname(myRs.getString(4));
			rform.setEmail(myRs.getString(5));
			}
			return rform;
		}
			finally {
				close (myConn, myStmt);
			}
	}
	
	public List<RegisterForm> getUser() {

		List<RegisterForm> users = new ArrayList<>();

		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = ds.getConnection();

			String sql = "select * from testdb.account_info order by id_num;";

			myStmt = myConn.createStatement();

			myRs = myStmt.executeQuery(sql);

			// process result set
			while (myRs.next()) {
				
				// retrieve data from result set row
				String firstName = myRs.getString("first_name");
				String lastName = myRs.getString("last_name");
				String username = myRs.getString("username");
				String email = myRs.getString("email");
				String password = myRs.getString("password");
				
				// create new user object
				RegisterForm tempUsers = new RegisterForm(username, firstName, lastName, email, password);

				// add it to the list of students
				users.add(tempUsers);
			}
			
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			close (myConn, myStmt, myRs);
		}
		return users;	
	}
	
	
	private void close(Connection theConn, Statement theStmt) {
		close(theConn, theStmt, null);
	}
	
	//class to simplify closing connection
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
