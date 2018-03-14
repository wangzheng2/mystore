package com.cskaoyan.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

 import com.cskaoyan.dao.CategoryDao;
import com.cskaoyan.db.C3P0DBUtils;
import com.cskaoyan.domain.Category;

public class CategoryDaoImpl implements CategoryDao {

	@Override
	public boolean addCategoryToDB(Category c) {
		// TODO Auto-generated method stub
		
		boolean ret =false;
		
		QueryRunner qr = new QueryRunner(C3P0DBUtils.getCpds());
		
		try {
			int update = qr.update("insert into category ( cname ) values (?) ;", c.getCname());
			
			System.out.println("CategoryDaoImpl.addCategoryToDB()"+update);
			if (update==1) {
				
				ret =true;
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			 e.printStackTrace();			 
			 throw  new RuntimeException("insert  category error! ");
		}
		
		
		return ret;
	}

	@Override
	public List<Category> queryAllCategory() {
		// TODO Auto-generated method stub
		
		List<Category> categories =null;
		
		QueryRunner qr = new QueryRunner(C3P0DBUtils.getCpds());
		
		try {
		     categories = qr.query("select * from category;", new BeanListHandler<Category>(Category.class));
		
		     System.out.println("CategoryDaoImpl.queryAllCategory()"+categories);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			throw new RuntimeException("queryAllCategory sql error!");
		}
		
		
		return categories;
	}

	@Override
	public boolean updateCategory(Category category) {
		// TODO Auto-generated method stub
		
		boolean ret =false;
		
 		
		QueryRunner qr = new QueryRunner(C3P0DBUtils.getCpds());
		
		try {
			int update = qr.update("update   category  set   cname =? where cid =? ;", category.getCname(),category.getCid());
			
			System.out.println("CategoryDaoImpl.addCategoryToDB()"+update);
			if (update==1) {
				ret =true;
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			 e.printStackTrace();			 
			 throw  new RuntimeException("update  category error! ");
		}
		
		
		
		return ret;
	}

	@Override
	public boolean deleteCategoryById(int cid) {
		// TODO Auto-generated method stub
		
		
	boolean ret =false;
		
 		
		QueryRunner qr = new QueryRunner(C3P0DBUtils.getCpds());
		
		try {
			int update = qr.update("delete  from category    where cid =? ;", cid);
			
			System.out.println("CategoryDaoImpl.deleteCategoryById()"+update);
			if (update==1) {
				ret =true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			 e.printStackTrace();			 
			 throw  new RuntimeException("detete  category error! " + e.getMessage());
		}
		
		
		
		return ret;
		
	}

	@Override
	public int findTotalNumber() {
		// TODO Auto-generated method stub
	   QueryRunner qr = new QueryRunner(C3P0DBUtils.getCpds());
		
		try {
			Long count = (Long) qr.query("select count(*) from  category  ;", new ScalarHandler());
			
			System.out.println("CategoryDaoImpl.addCategoryToDB()"+count);
			 
			return count.intValue();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			 e.printStackTrace();			 
			 throw  new RuntimeException("update  category error! ");
		}
		
		
	}

	@Override
	public List queryPartCategory(int limit, int offset) {
		// TODO Auto-generated method stub
		
	    List<Category> categories =null;
		
		QueryRunner qr = new QueryRunner(C3P0DBUtils.getCpds());
		
		try {
		     categories = qr.query("select * from category limit  ? offset  ?;", new BeanListHandler<Category>(Category.class),limit,offset);
		
		     System.out.println("CategoryDaoImpl.queryAllCategory()"+categories);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			throw new RuntimeException("queryAllCategory sql error!");
		}
		
		
		return categories;
	}

	@Override
	public void deleteCategoriesByIds(String[] cids) {
		
		QueryRunner qRunner = new QueryRunner();	
		Connection connection =null;
		 try {
			
			 connection = C3P0DBUtils.getConnection();
			 
			 Object[][] params = new Object[cids.length][];
			 
			 for (int i =0;i<cids.length;i++) {
				 			 
				 Object[] o= { cids[i]};
				 params[i]=o;

			}

			 //开启事务
			 connection.setAutoCommit(false);
			 
			 int[] batch = qRunner.batch(connection,"delete from category where cid =? ;", params);
			
			 //提交
			 connection.commit();
			 for (int i : batch) {
				
				 System.out.println("CategoryDaoImpl.deleteCategoriesByIds()"+i);
 			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
			if(connection!=null){
				try {
					connection.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			throw new RuntimeException(e.getMessage());
		}

 		
	}

	
	
	
	
}
