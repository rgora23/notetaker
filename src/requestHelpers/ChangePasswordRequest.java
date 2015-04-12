package requestHelpers;

/**
 * This class inherits directly from the Request class. Objects of this class
 * are created during the password change process. This class provides data to
 * the Account class' changePassword method. The oldPassword instance variable
 * represents the users current password or the password he/she wishes to
 * change. The newPassword instance variable is the password the user would like
 * to change to and the confirmNewPassword instance variable is used to verify
 * that the user has entered his/her new password correctly.
 * 
 * @author Tony Benny
 * @author Brian Maxwell
 * @author Matt Yeager
 * 
 */
public class ChangePasswordRequest extends Request {

	String accountId;
	String oldPassword;
	String newPassword;
	String confirmNewPassword;

	public ChangePasswordRequest(String oldPassword, String newPassword,
			String confirmNewPassword) {
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
		this.confirmNewPassword = confirmNewPassword;
	}
	
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}

	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
	}

}
