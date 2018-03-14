package com.cskaoyan.dao;

import java.util.List;

import com.cskaoyan.domain.Admin;

 
public interface AdminDao {
	
	/**
	 * 根据用户名和密码查找管理员
	 * @param username
	 * @param password
	 * @return
	 */
 	public Admin findAdminByUsernameAndPassword(String username,String password);
	
	/**
	 * 保存管理员
	 * @param user
	 * @return
	 */
	public boolean saveAdmin(Admin admin);
	
	/**
	 * 根据管理员id更改管理员信息
	 * @param user
	 * @return
	 */
	 public boolean updateAdminByAid(Admin admin);

	/**
	 * 查找所有管理员
	 * @return
	 *//*
	List<Admin> findAllAdmin();*/
	
	/**
	 * 查询指定页的记录
	 * @param startIndex 每页记录开始的索引值
	 * @param recordCount 总记录数
	 * @return
	 */
	List<Admin> findPageRecords(int startIndex,int recordCount);
	
	int findRecordCount();

}
