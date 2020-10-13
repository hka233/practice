package CreateUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.sql.DataSource;

import DBSource.DsFactory;
import Validation.CheckAuthentication;
import Validation.DbConnect;
import Validation.PassHash;

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
			requestMap.put("registerForm", userinfo);
			
			
		} catch (Exception e) {
			e.getStackTrace();
			return null;
		}
		
		
		
		return "update-userinfo.xhtml";
	}
	
	public void updateUserInfo(RegisterForm registerform , String username) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			Class.forName("com.mysql.jdbc.Driver"); //used to force apache to use this driver
			myConn = ds.getConnection();

			String sql = "update testdb.account_info "
						+ " set username=?, first_name=?, last_name=?, email=?"
						+ " where username=?";

			myStmt = myConn.prepareStatement(sql);
			
			String salt = null;
			String hashedpass = null;
			
			// set params
			myStmt.setString(1, registerform.getUname());
			myStmt.setString(2, registerform.getFirstname());
			myStmt.setString(3, registerform.getLastname());
			myStmt.setString(4, registerform.getEmail());
			//myStmt.setString(5, registerform.getUname());
			myStmt.setString(5, username);
			
			myStmt.execute();
					
			CheckAuthentication checkauth = new CheckAuthentication();
			//checkauth.setUsername(registerform.getUname());
			checkauth.setUsername(registerform.getUname());
			
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("User info updated"));
		}
		finally {
			close (myConn, myStmt);
		}
		
	}
	
	public void updateUserPass(String username, String curPass, String newPass, String confPass) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;
		FacesContext context = FacesContext.getCurrentInstance();
		DbConnect dbConnect = new DbConnect();
		
		if (dbConnect.getPass(username, curPass)) {
			if (newPass.equals(confPass)) {
				try {
					byte[] salt = PassHash.getSalt();
					String strSalt = PassHash.toStr(salt);
					String hashpassword = PassHash.hashPassword(newPass, salt);
					
					Class.forName("com.mysql.jdbc.Driver"); //used to force apache to use this driver
					myConn = ds.getConnection();
			
					String sql = "update testdb.account_info "
								+ " set salt=?, password_name=?, password=?"
								+ " where username=?";
			
					myStmt = myConn.prepareStatement(sql);
					
					// set params
					myStmt.setString(1, strSalt);
					myStmt.setString(2, hashpassword);
					myStmt.setString(3, newPass);
					myStmt.setString(4, username);
					
					myStmt.execute();
					context.addMessage(null, new FacesMessage("Password has been updated"));
				}
				finally {
					close (myConn, myStmt);
				}
				
			}
			else
				context.addMessage(null, new FacesMessage("Passwords do not match"));
		}
		else
			context.addMessage(null, new FacesMessage("Incorrect Password"));
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

