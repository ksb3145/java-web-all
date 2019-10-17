package board.service;

import java.util.List;
import board.CommentVO;

public class CommentService {
	
	private CommentDao commentDao;
	public CommentService(){
		commentDao = CommentDao.getInstance();
	}
	
	// 코멘트 등록
	public int setCommentInsert(CommentVO cvo){
		return commentDao.insertComment(cvo);
	}
	
	// **memo 아직.. 수정중,, 적용안함**
	// 코멘트 그불 순서 업뎃 (sort) 
	public int setCommentSortUp(CommentVO cvo){
		return commentDao.setCommentSortUp(cvo);
	}
	
	// 코멘트 그룹(부모글 key) 업뎃
	public int setGroupNOUpdate(int cId, int group){
		return commentDao.setGroupNOUpdate(cId, group);
	}
	
	// 코멘트 리스트
	public List<CommentVO> getCommentList(int boardId){
		return commentDao.selectComment(boardId);	
	}
}
