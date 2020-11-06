package CreateUser;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.sql.DataSource;

import DBSource.DsFactory;
import Validation.CheckAuthentication;
import Validation.DbConnect;
import Validation.PassHash;

@ManagedBean
@SessionScoped
public class UserController {
	
	DataSource ds = DsFactory.getDataSource();
	DbConnect dbConnect = new DbConnect();
	Connect2DB connect2db = new Connect2DB();
	
	private String username;
	private String password;
	private int idnum;

	public int getIdnum() {
		return idnum;
	}

	public void setIdnum(int idnum) {
		this.idnum = idnum;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean checkDB() throws Exception {
		DbConnect dbConnect = new DbConnect();
		setIdnum(connect2db.retrieveIDnum(username));
		return dbConnect.getPass(username,password);
	}
	//checks whether the typed username and password is correct
	public void checkAuth() throws Exception {
		
		FacesContext context = FacesContext.getCurrentInstance();
		
		if (checkDB()) {
			context.getExternalContext().getSessionMap().put("username", username); //adds username into session: as long as it is saved there, the user is logged in
			try {
				context.getExternalContext().redirect("welcome-page.xhtml"); //redirects to welcome page
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else 
			//Send an error message on Login Failure 
            context.addMessage(null, new FacesMessage("Authentication Failed. Check username or password."));
		
	}
	//removes values from the session which would make them unable to get into welcome page without logging in again
	public void logout() {
		
		FacesContext context = FacesContext.getCurrentInstance();
    	context.getExternalContext().invalidateSession();
        try {
			context.getExternalContext().redirect("login-page.xhtml");  //redirects them to login page
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void navigateRegister() {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			context.getExternalContext().redirect("user-info.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//method used to create the datatable with all user info
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
	
	public String retUser() throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver"); //used to force apache to use this driver
			myConn = ds.getConnection();
			
			String sql = "SELECT * FROM testDB.account_info WHERE id_num = ?;"; //query to database
			
			myStmt = myConn.prepareStatement(sql);
			
			myStmt.setInt(1, idnum);
			myRs = myStmt.executeQuery();
			
			String username = null;
			while (myRs.next()) {
			username = myRs.getString("username");
			}
			return username;
		}
			finally {
				close (myConn, myStmt);
			}
	}
	
	public String retPass() throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver"); //used to force apache to use this driver
			myConn = ds.getConnection();
			
			String sql = "SELECT * FROM testDB.account_info WHERE id_num = ?;"; //query to database
			
			myStmt = myConn.prepareStatement(sql);
			
			myStmt.setInt(1, idnum);
			myRs = myStmt.executeQuery();
			
			String retpass = null;
			while (myRs.next()) {
			retpass = myRs.getString("password");
			}
			return retpass;
		}
			finally {
				close (myConn, myStmt);
			}
	}
	
	//Displays the loggedin user credentials in text box so that it can be easily updated by user
	public String loadUser(String user) {
		//logger.info("loading student: " + registerform);
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver"); //used to force apache to use this driver
			myConn = ds.getConnection();
			
			String sql = "SELECT * FROM testDB.account_info WHERE username = ?;"; //query to database
			
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
	
	//Method used to update user information, it also chacks if username or email already exists
	public void updateUserInfo(RegisterForm registerform , String username) throws Exception {
		FacesContext context = FacesContext.getCurrentInstance();
		Connection myConn = null;
		PreparedStatement myStmt = null;
		RegisterForm regForm = new RegisterForm();
		
		boolean fncheck = true; // check firstname
		boolean lncheck = true; // checks lastname
		boolean uncheck = true; // checks username
		
		
		if(registerform.getFirstname().length() > 35 | registerform.getFirstname().length() < 2) {
			context.addMessage(null, new FacesMessage("First Name should have more than 1 char and less than 35"));
			fncheck = false;
		}
		if(registerform.getLastname().length() > 35 | registerform.getLastname().length() < 2) {
			context.addMessage(null, new FacesMessage("Last Name should have more than 1 char and less than 35"));
			lncheck = false;
		}
		if(registerform.getUname().length() > 16 | registerform.getUname().length() < 2)  {
			context.addMessage(null, new FacesMessage("Username should have more than 1 char and less than 15"));	
			uncheck = false;
		}
		
		if(fncheck && lncheck && uncheck) {
		if (!(regForm.checkUser(registerform)) || (username.equalsIgnoreCase(registerform.getUname()))) {
			if (!connect2db.searchUpEmail(registerform.getEmail()) || (registerform.getEmail().equalsIgnoreCase(connect2db.searchtheEmail(idnum)))) {
			try {
				Class.forName("com.mysql.jdbc.Driver"); //used to force apache to use this driver
				myConn = ds.getConnection();
	
				String sql = "update testDB.account_info "
							+ " set username=?, first_name=?, last_name=?, email=?"
							+ " where username=?";
	
				myStmt = myConn.prepareStatement(sql);
			
				
				// set params
				myStmt.setString(1, registerform.getUname());
				myStmt.setString(2, registerform.getFirstname());
				myStmt.setString(3, registerform.getLastname());
				myStmt.setString(4, registerform.getEmail());
				//myStmt.setString(5, registerform.getUname());
				myStmt.setString(5, username);
				
				myStmt.execute();
						
				//checkauth.setUsername(registerform.getUname());
				setUsername(registerform.getUname());
				
				context.addMessage(null, new FacesMessage("User info updated"));
			}
			finally {
				close (myConn, myStmt);
			}
		}
			else
				context.addMessage(null, new FacesMessage("This email is already registered"));
		} else
			context.addMessage(null, new FacesMessage("This username is taken"));
		}
			
		
	}
	//Used to navigate to password reset page
	public void navigateResetPass() {
		
		FacesContext context = FacesContext.getCurrentInstance();
        try {
			context.getExternalContext().redirect("password-reset.xhtml");  //redirects them to pass reset page
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Method used to update password information
	public void updateUserPass(String username, String curPass, String newPass, String confPass) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;
		FacesContext context = FacesContext.getCurrentInstance();
		
		
		if (dbConnect.getPass(username, curPass)) {		//check typed password to DB password
			if (newPass.equals(confPass)) {
				try {
					byte[] salt = PassHash.getSalt();
					String strSalt = PassHash.toStr(salt);
					String hashpassword = PassHash.hashPassword(newPass, salt);
					
					Class.forName("com.mysql.jdbc.Driver"); //used to force apache to use this driver
					myConn = ds.getConnection();
			
					String sql = "update testDB.account_info "
								+ " set salt=?, password_name=?, password=?"
								+ " where username=?";
			
					myStmt = myConn.prepareStatement(sql);
					
					// set params
					myStmt.setString(1, strSalt);
					myStmt.setString(2, hashpassword);
					myStmt.setString(3, newPass);
					myStmt.setString(4, username);
					
					myStmt.execute();
					
					setPassword(newPass);
					
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

