package board.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
	
	private Connection conn;
	private BoardDao(){
		try {
			conn = DBconn.getConnection();
		} catch ( ClassNotFoundException | SQLException e ) {
			e.printStackTrace();
		}
	}
	
	// 게시글 등록
	public void insertBoard(BoardVO bvo){
		
		String sql = "INSERT INTO mvc_board (mUserId, bTitle, bContent, bRegDate) VALUES ( ?, ?, ?, now() );";
		
		System.out.println(sql);
		
		PreparedStatement pstmt = null;
		try{	
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, bvo.getUserId());
			pstmt.setString(2, bvo.getTitle());
			pstmt.setString(3, bvo.getContent());
			
			pstmt.executeUpdate();
			
		}catch( SQLException e ) {
			e.printStackTrace();
		}finally{
			try {
				if( pstmt != null && !pstmt.isClosed() )
					pstmt.close();
			} catch ( SQLException e ) {
				e.printStackTrace();
			}
		}
	}
	
	// 게시글 전체 리스트
	public List<BoardVO> selectBoard(){
		String sql = "SELECT bId, bTitle, bContent, bHit, bRegdate, mUserId FROM mvc_board ORDER BY  bRegdate DESC;";
		
		System.out.println(sql);
		
		PreparedStatement pstmt = null;
		//결과 탐색
		ResultSet rs = null;
		List<BoardVO> boardList = new ArrayList<BoardVO>();
		
		try{
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				BoardVO bvo = new BoardVO();

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
		}finally{
			try {
				if( null != pstmt && !pstmt.isClosed() )
					pstmt.close();
				if( null != rs && !rs.isClosed() )
					pstmt.close();
			} catch ( SQLException e ) {
				e.printStackTrace();
			}
		}
		return boardList;
	}
	
	// 상세페이지
	public BoardVO selectView(int bId){
	
		String sql = "SELECT bId, bTitle, bContent, bHit, bRegdate, mUserId FROM mvc_board WHERE bid=?;";
	
	 	PreparedStatement pstmt = null;
	 	BoardVO bvo = null;	//리턴할 객체참조변수
		ResultSet rs = null; //결과셋 참조변수들 준비
		
		try {
			pstmt = conn.prepareStatement(sql);
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
		} finally{
			try{
	          if( pstmt != null && !pstmt.isClosed())
	              pstmt.close();
	          if( rs != null && !rs.isClosed())
	              pstmt.close();
			}catch(SQLException e){
	          e.printStackTrace();
			}
		}
		return bvo;
	}
}


