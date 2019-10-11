package member.service;

import member.vo.MemberVO;

public class MemberService {

	private MemberDao memberDao;
	public MemberService(){
		memberDao = MemberDao.getInstance();
	}
	
	// 회원가입
	public MemberVO join(MemberVO mvo){
		if (memberDao.selectOne(mvo.getUserId()) == null){
			memberDao.insertMember(mvo);
			return mvo;
		} else {
			return null;
		}
	}
	
	//로그인 체크
	public MemberVO login(String userId, String userPw) {
		
		MemberVO mvo = memberDao.selectOne(userId);
		
		//1. 실패) 아이디 없음
		if( null == mvo ) {
			return null;
		} else {
			//2. 성공) 로그인 정보 일치
			if( mvo.getUserPw().equals(userPw) ) {
				return mvo;
			}
			// 3. 실패) 로그인 정보 불일치
			else{
				return null;
			}
		}
	}

}
