package com.cskaoyan.dao;

import java.util.List;

import com.cskaoyan.domain.User;

 

public interface UserDao {

	/**
	 * findUserByUsernameAndPassword
	 * @return
	 */
	public User findUserByUsernameAndPassword(String username,String password);
	
	/**
	 * saveUser
	 * @param user
	 * @return
	 */
	public boolean saveUser(User user);
	
	/**
	 *updateUser
	 * @param user
	 * @return
	 */
	public boolean updateUser(User user);

	public List<User> findAllUser();

	public int findRecordCount();

	public List<User> findPageRecords(int startIndex, int pageSize);

	public boolean isActiveCodeExist(String activecode);

	public void changeRegistState(int i, String activecode);
	
	
	
}
