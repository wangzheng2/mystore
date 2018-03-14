package com.cskaoyan.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.cskaoyan.dao.ProductDao;
import com.cskaoyan.db.C3P0DBUtils;
import com.cskaoyan.domain.Category;
import com.cskaoyan.domain.Product;

     
public class ProductDaoImpl implements ProductDao {

	@Override
	public boolean addProductToDB(Product product) {
		// TODO Auto-generated method stub
		
		try {
			QueryRunner qr = new QueryRunner(C3P0DBUtils.getCpds());
			String sql = "insert into product"
					+ "(pid,pname,estoreprice,markprice,pnum,cid,imgurl,description)"
					+ " values(?,?,?,?,?,?,?,?)";
			int update = qr.update(sql, 
					product.getPid(), 
					product.getPname(), 
					product.getEstoreprice(), 
					product.getMarkprice(),
					product.getPnum(), 
					product.getCid(), 
					product.getImgurl(), 
					product.getDescription());
			return update==1?true:false;
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

 
	
	public List<Product> findAllProduct() {
		try {
			QueryRunner qr = new QueryRunner(C3P0DBUtils.getCpds());
			String sql = "select * from product";
			List<Product> products = qr.query(sql, new BeanListHandler<Product>(Product.class));
		  
			if(products != null || products.size() > 0){
				for(int i = 0; i < products.size(); i++){
					sql = "select * from category where cid=?";
					int cid = products.get(i).getCid();
					Category category = qr.query(sql, new BeanHandler<Category>(Category.class),cid);
					products.get(i).setCategory(category);
				}
			}
			
			
			System.out.println("ProductDaoImpl.findAllProduct()"+products);
			return products;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public int deleteProduct(String pid) {
		try {
			QueryRunner qr = new QueryRunner(C3P0DBUtils.getCpds());
			String sql = "delete from product where pid=?";
			int update = qr.update(sql, pid);
			return update;
		} catch (SQLException e) {
			throw new RuntimeException(e.getCause());
		}
	}



	public Product findProductByPid(String pid) {
		try {
			QueryRunner qr = new QueryRunner(C3P0DBUtils.getCpds());
			String sql = "select * from product where pid=?";
			Product product = qr.query(sql, new BeanHandler<Product>(Product.class), pid);
			
			String sql2 = "select * from category where cid=?";
			int cid = product.getCid();
			Category category = qr.query(sql2, new BeanHandler<Category>(Category.class),cid);
			product.setCategory(category);
			
			return product;
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}



	@Override
	public boolean updateProductById(Product product) {
		// TODO Auto-generated method stub

		try {
			QueryRunner qr = new QueryRunner(C3P0DBUtils.getCpds());
			String sql = "update   product set "
					+ "pname=?,estoreprice=?,markprice=?,pnum=?,cid=?,imgurl=?,description=?"
					+ "where pid= ?";
			int update = qr.update(sql, 					
					product.getPname(), 
					product.getEstoreprice(), 
					product.getMarkprice(),
					product.getPnum(), 
					product.getCid(), 
					product.getImgurl(), 
					product.getDescription(),
					product.getPid());
			return update==1?true:false;
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}	}



	 
	
	@Override
	public int findTotalNumber() {
		// TODO Auto-generated method stub
	   QueryRunner qr = new QueryRunner(C3P0DBUtils.getCpds());
		
		try {
			Long count = (Long) qr.query("select count(*) from  product  ;", new ScalarHandler());
			
			System.out.println("productDaoImpl.addCategoryToDB()"+count);
			 
			return count.intValue();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			 e.printStackTrace();			 
			 throw  new RuntimeException("productDaoImpl  findTotalNumber error! ");
		}
		
		
	}

	@Override
	public List queryPartCategory(int limit, int offset) {
		// TODO Auto-generated method stub
		
	   List<Product> productlist =null;
		
		QueryRunner qr = new QueryRunner(C3P0DBUtils.getCpds());
		
		try {
			productlist = qr.query("select * from product limit  ? offset  ?;", new BeanListHandler<Product>(Product.class),limit,offset);
		
			if(productlist != null || productlist.size() > 0){
				for(int i = 0; i < productlist.size(); i++){
					String sql = "select * from category where cid=?";
					int cid = productlist.get(i).getCid();
					Category category = qr.query(sql, new BeanHandler<Category>(Category.class),cid);
					productlist.get(i).setCategory(category);
				}
			}
		     System.out.println("CategoryDaoImpl.queryAllCategory()"+productlist);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			throw new RuntimeException("queryAllCategory sql error!");
		}
		
		
		return productlist;
	}



	@Override
	public void deleteProductsByIds(String[] pids) {
		// TODO Auto-generated method stub
		
		QueryRunner qRunner = new QueryRunner();	
		Connection connection =null;
		 try {
			
			 connection = C3P0DBUtils.getConnection();
			 
			 Object[][] params = new Object[pids.length][];
			 
			 for (int i =0;i<pids.length;i++) {
				 			 
				 Object[] o= { pids[i]};
				 params[i]=o;

			}

			 //开启事务
			 connection.setAutoCommit(false);
			 
			 int[] batch = qRunner.batch(connection,"delete from product where pid =? ;", params);
			
			 //提交
			 connection.commit();
			 for (int i : batch) {
				 System.out.println("ProductDaoImpl.deleteProductsByIds()"+i);
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



	@Override
	public int getCountByMultiCondition(String pid, String cid, String pname,
			String minprice, String maxprice) {
		
		//select count(*) from product where panme like ?
		
		//select count(*) from product where panme like and estoreprice >= ? and estoreprice<= ?
				
		//select count(*) from product where pid = ? and pname = ? 


		String sql = "select count(*) from product where 1=1 ";
		
		//Object[] paramsObjects =new Object[5];
		
		List paramList = new ArrayList<>();
		
		if (!pid.isEmpty()) {
			
			sql = sql + " and pid = ? ";
			
			paramList.add(pid);
			
		}
		
		if (!cid.isEmpty()){
			
			
			sql = sql + " and cid = ?" ;
			paramList.add(cid);

		}
		
	    if (!pname.isEmpty()){
			
			
			sql = sql + " and pname like ?" ;
			paramList.add("%"+pname+"%");

		}
		
	
		if (!minprice.isEmpty()){
			
			
			sql = sql + " and estoreprice >= ?" ;
			paramList.add(minprice);

		}
		
		
		if (!maxprice.isEmpty()){
			
			
			sql = sql + " and estoreprice  <= ?" ;
			paramList.add(maxprice);
	
		}			

		Object[] array = paramList.toArray();
	
		System.out.println("ProductDaoImpl.getCountByMultiCondition()" + sql);
		
		try {
			
			QueryRunner qRunner = new QueryRunner(C3P0DBUtils.getCpds());
			Long query = (Long) qRunner.query(
					     sql, 
					     new ScalarHandler(),					     
					     array
			);

			
			return query.intValue();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
			throw new RuntimeException(e.getMessage());
		}
	}



	@Override
	public List getProductsByMultiCondition(String pid, String cid,
			String pname, String minprice, String maxprice,int limit,int offset) {
        String sql = "select * from product where 1=1 ";	
		List paramList = new ArrayList<>();
		
		if (!pid.isEmpty()) {			
			sql = sql + " and pid = ? ";			
			paramList.add(pid);			
		}
		
		if (!cid.isEmpty()){
			sql = sql + " and cid = ?" ;
			paramList.add(cid);
		}
		
	    if (!pname.isEmpty()){				
			sql = sql + " and pname like ?" ;
			paramList.add("%"+pname+"%");
		}	
		if (!minprice.isEmpty()){					
			sql = sql + " and estoreprice >= ?" ;
			paramList.add(minprice);
		}		
		if (!maxprice.isEmpty()){
			sql = sql + " and estoreprice  <= ?" ;
			paramList.add(maxprice);
		}
		if (limit>0) {
			sql = sql + " limit ?" ;
			paramList.add(limit);
			
			if (offset>0) {
				sql = sql + " offset ?" ;
				paramList.add(offset);
			}
		}
		

		Object[] array = paramList.toArray();
	
		System.out.println("ProductDaoImpl.getProductByMultiCondition()" + sql);
		
		try {
			
			QueryRunner qRunner = new QueryRunner(C3P0DBUtils.getCpds());
			 List<Product> productlist = qRunner.query(
					     sql, 
					     new BeanListHandler<Product>(Product.class),					     
					     array
			);


				if(productlist != null || productlist.size() > 0){
					for(int i = 0; i < productlist.size(); i++){
						String sql2 = "select * from category where cid=?";
						int cid2 = productlist.get(i).getCid();
						Category category = qRunner.query(sql2, new BeanHandler<Category>(Category.class),cid2);
						productlist.get(i).setCategory(category);
					}
				}
			
			return  productlist;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
			throw new RuntimeException(e.getMessage());
		}
	}


	
	public List<Product> findTop(int count) {
		try {
			QueryRunner qr = new QueryRunner(C3P0DBUtils.getCpds());
			String sql = "select * from product limit ?";
			List<Product> products = qr.query(sql, new BeanListHandler<Product>(Product.class),count);
			return products;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Product> findTop(int count, int start) {
		try {
			QueryRunner qr = new QueryRunner(C3P0DBUtils.getCpds());
			String sql = "select * from product limit ?,?";
			List<Product> products = qr.query(sql, new BeanListHandler<Product>(Product.class),start,count);
			return products;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}



	@Override
	public List<Product> findProductByCid(int cid) {
		// TODO Auto-generated method stub
		try {
			QueryRunner qr = new QueryRunner(C3P0DBUtils.getCpds());
			String sql = "select * from product where cid=?";
			List<Product> products = qr.query(sql, new BeanListHandler<Product>(Product.class), cid);
			return products;
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}



	@Override
	public List<Product> findProductsByKeyword(String keyword) {
		// TODO Auto-generated method stub
		try {
			QueryRunner qr = new QueryRunner(C3P0DBUtils.getCpds());
			String sql = "select * from product where pname like ? or description like ?";
			List<Product> products = qr.query(sql, new BeanListHandler<Product>(Product.class), "%"+keyword+"%","%"+keyword+"%");
			return products;
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
		
 	}

	
	

	 

}
