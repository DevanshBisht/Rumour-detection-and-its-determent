package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import twitter4j.TwitterException;
import databaseConnection.ConnectionProvider;
import databaseConnection.DbOperation;

/**
 * Servlet implementation class PostMsg
 */
@WebServlet("/PostMsg")
public class PostMsg extends HttpServlet {
	private static final long serialVersionUID = 1L;
    String postmsg,query;
    int i=0;
    
    Connection con=null;
    //DbOperation dbOperation;
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		try {
		//	dbOperation=new DbOperation();
			con=ConnectionProvider.getConnection();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		postmsg=request.getParameter("postmsg");
		System.out.println(postmsg);
		postmsg=postmsg.trim();
		
			
		/*DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date dateobj = new Date();
		String c_date = df.format(dateobj);*/
		String ck=(String)session.getAttribute("ck"); 
		String cs=(String)session.getAttribute("cs");
		String at=(String)session.getAttribute("at");
		String ats=(String)session.getAttribute("ats");
		String srno=(String)session.getAttribute("srno");
		TweetsFromTwitter tweet=new TweetsFromTwitter(ck, cs, at, ats);
		
		Calendar calendar = Calendar.getInstance();
	    java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());
        Algorithm algorithm=new Algorithm(postmsg);

		try {
		 String type=algorithm.MyAlgorithm();
		
		 
		 
		/*query="INSERT INTO postdatabase(userid,post,date,type) VALUES ('"+Integer.parseInt(srno)+"','"+postmsg+"','"+startDate+"','"+type+"')";
		System.out.println(query);
		
		i=DbOperation.IUDOperation(query);
		*/
		PreparedStatement ps=con.prepareStatement("INSERT INTO postdatabase(userid,post,date,type) VALUES (?,?,?,?)");
		ps.setInt(1, Integer.parseInt(srno));
		ps.setString(2, postmsg);
		ps.setDate(3, startDate);
		ps.setString(4, type);
		
		i=ps.executeUpdate();
		if(i==1)
			{
			tweet.twitter.updateStatus(postmsg+"                         "+type);
			response.sendRedirect("UserHomepage.jsp?Result=postsucess");	
			}
			else
			{
		    response.sendRedirect("UserHomepage.jsp?Result=postfail");	
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}