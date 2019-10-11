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
				
				MemberVO mvo = (MemberVO) session.getAttribute("sessionVO");
				
				if(null == mvo){
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
					location = "/BoardServlet?command=bbsList";
					
					req.setAttribute("code", "OK");
					req.setAttribute("msg", "로그인 성공");
					session.setAttribute("sessionVO", mvo);
					
					System.out.println("로그인 성공");
					
				} else {
					location = "/WEB-INF/views/member/login.jsp";
					
					req.setAttribute("code", "Fail");
					req.setAttribute("msg", "로그인 실패");
					
					System.out.println("로그인 실패");
				}
				
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
