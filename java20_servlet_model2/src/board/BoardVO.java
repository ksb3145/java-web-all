package board;

import java.util.Date;

public class BoardVO {
	private int rownum;		// 게시글 번호
	private int id;				// 게시글 key
	private String userId;		// 작성자id
	private String title;			// 제목
	private String content;		// 내용
	private int hit;				// 조회수
	private Date regDate;		// 작성일
	
	private int pid;				// 부모 개시글 key
	private int bGroup;		
	private int bSort;
	
	public int getRownum() {
		return rownum;
	}
	public void setRownum(int rownum) {
		this.rownum = rownum;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public int getbGroup() {
		return bGroup;
	}
	public void setbGroup(int bGroup) {
		this.bGroup = bGroup;
	}
	public int getbSort() {
		return bSort;
	}
	public void setbSort(int bSort) {
		this.bSort = bSort;
	}
	
}
