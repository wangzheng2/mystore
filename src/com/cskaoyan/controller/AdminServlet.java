package com.cskaoyan.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import com.cskaoyan.domain.Admin;
import com.cskaoyan.service.AdminService;
import com.cskaoyan.service.impl.AdminServiceImpl;
import com.cskaoyan.utils.Page;

 

public class AdminServlet extends HttpServlet {
	AdminService adminService = new AdminServiceImpl();

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");

		String op = request.getParameter("op");
		if ("login".equals(op)) {
			 login(request, response);
		} else if ("addAdmin".equals(op)) {
			addAdmin(request, response);
		} else if ("findAllAdmin".equals(op)) {
			 findAllAdmin(request, response);
		} else if ("updateAdmin".equals(op)) {
			 updateAdmin(request, response);
		} else if ("lgout".equals(op)) {
			 lgout(request, response);
		}
	}

	private void lgout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getSession().removeAttribute("admin");
		response.sendRedirect(request.getContextPath()+"/admin/index.jsp");
	}

	 private void updateAdmin(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String aid = request.getParameter("aid");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String password1 = request.getParameter("password1");

		if (aid == null || aid.trim().length() == 0) {
			response.sendRedirect(request.getContextPath()
					+ "/admin/AdminServlet?op=findAllAdmin&num=1");
			return;
		}
		if (username == null || "".equals(username.trim())) {
			request.setAttribute("msg", "系统用户名不能为空");
			request.getRequestDispatcher("/admin/admin/updateAdmin.jsp")
					.forward(request, response);
			return;
		}
		if (password == null || "".equals(password.trim())) {
			request.setAttribute("msgPwd", "密码不能为空");
			request.getRequestDispatcher("/admin/admin/updateAdmin.jsp")
					.forward(request, response);
			return;
		}
		if (password1 == null || "".equals(password1.trim())) {
			request.setAttribute("msgPwd1", "密码不能为空");
			request.getRequestDispatcher("/admin/admin/updateAdmin.jsp")
					.forward(request, response);
			return;
		}
		if (!password.equals(password1)) {
			request.setAttribute("msgPwd2", "两次输入的密码不一致，请重新输入。");
			request.getRequestDispatcher("/admin/admin/updateAdmin.jsp")
					.forward(request, response);
			return;
		}

		Map<String, String[]> parameterMap = request.getParameterMap();
		
		 Admin admin =new Admin();
		 try {
			BeanUtils.populate(admin, parameterMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.getWriter().print("参数错误！");
		};


		boolean result = adminService.updateAdminMsg(admin);
		if (result) {
			response.sendRedirect(request.getContextPath()
					+ "/admin/AdminServlet?op=findAllAdmin&num=1");
			return;
		} else {
			request.setAttribute("username", username);
			request.setAttribute("password", password);
			request.getRequestDispatcher("/admin/admin/updateAdmin.jsp")
					.forward(request, response);
		}
	}
 
	private void findAllAdmin(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
 		String num = request.getParameter("pageNum");
		Page admins = adminService.findPageRecodes(num);
		request.setAttribute("page", admins);
		request.getRequestDispatcher("/admin/admin/adminList.jsp").forward(
				request, response);
	}

	private void addAdmin(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String password1 = request.getParameter("password1");

		if (username == null || "".equals(username.trim())) {
			request.setAttribute("msgUname", "系统用户名不能为空");
			request.getRequestDispatcher("/admin/admin/addAdmin.jsp").forward(
					request, response);
			return;
		}
		if (password == null || "".equals(password.trim())) {
			request.setAttribute("msgPwd", "密码不能为空");
			request.setAttribute("username", username);
			request.getRequestDispatcher("/admin/admin/addAdmin.jsp").forward(
					request, response);
			return;
		}
		if (password1 == null || "".equals(password1.trim())) {
			request.setAttribute("msgPwd1", "确认密码不能为空");
			request.setAttribute("username", username);
			request.setAttribute("password", password);
			request.getRequestDispatcher("/admin/admin/addAdmin.jsp").forward(
					request, response);
			return;
		}
		if (!password.trim().equals(password1.trim())) {
			request.setAttribute("msgPwd2", "两次输入的密码不一致，请重新输入！");
			request.setAttribute("username", username);
			request.getRequestDispatcher("/admin/admin/addAdmin.jsp").forward(
					request, response);
			return;
		}

		Map<String, String[]> parameterMap = request.getParameterMap();
		
		 Admin admin =new Admin();
		 try {
			BeanUtils.populate(admin, parameterMap);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.getWriter().print("insert admin error!" +e.getMessage());

			return;
		};
		
		
 		boolean result = adminService.regist(admin);
 		
		if (result) {
			response.getWriter().print("insert admin ok!");
			/*response.sendRedirect(request.getContextPath()
					+ "/admin/AdminServlet?op=findAllAdmin&pageNum=1");*/
			return;
		} else {
			request.setAttribute("username", username);
			request.setAttribute("password", password);
			request.setAttribute("password1", password1);
			request.getRequestDispatcher("/admin/admin/addAdmin.jsp").forward(
					request, response);
			return;
		}
	}

	private void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");

 		Admin admin = adminService.login(username, password);

		if (admin != null) {
			HttpSession session = request.getSession();
			session.setAttribute("admin", admin);
			// 跳转到主页
			response.sendRedirect(request.getContextPath() + "/admin/main.jsp");
		} else {
			// 跳转到登录页面
			request.setAttribute("msg", "用户名或密码错误！");
			response.sendRedirect(request.getContextPath() + "/admin/index.jsp");
		}
	}

}
