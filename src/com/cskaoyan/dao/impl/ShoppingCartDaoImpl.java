package com.cskaoyan.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.cskaoyan.dao.ShoppingCartDao;
import com.cskaoyan.db.C3P0DBUtils;
import com.cskaoyan.domain.Product;
import com.cskaoyan.domain.ShoppingCart;
import com.cskaoyan.domain.ShoppingItem;

public class ShoppingCartDaoImpl implements ShoppingCartDao {

	public ShoppingCart findCart(int uid) {
		try {
			QueryRunner qr = new QueryRunner(C3P0DBUtils.getCpds());
			String sql = "select * from shoppingcar where uid=?";
			ShoppingCart shoppingCar = qr.query(sql, new BeanHandler<ShoppingCart>(ShoppingCart.class), uid);
			if(shoppingCar != null){
				sql = "select * from shoppingItem where sid=?";
				List<ShoppingItem> shoppingItems = qr.query(sql, new BeanListHandler<ShoppingItem>(ShoppingItem.class), shoppingCar.getSid());
				for(int i  = 0; i < shoppingItems.size(); i++){
					sql = "select * from product where pid=?";
					Product product = qr.query(sql, new BeanHandler<Product>(Product.class), shoppingItems.get(i).getPid());
					shoppingItems.get(i).setProduct(product);
				}
				shoppingCar.setShoppingItems(shoppingItems);
			}
			return shoppingCar;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public int saveCart(int uid) {
		try {
			QueryRunner qr = new QueryRunner(C3P0DBUtils.getCpds());
			String sql = "insert into shoppingcar(uid) values(?)";
			int update = qr.update(sql,uid);
			return update;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
 

}
