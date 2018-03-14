package com.cskaoyan.service.impl;

import java.util.List;

import com.cskaoyan.dao.AdminDao;
import com.cskaoyan.dao.impl.AdminDaoImpl;
import com.cskaoyan.domain.Admin;
import com.cskaoyan.service.AdminService;
import com.cskaoyan.utils.Page;

 

public class AdminServiceImpl implements AdminService {
	
	private AdminDao adminDao = new AdminDaoImpl();

	public Admin login(String username, String password) {
		Admin admin = adminDao.findAdminByUsernameAndPassword(username, password);
		return admin;
	}

	public boolean regist(Admin admin) {
		if (admin == null) {
			throw new IllegalArgumentException("admin is null...");
		}
 		boolean addAdmin = adminDao.saveAdmin(admin);
		if (addAdmin) {
 			return true;
		}
	 
		return false;
	}

	public boolean updateAdminMsg(Admin admin) {
		if (admin == null) {
			throw new IllegalArgumentException("admin is null...");
		}
 		boolean updateAdmin = adminDao.updateAdminByAid(admin);
		if (updateAdmin) {
 			return true;
		}
 		return false;
	}

	/*public List<Admin> findAllAdmin() {
		List<Admin> admins = adminDao.findAllAdmin();
		return admins;
	}*/

	public Page findPageRecodes(String num) {
		int pageNum = 1;
		if(num != null){
			pageNum = Integer.parseInt(num);
		}
		int totalRecordNum = adminDao.findRecordCount();
		Page page = new Page(pageNum,totalRecordNum,2);
		 List  records = adminDao.findPageRecords( 2, (pageNum-1)*2);
		page.setItemList(records);
		return page;
	}

	@Override
	public List<Admin> findAllAdmin() {
		// TODO Auto-generated method stub
		return null;
	}

}
