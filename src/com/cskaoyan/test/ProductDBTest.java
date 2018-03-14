package com.cskaoyan.test;

import java.util.List;

import org.junit.Test;

import com.cskaoyan.dao.CategoryDao;
import com.cskaoyan.dao.impl.CategoryDaoImpl;
import com.cskaoyan.dao.impl.ProductDaoImpl;

public class ProductDBTest {
	
	
	@Test
	public void testMutlitParam(){
		
		
		
	}
	
	/* public void test1(Integer ... ints){
		
		System.out.println("ProductDBTest.test1()");
	}
	
    public void test1(List<Integer> ints){
		
		System.out.println("ProductDBTest.test1() xx");
	}
	
    public void test1(Integer[]  ints){
		
		System.out.println("ProductDBTest.test1()");
	} */

	@Test
	public void testQueryALlCount(){
		
		 
		
		ProductDaoImpl dao = new ProductDaoImpl();
		 
		int findTotalNumber = dao.getCountByMultiCondition("", "", "¸ß¸ú", "", "");
		
		
		System.out.println("CategoryTest.testQueryALl()"+findTotalNumber);
		
		
	}
	
	
	@Test
	public void testQueryPart(){
		
		 
		
		ProductDaoImpl dao = new ProductDaoImpl();
		 
		 List list = dao.queryPartCategory(2, 2);
		
		
		System.out.println("CategoryTest.testQueryALl()"+list);
		
		
	}
	
	@Test
	public void testQueryALlCountByCondition(){
		
		 
		
		ProductDaoImpl dao = new ProductDaoImpl();
		 
		int findTotalNumber = dao.getCountByMultiCondition("", "2", "¸´¹Å", "800", "900") ;
		
		
		System.out.println("CategoryTest.testQueryALl()"+findTotalNumber);
		
		
	}
	
	@Test
	public void testQueryALlProductByCondition(){
		
		ProductDaoImpl dao = new ProductDaoImpl();		 
		List productsByMultiCondition = dao.getProductsByMultiCondition("", "2", "¸´¹Å", "0", "900", 10,1) ;			
		System.out.println("CategoryTest.testQueryALl()"+productsByMultiCondition);
				
	}
	
	@Test
	public void testQueryProductByKeyword(){
		
		ProductDaoImpl dao = new ProductDaoImpl();		 
		List productsByMultiCondition = dao.findProductsByKeyword("Í¸Æø") ;			
		System.out.println("CategoryTest.testQueryALl()"+productsByMultiCondition);
				
	}
}
