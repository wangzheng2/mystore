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
 		
		//1 ����UUID �ŵ�User��
		
		String activecode = UUID.randomUUID().toString();
		
		user.setActivecode(activecode);
		
		//2 ����ǰע�����������ַ��һ���ʼ�
		
		String email = user.getEmail();//"wdandroid.163.com"
		String activeURLString = "<a href=\"http://www.wangzheng.group/verify/user/UserServlet?op=activeUser&activecode="+activecode+" \">���Ҽ���webstore�̳�ע���û�</a>";
				
		//ֻ����ô����ȥ
		//���ĸ��ʼ���
		SendJMail.sendMail(email, activeURLString);

		
		//���뱣��md5֮���ֵ
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
