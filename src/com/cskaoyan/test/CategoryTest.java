package com.cskaoyan.test;

import java.util.List;

import org.junit.Test;

import com.cskaoyan.dao.CategoryDao;
import com.cskaoyan.dao.impl.CategoryDaoImpl;
import com.cskaoyan.domain.Category;
import com.cskaoyan.utils.Page;

public class CategoryTest {

	
	@Test
	public void testInsert(){
		
		Category category=new Category();
		category.setCname("nike");
		
		CategoryDao dao = new CategoryDaoImpl();
		dao.addCategoryToDB(category);
		
		
	}
	
	@Test
	public void testQueryALl(){
		
		 
		
		CategoryDao dao = new CategoryDaoImpl();
		 
		List<Category> queryAllCategory = dao.queryAllCategory();
		
		
		System.out.println("CategoryTest.testQueryALl()"+queryAllCategory);
		
		
	}
	
	@Test
	public void testQueryALlCount(){
		
		 
		
		CategoryDao dao = new CategoryDaoImpl();
		 
		int findTotalNumber = dao.findTotalNumber();
		
		
		System.out.println("CategoryTest.testQueryALl()"+findTotalNumber);
		
		
	}
	
	
	@Test
	public void testQueryPart(){
		
		 
		
		CategoryDao dao = new CategoryDaoImpl();
		 
		 List list = dao.queryPartCategory(2, 2);
		
		
		System.out.println("CategoryTest.testQueryALl()"+list);
		
		
	}
}
