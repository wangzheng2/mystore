package com.cskaoyan.dao;

import java.util.List;

import com.cskaoyan.domain.Category;

public interface CategoryDao {
	
	boolean addCategoryToDB(Category c);

	List<Category> queryAllCategory();

	boolean updateCategory(Category category);

	boolean deleteCategoryById(int cid);

	int findTotalNumber();

	List queryPartCategory(int countPerPage, int i);

	void deleteCategoriesByIds(String[] cids);

}
