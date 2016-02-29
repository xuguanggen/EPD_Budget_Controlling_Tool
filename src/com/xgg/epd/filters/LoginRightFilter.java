package com.xgg.epd.filters;
import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.FilterChain;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
public class LoginRightFilter implements Filter{

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
	    HttpServletResponse res = (HttpServletResponse) response;	    
	    HttpSession session = req.getSession(true);
	    //从session里取的用户名信息
	    String username = (String) session.getAttribute("EmpName");	    
	    //判断如果没有取到用户信息,就跳转到登陆页面
	    if (username == null || "".equals(username)) {
	      //跳转到登陆页面
	      res.sendRedirect("http://"+req.getHeader("Host")+"/index.jsp");
	    }
	    else {
	      //已经登陆,继续此次请求
	      chain.doFilter(request,response);
	    }
	 }

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
