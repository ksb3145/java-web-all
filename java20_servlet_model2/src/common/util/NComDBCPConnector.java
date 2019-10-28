package common.util;

import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class NComDBCPConnector
{
	static DataSource ds;
	
	static
	{
		try
		{
			Context fcontext=new InitialContext();
			Context envcontext=(Context)fcontext.lookup("java:/comp/env");
			ds=(DataSource)envcontext.lookup("jdbc/mysql");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static Connection getConn() throws Exception
	{
		return ds.getConnection();
	}
}
