package Validation;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet("/AccountDAO")

public class AccountDAO {

	@Resource(name = "jdbc/testdb")
	private DataSource dataSource;
	
	public boolean validate(String username, String password) throws ServletException, IOException {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			myConn = dataSource.getConnection();
			myStmt = myConn.prepareStatement("SELECT * FROM account_info WHERE username = ? and password = ?");
			myStmt.setString(1, username);
			myStmt.setString(2, password);

			myRs = myStmt.executeQuery();
			if (myRs.next()) 
				return true; //result found, means valid input
			
			//while loop to list out the user names
			while (myRs.next()) {
				String username = myRs.getString("username");
				out.println(username);
				System.out.println(username);
			}
		} catch (Exception exc) {
			exc.printStackTrace();
			out.println(exc.getMessage());
		}
	}
}

}
