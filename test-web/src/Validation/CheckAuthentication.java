package Validation;

import javax.faces.bean.ManagedBean;

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
	
	public String checkAuth() {
		if (username.equals("testuser") && 
				password.equals("testpass")) return "welcome-page";
		else return "failed-login";
	}
	
}
