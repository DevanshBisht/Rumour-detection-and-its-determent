package controller;


import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import twitter4j.TwitterException;
import databaseConnection.DbOperation;

/**
 * Servlet implementation class CreateAccount
 */
@WebServlet("/CreateAccount")
public class CreateAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public String query,email,mobile,dob,gender,password,ck,cs,at,ats,name,sname,pp;
    int count=0;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
               count=0;
		       email=request.getParameter("email");
               mobile=request.getParameter("mobile");
               password=request.getParameter("password");
               gender=request.getParameter("Gender");
               dob=request.getParameter("dob");
               ck=request.getParameter("ck");
               cs=request.getParameter("cs");
               at=request.getParameter("at");
               ats=request.getParameter("ats");
		        ck=ck.trim();
		        cs=cs.trim();
		        at=at.trim();
		        ats=ats.trim();
				TweetsFromTwitter tweet=new TweetsFromTwitter(ck, cs, at, ats);
				String[] details;
				try {
					details = tweet.getselfdetails().split(",");
					 name=details[0];
			            sname=details[1];
			            pp=details[2];
				query="INSERT INTO userrecords(name,screenname,emailAddress,mobileNumber,password,dob,gender,setOAuthConsumerKey,setOAuthConsumerSecret,setOAuthAccessToken,setOAuthAccessTokenSecret,ppfilename ) VALUES "
						+ "('"+name+"','"+sname+"','"+email+"','"+mobile+"','"+password+"','"+dob+"','"+gender+"','"+ck+"','"+cs+"','"+at+"','"+ats+"','"+pp+"')";
				System.out.println(query);
					count=DbOperation.IUDOperation(query);
					System.out.println(count);
					if(count==1)
					{
						response.sendRedirect("USignIn.jsp?Result=RegistrationSucess");
					}
					else
					{
						response.sendRedirect("Signup.jsp?Result=Registrationfail");
					}
				} catch (Exception e) {
					
				}
	           
		} 
	


}