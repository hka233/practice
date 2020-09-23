package CreateUser;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class RegisterForm {

	private String uname;
	private String firstname;
	private String lastname;
	private String email;
	private String pwd;
	private String checkpsw;
	private String password;

	public RegisterForm() {
	}
	
	public RegisterForm(String uname, String firstname, String lastname, String email, String pwd) {
		this.uname = uname;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.pwd = pwd;
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
	public String getCheckpsw() {
		return checkpsw;
	}
	public void setCheckpsw(String checkpsw) {
		this.checkpsw = checkpsw;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	//INSERT INTO `testdb`.`account_info` ( `username`, `first_name`, `last_name`, `email`, `salt`, `password_name`) VALUES ( 'test3', 'john', 'smith', 'sjohn@gmail.com', 'asd', 'asd');
}
