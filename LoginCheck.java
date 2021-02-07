package controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




import javax.servlet.http.HttpSession;

import com.sms.Otp;
import com.sms.SMS;

import databaseConnection.DbOperation;

/**
 * Servlet implementation class LoginCheck
 */
@WebServlet("/LoginCheck")
public class LoginCheck extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    String email,password,attribute,query;
    HttpSession httpSession=null;
    ResultSet rs;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		httpSession=request.getSession();
		email=request.getParameter("email");
		attribute=request.getParameter("attribute");
		password=request.getParameter("password");
		System.out.println(attribute);
		if(attribute.equals("userrecords"))
		{
			query="select * from userrecords where emailAddress ='"+email+"' And password = '"+password+"'";
			System.out.println(query);
		}
		else
		{
			query="select * from adminrecords where userName ='"+email+"' And password = '"+password+"'";
			System.out.println(query);
		}
		try {
			rs=DbOperation.selectOperation(query);
			if(rs.next())
			{
				if(attribute.equals("userrecords"))
				{
					String astatus=rs.getString("accountStatus");
					if(astatus.equals("Active"))
					{
					    String mobileNumber=rs.getString("mobileNumber");
					    httpSession.setAttribute("name", rs.getString("Name"));
						httpSession.setAttribute("sname", rs.getString("screenname"));
						httpSession.setAttribute("pp", rs.getString("ppfilename"));
						httpSession.setAttribute("ck", rs.getString("setOAuthConsumerKey"));
						httpSession.setAttribute("cs", rs.getString("setOAuthConsumerSecret"));
						httpSession.setAttribute("at", rs.getString("setOAuthAccessToken"));
						httpSession.setAttribute("ats", rs.getString("setOAuthAccessTokenSecret"));
						Integer srno=rs.getInt("srno");
		                Otp otp = new Otp();
		                String otpmsg = otp.skKeyGenerate();
		                String abc = "8908";
		                System.out.println("OTP Message is "+otpmsg);
		                String msgC = "Hello Mr " + rs.getString("screenname") + "\nYour login OTP is " + otpmsg;
		                String myURL = "http://sms.wecreatead.com/API/WebSMS/Http/v1.0a/index.php";
		                httpSession.setAttribute("otpm",otpmsg);
		     
		                
		                SMS.callURL(msgC, mobileNumber);
				        httpSession.setAttribute("srno",srno.toString());
					    response.sendRedirect("OTPChecker.jsp?Result=ok");
					  }
					else if(astatus.equals("Block"))
					  {
						  
						response.sendRedirect("USignIn.jsp?Result=blocked");
					  }
				}
				else
				{
					httpSession.setAttribute("userName", rs.getString("userName"));
					/*httpSession.setAttribute("ck", rs.getString("setOAuthConsumerKey"));
					httpSession.setAttribute("cs", rs.getString("setOAuthConsumerSecret"));
					httpSession.setAttribute("at", rs.getString("setOAuthAccessToken"));
					httpSession.setAttribute("ats", rs.getString("setOAuthAccessTokenSecret"));*/
					response.sendRedirect("AdminHomepage.jsp?Result=ok");
				}
			}
			else
			{
				if(attribute.equals("userrecords"))
				{
					response.sendRedirect("USignIn.jsp?Result=loginfail");
				}
				else
				{
					response.sendRedirect("ASignIn.jsp?Result=loginfail");
				}
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

}
