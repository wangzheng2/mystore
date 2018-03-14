package com.cskaoyan.dao;

import com.cskaoyan.domain.ShoppingCart;

public interface ShoppingCartDao {

	ShoppingCart findCart(int uid);
	
	int saveCart(int uid);
		
	
}
