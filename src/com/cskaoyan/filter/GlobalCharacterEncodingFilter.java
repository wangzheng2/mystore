package com.cskaoyan.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class GlobalCharacterEncodingFilter implements Filter {

	String encoding ;
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
	    encoding = filterConfig.getInitParameter("encoding");
		if (encoding == null) {
			encoding = "UTF-8";
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub

		//解决post提交的请求参数乱码
		request.setCharacterEncoding(encoding);
				
		//解决响应中出现中文浏览器的乱码
		response.setContentType("text/html;charset="+encoding+"");
		
		//放行
		chain.doFilter(request, response);	

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
