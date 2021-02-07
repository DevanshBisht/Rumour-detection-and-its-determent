package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import databaseConnection.DbOperation;

/**
 * Servlet implementation class SendFriendRequest
 */
@WebServlet("/SendFriendRequest")
public class SendFriendRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
    String requestf,requestt,query; 
    int count=0;
    
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	requestf=request.getParameter("requestfrom");
	requestt=request.getParameter("requestto");
	System.out.println("Request from "+requestf+" to "+requestt);
	query="insert into friendstatus (requestFrom,requestTo) values('"+requestf+"','"+requestt+"')";
	try {
		count=DbOperation.IUDOperation(query);
		if(count==1)
		{
			response.sendRedirect("FriendRequrestResult.jsp?Result=sucess");	
		}
		else
		{
			response.sendRedirect("FriendRequrestResult.jsp?Result=sendfail");
		}
		
	} catch (Exception e) {
	
		e.printStackTrace();
	}
	}

	

}
