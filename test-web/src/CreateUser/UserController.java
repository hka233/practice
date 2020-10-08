package CreateUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.sql.DataSource;

import DBSource.DsFactory;

@ManagedBean
@SessionScoped
public class UserController {
	
	DataSource ds = DsFactory.getDataSource();
	
	public List<RegisterForm> getUsers() {

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
	
	public String loadUser(String user) {
		//logger.info("loading student: " + registerform);
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
			
			RegisterForm userinfo = null;
			
			while (myRs.next()) {
				// retrieve data from result set row
				String firstName = myRs.getString("first_name");
				String lastName = myRs.getString("last_name");
				String username = myRs.getString("username");
				String email = myRs.getString("email");
				String password = myRs.getString("password");
				
				userinfo = new RegisterForm(username, firstName, lastName, email, password);
			}
			
			
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			Map<String, Object> requestMap = externalContext.getRequestMap();
			requestMap.put("user", userinfo);
			
			
		} catch (Exception e) {
			e.getStackTrace();
			return null;
		}
		
		
		
		return "update-userinfo.xhtml";
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

