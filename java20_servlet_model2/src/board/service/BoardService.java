package board.service;

import java.util.HashMap;
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
	public int resultTotalCnt(){
		return boardDao.resultTotalCnt();
	}
	
	// 게시판 리스트
	public List<BoardVO> getBBSList(HashMap<Object, Object> params){
		return boardDao.selectBoard(params);	
	}
	
	// 게시판 상세페이지
	public BoardVO getBBSView(int boardId){
		return boardDao.selectView(boardId);
	}
}
