package CreateUser;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class RegisterForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String uname;
	private String firstname;
	private String lastname;
	private String email;
	private String pwd;
	private String checkpwd;
	private String password;

	public RegisterForm() {
	}
	
	//Method to create register form object
	public RegisterForm(String uname, String firstname, String lastname, String email, String password) {
		this.uname = uname;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.password = password;
	}
	
	//checks if the email is already used
	public boolean checkEmail(RegisterForm registerForm)  {
		Connect2DB connect2DB = new Connect2DB();
		boolean chkemail = false;
		try {
			chkemail = connect2DB.searchEmail(registerForm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return chkemail;
	}
	
	//checks if username is already taken
	public boolean checkUser(RegisterForm registerForm)  {
		Connect2DB connect2DB = new Connect2DB();
		boolean chkuser = false;
		try {
			chkuser = connect2DB.searchUser(registerForm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return chkuser;
	}
	
	//Method to check if "password" and confirmed password match
	public String checkPass(RegisterForm registerForm) {
		Connect2DB connect2DB = new Connect2DB();
		FacesContext context = FacesContext.getCurrentInstance();
		if (!checkUser(registerForm)) { 		//checks if user exists in DB
			if (!checkEmail(registerForm)) {	//checks if email exists in DB
				if (pwd.equals(checkpwd)) {
					this.password = pwd;
					try {
						connect2DB.addUser(registerForm);
						
						return "user-info?faces-redirect=true";
						
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}	
				else
					context.addMessage(null, new FacesMessage("Passwords do not match"));
			}
			else 
				context.addMessage(null, new FacesMessage("This email is already used."));
		}
		else
			context.addMessage(null, new FacesMessage("This username is taken"));
		
		return null;
	}
	
	//Used to redirect to user-info page, and to erase all input field when returning to login page
	public void navigateUserInfo(RegisterForm registerForm) {
		
		FacesContext context = FacesContext.getCurrentInstance();
        try {
			context.getExternalContext().redirect("login-page.xhtml");  //redirects them to login page
			//This clears out the input field after a successful submission
			registerForm.setFirstname(null); 
			registerForm.setLastname(null);
			registerForm.setUname(null);
			registerForm.setEmail(null);
			registerForm.setPwd(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Method to redirect user to register page when "sign up" is clicked
	public void navigateRegister() {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			context.getExternalContext().redirect("register-form.xhtml");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getCheckpwd() {
		return checkpwd;
	}
	public void setCheckpwd(String checkpwd) {
		this.checkpwd = checkpwd;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	//INSERT INTO `testdb`.`account_info` ( `username`, `first_name`, `last_name`, `email`, `salt`, `password_name`) VALUES ( 'test3', 'john', 'smith', 'sjohn@gmail.com', 'asd', 'asd');
}
