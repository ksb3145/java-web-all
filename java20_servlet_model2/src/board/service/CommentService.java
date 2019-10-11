package board.service;

import board.CommentVO;
import member.service.MemberDao;

public class CommentService {
	
	private CommentDao commentDao;
	
	public CommentService(){
		commentDao = CommentDao.getInstance();
	}
	
	public CommentVO setCommentInsert(CommentVO cvo){
		commentDao.insertComment(cvo);
		return null;
	}
}
