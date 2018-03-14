package com.cskaoyan.service;

import java.util.List;

import com.cskaoyan.domain.Admin;
import com.cskaoyan.utils.Page;

 

public interface AdminService {

	/**
	 * ����Ա��¼
	 * @param username
	 * @param password
	 * @return
	 */
	 Admin login(String username,String password);
	
	/**
	 * ����Աע��
	 * @param admin
	 * @return
	 */
	boolean regist(Admin admin);
	
	/**
	 * ����Ա��Ϣ�޸�
	 * @param admin
	 * @return
	 */
	 boolean updateAdminMsg(Admin admin);

	/**
	 * ��ѯ���й���Ա
	 * @return
	 */
	List<Admin> findAllAdmin();
	
	/**
	 * ����Ա��ѯ
	 * @param num ҳ���ַ�����ʽ
	 * @return
	 */
	public Page findPageRecodes(String num);
	
}
