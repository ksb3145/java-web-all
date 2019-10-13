package board.service;

import java.util.List;

import board.BoardVO;
import member.service.MemberDao;
import member.vo.MemberVO;

public class BoardService {
	
	private BoardDao boardDao;
	private MemberDao memberDao;
	public BoardService(){
		boardDao = BoardDao.getInstance();
	}
	
	public BoardVO setBBSInsert(BoardVO bvo){
		MemberVO mvo = memberDao.selectOne(bvo.getUserId());
		
		if (null != mvo){
			boardDao.insertBoard(bvo);
			return bvo;
		}else{
			return null;
		}
	}
	
	// 게시판 리스트
	public List<BoardVO> getBBSList(){
		return boardDao.selectBoard();	
	}
	
	// 게시판 상세페이지
	public BoardVO getBBSView(int boardId){
		return boardDao.selectView(boardId);
	}
}
