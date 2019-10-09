package member.vo;

public class MemberVO {
	private String mUserId;
	private String mUserPw;
	private String mUserName;
	private String mUserEmail;
	
	public String getmUserId() {
		return mUserId;
	}
	public void setmUserId(String mUserId) {
		this.mUserId = mUserId;
	}
	public String getmUserPw() {
		return mUserPw;
	}
	public void setmUserPw(String mUserPw) {
		this.mUserPw = mUserPw;
	}
	public String getmUserName() {
		return mUserName;
	}
	public void setmUserName(String mUserName) {
		this.mUserName = mUserName;
	}
	public String getmUserEmail() {
		return mUserEmail;
	}
	public void setmUserEmail(String mUserEmail) {
		this.mUserEmail = mUserEmail;
	}
	@Override
	public String toString() {
		return "MemberVO [mUserId=" + mUserId + ", mUserPw=" + mUserPw
				+ ", mUserName=" + mUserName + ", mUserEmail=" + mUserEmail
				+ "]";
	}
}
