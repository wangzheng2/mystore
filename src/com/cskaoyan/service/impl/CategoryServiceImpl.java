package com.cskaoyan.service.impl;

import java.util.List;

import com.cskaoyan.dao.CategoryDao;
import com.cskaoyan.dao.impl.CategoryDaoImpl;
import com.cskaoyan.domain.Category;
import com.cskaoyan.service.CategoryService;
import com.cskaoyan.utils.Page;

public class CategoryServiceImpl implements CategoryService {

	int countPerPage=4;
	
	CategoryDao categoryDao = new CategoryDaoImpl();
	@Override
	public boolean addCategory(Category c) {
		// TODO Auto-generated method stub
		return categoryDao.addCategoryToDB(c);
	}
	@Override
	public List<Category> findallCategory() {
		// TODO Auto-generated method stub
		return categoryDao.queryAllCategory();
	}
	@Override
	public boolean updateCategory(Category category) {
		// TODO Auto-generated method stub
		return categoryDao.updateCategory(category);
	}
	@Override
	public boolean deleteCategory(int cid) {
		// TODO Auto-generated method stub
		return categoryDao.deleteCategoryById(cid);
	}
	@Override
	public Page findPageCategory(int pagenumInt) {			
		// TODO Auto-generated method stub
		
	
		 
		int totalNumber = categoryDao.findTotalNumber();
		
		Page page=new Page(pagenumInt,totalNumber,countPerPage);

	
	    List list =	categoryDao.queryPartCategory(countPerPage, (pagenumInt-1)*countPerPage);
		
	    page.setItemList(list);
		
		return  page;
	}
	@Override
	public void deleteCategories(String[] cids) {
		// TODO Auto-generated method stub
		
		categoryDao.deleteCategoriesByIds(cids);
	}

}
