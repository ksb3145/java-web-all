package member.vo;

public class SessionVO {
	private String userId;		// 로그인) 아이디
	private String userName;	// 로그인) 이름
	private String userEmail;	// 로그인) 이메일
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
}
