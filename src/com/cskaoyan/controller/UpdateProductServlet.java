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
		//��Ҫ���û��ύ�Ĳ�����ȡ����
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
				        processFormField(fileItem);  // J�����ı����� �ļ�����
				    }else {
				    	
				    	//�������ԭ����ͼƬ�����ȱ�������������и���ͼƬ����ɾ��ԭ����ͼƬ
				    	String[] strings = parameterMap.get("imgurl");	
				    	if (strings!=null) {					
							    imgToDeleteFile =new File(getuploadFolder(),strings[0]);		
						}
				    	
				    	
				    	System.out.println("ApacheFileupload.doPost() start processUploadedFile");
				    	needDelete = processUploadedFile(fileItem);// ��item��һ���ļ�����
					} 
				
			}
			
			//�����ϴ��ļ��󣬰�֮ǰ�������ϱ�����ļ�ɾ��
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
			

			//ҵ�����
		    boolean ret =	productService.updateProduct(product);
			
			
			//ע���ת����¼ҳ�����û�ȥ��¼
			req.getRequestDispatcher("/admin/ProductServlet?op=findAllProduct").forward(req, resp);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
			resp.getWriter().println("���ʧ�ܣ������ԣ� <br>  message:"+e.getMessage());
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
	 * 0��û���ϴ�ͼƬ
	 * 1�� ���ϴ�ͼƬ
	 * @return 0 
	 */
	private int processUploadedFile(FileItem fileItem) {
		String name = fileItem.getName(); //filename	
 		
		if (name==null||name.isEmpty()) { //�û�û�и���			
			//��ô parameterMap��� imgurl Ӧ����ԭ���ġ�
			return 0;
			
		}else{//�û��и���
			
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
				
				throw new RuntimeException("����ͼƬʧ�ܣ�"+e.getMessage());
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
