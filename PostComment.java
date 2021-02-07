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
 * Servlet implementation class PostComment
 */
@WebServlet("/PostComment")
public class PostComment extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String value,query,post,sn;
	DbOperation dbOperation;
	public void init(ServletConfig config) throws ServletException {
		try {
			dbOperation=new DbOperation();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int i=0;
	    int pid=0;
	    int puid=0;
		HttpSession session=request.getSession();
		value=request.getParameter("postmsg");
		post=request.getParameter("post");
		sn=request.getParameter("sn");
		String uid=(String)session.getAttribute("srno");
		int id=Integer.parseInt(uid);
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
			query="INSERT INTO commentdatabase( pid,commentby,comments,date) VALUES ('"+pid+"','"+uid+"','"+value+"','"+startDate+"')";
				i=DbOperation.IUDOperation(query);
				if(i==1)
				{
				response.sendRedirect("UserHomepage.jsp?Result=cmtsucess");	
				}
				else
				{
			    response.sendRedirect("UserHomepage.jsp?Result=cmtfail");	
				}	
			}
			else
			{
				query="SELECT * FROM  `postdatabase` WHERE  `post` =  '"+post+"'";
				System.out.println(query);
				rs=DbOperation.selectOperation(query);
				if(rs.next())
		        pid=rs.getInt("srno");
				query="SELECT * FROM `sharedpostdatabase` WHERE  `uid` ="+puid+" AND  `pid` =  '"+pid+"'";
				System.out.println(query);
				rs=DbOperation.selectOperation(query);
				if(rs.next())
				pid=rs.getInt("pid");
				query="INSERT INTO commentdatabase( pid,commentby,comments,date) VALUES ('"+pid+"','"+uid+"','"+value+"','"+startDate+"')";
					i=DbOperation.IUDOperation(query);
					if(i==1)
					{
					response.sendRedirect("UserHomepage.jsp?Result=cmtsucess");	
					}
					else
					{
				    response.sendRedirect("UserHomepage.jsp?Result=cmtfail");	
					}	
			}
		} catch (Exception e1) {
			
		}
	}

}
