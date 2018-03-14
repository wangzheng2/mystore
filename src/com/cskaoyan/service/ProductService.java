package com.cskaoyan.service;

import java.util.List;

import com.cskaoyan.domain.Product;
import com.cskaoyan.utils.Page;

public interface ProductService {

	boolean addProduct(Product p);

	List<Product> findAllProduct();
	
	  int deleteProduct(String pid) ;

	  public Product findProductByPid(String pid) ;

	boolean updateProduct(Product product);

	Page findPageCategory(int pagenumInt);

 
	void deleteProducts(String[] pids);

	Page multiConditionSearch(String pid, String cid, String pname,
			String minprice, String maxprice, String pageNum);

	List<Product> selectTop(int count);

	List<Product> selectTop(int i, int j);

	List<Product> findProductsByCid(int cid);

	List<Product> findProductsByKeyword(String keyword);
}
