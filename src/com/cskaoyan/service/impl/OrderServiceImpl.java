package com.cskaoyan.service.impl;

import java.util.Date;
import java.util.List;

import javax.management.RuntimeErrorException;

import com.cskaoyan.dao.OrderDao;
import com.cskaoyan.dao.OrderItemDao;
import com.cskaoyan.dao.impl.OrderDaoImpl;
import com.cskaoyan.dao.impl.OrderItemDaoImpl;
import com.cskaoyan.db.TransactionManager;
import com.cskaoyan.domain.Order;
import com.cskaoyan.domain.OrderItem;
import com.cskaoyan.service.OrderService;

public class OrderServiceImpl implements OrderService {

	@Override
	public void craetOrder(Order order, List<OrderItem> orderItems) {
		// TODO Auto-generated method stub
		
		OrderDao orderDao = new OrderDaoImpl();
		OrderItemDao orderItemDao = new OrderItemDaoImpl();
		//开启事务
		
		try {
			
			TransactionManager.startTransaction();
			order.setOrdertime(new Date());
			orderDao.createOrder(order);
			for (OrderItem orderItem2 : orderItems) {
				orderItemDao.saveOrderItem(orderItem2);
			}	
			//提交事务
			TransactionManager.commit();
			TransactionManager.release();
		} catch (Exception e) {
			// TODO: handle exception
			//发生异常回滚事务
			TransactionManager.rollback();
			TransactionManager.release();
			
			e.printStackTrace();
			new RuntimeException(e);
		
		}
		
		
		
		
	}

	@Override
	public List<Order> findOrdersByUid(int uid) {
		// TODO Auto-generated method stub
		
		OrderDao dao = new OrderDaoImpl();
		
		return dao.findOrderByUid( uid);
	}

	@Override
	public void updateOrderState(String oid, int i) {
		// TODO Auto-generated method stub
		
		
		OrderDao dao = new OrderDaoImpl();

		dao.updateOrderStateByOid(oid, i);
	}

	@Override
	public void cancelOrder(String oid) {
		// TODO Auto-generated method stub
		OrderDao dao = new OrderDaoImpl();

		dao.updateOrderStateByOid(oid, 1);// 1 取消
		
	}

	@Override
	public Order getOrderByOid(String oid) {
		// TODO Auto-generated method stub
		OrderDao dao = new OrderDaoImpl();

		return dao.findOrderById(oid);
	}
	
	
	
	

}
