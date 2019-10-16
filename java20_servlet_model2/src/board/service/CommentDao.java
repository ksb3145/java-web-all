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
	
	//코멘트 등록
	public int insertComment(CommentVO cvo){
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql = "INSERT INTO mvc_comment (bId, cmtGroup, sort, depth, contents, mUserId) VALUES (?, ?, ?, ?, ?, ?);";
		
		try{	
			DBconn.dbConn = DBconn.getConnection();
			
			//sql 실행객체 생성
			pstmt = DBconn.dbConn.prepareStatement(sql);
			// ? 에 입력될 값 매핑
			pstmt.setInt(1, cvo.getbId());
			pstmt.setInt(2, cvo.getCmtGroup());
			pstmt.setInt(3, cvo.getSort());
			pstmt.setInt(4, cvo.getDepth());
			pstmt.setString(5, cvo.getContents());
			pstmt.setString(6, cvo.getUserId());
			
			pstmt.executeUpdate();
			
			ResultSet rs = pstmt.executeQuery("SELECT LAST_INSERT_ID()");
			
			if(rs.next()){
				result = rs.getInt(1);
			}

		}catch( SQLException e ) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			
			try {
				if( null != pstmt) pstmt.close();
			} catch ( SQLException e ) {
				e.printStackTrace();
			}
			
			try {
				if(null != DBconn.dbConn) DBconn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	// 코멘트 삭제
	public int setCommentDel(int bId){
		int result = 0;
		
		
		return result;
	}
	
	// 코멘트 그룹 업데이트
	public int setGroupNOUpdate(int cId, int group){
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql = "UPDATE mvc_comment SET cmtGroup = ? WHERE cId = ? ;";
		
		try{	
			DBconn.dbConn = DBconn.getConnection();
			
			pstmt = DBconn.dbConn.prepareStatement(sql);
			pstmt.setInt(1, cId);
			pstmt.setInt(2, group);
			
			result = pstmt.executeUpdate();
		}catch( SQLException e ) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			
			try {
				if( null != pstmt ) pstmt.close();
			} catch ( SQLException e ) {
				e.printStackTrace();
			}
			
			try {
				if(null != DBconn.dbConn) DBconn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	// 코멘트 리스트
	public List<CommentVO> selectComment(int bId){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<CommentVO> commentList = new ArrayList<CommentVO>();
		
		String sql = "SELECT cId, bid, cmtGroup, sort, depth, contents, mUserId, regdate FROM mvc_comment WHERE delYN='N' AND bid = "+bId+" ORDER BY cmtGroup ASC, depth ASC, regdate ASC;";
		
		try{
			DBconn.dbConn = DBconn.getConnection();
			
			pstmt = DBconn.dbConn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				CommentVO cvo = new CommentVO();
				cvo.setcId(rs.getInt("cId"));
				cvo.setbId(rs.getInt("bId"));
				cvo.setCmtGroup(rs.getInt("cmtGroup"));
				cvo.setSort(rs.getInt("sort"));
				cvo.setDepth(rs.getInt("depth"));
				cvo.setContents(rs.getString("contents"));
				cvo.setUserId(rs.getString("mUserId"));
				cvo.setRegdate(rs.getDate("regdate"));
				
				commentList.add(cvo);
			}
		}catch( SQLException e ) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			
			try {
				if( null != pstmt ) pstmt.close();
			} catch ( SQLException e ) {
				e.printStackTrace();
			}
			
			try {
				if( null != rs ) rs.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			try {
				if(null != DBconn.dbConn) DBconn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return commentList;
	}
}
