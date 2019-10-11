package board.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.BoardVO;
import board.service.CommentService;
import member.vo.MemberVO;

public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    private CommentService service;
	public CommentServlet() {
    	service = new CommentService();
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
		
		// 로그인 세션체크
		HttpSession session = req.getSession();
    	MemberVO mvo = (MemberVO) session.getAttribute("sessionVO");
    	System.out.println("게시판) 세션체크 ==> " + mvo.getUserId());

		String location = "";
		String command = req.getParameter("command");
		
		System.out.println("command : "+command);
		
		if(command.equals("commentInsert")){
			System.out.println("코벤트 등록..");
			
			String userId = req.getParameter("userId");
			String title = req.getParameter("title");
			String content = req.getParameter("content");
			
			
//			if(null != resultVO){
//				// 등록 성공
//				location = "/BoardServlet?command=bbsList";
//				
//				req.setAttribute("code", "OK");
//				req.setAttribute("msg", "등록 성공");	
//				
//			}else{
//				// 등록 실패
//				location = "/BoardServlet?command=bbsWrite";
//				
//				req.setAttribute("code", "Fail");
//				req.setAttribute("msg","등록 실패!");
//			}
			
			RequestDispatcher rd = req.getRequestDispatcher(location);
			rd.forward(req, resp);
		}
	}
}
