package com.cskaoyan.service;

import java.util.List;

import com.cskaoyan.domain.Category;
import com.cskaoyan.utils.Page;

public interface CategoryService {
	
	public boolean addCategory(Category c);

	public List<Category> findallCategory();

	public boolean updateCategory(Category category);

	public boolean deleteCategory(int cid);

	public Page findPageCategory(int pagenumInt);

	public void deleteCategories(String[] cids);
	
	

}
