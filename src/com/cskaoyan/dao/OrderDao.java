package com.cskaoyan.dao;

import java.util.List;

import com.cskaoyan.domain.Order;

public interface OrderDao {

	/**
	 * 下单
	 * @param order
	 * @return
	 */
	public int createOrder(Order order);

	/**
	 * 根据订单号修改订单状态
	 * @param oid
	 * @param state
	 * @return 
	 */
	public int updateOrderStateByOid(String oid, int state);
	
	
	/*
	 * 
	 * 删除订单
	 * 
	 */
	
	public int deleteOrderByOid(String oid);
	
	/**
	 * 根据订单号查询订单
	 * @param oid
	 */
	public Order findOrderById(String oid);
	
	/**
	 * 查询所有订单
	 * @return
	 */
	//public List<Order> findAllOrder();

	/**
	 * 查询记录数
	 * @return
	 */
	//public int findRecordCount();

	/**
	 * 分页查询记录
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	//public List<Order> findPageRecords(int startIndex, int pageSize);

	/**
	 * 分页查询记录
	 * @param startIndex
	 * @param pageSize
	 * @param uid
	 * @return
	 */
	public List<Order> findPageRecords(int startIndex, int pageSize, int uid);
	
	/**
	 * 根据用户id查询订单
	 * @param uid
	 * @return
	 */
	public List<Order> findOrderByUid(int uid);

}
