package com.cskaoyan.dao;

public interface ShoppingItemDao {

	/**
	 * 添加商品到购物车�?
	 * @param pid
	 * @param sid
	 * @param snum
	 * @return
	 */
	public int addShoppingItem(String pid,int sid,int snum);
	
	/**
	 * 从购物车删除某一�?
	 * @param itemid
	 * @return
	 */
	public int deleteShoppingItem(int itemid);
	
}
