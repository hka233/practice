package Validation;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

//@WebServlet("/AccountDAO")

public class AccountDAO extends HttpServlet{
	
	private static AccountDAO instance;
	private static DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/account_info";
	
	//@Resource(name = "jdbc/testdb")
	
	public static AccountDAO getInstance() throws Exception {
		if(instance == null) {
			instance = new AccountDAO();
		}
		
		return instance;
	}
	
	private AccountDAO() throws Exception {
		dataSource = getDataSource();
	}
	
	private DataSource getDataSource() throws NamingException {
		Context context = new InitialContext();
		DataSource theDataSource = (DataSource) context.lookup(jndiName);
		return theDataSource;
	}
	
	public static boolean validate(String username, String password) throws ServletException, IOException, SQLException {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		String pw = "";
		String salt = "";
		String pwh = "";
		String passwordh = "";

		try {
			myConn = dataSource.getConnection();
			myStmt = myConn.prepareStatement("SELECT * FROM testdb.account_info WHERE username = ?");
			myStmt.setString(1, username);

			myRs = myStmt.executeQuery();
			
			if (myRs.next()) {
				pw = myRs.getString("password");
				salt = myRs.getString("salt");
			}
			
			byte[] salt2byte = PassHash.toByteArr(salt);
			pwh = PassHash.hashPassword(pw, salt2byte);
			passwordh = PassHash.hashPassword(password, salt2byte);
			if(pwh.equals(passwordh)) 
				return true; //result found, means valid input
			
		} catch (Exception exc) {
			exc.printStackTrace();
			System.out.println(exc.getMessage());
			return false;
			
		} finally {
			close (myConn, myStmt, myRs);
		}
		return false;
	}
	
	private static void close(Connection theConn, Statement theStmt, ResultSet theRs) {

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




