package controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sms.CurrentDateTimevalue;
import com.sms.SMS;

import twitter4j.Status;
import twitter4j.TwitterException;
import databaseConnection.DbOperation;

/**
 * Servlet implementation class BlockPost
 */
@WebServlet("/BlockPost")
public class BlockPost extends HttpServlet {
	private static final long serialVersionUID = 1L;
    String pid,uid,query,postm;
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		t.clear();
		System.out.println("here");
		pid=request.getParameter("pid");
		
		uid=request.getParameter("uid");
		System.out.println(uid);
		t.add(Integer.parseInt(uid));
		postm=request.getParameter("post");
		System.out.println(pid+uid);
		try {
			
			
			//userrecords
			query="UPDATE postdatabase SET status='Block' where srno='"+pid+"'";
			DbOperation.IUDOperation(query);
			query="UPDATE likedislike set poststatus='Block' where pid='"+pid+"'";
			DbOperation.IUDOperation(query);
			query="UPDATE commentdatabase set cmtstatus='Block',poststatus='Block' where pid='"+pid+"'";
			DbOperation.IUDOperation(query);
			query="UPDATE `sharedpostdatabase` SET `poststatus`='Block' WHERE `pid` ="+pid+"";
			DbOperation.IUDOperation(query);
			query="SELECT *FROM `sharedpostdatabase` WHERE `pid` ="+pid;
			ResultSet rs=DbOperation.selectOperation(query);
			while(rs.next())
			{
				t.add(rs.getInt("uid"));
			}
			Iterator< Integer> itr=t.iterator();
			while(itr.hasNext())
			{
			int srno=itr.next();
			query="SELECT * FROM `userrecords` WHERE `srno` ="+srno;
			rs=DbOperation.selectOperation(query);
			String ck=""; 
			String cs="";
			String at="";
			String ats="";
			String sn="";
			if(rs.next())
			{
				sn=rs.getString("screenname");
				ck=rs.getString("setOAuthConsumerKey"); 
				cs=rs.getString("setOAuthConsumerSecret");
				at=rs.getString("setOAuthAccessToken");
				ats=rs.getString("setOAuthAccessTokenSecret");
			}
			System.out.println(ck+" "+cs+" "+at+" "+ats);
			TweetsFromTwitter tweet=new TweetsFromTwitter(ck, cs, at, ats);
			List<Status> status=tweet.getTwitts();
			long tweetid=0L;
			for(Status s:status)
			{
				if(s.getText().contains(postm))
				{
					if(s.getUser().getScreenName().equals(sn))
					{
				     tweetid=s.getId();
				     System.out.println("tweetid "+tweetid);
					}
				}
			}
			tweet.twitter.destroyStatus(tweetid);
			
			CurrentDateTimevalue a=new CurrentDateTimevalue();
			String cdt=a.getCurrentDate();
			String cdt_15mn=a.getCurrentDateTimevalue(cdt);
			
			
			query="update userrecords set accountStatus='Block',blockcount = blockcount + 1,startDate='"+cdt+"',endDate='"+cdt_15mn+"' where srno='"+srno+"'";
			DbOperation.IUDOperation(query);
			SMS sms=new SMS();
			
			GlobalFunction gf=new GlobalFunction();
			String mobno=gf.getMobileNumber(srno);
			sms.callURL("Dear User Your Account is Block",mobno);
			
			
			}
			response.sendRedirect("AdminHomepage.jsp?Result=");	
		} catch (Exception e) {
			System.out.println(e);
		}
			
	}

}
