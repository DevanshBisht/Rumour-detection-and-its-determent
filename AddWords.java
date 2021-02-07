package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import databaseConnection.DbOperation;

/**
 * Servlet implementation class AddWords
 */
@WebServlet("/AddWords")
public class AddWords extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public String word,db,query;
    int count=0;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		count=0; 
		word=request.getParameter("word");
         db=request.getParameter("db");
         System.out.println(db);
         if(db.equals("p"))
 		{
 		
 		query="INSERT INTO `positivewords`(`words`) VALUES ('"+word+"')";
 		}
 		if(db.equals("n"))
 		{
 			query="INSERT INTO `negativewords`(`words`) VALUES ('"+word+"')";
 		}
 		if(db.equals("s"))
 		{
 		query="INSERT INTO `stressdataset`(`words`) VALUES ('"+word+"')";
 		}
 		try {
 			System.out.println(query);
			count=DbOperation.IUDOperation(query);
			if(count==1)
			{
				response.sendRedirect("AdminHomepage.jsp?Result=add");
			}
			else
			{
				response.sendRedirect("AdminHomepage.jsp?Result=error");
			}
		} catch (Exception e) {
			response.sendRedirect("AdminHomepage.jsp?Result=add");
		}
		
	}

}
