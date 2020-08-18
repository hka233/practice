package Validation;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
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
	private boolean isLogin = false; //authentication check is done from this value
		
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
	
	public boolean isLogin() {
		return isLogin;
	}

	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}
	
	//checks login state when accessing pages other than login
	public void verifyLogin() {
		if (!this.isLogin) {
			doRedirect("login-page.xhtml");
		}
	}
	
	/*
	 * JSF saves the state onto the server, so in this method we will try to shift it to a new state
	 * Whenever this method is called, and is successful, it would send a request to the specified URL
	 */
	public void doRedirect(String url) {
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance(); //facesContex stores current state of web-app
			facesContext.getExternalContext().redirect(url); //sends a request to the server to redirect to the URL
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/*
	 * Hard-coded authentication, used for testing the web application
	 */
	public void checkAuth() {
		if (username.equals("testuser") && 
				password.equals("testpass")) {
			this.isLogin = true;
			doRedirect("welcome-page.xhtml");
		}
				
		else doRedirect("login-page.xhtml");
	}
	
}
