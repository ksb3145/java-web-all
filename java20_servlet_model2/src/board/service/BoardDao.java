package board.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import board.BoardVO;
import common.util.DBconn;

// 게시판 쿼리
public class BoardDao {
	private static BoardDao instance;
	public static BoardDao getInstance(){
		if(instance == null)
			instance = new BoardDao();
		return instance;
	}
	
	// 게시글 등록
	public int insertBoard(BoardVO bvo){
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql = "INSERT INTO mvc_board (mUserId, bTitle, bContent) VALUES ( ?, ?, ?);";
		
		try{
			
			DBconn.dbConn = DBconn.getConnection();
			
			pstmt = DBconn.dbConn.prepareStatement(sql);
			pstmt.setString(1, bvo.getUserId());
			pstmt.setString(2, bvo.getTitle());
			pstmt.setString(3, bvo.getContent());
			
			result = pstmt.executeUpdate();
			
		}catch( SQLException e ) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			
			try{
				if( null != pstmt) pstmt.close();
			}catch ( SQLException e ) {
				e.printStackTrace();
			}
			
			try{
				if(null != DBconn.dbConn) DBconn.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	// 게시판 총 카운트
	public int resultTotalCnt(){
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql="SELECT count(*) totalCnt FROM mvc_board;";
		
		try{
			DBconn.dbConn = DBconn.getConnection();
			
			pstmt = DBconn.dbConn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				result = rs.getInt("totalCnt");
			}
		}catch( SQLException e ) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			
			try{
				if( null != pstmt ) pstmt.close();
			}catch ( SQLException e ) {
				e.printStackTrace();
			}
			
			try{
				if( null != rs ) rs.close();
			}catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			try{
				if(null != DBconn.dbConn) DBconn.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	// 게시글 전체 리스트
	public List<BoardVO> selectBoard(HashMap<Object, Object> params){
		
		PreparedStatement pstmt = null;
		List<BoardVO> boardList = new ArrayList<BoardVO>();
		ResultSet rs = null;
		
		int offset=0;
		int limit = (int)params.get("limit");
		int bNo = offset-1;
		
		if(0>bNo) bNo = 0;
		if(params.get("offset") != "") offset = (int)params.get("offset");
		String sql = "" , where = "";
		
		sql  = "SELECT @rownum:=@rownum+1 ROWNUM, B.*";
		sql += " FROM mvc_board B";
		sql += " WHERE (@rownum:="+offset+")="+offset;
		sql +=  where;
		sql += " ORDER BY bRegdate DESC";
		sql += " LIMIT "+offset+","+limit;
		
		// System.out.println("BoardDao list Sql ::: "+sql);
		
		try{
			
			DBconn.dbConn = DBconn.getConnection();
			
			pstmt = DBconn.dbConn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				BoardVO bvo = new BoardVO();
				
				bvo.setRownum(rs.getInt("ROWNUM"));
				bvo.setId(rs.getInt("bId"));
				bvo.setTitle(rs.getString("bTitle"));
				bvo.setContent(rs.getString("bContent"));
				bvo.setHit(rs.getInt("bHit"));
				bvo.setRegDate(rs.getDate("bRegDate"));
				bvo.setUserId(rs.getString("mUserId"));
				
				boardList.add(bvo);
			}
		}catch( SQLException e ) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			
			try{
				if( null != pstmt ) pstmt.close();
			}catch ( SQLException e ) {
				e.printStackTrace();
			}
			
			try{
				if( null != rs ) rs.close();
			}catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			try{
				if(null != DBconn.dbConn) DBconn.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return boardList;
	}
	
	// 게시글 수정
	public int updateBoard(BoardVO bvo){
		int result = 0;
		
		String sql = "UPDATE mvc_board SET bTitle = ?, bContent = ? WHERE bId = ? AND mUserId = ?";

		PreparedStatement pstmt = null;
		try{	
			DBconn.dbConn = DBconn.getConnection();
			
			pstmt = DBconn.dbConn.prepareStatement(sql);
			pstmt.setString(1, bvo.getTitle());
			pstmt.setString(2, bvo.getContent());
			pstmt.setInt(3, bvo.getId());
			pstmt.setString(4, bvo.getUserId());
			
			result = pstmt.executeUpdate();
			
		}catch( SQLException e ) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			
			try {
				if( null != pstmt ) pstmt.close();
			}catch ( SQLException e ) {
				e.printStackTrace();
			}
			
			try {
				if(null != DBconn.dbConn) DBconn.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	// 게시글 상세
	public BoardVO selectView(int bId){
	
		String sql = "SELECT bId, bTitle, bContent, bHit, bRegdate, mUserId FROM mvc_board WHERE bid=?;";
	
	 	PreparedStatement pstmt = null;
	 	BoardVO bvo = null;	//리턴할 객체참조변수
		ResultSet rs = null; //결과셋 참조변수들 준비
		
		try {
			DBconn.dbConn = DBconn.getConnection();
			
			pstmt = DBconn.dbConn.prepareStatement(sql);
			pstmt.setInt(1, bId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				bvo = new BoardVO();
				bvo.setTitle(rs.getString("bTitle"));
				bvo.setContent(rs.getString("bContent"));
				bvo.setRegDate(rs.getDate("bRegDate"));
				bvo.setId(rs.getInt("bId"));
				bvo.setUserId(rs.getString("mUserId"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally{
			
			try {
				if( null != pstmt ) pstmt.close();
			} catch ( SQLException e ) {
				e.printStackTrace();
			}
			
			try {
				if( null != rs ) rs.close();
			} catch ( SQLException e ) {
				e.printStackTrace();
			}
			
			try {
				if(null != DBconn.dbConn) DBconn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return bvo;
	}
}


