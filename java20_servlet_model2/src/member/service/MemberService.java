package member.service;

import member.vo.MemberVO;

public class MemberService {

	private MemberDao memberDao;
	public MemberService(){
		memberDao = MemberDao.getInstance();
	}
	
	
	// 회원가입
	public boolean join ( String userId, String userPw, String userName, String userEmail ) {
	
		if ( memberDao.selectOne( userId ) == null ){
			MemberVO mvo = new MemberVO();
			
			mvo.setmUserId( userId );
			mvo.setmUserPw( userPw );
			mvo.setmUserName( userName );
			mvo.setmUserEmail( userEmail );
			
			memberDao.insertMember( mvo );
			
			return true;
		} else {
			return false;
		}
	}
	
	// 로그인
	public boolean login( String userId, String userPw ) {
		MemberVO mvo = memberDao.selectOne(userId);
		//로그인 체크
		
		//1. 실패) 아이디 없음
		if( mvo == null ) {
			return false;
		} else {
			//2. 성공) 로그인 정보 일치
			if( mvo.getmUserPw().equals(userPw) ) {
				return true;
			} else {
				// 3. 실패) 로그인 정보 불일치
				return false;
			}
		}
	}
	
	
	
	

}
