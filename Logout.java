package controller;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Logout
 */
@WebServlet("/Logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		String result=request.getParameter("Result");
		
		if(result.equals("user"))
		{
		session.removeAttribute("srno");
		session.removeAttribute("firstname");
		session.removeAttribute("lastname");
		session.invalidate();
		response.sendRedirect("USignIn.jsp?Result=Logoutsucessful");
		}
		else
		{
			session.removeAttribute("userName");
			session.invalidate();
			response.sendRedirect("ASignIn.jsp?Result=Logoutsucessful");	
		}
		
		
		}

}
