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
 * Servlet implementation class SharedPost
 */
@WebServlet("/SharedPost")
public class SharedPost extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 int i=0;
	 String query;
	    DbOperation dbOperation;

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		try {
			dbOperation=new DbOperation();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		String pid=request.getParameter("pid");
		String id=(String)session.getAttribute("id");
		query="SELECT * FROM postdatabase WHERE srno='"+pid+"'";
		ResultSet rs;
		String type="";
		try {
			rs = DbOperation.selectOperation(query);
			if(rs.next())
			{
			type=rs.getString("type");	
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		/*DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date dateobj = new Date();
		String c_date = df.format(dateobj);*/
		Calendar calendar = Calendar.getInstance();
	      java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());

		query="INSERT INTO sharedpostdatabase(uid,pid,date,type) VALUES ('"+id+"','"+pid+"','"+startDate+"','"+type+"')";
		try {
			i=DbOperation.IUDOperation(query);
			if(i==1)
			{
			response.sendRedirect("UserHome.jsp?Result=postsucess");	
			}
			else
			{
		    response.sendRedirect("UserHome.jsp?Result=postfail");	
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
