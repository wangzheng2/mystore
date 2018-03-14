package com.cskaoyan.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;

import com.cskaoyan.controller.bean.UserFormBean;
import com.cskaoyan.domain.User;
import com.cskaoyan.service.UserService;
import com.cskaoyan.service.impl.UserServiceImpl;
import com.cskaoyan.utils.Page;

 

public class UserServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String op = request.getParameter("op");
		String uid = request.getParameter("uid");
		
		if ("regist".equals(op)) {
			 regist(request, response);
		}else if("login".equals(op)){
			 login(request,response);
		}else if("lgout".equals(op)){
			lgout(request,response);
		}else if("adduser".equals(op)){
			adduser(request,response);
		}
		else if("findAllUser".equals(op)){
			findAllUser(request,response);
		}
		else if("updateUser".equals(op)&&uid != null) {
			updatePersonalData(request, response);
		}else if("activeUser".equals(op)) {
			activeUser(request, response);
		}
	}
	
	
	private void activeUser(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
  
			
			String activecode = request.getParameter("activecode");
			
			if (activecode!=null) {
				UserService userService = new UserServiceImpl();

			  boolean exist=	userService.checkActiveCode(activecode);
				
			  if (exist) {
					
					response.getWriter().write("激活用户成功！请登录！谢谢！");
					response.setHeader("Refresh", "2;URL="+request.getContextPath()+"/user/login.jsp");
			
				}else {
					
					response.getWriter().write("激活失败，请重新注册！");
					response.setHeader("Refresh", "2;URL="+request.getContextPath()+"/user/regist.jsp");
							 
				}
				
			}
			
	 
 		
	}

	private void findAllUser(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		UserService userService = new UserServiceImpl();
		String num = request.getParameter("num");
		Page users = userService.findPageRecodes(num);
		request.setAttribute("page", users);
		System.out.println("UserServlet.findAllUser()"+users);
		request.getRequestDispatcher("/admin/user/userList.jsp").forward(
				request, response);
	}

	private void adduser(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {	
		UserFormBean userFormBean = new UserFormBean();		
		try {
			BeanUtils.populate(userFormBean, request.getParameterMap());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			response.getWriter().print("提交参数有误！请重试！");
			return;
		}
		
		
		boolean validate = userFormBean.validate();
		if(!validate){
			System.out.println("UserServlet.adduser() validate fail!");
			request.setAttribute("msg", userFormBean);
			request.getRequestDispatcher("/admin/user/addUser.jsp").forward(request, response);
			return ;
		}
		
		User user = new User();
		try {
			ConvertUtils.register(new DateLocaleConverter(), Date.class);
			BeanUtils.copyProperties(user, userFormBean);
			UserService userService = new UserServiceImpl();
			user.setUpdatetime(new Date());
			boolean regist = userService.regist(user);
			if(regist){
				response.getWriter().write("添加用户成功！");
				response.setHeader("Refresh", "1;URL="+request.getContextPath()+"/admin/user/userList.jsp");
				return;
			}else{
				response.getWriter().write("添加用户失败！");
				response.setHeader("Refresh", "2;URL="+request.getContextPath()+"/admin/user/addUser.jsp");
			}
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	private void updatePersonalData(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UserFormBean userFormBean =new UserFormBean();
		try {
			BeanUtils.populate(userFormBean, request.getParameterMap());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			response.getWriter().print("提交参数有误！请重试！");
			response.setHeader("Refresh", "1;URL="+request.getContextPath()+"/user/regist.jsp");
			return;
		}
		
		User user = new User();
		try {
			ConvertUtils.register(new DateLocaleConverter(), Date.class);
			BeanUtils.copyProperties(user, userFormBean);
			user.setUpdatetime(new Date());
			
			UserService userService = new UserServiceImpl();
			
			boolean update = userService.updateUserMsg(user);
			if (update) {
				
				//更新session
				User usernew = userService.login(user.getUsername(), user.getPassword());
				request.getSession().setAttribute("user", usernew);
				
				response.getWriter().write("更新成功！即将跳转到首页！");
				response.setHeader("Refresh", "1;URL="+request.getContextPath()+"/index.jsp");
				return;
			} else {
 				request.setAttribute("user", user);
				request.getRequestDispatcher("/user/personal.jsp").forward(request, response);
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	private void lgout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		request.getSession().removeAttribute("user");
		response.sendRedirect(request.getContextPath()+"/index.jsp");
	}

	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

		
		String verifycode = request.getParameter("verifycode");
		
		//测试时间，不测试验证码
		/*if (verifycode==null||verifycode.isEmpty()) {			
			
			request.setAttribute("msg", "验证码为空，请重试！");
			request.getRequestDispatcher("/user/login.jsp").forward(request, response);
			return ;
			
		}else  if (!verifycode.equals(request.getSession().getAttribute("checkcode_session"))) {
			
			request.setAttribute("msg", "验证码不对，请重试！");
			request.getRequestDispatcher("/user/login.jsp").forward(request, response);
			return ;
		}*/
		
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		if(username == null || password == null){
			request.setAttribute("msg", "用户名或密码为空，请重试！");
			request.getRequestDispatcher("/user/login.jsp").forward(request, response);
			return ;
		}
		
		UserService userService = new UserServiceImpl();
		User user = userService.login(username, password);
		 
		if(user != null&&user.getState()==1){
			request.getSession().setAttribute("user", user);
			response.sendRedirect(request.getContextPath()+"/index.jsp");
			System.out.println("UserServlet.login() OK" +user);
			return ;
		}else{
			
			if (user==null) {
				request.setAttribute("username", username);
				request.setAttribute("password", password);
				request.setAttribute("msg", "用户名或密码错误，请重试！");
			}else{				
				request.setAttribute("msg", "用户名还未激活！");
			}
			

			request.getRequestDispatcher("/user/login.jsp").forward(request, response);
			return ;
		}
		
	}

	private void regist(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UserFormBean userFormBean = new UserFormBean(); 	
		try {
			BeanUtils.populate(userFormBean, request.getParameterMap());
		} catch (Exception e1) {
			e1.printStackTrace();
			response.getWriter().print("提交参数有误！请重试！");
			response.setHeader("Refresh", "1;URL="+request.getContextPath()+"/user/regist.jsp");
			return;
		}
		
		boolean validate = userFormBean.validate();
		if(!validate){
			request.setAttribute("msg", userFormBean);
			request.getRequestDispatcher("/user/regist.jsp").forward(request, response);
			return ;
		}
		
		User user = new User();
		try {
			ConvertUtils.register(new DateLocaleConverter(), Date.class);
			//把一个javabean里的同名字段赋值到另一个javabean（效率不高）
			BeanUtils.copyProperties(user, userFormBean);
			
			
			UserService userService = new UserServiceImpl();
			user.setUpdatetime(new Date());
			boolean regist = userService.regist(user);
			if(regist){
				response.getWriter().write("注册成功,请先登录邮箱激活用户，然后再登录！5秒后跳到登录页");
				response.setHeader("Refresh", "1;URL="+request.getContextPath()+"/user/login.jsp");
				return;
			}else{
				response.getWriter().write("注册失败！请重试！！");
				response.setHeader("Refresh", "1;URL="+request.getContextPath()+"/user/login.jsp");
			}
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}
}
