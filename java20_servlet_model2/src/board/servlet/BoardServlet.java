package board.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.oreilly.servlet.MultipartRequest;

import board.BoardVO;
import board.service.BoardService;
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
		
		System.out.println("command : "+command);
		
		if(command.equals("bbsList")){
			System.out.println("게시판 리스트...");
			
			location= "/WEB-INF/views/board/list.jsp";
			
			//System.out.println(service.getBoardList());
			req.setAttribute("boardList", service.getBBSList());
			req.setAttribute("code", "OK");
			req.setAttribute("msg", "게시판 조회 성공");
			
			RequestDispatcher rd = req.getRequestDispatcher(location);
			rd.forward(req, resp);
			
		}else if(command.equals("bbsWrite")){
			System.out.println("게시판 글쓰기..");
			
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
			
			BoardVO resultVO = service.setBBSInsert(bvo);
			
			if(null != resultVO){
				// 등록 성공
				location = "/BoardServlet?command=bbsList";
				
				req.setAttribute("code", "OK");
				req.setAttribute("msg", "등록 성공");	
				
			}else{
				// 등록 실패
				location = "/BoardServlet?command=bbsWrite";
				
				req.setAttribute("code", "Fail");
				req.setAttribute("msg","등록 실패!");
			}
			
			RequestDispatcher rd = req.getRequestDispatcher(location);
			rd.forward(req, resp);
			
		}else if(command.equals("bbsUpdate")){
			System.out.println("게시글 수정..");
			
		}else if(command.equals("bbsView")){
			System.out.println("게시판 상세보기..");
			
			location= "/WEB-INF/views/board/view.jsp";
			
			String no = req.getParameter("no");
			int boardId = Integer.parseInt(no);	// 게시판id
			
			// 게시판 상세페이지 조회
			req.setAttribute("boardDetail", service.getBBSView(boardId));
			req.setAttribute("code", "OK");
			req.setAttribute("msg", "게시판 조회 성공");
			
			
			RequestDispatcher rd = req.getRequestDispatcher(location);
			rd.forward(req, resp);
		}
	}
	
	


}
