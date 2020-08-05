package Validation;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class CheckPassword {
	
	private String password;
	
	
	
	
	public CheckPassword() {
		/*
		String check = "testpass";
		String redir = "";
		if (password == check) redir = "welcome-page";
		else redir = "failed-login";
		*/
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
