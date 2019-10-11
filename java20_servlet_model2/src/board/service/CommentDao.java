package board.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import board.CommentVO;
import common.util.DBconn;

public class CommentDao {
	private static CommentDao instance;
	public static CommentDao getInstance() {
		if(instance == null)
			instance = new CommentDao();
		return instance;
	}
	
	private Connection conn;
	private CommentDao(){
		try {
			conn = DBconn.getConnection();
		} catch ( ClassNotFoundException | SQLException e ) {
			e.printStackTrace();
		}
	}
	
	//코멘트 등록
	public void insertComment(CommentVO cvo){
		
		String sql = "insert into mvc_comment (cGroup, cSort, cDepth, cComment, mUserId, cRegdate) value (?,?,?,?,?,now());";
		
		System.out.println(sql);
		
		PreparedStatement pstmt = null;
		try{	
			pstmt = conn.prepareStatement(sql);
			
			
			pstmt.setInt(1, cvo.getGroup());
			pstmt.setInt(2, cvo.getSort());
			pstmt.setInt(3, cvo.getDepth());
			pstmt.setString(4, cvo.getComment());
			pstmt.setString(5, cvo.getUserId());
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

}
