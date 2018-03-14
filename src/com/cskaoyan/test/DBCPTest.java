package com.cskaoyan.test;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

import com.cskaoyan.db.C3P0DBUtils;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DBCPTest {

	
	@Test
	public void getConn(){
		
		try {
			Connection connection = C3P0DBUtils.getConnection();
			
			ComboPooledDataSource cpds = C3P0DBUtils.getCpds();
			
			System.out.println("DBCPTest.getConn()"+connection);
			System.out.println("DBCPTest.getConn()"+cpds);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
