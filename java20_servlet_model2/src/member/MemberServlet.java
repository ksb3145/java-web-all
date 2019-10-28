package member;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MemberServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("init doget...");
		process(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("init dopost...");
		process(req, resp);
	}
	
	private void process(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String command = req.getParameter("command");
		System.out.println(command);
		
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		
		PrintWriter pw = resp.getWriter();
		pw.println("<!DOCTYPE html>");
		pw.println("<html>");
		pw.println("<head>");
		pw.println("<meta charset=\"utf-8\">");
		pw.println("</head>");
		pw.println("<body>");
		pw.println("환영합니다.");
		pw.println("</body>");
		pw.println("</html>");
	}
}
