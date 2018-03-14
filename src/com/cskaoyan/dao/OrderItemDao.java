package com.cskaoyan.dao;

import java.util.List;

import com.cskaoyan.domain.OrderItem;

public interface OrderItemDao {
	
	/**
	 * 保存订单项
	 * @param orderItem
	 * 			传入oid,pid,buynum的内容，itemid为数据库自增
	 * @return
	 */
	public int saveOrderItem(OrderItem orderItem);
	
	/**
	 * 删除订单项
	 * @param itemid
	 * @return
	 */
	public int deleteOrderItem(int itemid);
	
	
	/**
	 * 删除订单项
	 * @param itemid
	 * @return
	 */
	public int deleteOrderItemByOid(String oid);
	
	
	
	/**
	 * 修改订单项
	 * @param orderItem
	 * @return
	 */
	public int updateOrderItem(OrderItem orderItem);
	
	/**
	 * 根据订单项id查询订单项
	 * @param itemid
	 * @return
	 */
	public OrderItem findOrderItemByItemid(int itemid);

	/**
	 * 查询所有商品
	 * @return
	 */
	public List<OrderItem> findAllOrderItem();

	
	/**
	 * 查询某个订单下的所有订单项
	 * 
	 * @param oid
	 * @return
	 */
	public List<OrderItem> findOrderItemsByOrderid(String oid) ;

}
