package controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import databaseConnection.DbOperation;

public class Checklikedislike {
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
		query="SELECT * FROM  `postdatabase` WHERE  `post` =  '"+post+"' and userid = '"+puid+"'";
		System.out.println(query);
		rs=DbOperation.selectOperation(query);
		if(rs.next())
		{
			pid=rs.getInt("srno");
			query="SELECT * FROM  `likedislike`WHERE  `pid` ="+pid+" AND  `uid` ="+id ;
			System.out.println(query);
			rs=DbOperation.selectOperation(query);
			if(rs.next())
			{
				if(rs.getInt("likepost")== 1)
				{
				return 1;
				}
				else if(rs.getInt("dislikepost")== 1)
				{
					
					System.out.println(pid+"2");
				return 2;	
				}
			}
		}
		else
		{
			query="SELECT * FROM  `postdatabase` WHERE  `post` =  '"+post+"'";
			System.out.println(query);
			rs=DbOperation.selectOperation(query);
			if(rs.next())
			pid=rs.getInt("srno");
			query="SELECT * FROM `sharedpostdatabase` WHERE `uid` ='"+puid+"' AND `pid` ="+pid;
			System.out.println(query);
			rs=DbOperation.selectOperation(query);
			if(rs.next())
			{
				query="SELECT * FROM  `likedislike`WHERE  `pid` ="+pid+" AND  `uid` ="+id ;
				System.out.println(query);
				rs=DbOperation.selectOperation(query);
				if(rs.next())
				{
					if(rs.getInt("likepost")== 1)
					{
					return 1;
					}
					else if(rs.getInt("dislikepost")== 1)
					{
						
						System.out.println(pid+"2");
					return 2;	
					}
				}	
			}
		}
		
		
		return flag;
		
		
	}
	public void closeobj()
	{
	System.gc();	
	}

}
