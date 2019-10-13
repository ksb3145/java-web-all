package board.servlet;

import java.io.IOException;
import java.io.PrintWriter;

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
import net.sf.json.JSONObject;

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
			
			String boardId = req.getParameter("boardId");	// 코멘트의 부모글
			int gorup = Integer.parseInt(boardId);			// 캐스팅..
			
			// 선언과 값 할당
			String userId = req.getParameter("userId");
			String comment = req.getParameter("comment");
			
			System.out.println(boardId +"/"+ userId +"/"+ comment);
			
			// 바인딩시킬 코멘트 객체 생성..
			CommentVO cvo = new CommentVO();
			
			cvo.setGroup(gorup);
			cvo.setUserId(userId);
			cvo.setComment(comment);
			
			service.setCommentInsert(cvo);
			
			// 댓글 없으면 depth = 0, sort = 1
			// 댓글 있으면 depth = 0, sort = 2
			// 댓글에 댓글이면 depth = 1, sort = 1
			
			
			// JSONObject는 HashMap을 상속
			JSONObject json = new JSONObject(); 
			
			json.put("resultCode", "OK");
			json.put("gorup", gorup);
		    json.put("userId", userId);
		    json.put("comment", comment);
		    
		    // 헤더설정
		    resp.setContentType("application/json");
		    resp.setCharacterEncoding("UTF-8");
		    
		    PrintWriter out = resp.getWriter();
		    out.print(json);
		}
	}
	
	
}
