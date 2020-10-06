package CreateUser;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.sql.DataSource;

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

			String sql = "select * from testDB.account_info order by id_num;";

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

