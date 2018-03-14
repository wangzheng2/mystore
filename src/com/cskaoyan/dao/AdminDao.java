package com.cskaoyan.dao;

import java.util.List;

import com.cskaoyan.domain.Admin;

 
public interface AdminDao {
	
	/**
	 * �����û�����������ҹ���Ա
	 * @param username
	 * @param password
	 * @return
	 */
 	public Admin findAdminByUsernameAndPassword(String username,String password);
	
	/**
	 * �������Ա
	 * @param user
	 * @return
	 */
	public boolean saveAdmin(Admin admin);
	
	/**
	 * ���ݹ���Աid���Ĺ���Ա��Ϣ
	 * @param user
	 * @return
	 */
	 public boolean updateAdminByAid(Admin admin);

	/**
	 * �������й���Ա
	 * @return
	 *//*
	List<Admin> findAllAdmin();*/
	
	/**
	 * ��ѯָ��ҳ�ļ�¼
	 * @param startIndex ÿҳ��¼��ʼ������ֵ
	 * @param recordCount �ܼ�¼��
	 * @return
	 */
	List<Admin> findPageRecords(int startIndex,int recordCount);
	
	int findRecordCount();

}
