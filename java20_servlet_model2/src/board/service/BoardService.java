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
		memberDao = MemberDao.getInstance();
	}
	
	public int setBBSInsert(BoardVO bvo){
		MemberVO mvo = memberDao.selectOne(bvo.getUserId());
		if (null != mvo){
			return boardDao.insertBoard(bvo);
		}else{
			return 0;
		}
	}
	
	// 조회한 데이터 row count
	public int resultRowCnt(){
		return boardDao.resultRowCnt();
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
