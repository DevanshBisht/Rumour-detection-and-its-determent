package controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import databaseConnection.DbOperation;


/**
 * Servlet implementation class FriendRequest
 */
@WebServlet("/FriendRequest")
public class FriendRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String data, query, fname, lname,srno;
	ResultSet rs = null;
	boolean empty = true;
	ArrayList<String> friendData = new ArrayList<String>();

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		fname = (String) session.getAttribute("firstname");
		lname = (String) session.getAttribute("lastname");
		srno = (String) session.getAttribute("srno");
		data = request.getParameter("serach");
		query = "select * from userrecords where name like'"+data+"%' or  screenname like'"+data+"%'";
		System.out.println(query);
		try 
		{
			rs=DbOperation.selectOperation(query);
			while(rs.next())
			{
				if(rs.getInt("srno")!=Integer.parseInt(srno))
				{
				friendData.add(rs.getString("ppfilename")+","+rs.getString("name")+","+rs.getString("screenName")+","+rs.getString("emailAddress")+","+rs.getString("dob")+","+rs.getString("gender")+","+rs.getString("srno"));
				empty=false;
				}
				empty=false;
			}	
			if(empty!=false)
			{
				response.sendRedirect("UserHomepage.jsp?Result=nouserfound");
			}
			else
			{
				session.setAttribute("data", friendData);
				response.sendRedirect("FriendRequrestResult.jsp?Result=userfound");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		
		
	

}
