package Validation;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
/**
 * 
 * @author Hamza Ahmed
 * This class is used to test the login function with hard-coded password and username
 *  
 */
@SessionScoped
@ManagedBean
public class CheckAuthentication {

	private static String username;
	private static String password;
		
	public CheckAuthentication() {
	
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
	
	public boolean checkDB() {
		DbConnect dbConnect = new DbConnect();
		return dbConnect.getPass(username,password);
	}
	//checks whether the typed username and password is correct
	public void checkAuth() throws ServletException, IOException, SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
		
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
	
}
