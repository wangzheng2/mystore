package com.cskaoyan.test;

import org.junit.Test;

import com.cskaoyan.service.OrderService;
import com.cskaoyan.service.impl.OrderServiceImpl;

public class OrderTest {
	
	
	@Test
	public void testUpdateState(){
		
		OrderService orderService = new OrderServiceImpl();
		
		orderService.updateOrderState("15bc4434048b48a290cfaa595c0f5063", 2);
		
	}

}
