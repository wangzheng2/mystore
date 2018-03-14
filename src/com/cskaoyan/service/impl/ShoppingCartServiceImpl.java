package com.cskaoyan.service.impl;

import com.cskaoyan.dao.ShoppingCartDao;
import com.cskaoyan.dao.impl.ShoppingCartDaoImpl;
import com.cskaoyan.domain.ShoppingCart;
import com.cskaoyan.service.ShoppingCartService;
 
public class ShoppingCartServiceImpl implements ShoppingCartService{

	private ShoppingCartDao cartDao = new ShoppingCartDaoImpl();
	
	public ShoppingCart findCart(int uid) {
		ShoppingCart sc = cartDao.findCart(uid);
		return sc;
	}
	
	public boolean addCart(int uid) {
 		int saveCar = cartDao.saveCart(uid);
		if(saveCar > 0){
 
			return true;
		}
 
		return false;
	}

	

}
