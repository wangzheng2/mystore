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

		//���post�ύ�������������
		request.setCharacterEncoding(encoding);
				
		//�����Ӧ�г������������������
		response.setContentType("text/html;charset="+encoding+"");
		
		//����
		chain.doFilter(request, response);	

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
