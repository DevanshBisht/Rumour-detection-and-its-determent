package controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import databaseConnection.DbOperation;

public class CheckShare {
	int flag=0;
	int pid=0;
	int puid=0;
	
	public int check(String post,int id,String sn) throws ClassNotFoundException, SQLException
	{
		  String query="SELECT * FROM  `userrecords`WHERE  `screenname` =  '"+sn+"'";
    	  System.out.println(query);
		ResultSet rs=DbOperation.selectOperation(query);
		if(rs.next())
			puid=rs.getInt("srno");
		if(puid==id)
		{
			return 0;
		}
		else
		{
		query="SELECT * FROM  `postdatabase` WHERE  `post` =  '"+post+"' and userid = '"+puid+"'";
		System.out.println(query);
		rs=DbOperation.selectOperation(query);
		if(rs.next())
		{
		pid=rs.getInt("srno");
		query="SELECT * FROM  `sharedpostdatabase` WHERE  `pid` ="+pid+" AND  `uid` ="+id ;
		System.out.println(query);
		rs=DbOperation.selectOperation(query);
		int records=0;
		rs.last();
		records=rs.getRow();
		rs.beforeFirst();
	     if(records >0)
			{
			return 1;
			}
			else
			{
				return 2;	
			}
		}
		else
		{
			query="SELECT * FROM  `postdatabase` WHERE  `post` =  '"+post+"'";
			System.out.println(query);
			rs=DbOperation.selectOperation(query);
			if(rs.next())
		    pid=rs.getInt("srno");
			query="SELECT * FROM  `sharedpostdatabase` WHERE  `pid` ="+pid+" AND  `uid` ="+id ;
			System.out.println(query);
			rs=DbOperation.selectOperation(query);
			int records=0;
			rs.last();
			records=rs.getRow();
			rs.beforeFirst();
		     if(records >0)
				{
				return 1;
				}
				else
				{
					return 2;	
				}
			
		}
		
		}
		
		
	}
	public void closeobj()
	{
	System.gc();	
	}

}
