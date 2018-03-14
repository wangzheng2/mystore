package com.cskaoyan.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cskaoyan.domain.Product;
import com.cskaoyan.service.ProductService;
import com.cskaoyan.service.impl.ProductServiceImpl;
 

public class MainPageServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		doPost(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
		ProductService productService = new ProductServiceImpl();
		List<Product> productTop = productService.selectTop(4);
		request.getSession().setAttribute("productTop", productTop);
		System.out.println("MainPageServlet.doPost()"+productTop);
		
		List<Product> hotProducts = productService.selectTop(6, 5);
		request.getSession().setAttribute("hotProducts", hotProducts);
		System.out.println("MainPageServlet.doPost()"+hotProducts);

		request.getRequestDispatcher("/main.jsp").forward(request, response);;
		
	}

}
