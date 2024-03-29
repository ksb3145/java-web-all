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
	
	private Connection conn;
	private MemberDao(){
		try {
			conn = DBconn.getConnection();
		} catch ( ClassNotFoundException | SQLException e ) {
			e.printStackTrace();
		}
	}
	
	// 회원등록
	public void insertMember( MemberVO mvo ){
		//쿼리문
		String sql = "insert into mvc_member (mUserId, mUserPw, mUserName, mUserEmail, mRegdate) values (?,?,?,?,now())";
		
		PreparedStatement pstmt = null;
		try{	
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, mvo.getmUserId());
			pstmt.setString(2, mvo.getmUserPw());
			pstmt.setString(3, mvo.getmUserName());
			pstmt.setString(4, mvo.getmUserEmail());
			
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
	
	// id 조회
	public MemberVO selectOne( String id ){
		String sql = "select mId, mUserId, mUserPw, mUserName, mUserEmail from mvc_member where mUserId = ?";
		
		PreparedStatement pstmt = null;
		MemberVO mvo = null;
		ResultSet rs = null;
		
		try{
			pstmt = conn.prepareStatement( sql );
			pstmt.setString( 1, id );
			rs = pstmt.executeQuery();
			
			if( rs.next() ){
				mvo = new MemberVO();
				mvo.setmUserId( rs.getString("mUserId") );
				mvo.setmUserPw( rs.getString("mUserPw") );
				mvo.setmUserName( rs.getString("mUserName") );
				mvo.setmUserEmail( rs.getString("mUserEmail") );
			}
			
		} catch ( SQLException e ) {
			e.printStackTrace();
		} finally {
			try {
				
				if( pstmt != null && !pstmt.isClosed() )
					pstmt.close();
				if( rs != null && !rs.isClosed() )
					pstmt.close();
				
			} catch ( SQLException e ){
				e.printStackTrace();
			}
		}
		return mvo;
	}
		
}
