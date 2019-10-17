package common.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class NComDB 
{
	protected static final int eEVENT_SUCCESS=1;
	protected static final int eEVENT_FAILED=-1;
	
	protected Connection conn;
	protected PreparedStatement ps;
	protected ResultSet rs;
	
	private String driverName;
	private String url;
	private String user;
	private String pwd;
	
	public NComDB() {
		super();
		conn=null;
		ps=null;
		rs=null;
		
		try 
		{

		}
		catch (Exception e) 
		{
			System.out.println("[Error]" + e.getStackTrace());
		}
	}
	
	/**
	 * connectDB
	 * 		try to connect to Database.!
	 * */
	protected int connectDB()
	{
		try 
		{
			conn=NComDBCPConnector.getConn();
			return eEVENT_SUCCESS;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return eEVENT_FAILED;
		}
	}
	
	/**
	 * clearConnector
	 * 		try to clear DB-Connection.!
	 * */
	protected void clearConnector()
	{
		try
		{
			if(rs != null){ rs.close(); rs=null; }
			if(ps != null){ ps.close(); ps=null; }
			if(conn != null){ conn.close();conn=null; }
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * closeRS
	 * 		
	 * */
	protected void closeRS()
	{
		try 
		{
			if(rs != null){ rs.close(); rs=null;}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	/**
	 * closePS
	 * 		
	 * */
	protected void closePS()
	{
		try 
		{
			if(ps != null){ ps.close(); ps=null;}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
}
