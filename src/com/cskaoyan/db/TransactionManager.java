package com.cskaoyan.db;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {

	private static ThreadLocal<Connection> tl = new ThreadLocal<Connection>();
	
	/**
	 * ��ȡ����
	 * @return Connection���Ӷ���
	 * @throws SQLException 
	 */
	public static Connection getConnection() throws SQLException{
		Connection conn = tl.get();
		if(conn == null){
			conn = C3P0DBUtils.getConnection();
			tl.set(conn);
		}
		return conn;
	}
	
	/**
	 * ��������
	 */
	public static void startTransaction(){
		try {
			Connection conn = getConnection();
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * �ύ����
	 */
	public static void commit(){
		try {
			Connection conn = getConnection();
			conn.commit();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * ����ع�
	 */
	public static void rollback(){
		try {
			Connection conn = getConnection();
			conn.rollback();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * �ͷ�����
	 */
	public static void release(){
		try {
			Connection conn = getConnection();
			conn.close();
			tl.remove();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	
}
