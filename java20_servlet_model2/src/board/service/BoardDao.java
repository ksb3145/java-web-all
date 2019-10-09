package board.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
	public void insertBBS(BoardVO bvo){
		
		String sql = "INSERT INTO mvc_border (mUserId, bTitle, bContent, bRegDate) VALUES ( ?, ?, ?, now() )";
		
		PreparedStatement pstmt = null;
		try{	
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, bvo.getmUserId());
			pstmt.setString(2, bvo.getbTitle());
			pstmt.setString(3, bvo.getbContent());
			
			pstmt.executeUpdate();
			
		}  catch ( SQLException e ) {
			e.printStackTrace();
		} finally {
			try {
				if( pstmt != null && !pstmt.isClosed() );
			} catch ( SQLException e ) {
				e.printStackTrace();
			}
		}
	}
	
}


