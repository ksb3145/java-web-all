package board.service;

import java.util.List;
import board.CommentVO;

public class CommentService {
	
	private CommentDao commentDao;
	public CommentService(){
		commentDao = CommentDao.getInstance();
	}
	
	// 코멘트 등록
	public int commentInsert(CommentVO cvo){
		return commentDao.commentInsert(cvo);
	}
	
	// **memo 아직.. 수정중,, 적용안함**
	// 코멘트 그불 순서 업뎃 (sort) 
	public int commentSortUpdate(CommentVO cvo){
		return commentDao.commentSortUpdate(cvo);
	}
	
	// 코멘트 그룹(부모글 key) 업뎃
	public int groupNOUpdate(int cId, int group){
		return commentDao.groupNOUpdate(cId, group);
	}
	
	// 코멘트 bid 삭제 where => bid = cId
	public int commentBidDel(int cId){
		return commentDao.commentBidDel(cId);
	}
	
	// 코멘트 삭제(단일) where => pId = cId
	public int commentDel(int cId){
		return commentDao.commentDel(cId);
	}
	
	//
	public List<Integer> selectCommentKey(int cId){
		return commentDao.selectCommentKey(cId);
	}
	
	// 코멘트 리스트
	public List<CommentVO> selectCommentList(int boardId){
		return commentDao.selectCommentList(boardId);	
	}
	
}
