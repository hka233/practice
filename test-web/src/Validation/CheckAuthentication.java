package Validation;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.faces.bean.RequestScoped;
/**
 * 
 * @author Hamza Ahmed
 * This class is used to test the login function with hard-coded password and username
 *  
 */
@SessionScoped
@ManagedBean
public class CheckAuthentication {

	private String username;
	private String password;
		
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
	
	public void checkAuth() throws ServletException, IOException, SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
		
		FacesContext context = FacesContext.getCurrentInstance();
		DbConnect dbConnect = new DbConnect();
		boolean valid =dbConnect.getPass(username,password);
		
		if (valid) {
			context.getExternalContext().getSessionMap().put("username", username);
			try {
				context.getExternalContext().redirect("welcome-page.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else 
			context.getExternalContext().redirect("failed-login.xhtml");
	}
	
	public void logout() {
		FacesContext context = FacesContext.getCurrentInstance();
    	context.getExternalContext().invalidateSession();
        try {
			context.getExternalContext().redirect("login-page.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
}
