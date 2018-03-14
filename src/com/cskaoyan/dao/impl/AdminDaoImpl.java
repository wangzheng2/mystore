package com.cskaoyan.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;

import com.cskaoyan.dao.AdminDao;
import com.cskaoyan.db.C3P0DBUtils;
import com.cskaoyan.domain.Admin;
 

public class AdminDaoImpl implements AdminDao {

	public Admin findAdminByUsernameAndPassword(String username, String password) {
		try {
			QueryRunner qr = new QueryRunner(C3P0DBUtils.getCpds());
			String sql = "select * from admin where username=? and password=?";
			Admin admin = qr.query(sql, new BeanHandler<Admin>(Admin.class), username,password);
			if(admin != null){
				return admin;
			}
			return null;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean saveAdmin(Admin admin) {
		try {
			QueryRunner qr = new QueryRunner(C3P0DBUtils.getCpds());
			String sql = "insert into admin(username,password) values(?,?)";
			int update = qr.update(sql, admin.getUsername(), admin.getPassword());
			if(update > 0){
				return true;
			}
			return false;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean updateAdminByAid(Admin admin) {
		try {
			QueryRunner qr = new QueryRunner(C3P0DBUtils.getCpds());
			String sql = "update admin set username=?,password=? where aid=?";
			int update = qr.update(sql, admin.getUsername(), admin.getPassword(), admin.getAid());
			if(update > 0){
				return true;
			}
			return false;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/*public List<Admin> findAllAdmin() {
		try {
			QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
			String sql = "select * from admin";
			List<Admin> admins = qr.query(sql, new BeanListHandler<Admin>(Admin.class));
			return admins;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}*/

	public List<Admin> findPageRecords(int limit, int offset) {
		try {
			String sql = "select * from admin limit ? offset ?";
			QueryRunner qr = new QueryRunner(C3P0DBUtils.getCpds());
			List<Admin> admins = qr.query(sql, new BeanListHandler<Admin>(Admin.class), limit,offset);
			return admins;
		} catch (SQLException e) {
			throw new RuntimeException(e+"...≤È—Ø ß∞‹");
		}
	}

	public int findRecordCount() {
		try {
			QueryRunner qr = new QueryRunner(C3P0DBUtils.getCpds());
			String sql = "select count(*) count from admin";
			List query = qr.query(sql, new ColumnListHandler("count"));
			return Integer.parseInt(query.get(0).toString());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
