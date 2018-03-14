package com.cskaoyan.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cskaoyan.domain.ShoppingCart;
import com.cskaoyan.domain.User;
import com.cskaoyan.service.ShoppingCartService;
import com.cskaoyan.service.ShoppingItemService;
 import com.cskaoyan.service.impl.ShoppingCartServiceImpl;
import com.cskaoyan.service.impl.ShoppingItemServiceImpl;

 

public class ShoppingCartServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = request.getParameter("op");
		if("addCart".equals(op)){
			addCart(request,response);
		}else if("delItem".equals(op)){
			delItem(request,response);
		}else if("findCart".equals(op)){
			findCart(request,response);
		}
	}

	private void findCart(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		ShoppingCartService cartService = new ShoppingCartServiceImpl();
		User user = (User) request.getSession().getAttribute("user");
		if(user==null){
			response.sendRedirect(request.getContextPath()+"/user/login.jsp");
			return ;
		}
		ShoppingCart ShoppingCart = cartService.findCart(user.getUid());
		request.getSession().setAttribute("shoppingCar", ShoppingCart);
		response.sendRedirect(request.getContextPath()+"/user/shoppingCart.jsp");
	}

	private void delItem(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		String itemid = request.getParameter("itemid");
		String uid = request.getParameter("uid");
		ShoppingItemService shoppingItemService = new ShoppingItemServiceImpl();
		boolean result = shoppingItemService.deleteShoppingItem(Integer.parseInt(itemid));
		if(result){
			ShoppingCartService cartService = new ShoppingCartServiceImpl();
			ShoppingCart ShoppingCart = cartService.findCart(Integer.parseInt(uid));
			request.getSession().setAttribute("shoppingCar", ShoppingCart);
			request.getRequestDispatcher("/user/shoppingCart.jsp").forward(request, response);
		}
	}

	private void addCart(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		
		String uid = request.getParameter("uid");
		String pid = request.getParameter("pid");
		String snumS = request.getParameter("snum");
		int snum = 1;
		if(snumS == null){
			snum = 1;
		}else{
			snum = Integer.parseInt(snumS);
		}
		ShoppingCartService cartService = new ShoppingCartServiceImpl();
		
		ShoppingCart ShoppingCart = cartService.findCart(Integer.parseInt(uid));
		if(ShoppingCart == null){
			boolean addCart = cartService.addCart(Integer.parseInt(uid));
			if(addCart){
				ShoppingCart = cartService.findCart(Integer.parseInt(uid));
				boolean result = addItemToCart(pid,ShoppingCart.getSid(),snum);
				if(result){
					ShoppingCart = cartService.findCart(Integer.parseInt(uid));
					request.getSession().setAttribute("shoppingCar", ShoppingCart);
					request.getRequestDispatcher("/user/shoppingCart.jsp").forward(request, response);
				}
				return ;
			}else{
				response.sendRedirect(request.getContextPath()+"/MainServlet");
			}
		}
		boolean result = addItemToCart(pid,ShoppingCart.getSid(),snum);
		if(result){
			ShoppingCart = cartService.findCart(Integer.parseInt(uid));
			request.getSession().setAttribute("shoppingCar", ShoppingCart);
			request.getRequestDispatcher("/user/shoppingCart.jsp").forward(request, response);
		}
	}
	
	private boolean addItemToCart(String pid,int sid,int snum){
		ShoppingItemService shoppingItemService = new ShoppingItemServiceImpl();
		boolean result = shoppingItemService.addShoppingItem(pid, sid, snum);
		if(result){
			return true;
		}
		return false;
	}

}
