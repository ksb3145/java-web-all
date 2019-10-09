package board.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet("/BoardServlet")
public class BoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BoardServlet() {
    	// 로그인 세션체크
    	
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
		
		if(command.equals("bbsList")){
			System.out.println("게시판 리스트...");
			
			location= "/WEB-INF/views/board/list.jsp";
			
			RequestDispatcher rd = req.getRequestDispatcher(location);
			rd.forward(req, resp);
		} else if (command.equals("bbsWrite")){
			System.out.println("게시판 글쓰기..");
			
			location= "/WEB-INF/views/board/write.jsp";
			
			RequestDispatcher rd = req.getRequestDispatcher(location);
			rd.forward(req, resp);
		} else if (command.equals("bbsView")){
			System.out.println("게시판 상세보기..");
			
			location= "/WEB-INF/views/board/view.jsp";
			
			RequestDispatcher rd = req.getRequestDispatcher(location);
			rd.forward(req, resp);
		}
	}

}
