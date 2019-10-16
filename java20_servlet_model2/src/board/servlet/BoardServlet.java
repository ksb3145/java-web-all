package board.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.Session;

import net.sf.json.JSONObject;

//import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.oreilly.servlet.MultipartRequest;

import board.BoardVO;
import board.service.BoardService;
import board.service.CommentService;
import member.vo.MemberVO;

//@WebServlet("/BoardServlet")
public class BoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    private BoardService service;
	public BoardServlet() {
    	service = new BoardService();
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
		
    	// 멀티파트 요청시 null값 처리
    	// 파일 업로드. 폼에서 가져온 인자값을 얻기 위해request 객체 전달, 업로드 경로, 파일 최대 크기, 한글처리, 파일 중복처리
    	// MultipartRequest multi = new MultipartRequest(request, uploadPath, size, "euc-kr", new DefaultFileRenamePolicy());
    	
		String location = "";
		String command = req.getParameter("command");
		String pno = req.getParameter("page");
		HttpSession session = req.getSession();
		
		int page = 1;
		if(null != pno) page = Integer.parseInt(pno);
		
		
		if(command.equals("bbsList")){
		/* S: 페이징 ____ 나중에 소스 분리하기 */
			// 페이징 셋팅
			String paging = "";
			String url = "/BoardServlet?command=bbsList";
			
			int totalCnt = service.resultTotalCnt();	// 총 게시물 수
			int cntPage = 10;	// 페이지 수
			int limit = 10;	// 게시물 수
			int offset = (page-1)*limit; //(페이지번호 - 1) * 로우 출력 사이즈
			int totalPage = totalCnt / limit;	// 총 페이지 수
			int startPage = ((page - 1) / 10) * 10 + 1;	// 시작 페이지
			int endPage = startPage + cntPage - 1;	// 마지막 페이지
			
			// 예외사항 처리
			if (totalCnt % limit > 0) totalPage++; // 나머지가 있는 경우 "총 페이지 수"에 +1
			if (totalPage < page) page = totalPage; // 현재 페이지가 총 페이지 수 보다 클 경우 총 페이지 번호로 치환
			if (endPage > totalPage) endPage = totalPage; // 총 페이지가 마지막 페이지보다 클 경우 마지막 페이지로 치환
			
			// S: tag
			paging += "<ul class='pagination'>";
			if (startPage > 1){
				paging += "<li class='page-item'><a class='page-link' href='"+ url +"&page=1'>처음</a></li>";
			}
			if (page > 1) {
				paging += "<li class='page-item'><a class='page-link' href='"+ url +"&page="+ (page-1) +"'>이전</a></li>";
			}
			
			for (int i=startPage; i<=endPage; i++) {
				paging += "<li class='page-item'>";
				paging += "<a class='page-link' href='"+ url +"&page="+ i +"'>";
				    if (i == page){
				    	paging += "<b>"+ i +"</b>";
				    }else{
				    	paging += i;
				    }
			    paging += "</a>";
			    paging += "</li>";
			}

			if (page < totalPage) {
				paging += "<li class='page-item'><a class='page-link' href='"+ url +"&page="+ (page+1) +"'>다음</a></li>";
			}
			if (endPage < totalPage) {
				paging += "<li class='page-item'><a class='page-link' href='"+ url +"&page="+ totalPage +"'>끝</a></li>";
			}
			paging += "</ul>";
			// E: tag
		/* E: 페이징 */
			
			HashMap<Object, Object> resultData = new HashMap<Object, Object>();
			resultData.put("totalCnt", totalCnt);
			resultData.put("page", page);
			resultData.put("paging", paging);
			
			
			HashMap<Object, Object> params = new HashMap<Object, Object>();
			params.put("page", page);
			params.put("limit", limit);
			params.put("offset", offset);
			
			
			req.setAttribute("boardList", service.getBBSList(params));
			req.setAttribute("code", "OK");
			req.setAttribute("msg", "게시판 조회 성공");
			req.setAttribute("resultData", resultData);
			
			location= "/WEB-INF/views/board/list.jsp";
			
			RequestDispatcher rd = req.getRequestDispatcher(location);
			rd.forward(req, resp);
			
		}else if(command.equals("bbsWriteView")){
			System.out.println("게시글 등록 화면..");
			
			String no = (req.getParameter("boardId") == null) ? "" : req.getParameter("boardId");
			String userId = (req.getParameter("userId") == null) ? "" : req.getParameter("userId");
			
//			String sessionId = (String) session.getAttribute("userId");
//			System.out.println("sessionId::::::::::::::"+sessionId+", userId:::::::::::::::::::"+userId);
			
			if( no != "" && userId != ""){
				int boardId = Integer.parseInt(no);	// 게시판id
				// 게시글 조회
				BoardVO bvo = new BoardVO();
				bvo = service.getBBSView(boardId);
				
				req.setAttribute("page", page);
				req.setAttribute("boardDetail", bvo );
			}
			
			location= "/WEB-INF/views/board/write.jsp";
			RequestDispatcher rd = req.getRequestDispatcher(location);
			rd.forward(req, resp);
			
		}else if(command.equals("bbsInsert")){
			System.out.println("게시글 등록..");
			
			String userId = req.getParameter("userId");
			String title = req.getParameter("title");
			String content = req.getParameter("content");
			
			BoardVO bvo = new BoardVO();
			bvo.setUserId(userId);
			bvo.setTitle(title);
			bvo.setContent(content);
			
			int result = service.setBBSInsert(bvo);
			
			if(result>0){
				// 등록 성공
				location = "/BoardServlet?command=bbsList&page="+page;
				req.setAttribute("code", "OK");
				req.setAttribute("msg", "등록 성공");	
				
			}else{
				// 등록 실패
				location = "/BoardServlet?command=bbsWrite&page="+page;
				req.setAttribute("code", "Fail");
				req.setAttribute("msg","등록 실패!");
			}
			
			RequestDispatcher rd = req.getRequestDispatcher(location);
			rd.forward(req, resp);
		}else if(command.equals("bbsUpdate")){
			System.out.println("게시글 수정..");
			
			String no = req.getParameter("boardId");
			String userId = req.getParameter("userId");
			String title = req.getParameter("title");
			String content = req.getParameter("content");
			
			int boardId = Integer.parseInt(no);	// 게시판id
			page = Integer.parseInt(req.getParameter("page"));	// 페이지
			
			BoardVO bvo = new BoardVO();
			bvo.setId(boardId);
			bvo.setUserId(userId);
			bvo.setTitle(title);
			bvo.setContent(content);
			
			int result = service.setBBSUpdate(bvo);
			
			if(result>0){
				// 등록 성공
				location = "/BoardServlet?command=bbsView&no="+boardId+"&page="+page;
			}else{
				// 등록 실패
				location = "/BoardServlet?command=bbsView&no="+boardId+"&page="+page;
			}
			RequestDispatcher rd = req.getRequestDispatcher(location);
			rd.forward(req, resp);
		}else if(command.equals("bbsDelete")){
			System.out.println("게시글 삭제..");
			
			String boardId = req.getParameter("boardId");
			String userId = req.getParameter("userId");
			
			BoardVO bvo = new BoardVO();
			bvo.setUserId(userId);
			
			
			// JSONObject는 HashMap을 상속
			JSONObject json = new JSONObject(); 
			json.put("code", "OK");
		    
		    // 헤더설정
		    resp.setContentType("application/json");
		    resp.setCharacterEncoding("UTF-8");
		    
		    PrintWriter out = resp.getWriter();
		    out.print(json);
			
		}else if(command.equals("bbsView")){
			String no = req.getParameter("no");
			int boardId = Integer.parseInt(no);
			
			// 게시판 상세페이지 조회
			req.setAttribute("boardDetail", service.getBBSView(boardId));
			// 게시판 댓글
			CommentService cmtService = new CommentService();
			req.setAttribute("commentList", cmtService.getCommentList(boardId) );
			req.setAttribute("page", page);
			req.setAttribute("code", "OK");
			req.setAttribute("msg", "게시판 조회 성공");
			
			location= "/WEB-INF/views/board/view.jsp";
			
			RequestDispatcher rd = req.getRequestDispatcher(location);
			rd.forward(req, resp);
			
		}
//		// 코드 지우기
//		else if(command.equals("session_print")){
//			Enumeration ee  = session.getAttributeNames();
//			int t=0;
//			String s_name="" , s_val="";
//			while(ee.hasMoreElements()){
//				t++;
//				s_name = ee.nextElement().toString();
//				s_val = session.getAttribute(s_name).toString();
//				System.out.println("ss_name [ "+s_name+" ], ss_val[ "+s_val+ " ]");
//			}
//		}
	}

}
