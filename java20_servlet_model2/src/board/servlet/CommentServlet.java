package board.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

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
	
	private static List<Integer> delList = new ArrayList<>();	// 삭제리스트
    
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
			// 코멘트 등록 (ajax/json)
			
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
			
			int cId = service.commentInsert(cvo);	// 원글이면 insert 후 last_insert_ID 값
			
			if(cId == 0){
				resultCode = "Fail";
			}else if(group==0 && cId>0){
				// group==0 원글..   [ group값 last_insert_ID ]
				// 원글 (댓글 기준)
				int result = service.groupNOUpdate(cId,cId);	// setGroupNOUpdate(int cId, int group)
				if(result == 0) resultCode = "Fail";
			}
			
			
			JSONObject json = new JSONObject(); 
			json.put("code", resultCode);
			
		    // 헤더설정
		    resp.setContentType("application/json");
		    resp.setCharacterEncoding("UTF-8");
		    
		    PrintWriter out = resp.getWriter();
		    out.print(json);
		    
		}else if(command.equals("cmtDelete")){
			//  코멘트삭제
			
			String resultCode 	= "";
			//String boardId		= req.getParameter("boardId");	// 게시글 key
			String cmtId		= req.getParameter("cmtId");	// 댓글 key
			//String cmtDepth		= req.getParameter("cmtDepth");	// 댓글 깊이
			
			// 캐스팅..
			//int bId				= Integer.parseInt(boardId);
			int cId				= Integer.parseInt(cmtId);
			//int depth			= Integer.parseInt(cmtDepth);
			
			
			CommentServlet cs = new CommentServlet();
			List<Integer> id = new ArrayList<Integer>();
			
//			if(depth == 0){
//				// 1. depth == 0 :: 코멘트의 원글.. (그룹삭제) ==> cGroup = cId
//				
//				id.add(cId);
//				
//				delList.add(cId);
//				if(cs.commentList(id) != null){
//					System.out.println(delList);
//					int val = cs.commentDel(delList,1);
//					
//					if(val == 0){
//						msg = "다시시도하세요";
//					}else{
//						msg = "삭제되었습니다.";
//					}
//				}
//				
//			}else{
				// 2. depth > 0 :: 대댓글~ (단일삭제) ==> pId = cId
				// 2-1.cid 값 삭제
				// 2-2.pid 값으로 cid 셀렉
				
				// 2-3. 값이 있을경우 삭제 후 [2-1] 재귀호출
				
				id.add(cId);
				
				delList.add(cId);
				if(cs.commentList(id) != null){
					System.out.println(delList);
					int val = cs.commentDel(delList);
					
					if(val == 0){
						resultCode = "OK";
					}else{
						resultCode = "Fail";
					}
				}
//			}
			
			JSONObject json = new JSONObject(); 
			json.put("code", resultCode);
			
		    // 헤더설정
		    resp.setContentType("application/json");
		    resp.setCharacterEncoding("UTF-8");
		    
		    PrintWriter out = resp.getWriter();
		    out.print(json);
			
		}
	}
	
	// 자식 key
	public List<Integer> commentList(List<Integer> cId) {
		List<Integer> result = null ,resultData = null; 
	
		if (cId.isEmpty()) {
			result = null;
		} else {
			for(int i=0; i<cId.size(); i++){
				resultData = service.selectCommentKey(cId.get(i));
				
				for(int ii=0; ii<resultData.size(); ii++){
					delList.add(resultData.get(ii));
				}
			}
			
			if(!resultData.isEmpty()){
				CommentServlet ss = new CommentServlet();
				ss.commentList(resultData);
			}
			
			result = resultData;
		}
		
		return result;
	}
	
	
	// 리스트 삭제
	public int commentDel(List<Integer> cIdList) {
		int result = 0;
		
		for(int i=0; i<cIdList.size(); i++){
			result = service.commentDel(cIdList.get(i));
		}
		return result;
	}
	
	
}
