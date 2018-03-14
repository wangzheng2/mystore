package com.cskaoyan.service;

public interface ShoppingItemService {

	/**
	 * 添加商品到购物车项表
	 * @param pid
	 * @param sid
	 * @param snum
	 */
	boolean addShoppingItem(String pid,int sid,int snum);
	
	/**
	 * 删除购物车中的某�?��
	 * @param itemid
	 * @return
	 */
	public boolean deleteShoppingItem(int itemid);
	
}
