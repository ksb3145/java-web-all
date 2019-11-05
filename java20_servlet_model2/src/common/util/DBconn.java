package common.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconn {
	public static Connection dbConn;
	
	// connection
	public static Connection getConnection() throws ClassNotFoundException, SQLException{
		if(dbConn == null){
			
			String jdbcDriver = "jdbc:apache:commons:dbcp:cp";
			dbConn = DriverManager.getConnection(jdbcDriver);
			System.out.println("DB Connection...");
		}
		
		return dbConn;
	}
	
	// close
	public static void close() throws SQLException{
		
		if(dbConn!=null){
			System.out.println("conn close...");
			if(!dbConn.isClosed()){
				dbConn.close();
			}
		}
		
		dbConn = null;
	}
}
