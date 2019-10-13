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
			
			String boardId  = req.getParameter("boardId");	// 코멘트의 부모글
			String userId 	= req.getParameter("userId");
			String comment 	= req.getParameter("comment");
			String cmtDepth = req.getParameter("cmtDepth");
			String cmtSort 	= req.getParameter("cmtSort");
			// 캐스팅..
			int CmtGroup 	= Integer.parseInt(boardId);
			int depth 		= Integer.parseInt(cmtDepth);
			int sort 		= Integer.parseInt(cmtSort);
			
			System.out.println( userId +"/"+ comment +"/"+ depth +"/"+ sort +"/"+ CmtGroup);
			
			// 바인딩시킬 코멘트 객체 생성..
			CommentVO cvo = new CommentVO();
			
			cvo.setCmtGroup(CmtGroup);
			cvo.setUserId(userId);
			cvo.setContents(comment);
			cvo.setDepth(depth);
			cvo.setSort(sort);
			
			service.setCommentInsert(cvo);
			
			// 댓글 없으면 depth = 0, sort = 1
			// 댓글 있으면 depth = 0, sort = 2
			// 댓글에 댓글이면 depth = 1, sort = 1
			
			
			// JSONObject는 HashMap을 상속
			JSONObject json = new JSONObject(); 
			
			json.put("resultCode", "OK");
			json.put("gorup", CmtGroup);
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
