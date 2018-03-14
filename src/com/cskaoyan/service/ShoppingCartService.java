package com.cskaoyan.service;

import java.util.List;

import com.cskaoyan.domain.Order;
import com.cskaoyan.domain.OrderItem;
import com.cskaoyan.domain.ShoppingCart;
 
public interface ShoppingCartService {

	/**
	 * 根据用户id查询购物�?
	 * @param uid
	 * @return
	 */
	ShoppingCart findCart(int uid);
	
	/**
	 * 添加商品到购物车
	 * @param 用户id
	 * @return
	 */
	boolean addCart(int uid);

 	
}
