package board.service;

import java.util.List;

import board.BoardVO;
import board.CommentVO;
import member.service.MemberDao;

public class CommentService {
	
	private CommentDao commentDao;
	public CommentService(){
		commentDao = CommentDao.getInstance();
	}
	
	// 코멘트 등록
	public int setCommentInsert(CommentVO cvo){
		return commentDao.insertComment(cvo);
	}
	
	// 코멘트 그룹(부모글 key) 업뎃
	public int setGroupNOUpdate(int cId, int group){
		return commentDao.setGroupNOUpdate(cId, group);
	}
	
	// 게시판 리스트
	public List<CommentVO> getCommentList(int boardId){
		return commentDao.selectComment(boardId);	
	}
}
