package Validation;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@SessionScoped
@ManagedBean
public class CheckAuthentication {

	private String username;
	private String password;
	private boolean isLogin = false;
		
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
	
	public void verifyLogin() {
		if (!this.isLogin) {
			doRedirect("login-page.xhtml");
		}
	}
	
	private void doRedirect(String url) {
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.getExternalContext().redirect(url);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void checkAuth() {
		if (username.equals("testuser") && 
				password.equals("testpass")) {
			this.isLogin = true;
			doRedirect("welcome-page.xhtml");
		}
				
		else doRedirect("login-page.xhtml");
	}
	
}
