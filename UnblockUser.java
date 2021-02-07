package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.TreeSet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sms.SMS;

import databaseConnection.DbOperation;

/**
 * Servlet implementation class UnblockUser
 */
@WebServlet("/UnblockUser")
public class UnblockUser extends HttpServlet {
	String uid,query,mobile;
    DbOperation dbOperation;
    TreeSet<Integer> t=new TreeSet<Integer>();
	public void init(ServletConfig config) throws ServletException {
		try {
			dbOperation=new DbOperation();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
    /**
     * @see HttpServlet#HttpServlet()
     */
  

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    uid=request.getParameter("uid");
		mobile=request.getParameter("mnumber");
		String name1=request.getParameter("name1");
		query="UPDATE userrecords set accountStatus='Active',blockcount=0,endDate=0000-00-00,startDate=0000-00-00 where srno="+uid;
		try {
			dbOperation.IUDOperation(query);
			String abc = "8908";
            String msgC = "Hello "+name1+"\nAdmin unblock you.";
            String myURL = "http://sms.wecreatead.com/API/WebSMS/Http/v1.0a/index.php";
          
            SMS.callURL(msgC, mobile);
            response.sendRedirect("AdminHomepage.jsp?Result=");	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
