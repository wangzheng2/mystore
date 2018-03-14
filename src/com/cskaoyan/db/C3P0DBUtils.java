package com.cskaoyan.db;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3P0DBUtils {

	
  private static	ComboPooledDataSource  cpds ;

  DataSource ds ;
  
  static{
	  
	  
	  
	  
		  cpds= new ComboPooledDataSource();
		
		  //cpds.setDriverClass( "com.mysql.jdbc.Driver" ); //loads the jdbc driver            
		  //cpds.setJdbcUrl( "jdbc:mysql://localhost:3306/mylogindemo" );
		  /* cpds.setUser("root");                                  
		  cpds.setPassword("123456"); */
		  
		  //cpds.setMaxPoolSize(maxPoolSize);

		 // cpds.setMaxStatements(20);
		  
		  
	 
	  
	  
  }
    
  
  
  
  public static ComboPooledDataSource getCpds() {
	return cpds;
}



 


public static Connection  getConnection() throws SQLException{
	  
	  
	  return cpds.getConnection();
	  
  }
  
}
