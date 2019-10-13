package board.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.BoardVO;
import board.CommentVO;
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
		
		String location = "";
		String command = req.getParameter("command");
		
		System.out.println("command : "+command);
		
		if(command.equals("commentInsert")){
			System.out.println("코벤트 등록..");
			
			
			String boardId = req.getParameter("boardId");	//코멘트의 부모글
			int gorup = Integer.parseInt(boardId);
			
			String userId = req.getParameter("userId");
			String comment = req.getParameter("comment");
			
			
			System.out.println(boardId +"/"+ userId +"/"+ comment);
			CommentVO cvo = new CommentVO();
			cvo.setGroup(gorup);
			cvo.setUserId(userId);
			cvo.setComment(comment);
			
			System.out.println(cvo.toString());
			
			CommentServlet cs = new CommentServlet();
			cs.ajaxResult(cvo);
			
			// 코멘트테이블에 부모글key값 있는지 확인(등록된 코멘트확인)
			// 없으면 depth = 0, sort = 1
			// 있으면 depth = 0, sort = 2
			// 댓글에 댓글이면 depth = 1, sort = 1
			
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
			
			//RequestDispatcher rd = req.getRequestDispatcher(location);
			//rd.forward(req, resp);
		}
	}
	
	// ajax 결과 반환
	public Object ajaxResult(Object obj){
		return obj;
	}
}
