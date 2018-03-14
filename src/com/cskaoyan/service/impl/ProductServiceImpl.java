package com.cskaoyan.service.impl;

import java.util.List;

import com.cskaoyan.dao.ProductDao;
import com.cskaoyan.dao.impl.ProductDaoImpl;
import com.cskaoyan.domain.Product;
import com.cskaoyan.service.ProductService;
import com.cskaoyan.utils.Page;

public class ProductServiceImpl implements ProductService {

	ProductDao dao = new ProductDaoImpl();
	private int countPerPage=3;
	
	@Override
	public boolean addProduct(Product p) {
		// TODO Auto-generated method stub
		return dao.addProductToDB(p);
	}

	@Override
	public List<Product> findAllProduct() {
		// TODO Auto-generated method stub
		return dao.findAllProduct();
	}

	@Override
	public int deleteProduct(String pid) {
		// TODO Auto-generated method stub
		return dao.deleteProduct(pid);
	}

	public Product findProductByPid(String pid) {
		Product product = dao.findProductByPid(pid);
		return product;
	}

	@Override
	public boolean updateProduct(Product product) {
		// TODO Auto-generated method stub
		return dao.updateProductById(product);
	}

	@Override
	public Page findPageCategory(int pagenumInt) {
		// TODO Auto-generated method stub
		 
			int totalNumber = dao.findTotalNumber();
			
			Page page=new Page(pagenumInt,totalNumber,countPerPage);

		
		    List list =	dao.queryPartCategory(countPerPage, (pagenumInt-1)*countPerPage);
			
		    page.setItemList(list);
			
			return  page;
	 
	}

	@Override
	public void deleteProducts(String[] pids) {
		// TODO Auto-generated method stub
		
		dao.deleteProductsByIds(  pids) ;
	}

	@Override
	public Page multiConditionSearch(String pid, String cid, String pname,
			String minprice, String maxprice, String pageNum) {
		// TODO Auto-generated method stub
		int pageNumInt = Integer.parseInt(pageNum);
		
	    int count =	dao.getCountByMultiCondition(pid,cid,pname,minprice,maxprice);
		
		Page page = new Page(pageNumInt, count, countPerPage);
		
		List<Object> productList =dao.getProductsByMultiCondition(pid,cid,pname,minprice,maxprice, 
				countPerPage,(pageNumInt-1)*countPerPage);
		
		page.setItemList(productList);
		
		
		return page;
	}
	
	
	public List<Product> selectTop(int count) {
		List<Product> products = dao.findTop(count);
		return products;
	}

	public List<Product> selectTop(int count, int start) {
		List<Product> products = dao.findTop(count,start);
		return products;
	}

	@Override
	public List<Product> findProductsByCid(int cid) {
		// TODO Auto-generated method stub
		return dao.findProductByCid(cid);
	}

	@Override
	public List<Product> findProductsByKeyword(String keyword) {
		// TODO Auto-generated method stub
		return dao.findProductsByKeyword(keyword);
	}
	
	
}
