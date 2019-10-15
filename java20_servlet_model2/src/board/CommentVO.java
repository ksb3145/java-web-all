package board;

import java.util.Date;

public class CommentVO {
	
	public int cId;			// 댓글 키값
	public int bId;			// 게시글 키
	public int cmtGroup; 	// 댓글 그룹
	public int sort;			// 댓글 순서
	public int depth;		// 댓글 깊이 (댓글, 댓글의 댓글 ..)
	public String contents; // 댓글 내용
	public String userId; 	// 댓글 작성자
	public Date regdate;	// 댓글 작성일
	
	public int getcId() {
		return cId;
	}
	public void setcId(int cId) {
		this.cId = cId;
	}
	public int getbId() {
		return bId;
	}
	public void setbId(int bId) {
		this.bId = bId;
	}
	public int getCmtGroup() {
		return cmtGroup;
	}
	public void setCmtGroup(int cmtGroup) {
		this.cmtGroup = cmtGroup;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	@Override
	public String toString() {
		return "CommentVO [cId=" + cId + ", bId=" + bId + ", cmtGroup="
				+ cmtGroup + ", sort=" + sort + ", depth=" + depth
				+ ", contents=" + contents + ", userId=" + userId
				+ ", regdate=" + regdate + "]";
	}
}
