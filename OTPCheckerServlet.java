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

import com.sms.Otp;
import com.sms.SMS;

import databaseConnection.DbOperation;

/**
 * Servlet implementation class OTPCheckerServlet
 */
@WebServlet("/OTPCheckerServlet")
public class OTPCheckerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String data, query, fname, lname, srno, otpe, otpm;
	ResultSet rs = null;
	boolean empty = true;
	ArrayList<String> friendData = new ArrayList<String>();

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession httpSession = request.getSession();
		fname = (String) httpSession.getAttribute("firstname");
		lname = (String) httpSession.getAttribute("lastname");
		srno = (String) httpSession.getAttribute("srno");
		otpm = (String) httpSession.getAttribute("otpm");
		otpe = request.getParameter("otp");
		if (otpm.equals(otpe)) {
			response.sendRedirect("UserHomepage.jsp?Result=ok");
		} else {
			response.sendRedirect("OTPChecker.jsp?Result=fail");
		}
	}
}
