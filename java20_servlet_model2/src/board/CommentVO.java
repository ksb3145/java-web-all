package board;

import java.util.Date;

public class CommentVO {
	public int id;
	public int group; 
	public int sort;
	public int depth;
	public String comment; 
	public String userId; 
	public Date regdate;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getGroup() {
		return group;
	}
	public void setGroup(int group) {
		this.group = group;
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
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
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
		return "CommentVO [id=" + id + ", group=" + group + ", sort=" + sort + ", depth=" + depth + ", comment="
				+ comment + ", userId=" + userId + ", regdate=" + regdate + "]";
	}
}
