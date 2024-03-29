package board.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import board.CommentVO;
import common.util.DBconn;
import member.vo.MemberVO;

public class CommentDao {
	private static CommentDao instance;
	public static CommentDao getInstance() {
		if(instance == null)
			instance = new CommentDao();
		return instance;
	}
	
	//등록된 코멘트 카운트
	public int selectCommentCount(int bId){
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql  = "SELECT count(*) cnt FROM mvc_comment WHERE bid = ?"; 
		System.out.println("등록된 코멘트 카운트 :: " + sql);
		try{	
			DBconn.dbConn = DBconn.getConnection();
			
			//sql 실행객체 생성
			pstmt = DBconn.dbConn.prepareStatement(sql);
			// ? 에 입력될 값 매핑
			pstmt.setInt(1, bId);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				result = rs.getInt("cnt");
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
	
	//코멘트 등록
	public int commentInsert(CommentVO cvo){
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql  = "INSERT INTO mvc_comment( bid, cmtGroup, pid, sort, depth, contents, mUserId, regdate) "; 
			   sql += "VALUES ( ?, 	?,	?,	?,	?,	?,	?, now())";
		System.out.println("코멘트등록 :: " + sql);
		try{	
			DBconn.dbConn = DBconn.getConnection();
			
			//sql 실행객체 생성
			pstmt = DBconn.dbConn.prepareStatement(sql);
			// ? 에 입력될 값 매핑
			pstmt.setInt(1, cvo.getbId());
			pstmt.setInt(2, cvo.getCmtGroup());
			pstmt.setInt(3, cvo.getpId());
			pstmt.setInt(4, cvo.getSort());
			pstmt.setInt(5, cvo.getDepth());
			pstmt.setString(6, cvo.getContents());
			pstmt.setString(7, cvo.getUserId());
			
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
	
	// 코멘트 key
	public List<Integer> selectCommentKey(int cId){
		List<Integer> commentCidList = new ArrayList<>();
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		String sql = "SELECT cId FROM mvc_comment WHERE pId=?";
		System.out.println("코멘트 key :: "+sql);
		try{
			DBconn.dbConn = DBconn.getConnection();
			pstmt = DBconn.dbConn.prepareStatement( sql );
			pstmt.setInt(1,cId);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				commentCidList.add(rs.getInt("cId"));
			}
			
		}catch ( SQLException e ) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
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
		return commentCidList;
	}
	
	// 코멘트 리스트
	public List<CommentVO> selectCommentList(int bId){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<CommentVO> commentList = new ArrayList<CommentVO>();
		
		   String sql  = 	"SELECT t1.*";
		   		  sql +=	 " FROM mvc_comment t1";
		   		  sql +=" LEFT JOIN mvc_comment t2";
		   		  sql +=	   " ON t1.cid = t2.pid";
		   		  sql +=	" WHERE t1.delYN='N' AND t1.bid = "+bId;
		   		  sql += " GROUP BY t1.cid";
		   		  sql += " ORDER BY t1.cmtGroup ASC, t1.sort ASC, t1.regdate ASC";
		
		   		  System.out.println("코멘트 리스트 :: "+sql);
		try{
			DBconn.dbConn = DBconn.getConnection();
			
			pstmt = DBconn.dbConn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				CommentVO cvo = new CommentVO();
				cvo.setcId(rs.getInt("cId"));
				cvo.setbId(rs.getInt("bId"));
				cvo.setCmtGroup(rs.getInt("cmtGroup"));
				cvo.setpId(rs.getInt("pId"));
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
	
	// 코멘트 그룹 업데이트
	public int groupNOUpdate(int cId, int group){
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql  = "UPDATE mvc_comment";
			   sql +=   " SET cmtGroup = ?";
			   sql += " WHERE cid = ?";
		System.out.println("코멘트 그룹 업데이트 :: "+sql);
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
	
	// 코멘트 그룹순서 증가 (정렬)
	public int commentSortUpdate(CommentVO cvo){
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql = "UPDATE mvc_comment SET sort = sort+1 WHERE bid = ? AND ?<depth";
		System.out.println("코멘트 그룹순서 증가 (정렬) :: "+sql);
		try{
			DBconn.dbConn = DBconn.getConnection();
			
			pstmt = DBconn.dbConn.prepareStatement(sql);
			pstmt.setInt(1, cvo.getbId());
			pstmt.setInt(2, cvo.getDepth());
			
			result = pstmt.executeUpdate();
			
			
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
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
	
		
	// 코멘트 삭제(단일) ==> pId = cId
	// selectCommentKey(int cid) 로 조회한 cid의 하위뎁스 지우기
	public int commentDel(int cId){
		int result = 0;
		PreparedStatement pstmt = null;
		
		//String sql = "DELETE FROM mvc_comment WHERE  delYN = 'N' AND pId = ?";
		String sql = "UPDATE mvc_comment SET delYN = 'Y' WHERE  delYN = 'N' AND cid = ?";
		
		System.out.println("코멘트 삭제 :: "+sql);
		
		try{
			DBconn.dbConn = DBconn.getConnection();
			
			pstmt = DBconn.dbConn.prepareStatement(sql);
			pstmt.setInt(1, cId);
			
			result = pstmt.executeUpdate();
			
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
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
	
	// 코멘트 삭제(그룹) ==> bid(게시글 key) = cId
	public int commentBidDel(int cId){
		int result = 0;
		PreparedStatement pstmt = null;
		
		//String sql = "DELETE FROM mvc_comment WHERE  delYN = 'N' AND cmtGroup = ?";
		String sql = "UPDATE mvc_comment SET delYN = 'Y' WHERE delYN = 'N' AND bid = ?";
		System.out.println("코멘트 삭제(그룹) :: "+sql);
		
		try{
			DBconn.dbConn = DBconn.getConnection();
			
			pstmt = DBconn.dbConn.prepareStatement(sql);
			pstmt.setInt(1, cId);
			
			result = pstmt.executeUpdate();
			
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
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

	
}
