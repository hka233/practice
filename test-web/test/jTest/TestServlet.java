package jTest;

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

@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	
	@Resource(name = "jdbc/testdb")
	private DataSource dataSource;
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
		
		
		//Basic JDBC code to connect to the testdb dataset
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			myConn = dataSource.getConnection();

			//String sql = "SELECT * FROM account_info;";
			//String sql = "SELECT * FROM testdb.account_info WHERE username = \"testUser\";";

			//myStmt = myConn.createStatement();

			//myRs = myStmt.executeQuery(sql);
			
			String sql = "SELECT * FROM testdb.account_info WHERE username = ?;";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, "test2");
			
			myRs = myStmt.executeQuery();
			
			//while loop to list out the user names
			while (myRs.next()) {
				String username = myRs.getString("password_name");
				out.println(username);
				System.out.println(username);
			}
		} catch (Exception exc) {
			exc.printStackTrace();
			out.println(exc.getMessage());
		}
	}
}
