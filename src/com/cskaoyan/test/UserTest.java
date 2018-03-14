package com.cskaoyan.test;

import org.junit.Test;

import com.cskaoyan.service.impl.UserServiceImpl;
import com.cskaoyan.service.UserService;
import com.cskaoyan.utils.Page;

public class UserTest {

	
	@Test
	public void getPageuser(){
		
		UserService service = new UserServiceImpl();
		
		Page findPageRecodes = service.findPageRecodes("2");
		
		System.out.println("UserTest.getPageuser()"+findPageRecodes);
		
	}
}
