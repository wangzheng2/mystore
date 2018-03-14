package com.cskaoyan.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
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
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.cskaoyan.domain.Product;
import com.cskaoyan.service.ProductService;
import com.cskaoyan.service.impl.ProductServiceImpl;

public class UpdateProductServlet extends HttpServlet {

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

			updateProduct(req,resp);

	 }

	private void updateProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		parameterMap = new HashMap<String, String[]>();
		//需要把用户提交的参数提取出来
 		Product product =new Product();

 		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold( 1024*1024*5);
		ServletContext servletContext = this.getServletConfig().getServletContext();
		File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
		factory.setRepository(repository);
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(1024*1024*10);		
		try {
			List<FileItem> items = upload.parseRequest(req);
			int needDelete=-1;
			File imgToDeleteFile =null;
			for (FileItem fileItem : items) {
				
				    if (fileItem.isFormField()) {     //simple form field.
				        processFormField(fileItem);  // J基本的表单数据 文件除外
				    }else {
				    	
				    	//把上面的原来的图片名字先保留，后面如果有更新图片，就删掉原来的图片
				    	String[] strings = parameterMap.get("imgurl");	
				    	if (strings!=null) {					
							    imgToDeleteFile =new File(getuploadFolder(),strings[0]);		
						}
				    	
				    	
				    	System.out.println("ApacheFileupload.doPost() start processUploadedFile");
				    	needDelete = processUploadedFile(fileItem);// 该item是一个文件数据
					} 
				
			}
			
			//增加上传文件后，把之前服务器上保存的文件删除
			if (needDelete==1) {				  
				  if (imgToDeleteFile!=null&&imgToDeleteFile.exists()) 
				  {
					  boolean delete = imgToDeleteFile.delete();					
					  System.out.println("UpdateProductServlet.updateProduct() imgToDeleteFile" +delete);
				  }else{				  
					  System.out.println("UpdateProductServlet.updateProduct() imgToDeleteFile no need"  );
				  }				
			}

			System.out.println("addProduct.doPost()"+parameterMap);									
			BeanUtils.populate(product, parameterMap);		
			System.out.println("addProduct.doPost() product done!:" +product);
			

			//业务操作
		    boolean ret =	productService.updateProduct(product);
			
			
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

	/**
	 * 0：没有上传图片
	 * 1： 有上传图片
	 * @return 0 
	 */
	private int processUploadedFile(FileItem fileItem) {
		String name = fileItem.getName(); //filename	
 		
		if (name==null||name.isEmpty()) { //用户没有更换			
			//那么 parameterMap里的 imgurl 应该用原来的。
			return 0;
			
		}else{//用户有更换
			
			try {
				InputStream inputStream = fileItem.getInputStream();
				
				String filename=UUID.randomUUID().toString()+name;
				File saveFile = new File(getuploadFolder() ,filename);
				System.out
						.println("UpdateProductServlet.processUploadedFile()"+saveFile.getAbsolutePath());
				
				FileOutputStream fos = new FileOutputStream(saveFile);
				
				byte[] bs =new byte[1024];
				int length=0;
				while ((length =inputStream.read(bs))!=-1) {
	 				
					fos.write(bs, 0, length);
				}
				
				fos.close();
				inputStream.close();

				parameterMap.put("imgurl", new String[]{filename});
				System.out
						.println("UpdateProductServlet.new imgurl():"+filename);

				return 1;
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				throw new RuntimeException("更新图片失败！"+e.getMessage());
			}
			
			
		}
		
	
 	}

	private void processFormField(FileItem fileItem) throws UnsupportedEncodingException {
		 String name = fileItem.getFieldName();
		 String string = fileItem.getString("utf-8");
		 parameterMap.put(name, new String[]{string});

	     System.out.println("Controller.processFormField()" +name +":"+string);		
	}
	 
}
