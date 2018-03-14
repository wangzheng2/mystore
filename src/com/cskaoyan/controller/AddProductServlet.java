package com.cskaoyan.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

 import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.cskaoyan.domain.Product;
import com.cskaoyan.service.ProductService;
import com.cskaoyan.service.impl.ProductServiceImpl;
 
public class AddProductServlet extends HttpServlet{

	ProductService productService = new ProductServiceImpl();
	 
	private HashMap<String, String[]> parameterMap;
	private String uploadpathString ="/productimg" ;
	 

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		    doPost(req, resp);		 
  	}
	 
	 @Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

			addProduct(req,resp);

	 }

	private void addProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		parameterMap = new HashMap<String, String[]>();
		//需要把用户提交的参数提取出来
 		Product product =new Product();

 		
		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();
		
		// Set factory constraints
		//Sets the size threshold beyond which files are written directly to disk.
		//The default threshold above which uploads will be stored on disk.is 10M
		factory.setSizeThreshold( 1024*1024*5);
		

		// Configure a repository (to ensure a secure temp location is used)
		ServletContext servletContext = this.getServletConfig().getServletContext();
		File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
		
		//把临时文件夹给到Factory使用
		factory.setRepository(repository);

		// Create a new file upload handler  使用工厂获取一个文件上传处理器
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		// Set overall request size constraint
		// Sets the maximum allowed size of a complete request, as opposed to setFileSizeMax(long).
		upload.setSizeMax(1024*1024*10);
		
		// Parse the request 解析请求
		try {
			List<FileItem> items = upload.parseRequest(req);
			
			for (FileItem fileItem : items) {
				
				    if (fileItem.isFormField()) {     //simple form field.
				        processFormField(fileItem);  // J基本的表单数据 文件除外
				    } else {
				    	System.out.println("ApacheFileupload.doPost() processUploadedFile");
				        processUploadedFile(fileItem);// 该item是一个文件数据
				    }
				
			}
			

			System.out.println("addProduct.doPost()"+parameterMap);
			
			BeanUtils.populate(product, parameterMap);
			
			System.out.println("addProduct.doPost() product done!:" +product);
			

			//业务操作
			productService.addProduct(product);
			
			
			//注册后转到登录页面让用户去登录
			req.getRequestDispatcher("/admin/ProductServlet?op=findAllProduct").forward(req, resp);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
			resp.getWriter().println("添加失败，请重试！ <br>  message:"+e.getMessage());
		}
		
	}
	
	public String getuploadFolder(){
			String folderpathString =getServletContext().getRealPath(uploadpathString);
			File folder = new File(folderpathString);
			if (!folder.exists()) {
				folder.mkdirs();
			}
		return folderpathString;
	}

	private void processUploadedFile(FileItem fileItem) {
		String name = fileItem.getName();
		
		if (name==null||name .isEmpty()) {
			return;  //没有提交文件
		}
		
		try {
			
			
			
			InputStream inputStream = fileItem.getInputStream();
			
			String filename=UUID.randomUUID().toString()+name;
			File saveFile = new File(getuploadFolder() ,filename);
			
			FileOutputStream fos = new FileOutputStream(saveFile);
			
			byte[] bs =new byte[1024];
			int length=0;
			while ((length =inputStream.read(bs))!=-1) {
 				
				fos.write(bs, 0, length);
			}
			
			fos.close();
			inputStream.close();

			parameterMap.put("imgurl", new String[]{filename});

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}

	private void processFormField(FileItem fileItem) throws UnsupportedEncodingException {
		 String name = fileItem.getFieldName();
		 String string = fileItem.getString("utf-8");
		 parameterMap.put(name, new String[]{string});

	     System.out.println("Controller.processFormField()" +name +":"+string);		
	}
	 
}
