package com.cskaoyan.dao;

import java.util.List;
import java.util.zip.Inflater;

import com.cskaoyan.domain.Product;

public interface ProductDao {

	boolean addProductToDB(Product p);

	List<Product> findAllProduct();
	
	public int deleteProduct(String pid) ;

	Product findProductByPid(String pid);

	boolean updateProductById(Product product);

	int findTotalNumber();

	List queryPartCategory(int countPerPage, int i);

 
	void deleteProductsByIds(String[] pids);

	int getCountByMultiCondition(String pid, String cid, String pname,
			String minprice, String maxprice);

	List  getProductsByMultiCondition(String pid, String cid,
			String pname, String minprice, String maxprice, int  limit, int offset);

	List<Product> findTop(int count);

	List<Product> findTop(int count, int start);

	List<Product> findProductByCid(int cid);

	List<Product> findProductsByKeyword(String keyword);

}
