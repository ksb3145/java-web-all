package board;

public class BoardVO {
	
	private int bId;
	private String mUserId;
	private String bTitle;
	private String bContent;
	private int bHit;
	private String bRegDate;
	public int getbId() {
		return bId;
	}
	public void setbId(int bId) {
		this.bId = bId;
	}
	public String getmUserId() {
		return mUserId;
	}
	public void setmUserId(String mUserId) {
		this.mUserId = mUserId;
	}
	public String getbTitle() {
		return bTitle;
	}
	public void setbTitle(String bTitle) {
		this.bTitle = bTitle;
	}
	public String getbContent() {
		return bContent;
	}
	public void setbContent(String bContent) {
		this.bContent = bContent;
	}
	public int getbHit() {
		return bHit;
	}
	public void setbHit(int bHit) {
		this.bHit = bHit;
	}
	public String getbRegDate() {
		return bRegDate;
	}
	public void setbRegDate(String bRegDate) {
		this.bRegDate = bRegDate;
	}
	
	
	@Override
	public String toString() {
		return "BoardVO [bId=" + bId + ", mUserId=" + mUserId + ", bTitle=" + bTitle + ", bContent=" + bContent
				+ ", bHit=" + bHit + ", bRegDate=" + bRegDate + "]";
	}
	
	
}
