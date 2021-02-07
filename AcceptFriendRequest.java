package controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import twitter4j.TwitterException;
import databaseConnection.DbOperation;

/**
 * Servlet implementation class AcceptFriendRequest
 */
@WebServlet("/AcceptFriendRequest")
public class AcceptFriendRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
String requestfrom,requestto,query,screenname;
	
	public void init(ServletConfig config) throws ServletException {
		
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		String ck=(String)session.getAttribute("ck"); 
		String cs=(String)session.getAttribute("cs");
		String at=(String)session.getAttribute("at");
		String ats=(String)session.getAttribute("ats");
		String srno=(String)session.getAttribute("srno");
		TweetsFromTwitter tweet=new TweetsFromTwitter(ck, cs, at, ats);
		requestfrom=request.getParameter("requestfrom");
		requestto=request.getParameter("requestto");
		screenname=request.getParameter("screenname");
		query="update friendstatus SET requestStatus='Friend' where requestFrom='"+requestfrom+"' and requestTo='"+requestto+"' and requestStatus='Pending'";
		try {
			tweet.getselfdetails();
			String fname=tweet.screenname;
			System.out.println("name is="+fname);
			System.out.println("name is="+screenname);
			tweet.acceptrequest(screenname);
			ResultSet rs=DbOperation.selectOperation("select * from userrecords where screenname='"+screenname+"'");
	    	 if(rs.next())
	    	 {
	    		 System.out.println();
	    		 TweetsFromTwitter tweet1=new TweetsFromTwitter(rs.getString("setOAuthConsumerKey"), rs.getString("setOAuthConsumerSecret"), rs.getString("setOAuthAccessToken"), rs.getString("setOAuthAccessTokenSecret"));
	    		 tweet1.acceptrequest(fname);
	    	 }
			
			int i=DbOperation.IUDOperation(query);
			if(i==1)
			{
				response.sendRedirect("UserHomepage.jsp?Result=friendrequestaccept");	
			}
			else
			{
				response.sendRedirect("UserHomepage.jsp?Result=unabletoacceptfriendrequest");	
			}
		} catch (Exception e) {
			
			System.out.println(e);
		}
		
				
				
	}

}
