package member.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import common.util.DBconn;
import member.vo.MemberVO;

public class MemberDao {
	private static MemberDao instance;
	public static MemberDao getInstance(){
		if(instance == null)
			instance = new MemberDao();
		return instance;
	}
	
	// 회원등록
	public void insertMember( MemberVO mvo ){
		PreparedStatement pstmt = null;
		
		String sql = "insert into mvc_member (mUserId, mUserPw, mUserName, mUserEmail) values (?,?,?,?);";
		
		try{	
			
			DBconn.dbConn = DBconn.getConnection();
			pstmt = DBconn.dbConn.prepareStatement(sql);
			
			pstmt.setString(1, mvo.getUserId());
			pstmt.setString(2, mvo.getUserPw());
			pstmt.setString(3, mvo.getUserName());
			pstmt.setString(4, mvo.getUserEmail());
			
			pstmt.executeUpdate();
			
		}catch ( SQLException e ) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
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
	}
	
	// id 조회
	public MemberVO selectOne( String id ){
		MemberVO mvo = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		String sql = "select mId, mUserId, mUserPw, mUserName, mUserEmail from mvc_member where mUserId = ?";
		
		try{
			DBconn.dbConn = DBconn.getConnection();
			pstmt = DBconn.dbConn.prepareStatement( sql );
			pstmt.setString( 1, id );
			rs = pstmt.executeQuery();
			
			if( rs.next() ){
				mvo = new MemberVO();
				mvo.setId( rs.getInt("mId") );
				mvo.setUserId( rs.getString("mUserId") );
				mvo.setUserPw( rs.getString("mUserPw") );
				mvo.setUserName( rs.getString("mUserName") );
				mvo.setUserEmail( rs.getString("mUserEmail") );
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
		return mvo;
	}
		
}
