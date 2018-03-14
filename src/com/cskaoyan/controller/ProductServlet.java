package com.cskaoyan.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cskaoyan.domain.Product;
import com.cskaoyan.service.CategoryService;
import com.cskaoyan.service.ProductService;
import com.cskaoyan.service.impl.CategoryServiceImpl;
import com.cskaoyan.service.impl.ProductServiceImpl;
import com.cskaoyan.utils.Page;

public class ProductServlet extends HttpServlet {

	ProductService productService =new ProductServiceImpl();
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		 doPost(request, response);
		
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = request.getParameter("op");
		
		if (op!=null) {
			
			switch (op) {
			case "findAllProduct":
				findAllProduct(request,response);
				break;

			case "deleteOneProduct":
				deleteOneProduct(request,response);
				break;
			case "editOneProduct":
				editOneProduct(request,response);
				break;
				
			case "deleteMulti":
				deleteMulti(request,response);
				break;	
			case "multiConditionSearch":
				multiConditionSearch(request,response);
				break;		
			case "findProductsbyCid":
				findProductsbyCid(request,response);
				break;			
			case "findProductByPid":
				findProductByPid(request,response);
				break;	
			case "searchProductsByKeyword":
				searchProductsByKeyword(request,response);
				break;	
 			default:
				break;
			}
		}
	}

	private void searchProductsByKeyword(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String keyword = request.getParameter("keyword");
		
       List<Product>	 products =	productService.findProductsByKeyword(keyword);
		
		
		request.setAttribute("products", products);
		request.setAttribute("keyword",keyword);
		request.getRequestDispatcher("/products.jsp").forward(request, response);
 		
	}

	private void findProductByPid(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String pid  = request.getParameter("pid");
  		 Product  product  = productService.findProductByPid(pid);
		request.setAttribute("product", product);
		request.getRequestDispatcher("/productdetail.jsp").forward(request, response);
	}

	private void findProductsbyCid(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
 		String cidStr = request.getParameter("cid");
		int cid = Integer.parseInt(cidStr.trim());
 		List<Product> products = productService.findProductsByCid(cid);
		request.setAttribute("products", products);
		request.getRequestDispatcher("/products.jsp").forward(request, response);
			
	}

	private void multiConditionSearch(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		
		String pid = request.getParameter("pid");		//
		String cid = request.getParameter("cid");
		String pname = request.getParameter("pname");
		String minprice = request.getParameter("minprice");
		String maxprice = request.getParameter("maxprice");
		String pageNum = request.getParameter("pageNum");


        
    	try {
            Page	 page=	productService.multiConditionSearch(pid,cid,pname,minprice,maxprice,pageNum);
				 
            request.setAttribute("page", page);
            request.setAttribute("pid", pid);
            request.setAttribute("cid", cid);
            request.setAttribute("pname", pname);
            request.setAttribute("minprice", minprice);
            request.setAttribute("maxprice", maxprice);

			request.getRequestDispatcher("/admin/product/searchproductList.jsp").forward(request, response);

 
 		} catch (Exception e) {
			
			  response.getWriter().println("多条件查询失败，请重新填写条件!<br>"+e.getMessage());
		  
			  response.setHeader("refresh", "3;url="+request.getServletContext().getContextPath()+"/admin/product/searchProduct.jsp");
		}

	}

	private void deleteMulti(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
 
		String[] pids = request.getParameterValues("pid");
		System.out.println("ProductServlet.deleteMulti()"+Arrays.toString(pids));
		if(pids == null || pids.length == 0){
			response.sendRedirect(request.getContextPath()+"/admin/ProductServlet?op=findAllProduct&pageNum=1");
			return ;
		}
 		
		try {
			  productService.deleteProducts(pids);
				 
 			  response.getWriter().println("批量删除成功!<br>");

			  response.setHeader("refresh", "1;url="+request.getServletContext().getContextPath()+"/admin/ProductServlet?op=findAllProduct&pageNum=1");
		} catch (Exception e) {
			
			  response.getWriter().println("批量删除失败!<br>"+e.getMessage());
 		  
			  response.setHeader("refresh", "3;url="+request.getServletContext().getContextPath()+"/admin/ProductServlet?op=findAllProduct&pageNum=1");
		}
	}

	private void editOneProduct(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
 		
		
		String pid = request.getParameter("pid");
		if (pid!=null&&!pid.isEmpty()) {
			
			Product product = productService.findProductByPid(pid);			
			request.setAttribute("product", product);
			request.getRequestDispatcher("/admin/product/updateProduct.jsp").forward(request, response);
		}else {
			response.getWriter().write("参数pid错误<br/>");
		}
		
	}

	private void deleteOneProduct(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String pid = request.getParameter("pid");
		
		if (pid!=null) {
			
			int deleteProduct = productService.deleteProduct(pid);
			
			if (deleteProduct==1) {
				
				response.getWriter().write("商品删除成功<br/>");
				response.setHeader("Refresh", "1;URL="+request.getContextPath()+"/admin/ProductServlet?op=findAllProduct");
  
				 	
			}else {
				response.getWriter().println("删除失败，请重试！ <br>    " );

			}
		}
		
 		
	}

	private void findAllProduct(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException   {
 		
		/*try {
			List<Product> productlist = productService.findAllProduct();
			
			request.setAttribute("productlist", productlist);
	
			request.getRequestDispatcher("/admin/product/productList.jsp").forward(request, response);
		} catch ( Exception e) {
			 
			e.printStackTrace();
			response.getWriter().println("显示失败，请重试！ <br>  message:"+e.getMessage());

		}*/
		
	String pageNum = request.getParameter("pageNum");
		
		if (pageNum!=null&&!pageNum.isEmpty()) {
			
			int pagenumInt = Integer.parseInt(pageNum);
			
			if (pagenumInt==0) {
				
				response.getWriter().println("pageNum 参数错误！");
				
				return ;
			}
			Page page = productService.findPageCategory(pagenumInt);
			
			System.out.println("productServlet.findAllCategory()"+page);
			request.setAttribute("page", page);
			
			request.getRequestDispatcher("/admin/product/productList.jsp").forward(request, response);

		}
		
		
		
	}

}
