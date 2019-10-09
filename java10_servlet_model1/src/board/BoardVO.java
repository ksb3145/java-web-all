package board;

public class BoardVO {
	
	private int bId;
	private String bName;
	private String bWiter;
	private String bContent;
	private int bHit;
	private String bRegDate;
	public int getbId() {
		return bId;
	}
	public void setbId(int bId) {
		this.bId = bId;
	}
	public String getbName() {
		return bName;
	}
	public void setbName(String bName) {
		this.bName = bName;
	}
	public String getbWiter() {
		return bWiter;
	}
	public void setbWiter(String bWiter) {
		this.bWiter = bWiter;
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
		return "BoardVO [bId=" + bId + ", bName=" + bName + ", bWiter="
				+ bWiter + ", bContent=" + bContent + ", bHit=" + bHit
				+ ", bRegDate=" + bRegDate + "]";
	}
	
}
