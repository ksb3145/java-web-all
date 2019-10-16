package board.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.CommentVO;
import board.service.CommentService;
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
		
		String command = req.getParameter("command");
		
		if(command.equals("commentInsert")){
			System.out.println("코벤트 등록..");
			
			String resultCode 	= "OK"; // 결과코드
			String boardId		= req.getParameter("boardId");	// 게시글
			String userId		= req.getParameter("userId");	// 댓글 작성자
			String comment		= req.getParameter("comment");	// 댓글 내용
			String cmtGroup		= req.getParameter("cmtGroup");	// 댓글 그룹
			String cmtPid		= req.getParameter("pid");		// 부모 댓글 키값
			String cmtDepth		= req.getParameter("cmtDepth");	// 댓글 깊이
			String cmtSort		= req.getParameter("cmtSort");	// 댓글 순서
			
			// 캐스팅..
			int bId				= Integer.parseInt(boardId);
			int group			= Integer.parseInt(cmtGroup);
			int pId				= Integer.parseInt(cmtPid);
			int depth			= Integer.parseInt(cmtDepth);
			int sort 			= Integer.parseInt(cmtSort);
			
			// 바인딩시킬 코멘트 객체 생성..
			CommentVO cvo = new CommentVO();
			cvo.setbId(bId);
			cvo.setUserId(userId);
			cvo.setpId(pId);
			cvo.setContents(comment);
			cvo.setDepth(depth);
			cvo.setSort(sort);
			
			if(group>0) {
				cvo.setCmtGroup(group);		// 원글(댓글)이 아니면(=대댓글) 부모의 group값
			}
			
			int cId = service.setCommentInsert(cvo);	// 원글이면 insert 후 last_insert_ID 값
			
			if(cId == 0){
				resultCode = "Fail";
			}else if(group==0){
				// group==0 원글..   [ group값 last_insert_ID ]
				// 원글 (댓글 기준)
				int result = service.setGroupNOUpdate(cId,cId);	// setGroupNOUpdate(int cId, int group)
				if(result == 0) resultCode = "Fail";
			}
			
			
			
			JSONObject json = new JSONObject(); 
			json.put("code", resultCode);
			
		    // 헤더설정
		    resp.setContentType("application/json");
		    resp.setCharacterEncoding("UTF-8");
		    
		    PrintWriter out = resp.getWriter();
		    out.print(json);
		}
	}
	
	
}
