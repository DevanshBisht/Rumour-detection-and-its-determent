package controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import twitter4j.TwitterException;
import databaseConnection.DbOperation;

/**
 * Servlet implementation class ShareTweet
 */
@WebServlet("/ShareTweet")
public class ShareTweet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String value,query,post,sn,posttoshare,type;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int i=0;
	    int pid=0;
	    int puid=0;
		HttpSession session=request.getSession();
		post=request.getParameter("post");
		sn=request.getParameter("sn");
		String uid=(String)session.getAttribute("srno");
		int id=Integer.parseInt(uid);
		String ck=(String)session.getAttribute("ck"); 
		String cs=(String)session.getAttribute("cs");
		String at=(String)session.getAttribute("at");
		String ats=(String)session.getAttribute("ats");
		TweetsFromTwitter tweet=new TweetsFromTwitter(ck, cs, at, ats);
		Calendar calendar = Calendar.getInstance();
	    java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());
		
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
				type=rs.getString("type");
				posttoshare=rs.getString("post");
			}
			else
			{
				query="SELECT * FROM  `postdatabase` WHERE  `post` =  '"+post+"'";
				System.out.println(query);
				rs=DbOperation.selectOperation(query);
				if(rs.next())
				{
					pid=rs.getInt("srno");
					query="SELECT * FROM `sharedpostdatabase` WHERE `uid` ="+puid+" AND `pid` ="+pid+"";
					System.out.println(query);
					ResultSet rs1=DbOperation.selectOperation(query);
					if(rs1.next())
					{
					type=rs.getString("type");
					posttoshare=rs.getString("post");
					}
				}	
			}
			query="INSERT INTO `sharedpostdatabase`(`uid`, `pid`, `type`, `date`,  `poststatus`)"
					+ " VALUES ('"+uid+"','"+pid+"','"+type+"','"+startDate+"','Active')";
			System.out.println(query);
			
				i=DbOperation.IUDOperation(query);
				if(i==1)
				{
				tweet.twitter.updateStatus(posttoshare+"                         "+type);
				response.sendRedirect("UserHomepage.jsp?Result=postsucess");	
				}
				else
				{
			    response.sendRedirect("UserHomepage.jsp?Result=postfail");	
				}
				
		} catch (Exception e1) {
			
		}
	      
	    	 
		  		
	      }
	
	
	}


