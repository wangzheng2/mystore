package com.cskaoyan.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.cskaoyan.domain.Category;
import com.cskaoyan.service.CategoryService;
import com.cskaoyan.service.impl.CategoryServiceImpl;
import com.cskaoyan.utils.Page;

public class CategoryServlet extends HttpServlet {

	CategoryService categoryService = new CategoryServiceImpl();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		    doPost(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		  String op = request.getParameter("op");
		  
		  if (op!=null) {
			  
			  switch (op) {
			case "addCategory":
				addCategory(request,response);
				break;
			case "findAllCategory":
				findAllCategory(request,response);
				break;
				
			case "updateCategory":
				updateCategory(request,response);
				break;
			case "deleteCategory":
				deleteCategory(request,response);
				break;
			case "findCategoryToAddProduct":
				findCategoryToAddProduct(request,response);
				break;

			case "deleteMulti":
				deleteMulti(request,response);
				break;
				
				 
 			default:
				break;
			}

			  
		  }else {
			
			 response.getWriter().println("error！ op Is null！");
		  }
		
	}

	private void deleteMulti(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
 
		String[] cids = request.getParameterValues("cid");
		System.out.println("CategoryServlet.deleteMulti()"+Arrays.toString(cids));
		if(cids == null || cids.length == 0){
			response.sendRedirect(request.getContextPath()+"/admin/CategoryServlet?op=findAllCategory&pageNum=1");
			return ;
		}
 		
		try {
			  categoryService.deleteCategories(cids);
				 
			//	response.sendRedirect(request.getContextPath()+"/admin/CategoryServlet?op=findAllCategory&num=1");
			  response.getWriter().println("批量删除成功!<br>");

			  response.setHeader("refresh", "1;url="+request.getServletContext().getContextPath()+"/admin/CategoryServlet?op=findAllCategory&pageNum=1");
		} catch (Exception e) {
			
			  response.getWriter().println("批量删除失败!<br>"+e.getMessage());
 		  
			  response.setHeader("refresh", "3;url="+request.getServletContext().getContextPath()+"/admin/CategoryServlet?op=findAllCategory&pageNum=1");
		}
 	  }

	private void findAllCategory(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String pageNum = request.getParameter("pageNum");
		
		if (pageNum!=null&&!pageNum.isEmpty()) {
			
			int pagenumInt = Integer.parseInt(pageNum);
			
			if (pagenumInt==0) {
				
				response.getWriter().println("pageNum 参数错误！");
				
				return ;
			}
			Page page = categoryService.findPageCategory(pagenumInt);
			
			System.out.println("CategoryServlet.findAllCategory()"+page);
			request.setAttribute("page", page);
			
			request.getRequestDispatcher("/admin/category/categoryList.jsp").forward(request, response);

		}
		
		
		
	}

	private void deleteCategory(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
 
		    String cid_string = request.getParameter("cid");
		    
		    try {
 			     int cid = Integer.parseInt(cid_string);
 			   
			    boolean ret =    categoryService.deleteCategory(cid);
			    System.out.println("CategoryServlet.updateCategory()"+ret);
			    if (ret) {					
			    	request.getRequestDispatcher("/admin/CategoryServlet?op=findAllCategory").forward(request, response);;
				   
			    	//delete ok update cache
					updateCategoryListInCache();
			    
			    }
			   
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				response.getWriter().print(e.getMessage());
			} 
		    
	}

	private void updateCategory(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
 
		   String cid = request.getParameter("cid");
		   String cname = request.getParameter("cname");
		   
		   try {
			    Category category = new Category();
			    category.setCid(Integer.parseInt(cid));
			    category.setCname(cname);
			   
			    boolean ret =    categoryService.updateCategory(category);
			    System.out.println("CategoryServlet.updateCategory()"+ret);
			    if (ret) {					
			    	request.getRequestDispatcher("/admin/CategoryServlet?op=findAllCategory").forward(request, response);;
				
			    	//update ok update cache
					updateCategoryListInCache();
			    }
			   
			} catch ( Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				response.getWriter().print(e.getMessage());
			}
		
	}
	
	
	
	private void findCategoryToAddProduct(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
 		
		List<Category> categories= null;
		  
	  Object attribute = getServletContext().getAttribute("categorielist");
	  if (attribute!=null) {
		  categories =(List<Category>)attribute;
	  }else{ 
		  categories =categoryService.findallCategory();
	  }
		  
	  
	  if (categories!=null) {
		  
		 request.setAttribute("categorielist",   categories );		 
		 ///admin/Category
		 request.getRequestDispatcher("/admin/product/addProduct.jsp").forward(request, response);
	  }else {
		
		  response.getWriter().print("category is null!");
	  }
		
	}


	private void findAllCategory2(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
 		
		
	  List<Category> categories= null;
	  
	  Object attribute = getServletContext().getAttribute("categorielist");
	  if (attribute!=null) {
		  categories =(List<Category>)attribute;
	  }else{ 
		  categories =categoryService.findallCategory();
	  }
	  
	  
	  if (categories!=null) {
		  
		/* request.setAttribute("totalcount", o); 
		 request.setAttribute("currentPageNum", 1);
		 request.setAttribute("totalcount", o); 
		 request.setAttribute("currentPageNum", 1);*/
		 
		 
		 request.setAttribute("categorielist",   categories );		 
		 ///admin/Category
		 request.getRequestDispatcher("/admin/category/categoryList.jsp").forward(request, response);
	  }else {
		
		  response.getWriter().print("category is null!");
	  }
		
	}

	private void addCategory(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		
		//String cname = request.getParameter("cname");
		
		Map<String, String[]> parameterMap = request.getParameterMap();
		
		System.out.println("CategoryServlet.addCategory()"+parameterMap);
		
		try {
			Category category = new Category();
			
			BeanUtils.populate(category, parameterMap);
			
			System.out.println("CategoryServlet.addCategory()"+category);
			
			boolean addCategory = categoryService.addCategory(category);
			
			if (addCategory) {
				
				response.getWriter().println("insert ok!");
				response.setHeader("refrsh", "1;url="+request.getServletContext().getContextPath()+"/admin/CategoryServlet?op=findAllCategory");
				//add ok update cache
				updateCategoryListInCache();
			} 
			
			
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
	}

	private void  updateCategoryListInCache() {
		
		List<Category> categorylist = categoryService.findallCategory();		
		getServletContext().setAttribute("categorylist", categorylist);	
	}
}
