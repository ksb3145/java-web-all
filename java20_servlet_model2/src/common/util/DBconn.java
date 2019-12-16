package common.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


/**
 * DBCP 연동
 * @author dev2
 */
public class DBconn {
	public static Connection getConnection() throws SQLException, NamingException
															, ClassNotFoundException {
		Context initCtx=new InitialContext();
		System.out.println("1. JNDI 서버 객체 생성 ...");
//  ===========================================================
		//1.
//		Context envCtx=(Context) initCtx.lookup("java:comp/env");	 
//		// initCtx의 lookup메서드를 이용해서 "java:/comp/env" 에 해당하는 객체를 찾아서 envCtx에 할당
//		System.out.println("2-1. initCtx lookup...");
//		
//		DataSource ds =(DataSource) envCtx.lookup("jdbc/mysql");
//		// envCtx의 lookup메서드를 이용해서 "jdbc/mysql"에 해당하는 객체를 찾아서 ds에 할당
//		System.out.println("2-2. envCtx lookup...");
//	===========================================================
		
		//2.
		DataSource ds = (DataSource) initCtx.lookup("java:/comp/env/jdbc/mysql");
		//  lookup메서드를 이용해서 "jdbc/mysql"에 해당하는 객체를 찾아서 ds에 할당
		System.out.println("2. lookup...");
		
		//===========================================================
		
		Connection conn = ds.getConnection();
		// getConnection메서드를 이용해서 커넥션 풀로 부터 커넥션 객체를 얻어내어 conn에 할당
		System.out.println("3. Connection Pool 객체 conn에 할당...");
		
		return conn;
	}
}
