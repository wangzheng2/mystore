package com.cskaoyan.service.impl;

import java.util.List;
import java.util.UUID;

import javax.mail.SendFailedException;

import com.cskaoyan.dao.UserDao;
import com.cskaoyan.dao.impl.UserDaoImpl;
import com.cskaoyan.domain.User;
import com.cskaoyan.service.UserService;
import com.cskaoyan.utils.MD5Utils;
import com.cskaoyan.utils.Page;
import com.cskaoyan.utils.SendJMail;

 

public class UserServiceImpl implements UserService {

	private UserDao userDao = new UserDaoImpl();
	
	public User login(String username, String password) {
		
		
		//123456
		String md5 = MD5Utils.getMD5(password);
		
		User user = userDao.findUserByUsernameAndPassword(username, md5);
		
		return user;
	}

	public boolean regist(User user) {
		if(user == null){
			throw new IllegalArgumentException("user is null");
		}
 		
		//1 产生UUID 放到User里
		
		String activecode = UUID.randomUUID().toString();
		
		user.setActivecode(activecode);
		
		//2 给当前注册的这个邮箱地址发一个邮件
		
		String email = user.getEmail();//"wdandroid.163.com"
		String activeURLString = "<a href=\"http://www.wangzheng.group/verify/user/UserServlet?op=activeUser&activecode="+activecode+" \">点我激活webstore商城注册用户</a>";
				
		//只差怎么发出去
		//给哪个邮件发
		SendJMail.sendMail(email, activeURLString);

		
		//密码保存md5之后的值
		user.setPassword(MD5Utils.getMD5(user.getPassword()));
		
		return userDao.saveUser(user);
	}

	public boolean updateUserMsg(User user) {
		if(user == null){
			throw new IllegalArgumentException("user is null");
		}
 	
		

		return 	userDao.updateUser(user);
	}
	
	public List<User> findAllUser() {
		List<User> users = userDao.findAllUser();
		return users;
	}

	public Page findPageRecodes(String num) {
		// TODO Auto-generated method stub
		int pageNum = 1;
		if(num != null){
			pageNum = Integer.parseInt(num);
		}
		int totalRecordNum = userDao.findRecordCount();
		Page page = new Page(pageNum,totalRecordNum,3);
		List  records = userDao.findPageRecords((pageNum-1)*3, 3);
		page.setItemList(records);
		return page;
	}

	@Override
	public boolean checkActiveCode(String activecode) {

		boolean exist  = userDao.isActiveCodeExist(activecode);
		
		if (exist) {			
			userDao.changeRegistState(1,activecode);			
		}
		
		return  exist;
	}

}
