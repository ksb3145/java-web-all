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
		System.out.println("게시글 등록 :: "+sql);
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
		
		String sql   = "INSERT INTO mvc_board( pId, bGroup, bSort, mUserId, bTitle, bContent, bRegdate) VALUES (?, ?, ?, ?, ?, ?, now());";
		
		System.out.println("답글 등록 :: "+sql);
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
		System.out.println("게시판 총 카운트 :: "+sql);
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
		
		if(searchType != "" && keyword != ""){
			where += " AND "+searchType;
			where += " LIKE '%"+keyword+"%'";
		}
		
		sql  = 		"SELECT B.* ";
		sql += 		   	  " , (SELECT COUNT(*) FROM mvc_comment WHERE bId= B.bId AND delYN = 'N') cmtCNT";
		sql += 		   	  " , IFNULL( (SELECT IF(fId>0,'Y',NULL) FROM mvc_file WHERE bId= B.bId AND delYN = 'N') ,'N') file_YN";
		sql += 		" FROM mvc_board B ";
		sql += 	   "WHERE B.bDelYN='N' "+where;
		sql +=  " ORDER BY bGroup DESC ";
		sql +=			   ", bRegdate DESC "; 
		sql += 		 "LIMIT "+offset+","+limit;
		
		System.out.println("게시글 전체 리스트 :: "+sql);
		
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
				bvo.setFileYN(rs.getString("file_YN"));
				bvo.setCommentCNT(rs.getInt("cmtCNT"));
				
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
		
		String sql = "UPDATE mvc_board ";
				sql +=	"SET bTitle = ? ";
				sql +=	  ", bContent = ? ";
				sql +="WHERE bId = ? ";
				sql +=  "AND mUserId = ? ";
				
				System.out.println("게시글 수정 :: "+sql);

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
		
		System.out.println("그룹넘 업뎃 :: "+sql);
		
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
		
		String sql   = "UPDATE mvc_board ";
				sql += 	  "SET bSort = (bSort+1) ";
				sql +=  "WHERE bid != ? ";
				sql += 	  "AND bGroup = ? ";
		
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
	
		String sql   = "SELECT bId, pId, bGroup, bTitle, bContent, bHit, bRegdate, mUserId ";
				sql += 	 "FROM mvc_board ";
				sql += 	"WHERE bid=? ";
		
		/**
		String sql   = "SELECT B.bId, B.pId, B.bGroup, B.bTitle, B.bContent, B.bHit, B.bRegdate, B.mUserId, ";
				sql += "			F.fId, F.file_name, F.file_org_name, F.file_path ";
				sql += " FROM mvc_board B, mvc_file F ";
				sql += "WHERE B.bId = F.bId ";
				sql += "AND B.bid = ?";
		**/
				
		System.out.println("게시글 상세 ::" +sql +"/"+ bId);
				
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
			
			String sql = "SELECT DISTINCT bGroup, pId FROM mvc_board WHERE bGroup = ?";
			
			System.out.println("게시글 그룹 key ::" +sql +"/"+ bvo.getId());
			
			try{
				DBconn.dbConn = DBconn.getConnection();
				pstmt = DBconn.dbConn.prepareStatement( sql );
				pstmt.setInt(1,bvo.getId());
				rs = pstmt.executeQuery();
				
				if(rs.next()){
					rbvo = new BoardVO();
					rbvo.setbGroup(rs.getInt("bGroup"));
					rbvo.setPid(rs.getInt("pId"));
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
		
		String sql   = "UPDATE mvc_board ";
				sql += 	  "SET bDelYN = 'Y' ";
				sql +=  "WHERE bDelYN = 'N' ";
				sql += 	  "AND bid = ?";
		
		System.out.println("게시글 삭제 ::" +sql +"/"+ bvo.getId());
		
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
		String sql   = "UPDATE mvc_board ";
				sql += 	  "SET bDelYN = 'Y' ";
				sql += 	"WHERE bGroup = ? ";
		
		System.out.println("게시글 그룹삭제 ::" +sql +"/"+ bvo.getbGroup());
				
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
		
		System.out.println("파일등록 ::" +sql +"/"+ fvo.toString());
		
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
	
	// 파일
	public FileVO selectOneFile(FileVO fvo){
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		FileVO rfvo = null;	//리턴할 객체참조변수
		
		String sql = "SELECT fId, file_name, file_org_name, file_path, file_ext  FROM mvc_file WHERE fId = ?";
		
		System.out.println("파일 조회 ::" +sql +"/"+ fvo.getfId());
		
		try{
			DBconn.dbConn = DBconn.getConnection();
			pstmt = DBconn.dbConn.prepareStatement( sql );
			pstmt.setInt(1,fvo.getfId());
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				rfvo = new FileVO();
				rfvo.setfId(rs.getInt("fId"));
				rfvo.setFileName(rs.getString("file_name"));
				rfvo.setFileOrgName(rs.getString("file_org_name"));
				rfvo.setFilePath(rs.getString("file_path"));
				rfvo.setFileExt(rs.getString("file_ext"));
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
		return rfvo;
	}
	// 게시들에 등록된 파일리스트
	public List<FileVO> selectFiles(int boardId){
		
		PreparedStatement pstmt = null;
		List<FileVO> files = new ArrayList<FileVO>();
		ResultSet rs = null;
		
		String sql   = "SELECT fId, file_name, file_org_name, file_path, file_ext "; 
				sql += 	 "FROM mvc_file ";
				sql += 	"WHERE bid = ? ";
				sql += 	  "AND delYN = 'N' ";
				
				
				System.out.println("파일리스트::" +sql +"/"+ boardId);
		
		try{
			
			DBconn.dbConn = DBconn.getConnection();
			
			pstmt = DBconn.dbConn.prepareStatement(sql);
			pstmt.setInt(1, boardId);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				FileVO fvo = new FileVO();
				fvo.setfId(rs.getInt("fId"));
				fvo.setFileName(rs.getString("file_name"));
				fvo.setFileOrgName(rs.getString("file_org_name"));
				fvo.setFilePath("file_path");
				fvo.setFileExt("file_ext");
				files.add(fvo);
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
		return files;
	}
	
	
	// 파일삭제
	public int FileDel(FileVO fvo){
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql   = "UPDATE mvc_file ";
				sql += 	  "SET delYN = 'Y' ";
				sql += 	"WHERE fId = ? ";
				
				System.out.println("파일삭제::" +sql +"/"+ fvo.getfId());
		
		try{
			DBconn.dbConn = DBconn.getConnection();
			
			pstmt = DBconn.dbConn.prepareStatement(sql);
			pstmt.setInt(1, fvo.getfId());
			
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


