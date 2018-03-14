package com.cskaoyan.dao;

import java.util.List;

import com.cskaoyan.domain.Order;

public interface OrderDao {

	/**
	 * �µ�
	 * @param order
	 * @return
	 */
	public int createOrder(Order order);

	/**
	 * ���ݶ������޸Ķ���״̬
	 * @param oid
	 * @param state
	 * @return 
	 */
	public int updateOrderStateByOid(String oid, int state);
	
	
	/*
	 * 
	 * ɾ������
	 * 
	 */
	
	public int deleteOrderByOid(String oid);
	
	/**
	 * ���ݶ����Ų�ѯ����
	 * @param oid
	 */
	public Order findOrderById(String oid);
	
	/**
	 * ��ѯ���ж���
	 * @return
	 */
	//public List<Order> findAllOrder();

	/**
	 * ��ѯ��¼��
	 * @return
	 */
	//public int findRecordCount();

	/**
	 * ��ҳ��ѯ��¼
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	//public List<Order> findPageRecords(int startIndex, int pageSize);

	/**
	 * ��ҳ��ѯ��¼
	 * @param startIndex
	 * @param pageSize
	 * @param uid
	 * @return
	 */
	public List<Order> findPageRecords(int startIndex, int pageSize, int uid);
	
	/**
	 * �����û�id��ѯ����
	 * @param uid
	 * @return
	 */
	public List<Order> findOrderByUid(int uid);

}
