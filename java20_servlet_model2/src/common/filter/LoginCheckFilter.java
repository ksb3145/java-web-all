package common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginCheckFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) req;
		HttpSession session = httpRequest.getSession(false);
		
		httpRequest.setCharacterEncoding("UTF-8");
		boolean login = false;
		
		if(session != null){
			if(session.getAttribute("sessionVO") != null){
				login = true;	// 세션변수가 null이 아님.
			}
		}
		
		if(login){
			System.out.println("[[로그인 필터]] :: 요청페이지 이동");
			chain.doFilter(req, resp);
		}else{
			System.out.println("[[로그인 필터]] :: 로그인 만료, 로그인 폼으로 이동.");
			
			RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/member/login.jsp");
			rd.forward(req, resp);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
