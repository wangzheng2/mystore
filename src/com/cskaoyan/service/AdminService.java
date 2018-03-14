package com.cskaoyan.service;

import java.util.List;

import com.cskaoyan.domain.Admin;
import com.cskaoyan.utils.Page;

 

public interface AdminService {

	/**
	 * 管理员登录
	 * @param username
	 * @param password
	 * @return
	 */
	 Admin login(String username,String password);
	
	/**
	 * 管理员注册
	 * @param admin
	 * @return
	 */
	boolean regist(Admin admin);
	
	/**
	 * 管理员信息修改
	 * @param admin
	 * @return
	 */
	 boolean updateAdminMsg(Admin admin);

	/**
	 * 查询所有管理员
	 * @return
	 */
	List<Admin> findAllAdmin();
	
	/**
	 * 管理员查询
	 * @param num 页码字符串形式
	 * @return
	 */
	public Page findPageRecodes(String num);
	
}
