package com.cskaoyan.dao;

import java.util.List;

import com.cskaoyan.domain.OrderItem;

public interface OrderItemDao {
	
	/**
	 * ���涩����
	 * @param orderItem
	 * 			����oid,pid,buynum�����ݣ�itemidΪ���ݿ�����
	 * @return
	 */
	public int saveOrderItem(OrderItem orderItem);
	
	/**
	 * ɾ��������
	 * @param itemid
	 * @return
	 */
	public int deleteOrderItem(int itemid);
	
	
	/**
	 * ɾ��������
	 * @param itemid
	 * @return
	 */
	public int deleteOrderItemByOid(String oid);
	
	
	
	/**
	 * �޸Ķ�����
	 * @param orderItem
	 * @return
	 */
	public int updateOrderItem(OrderItem orderItem);
	
	/**
	 * ���ݶ�����id��ѯ������
	 * @param itemid
	 * @return
	 */
	public OrderItem findOrderItemByItemid(int itemid);

	/**
	 * ��ѯ������Ʒ
	 * @return
	 */
	public List<OrderItem> findAllOrderItem();

	
	/**
	 * ��ѯĳ�������µ����ж�����
	 * 
	 * @param oid
	 * @return
	 */
	public List<OrderItem> findOrderItemsByOrderid(String oid) ;

}
