package member.vo;

import java.util.Date;

public class MemberVO {
	private int mId;
	private String mUserId;
	private String mUserPw;
	private String mUserName;
	private String mUserEmail;
	private Date mRegDate;
	
	public int getmId() {
		return mId;
	}
	public void setmId(int mId) {
		this.mId = mId;
	}
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
	public Date getmRegDate() {
		return mRegDate;
	}
	public void setmRegDate(Date mRegDate) {
		this.mRegDate = mRegDate;
	}
	@Override
	public String toString() {
		return "MemberVO [mId=" + mId + ", mUserId=" + mUserId + ""
				+ ", mUserPw=" + mUserPw + ", mUserName=" + mUserName 
				+ ", mUserEmail=" + mUserEmail + ", mRegDate=" + mRegDate + "]";
	}
}
