package com.cskaoyan.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cskaoyan.domain.Category;
import com.cskaoyan.service.CategoryService;
import com.cskaoyan.service.impl.CategoryServiceImpl;

public class InitLoadCategoryServlet extends HttpServlet {
	
	
	/**
	 * 
	 * add category list to cache which is frequently  used;
	 * so we can get from mem next time
	 */
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("InitLoadCategoryServlet.init()");		
		CategoryService service = new CategoryServiceImpl();
		List<Category> categorylist = service.findallCategory();		
		getServletContext().setAttribute("categorylist", categorylist);	
		 
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
