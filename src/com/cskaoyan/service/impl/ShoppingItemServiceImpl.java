package com.cskaoyan.service.impl;

import com.cskaoyan.dao.ShoppingItemDao;
import com.cskaoyan.dao.impl.ShoppingItemDaoImpl;
import com.cskaoyan.service.ShoppingItemService;

 

public class ShoppingItemServiceImpl implements ShoppingItemService {

	private ShoppingItemDao shoppingItemDao = new ShoppingItemDaoImpl();

	public boolean addShoppingItem(String pid, int sid, int snum) {

 		int addShoppingItem = shoppingItemDao.addShoppingItem(pid, sid, snum);
		if (addShoppingItem > 0) {
 			return true;
		}
 		return false;
	}

	public boolean deleteShoppingItem(int itemid) {

 		int deleteShoppingItem = shoppingItemDao.deleteShoppingItem(itemid);
		if (deleteShoppingItem > 0) {
 			return true;
		}
 
		return false;
	}

}
