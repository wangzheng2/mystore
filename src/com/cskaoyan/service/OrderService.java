package com.cskaoyan.service;

import java.util.List;

import com.cskaoyan.domain.Order;
import com.cskaoyan.domain.OrderItem;

public interface OrderService {

	void craetOrder(Order order, List<OrderItem> orderItems);

	List<Order> findOrdersByUid(int uid);

	void updateOrderState(String r6_Order, int i);

	void cancelOrder(String oid);

	Order getOrderByOid(String oid);

}
