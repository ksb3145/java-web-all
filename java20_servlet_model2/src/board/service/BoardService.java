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
	
	// 게시글 등록
	public int setBBSInsert(BoardVO bvo){
		MemberVO mvo = memberDao.selectOne(bvo.getUserId());
		if (null != mvo){
			return boardDao.insertBoard(bvo);
		}else{
			return 0;
		}
	}
	// 게시글 그룹 업뎃
	public int setGroupNOUpdate(BoardVO bvo){
		return boardDao.setGroupNOUpdate(bvo);
	}
	// 게시글 특정 그룹별 순서 증가
	public int setSortNOUpdate(BoardVO bvo){
		System.out.println(bvo.toString());
		return boardDao.setSortNOUpdate(bvo);
	}
	
	// 답글 등록
	public int setBBSReInsert(BoardVO bvo){
	
		MemberVO mvo = memberDao.selectOne(bvo.getUserId());
		if (null != mvo){
			return boardDao.insertBoardRe(bvo);
		}else{
			return 0;
		}
	}
	
	// 게시글 수정
	public int setBBSUpdate(BoardVO bvo){
		MemberVO mvo = memberDao.selectOne(bvo.getUserId());
		if (null != mvo){
			return boardDao.updateBoard(bvo);
		}else{
			return 0;
		}
	}
	
	// 조회한 데이터 row count
	public int resultTotalCnt(HashMap<Object, Object> params){
		return boardDao.resultTotalCnt(params);
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
