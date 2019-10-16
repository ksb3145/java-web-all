package member.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.service.MemberService;
import member.vo.MemberVO;
import member.vo.SessionVO;

public class LoginServlet extends HttpServlet {
		private static final long serialVersionUID = 1L;
	
		private MemberService service;
		public LoginServlet() {
			service = new MemberService();
		}
		
		@Override
		protected void doGet( HttpServletRequest req, HttpServletResponse resp )
				throws ServletException, IOException {
			doProc(req, resp);
		}
		
		@Override
		protected void doPost( HttpServletRequest req, HttpServletResponse resp )
				throws ServletException, IOException {
			doProc(req, resp);
		}
		
		protected void doProc( HttpServletRequest req, HttpServletResponse resp )
				throws ServletException, IOException {
			
			// 한글깨짐 처리
			req.setCharacterEncoding("UTF-8");
			resp.setContentType("text/html;charset-UTF-8");
			
			String location = "";
			String command = req.getParameter("command");
			
			HttpSession session = req.getSession();
			
			if(command.equals("home")){
				
				SessionVO sVo = (SessionVO)session.getAttribute("sessionVO");
				
				if(null == sVo){
					location= "/WEB-INF/views/member/login.jsp";
				}else{
					location= "/WEB-INF/views/member/home.jsp";
				}
				
				RequestDispatcher rd = req.getRequestDispatcher(location);
				rd.forward(req, resp);
				
				//System.out.println();
				
			}else if( command.equals("login") ) {
				System.out.println("로그인");
				
				String userId = req.getParameter("userId");
				String userPw = req.getParameter("userPw");
				
				MemberVO mvo = service.login(userId, userPw);
				
				if(null != mvo){
					location = "/BoardServlet?command=bbsList&page=1";
					
					SessionVO svo = new SessionVO();
					svo.setUserId(mvo.getUserId());
					svo.setUserName(mvo.getUserName());
					svo.setUserEmail(mvo.getUserEmail());
					
					req.setAttribute("code", "OK");
					req.setAttribute("msg", "로그인 성공");
					session.setAttribute("sessionVO", svo);
					
					System.out.println("로그인 성공");
					
				} else {
					location = "/WEB-INF/views/member/login.jsp";
					
					req.setAttribute("code", "Fail");
					req.setAttribute("msg", "로그인 실패");
					
					System.out.println("로그인 실패");
				}
				
				RequestDispatcher rd = req.getRequestDispatcher(location);
				rd.forward(req, resp);
			}else if ( command.equals("logout") ) {
				
				session = req.getSession(false); // 이미 세션이 있다면 그 세션을 돌려주고, 세션이 없으면 null을 돌려준다.
				if(session!=null){
					session.invalidate(); // 세션값 초기화
				}else{
					System.out.println("로그아웃 실패");
				}
				
				location = "/loginServlet?command=home";
				
				RequestDispatcher rd = req.getRequestDispatcher(location);
				rd.forward(req, resp);
				
			} else if ( command.equals("joinForm") ) {
				System.out.println("회원가입 양식...");
				
				location = "/WEB-INF/views/member/join.jsp";
				
				RequestDispatcher rd = req.getRequestDispatcher(location);
				rd.forward(req, resp);
				
			} else if ( command.equals("join") ) {
				System.out.println("회원가입...");
				
				// 회원가입 처리
				String userId = req.getParameter("userId");
				String userPw = req.getParameter("userPw");
				String userName = req.getParameter("userName");
				String userEmail = req.getParameter("userEmail");
				
				System.out.println(userId+"/"+userPw+"/"+userName+"/"+userEmail);
				
				MemberVO mvo = new MemberVO();
				mvo.setUserId(userId);
				mvo.setUserPw(userPw);
				mvo.setUserName(userName);
				mvo.setUserEmail(userEmail);
				
				MemberVO resultVO = service.join(mvo);
				
				
				if(null != resultVO){
					// 가입성공
					location = "/WEB-INF/views/common/home.jsp";
					
					req.setAttribute("code", "OK");
					req.setAttribute("msg", "로그인 성공");
					session.setAttribute("sessionVO", resultVO);	
					
				}else{
					// 가입실패
					location = "/WEB-INF/views/member/login.jsp";
					
					req.setAttribute("code", "Fail");
					req.setAttribute("msg","가입 실패!");
				}
				
				RequestDispatcher rd = req.getRequestDispatcher(location);
				rd.forward(req, resp);
				
			}
		}

}
