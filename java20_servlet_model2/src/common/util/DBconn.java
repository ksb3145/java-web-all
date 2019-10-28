package common.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconn {
	public static Connection dbConn;
	
	// connection
	public static Connection getConnection() throws ClassNotFoundException, SQLException{
		if(dbConn == null){
			String driverName = "com.mysql.jdbc.Driver";
			
//			String url = "jdbc:mysql://ncom.iptime.org/test_bom?useUnicode=true&characterEncoding=utf8";
//			String id = "root";
//			String pw = "ncom";
			
			String url = "jdbc:mysql://localhost:3306/testDB?useUnicode=true&characterEncoding=utf8";
			String id = "root";
			String pw = "0000";
			
			Class.forName(driverName);
			System.out.println("Driver 로드..");
			
			dbConn = DriverManager.getConnection(url, id, pw);
			
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
