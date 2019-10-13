package board.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import board.BoardVO;
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
		
		String sql = "INSERT INTO mvc_comment (cmtGroup, sort, depth, contents, mUserId, regdate) VALUES (?, ?, ?, ?, ?, now());";
		
		System.out.println(sql);
		
		PreparedStatement pstmt = null;
		try{	
			//sql 실행객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// ? 에 입력될 값 매핑
			pstmt.setInt(1, cvo.getCmtGroup());
			pstmt.setInt(2, cvo.getSort());
			pstmt.setInt(3, cvo.getDepth());
			pstmt.setString(4, cvo.getContents());
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
	
	// 코멘트 리스트
	public List<CommentVO> selectComment(){
		String sql = "SELECT * FROM mvc_comment ORDER BY depth DESC, sort ASC, regdete DESC;";
		
		System.out.println(sql);
		
		PreparedStatement pstmt = null;
		//결과 탐색
		ResultSet rs = null;
		List<CommentVO> commentList = new ArrayList<CommentVO>();
		
		try{
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				CommentVO cvo = new CommentVO();
//					bvo.setId(rs.getInt("bId"));
//					bvo.setTitle(rs.getString("bTitle"));
				commentList.add(cvo);
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
		return commentList;
	}
}
