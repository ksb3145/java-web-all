package board.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import board.BoardVO;
import board.FileVO;
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
		
		String sql = "INSERT INTO mvc_board (mUserId, bTitle, bContent, bRegdate) VALUES ( ?, ?, ?, now());";
		
		try{
			
			DBconn.dbConn = DBconn.getConnection();
			
			pstmt = DBconn.dbConn.prepareStatement(sql);
			pstmt.setString(1, bvo.getUserId());
			pstmt.setString(2, bvo.getTitle());
			pstmt.setString(3, bvo.getContent());
			
			result = pstmt.executeUpdate();
			
			ResultSet rs = pstmt.executeQuery("SELECT LAST_INSERT_ID()");
			
			if(rs.next()){
				result = rs.getInt(1);
			}
			
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
	// 답글 등록
	public int insertBoardRe(BoardVO bvo){
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql   = "INSERT INTO mvc_board (pId, bGroup, bSort, mUserId, bTitle, bContent, bRegdate) VALUES (?, ?, ?, ?, ?, ?, now());";
		
		try{
			
			DBconn.dbConn = DBconn.getConnection();
			
			pstmt = DBconn.dbConn.prepareStatement(sql);
			pstmt.setInt(1, bvo.getPid());
			pstmt.setInt(2, bvo.getbGroup());
			pstmt.setInt(3, bvo.getbSort());
			pstmt.setString(4, bvo.getUserId());
			pstmt.setString(5, bvo.getTitle());
			pstmt.setString(6, bvo.getContent());
			
			result = pstmt.executeUpdate();
			
			ResultSet rs = pstmt.executeQuery("SELECT LAST_INSERT_ID()");
			
			if(rs.next()){
				result = rs.getInt(1);
			}
			
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
	public int resultTotalCnt(HashMap<Object, Object> params){
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String where = "";
		
		if(null != params){
			String searchType 	= (null == params.get("searchType")) 	? "" : (String) params.get("searchType"); 	// 검색타입
			String keyword		= (null == params.get("keyword")) 		? "" : (String) params.get("keyword"); 		// 검색어
		
			if(searchType != "" && keyword != "") where += " AND "+searchType+" LIKE '%"+keyword+"%'";
		}
		
		String sql="SELECT count(*) totalCnt FROM mvc_board WHERE bDelYN = 'N'"+ where;
		System.out.println(sql);
		//검색

		
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
		
		String sql = "" , where = "";
		int offset=0;
		int limit = (int)params.get("limit");
		int bNo = offset-1;
		
		if(0>bNo) bNo = 0;
		if(params.get("offset") != "") offset = (int)params.get("offset");
		//검색
		String searchType 	= (params.get("searchType") == null) 	? "" : (String) params.get("searchType"); 	// 검색타입
		String keyword		= (params.get("keyword") == null) 		? "" : (String) params.get("keyword"); 		// 검색어
		
		if(searchType != "" && keyword != "") where += " AND "+searchType+" LIKE '%"+keyword+"%'";
		
		//sql  = "SELECT @rownum:=@rownum+1 ROWNUM, B.*";
		sql  = "SELECT B.*";
		sql += " FROM mvc_board B";
		sql += " WHERE (@rownum:="+offset+")="+offset;
		sql += " AND B.bDelYN='N' ";
		sql +=  where;
		sql += " ORDER BY bGroup ASC, bSort DESC, bRegdate DESC "; 
		sql += " LIMIT "+offset+","+limit;
		
		System.out.println("BoardDao list Sql ::: "+sql);
		
		try{
			
			DBconn.dbConn = DBconn.getConnection();
			
			pstmt = DBconn.dbConn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				BoardVO bvo = new BoardVO();
				
				//bvo.setRownum(rs.getInt("ROWNUM"));
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
	
	// 그룹넘 업뎃
	public int groupNOUpdate(BoardVO bvo){
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql = "UPDATE mvc_board SET bGroup = ? WHERE bId = ?";
		
		System.out.println(sql);
		
		try{
			DBconn.dbConn = DBconn.getConnection();
			
			pstmt = DBconn.dbConn.prepareStatement(sql);
			pstmt.setInt(1, bvo.getbGroup());
			pstmt.setInt(2, bvo.getId());
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
	
	// 순서 증가
	public int sortNOUpdate(BoardVO bvo){
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql  = "UPDATE mvc_board";
			   sql +=   " SET bSort = (bSort+1)";
			   sql += " WHERE bid != ? ";
			   sql += " AND bGroup = ?";
		
		try{	
			DBconn.dbConn = DBconn.getConnection();
			
			pstmt = DBconn.dbConn.prepareStatement(sql);
			pstmt.setInt(1, bvo.getId());
			pstmt.setInt(2, bvo.getbGroup());
			
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
	
	// 게시글 상세
	public BoardVO selectView(int bId){
	
		String sql = "SELECT bId, pId, bGroup, bTitle, bContent, bHit, bRegdate, mUserId FROM mvc_board WHERE bid=?;";
	
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
				bvo.setPid(rs.getInt("pId"));
				bvo.setbGroup(rs.getInt("bGroup"));
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
	
	//  게시글 그룹 key ( result 값 null => 답글없음..)
		public BoardVO selectBoardGroupKey(BoardVO bvo){
			ResultSet rs = null;
			PreparedStatement pstmt = null;
			BoardVO rbvo = null;	//리턴할 객체참조변수
			
			String sql = "SELECT bGroup FROM mvc_board WHERE bGroup = ?";
			System.out.println(sql);
			try{
				DBconn.dbConn = DBconn.getConnection();
				pstmt = DBconn.dbConn.prepareStatement( sql );
				pstmt.setInt(1,bvo.getId());
				rs = pstmt.executeQuery();
				
				if(rs.next()){
					rbvo = new BoardVO();
					rbvo.setbGroup(rs.getInt("bGroup"));
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
			return rbvo;
		}
	
	// 게시글 삭제 (키값)
	public int boardDel(BoardVO bvo){
		int result = 0;
		PreparedStatement pstmt = null;
		
		//String sql = "DELETE FROM mvc_board WHERE bDelYN = 'N' AND bid = ?";
		String sql = "UPDATE mvc_board SET bDelYN = 'Y' WHERE bDelYN = 'N' AND bid = ?";
		
		System.out.println(sql);
		
		try{
			DBconn.dbConn = DBconn.getConnection();
			
			pstmt = DBconn.dbConn.prepareStatement(sql);
			pstmt.setInt(1, bvo.getId());
			
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
	
	// 게시글 그룹삭제
	public int boardGroupDel(BoardVO bvo){
		int result = 0;
		PreparedStatement pstmt = null;
		
		//String sql = "DELETE FROM mvc_board WHERE bDelYN = 'N' AND bid = ?";
		String sql = "UPDATE mvc_board SET bDelYN = 'Y' WHERE bDelYN = 'N' AND bGroup = ?";
		
		try{
			DBconn.dbConn = DBconn.getConnection();
			
			pstmt = DBconn.dbConn.prepareStatement(sql);
			pstmt.setInt(1, bvo.getbGroup());
			
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
	
	//파일등록
	public int FileUpload(FileVO fvo){
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql = "INSERT INTO mvc_file ( bId, file_name, file_org_name, file_path, regdate ) VALUES ( ?, ?, ?, ?, now() );";
		
		try{
			
			DBconn.dbConn = DBconn.getConnection();
			
			pstmt = DBconn.dbConn.prepareStatement(sql);
			pstmt.setInt(1, fvo.getBid());
			pstmt.setString(2, fvo.getFileName());
			pstmt.setString(3, fvo.getFileOrgName());
			pstmt.setString(4, fvo.getFilePath());
			
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
}


