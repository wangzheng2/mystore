package com.cskaoyan.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cskaoyan.domain.Admin;

public class AdminFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
 
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		HttpSession session = request.getSession(false);
		if (session!=null&& session.getAttribute("admin")!=null ) { 
				//已经登录过了，放行
				chain.doFilter(request, response);			 
		}else{
			
			//index.jsp放行
			String requestURI = request.getRequestURI();
			if (requestURI.endsWith("index.jsp")
					||requestURI.endsWith(".css")
					||requestURI.endsWith(".js")
					||requestURI.endsWith(".gif")) {
				chain.doFilter(request, response);
			} 
			///admin/AdminServlet的登录操作放行
			else if (requestURI.endsWith("AdminServlet")&&request.getParameter("op").equals("login")) {				
				chain.doFilter(request, response);
			}
			//其他情况不允许访问
			else{				
				response.sendRedirect( request.getContextPath() + "/admin/index.jsp");
			}
			
			
			
			
		}
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
