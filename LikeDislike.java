package controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import databaseConnection.DbOperation;

/**
 * Servlet implementation class LikeDislike
 */
@WebServlet("/LikeDislike")
public class LikeDislike extends HttpServlet {
	private static final long serialVersionUID = 1L;
    String value,query,post,sn;
    
    
   
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	
	

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int i=0;
	    int pid=0;
	    int puid=0;
		HttpSession session=request.getSession();
		value=request.getParameter("value");
		post=request.getParameter("post");
		sn=request.getParameter("sn");
		String uid=(String)session.getAttribute("srno");
		int id=Integer.parseInt(uid);
		Calendar calendar = Calendar.getInstance();
	      java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());
	      System.out.println(startDate);
	      try {
	    	  String query="SELECT * FROM  `userrecords`WHERE  `screenname` =  '"+sn+"'";
	    	  System.out.println(query);
			ResultSet rs=DbOperation.selectOperation(query);
			if(rs.next())
				puid=rs.getInt("srno");
			query="SELECT * FROM  `postdatabase` WHERE  `userid` ="+puid+" AND  `post` =  '"+post+"'";
			System.out.println(query);
			rs=DbOperation.selectOperation(query);
			if(rs.next())
			{	
				pid=rs.getInt("srno");
				if(value.equals("like"))
			      {
			    	  query="INSERT INTO likedislike (pid,uid,lddate, likepost,dislikepost) VALUES ('"+pid+"','"+id+"','"+startDate+"','1','0')";
			  		
			  			i=DbOperation.IUDOperation(query);
			  			if(i==1)
			  			{
			  				DbOperation.IUDOperation("UPDATE postdatabase SET likecount = likecount + 1 WHERE srno = '"+pid+"'");
			  			response.sendRedirect("UserHomepage.jsp?Result=");	
			  			}
			  			else
			  			{
			  		    response.sendRedirect("UserHomepage.jsp?Result=");	
			  			}
			  		
		  
			      }
			      else
			      {
			    	  query="INSERT INTO likedislike (pid,uid,lddate, likepost,dislikepost) VALUES ('"+pid+"','"+id+"','"+startDate+"','0','1')";
				  		
				  			i=DbOperation.IUDOperation(query);
				  			if(i==1)
				  			{
				  				DbOperation.IUDOperation("UPDATE postdatabase SET dislikecount = dislikecount + 1 WHERE srno = '"+pid+"'");
				  			response.sendRedirect("UserHomepage.jsp?Result=");	
				  			}
				  			else
				  			{
				  		    response.sendRedirect("UserHomepage.jsp?Result=");	
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
					if(value.equals("like"))
				      {
				    	  query="INSERT INTO likedislike (pid,uid,lddate, likepost,dislikepost) VALUES ('"+pid+"','"+id+"','"+startDate+"','1','0')";
				  		
				  			i=DbOperation.IUDOperation(query);
				  			if(i==1)
				  			{
				  				DbOperation.IUDOperation("UPDATE postdatabase SET likecount = likecount + 1 WHERE srno = '"+pid+"'");
				  			response.sendRedirect("UserHomepage.jsp?Result=");	
				  			}
				  			else
				  			{
				  		    response.sendRedirect("UserHomepage.jsp?Result=");	
				  			}
				  		
			  
				      }
				      else
				      {
				    	  query="INSERT INTO likedislike (pid,uid,lddate, likepost,dislikepost) VALUES ('"+pid+"','"+id+"','"+startDate+"','0','1')";
					  		
					  			i=DbOperation.IUDOperation(query);
					  			if(i==1)
					  			{
					  				DbOperation.IUDOperation("UPDATE postdatabase SET dislikecount = dislikecount + 1 WHERE srno = '"+pid+"'");
					  			response.sendRedirect("UserHomepage.jsp?Result=");	
					  			}
					  			else
					  			{
					  		    response.sendRedirect("UserHomepage.jsp?Result=");	
					  			}
					  		
				      }	
				}
			}
				
		} catch (Exception e1) {
			
		}
	      
	}
	
	}


