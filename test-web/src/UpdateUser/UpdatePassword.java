package UpdateUser;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class UpdatePassword {

	private String currPassword;  // current password stored in the database
	private String newPassword; // New Password inputed by the user
	private String confirmPassword; // Used as a checker for new password
	
	
	public String getCurrPassword() {
		return currPassword;
	}
	public void setCurrPassword(String currPassword) {
		this.currPassword = currPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	
}
