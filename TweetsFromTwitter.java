package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import databaseConnection.DbOperation;
import twitter4j.IDs;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

public class TweetsFromTwitter {
	ConfigurationBuilder cb=null;
	TwitterFactory tf=null;
	twitter4j.Twitter twitter=null;
	String screenname="";
	
public TweetsFromTwitter(String ck,String cs,String at,String ats) {
    cb=new ConfigurationBuilder();
	cb.setDebugEnabled(true)
	.setOAuthConsumerKey(ck)
	.setOAuthConsumerSecret(cs)
	.setOAuthAccessToken(at)
	.setOAuthAccessTokenSecret(ats);
	 tf=new TwitterFactory(cb.build());
	twitter=tf.getInstance();
	
}
	public List<Status> getTwitts() throws TwitterException {
		
		List<Status> status=twitter.getHomeTimeline();
		if(status.isEmpty())
		{
			System.out.println("empty");
		}
		for(Status s:status)
		{
			System.out.println(s.getUser().getName()+"-----------"+s.getText());
		}
		return status;
	}
     public String getselfdetails() throws TwitterException {
    	
    	 String details=twitter.showUser(twitter.getId()).toString();
    	 String []myvalue=details.split(",");
    	 String[] name=myvalue[1].split("=");
    	 name[1]=name[1].replaceAll("['']", "");
    	 System.out.println("name of my "+name[1]);
    	 String[] sname=myvalue[2].split("=");
    	 sname[1]=sname[1].replaceAll("['']", "");
    	 System.out.println("name of my "+sname[1]);
    	 String pp=null;
    	 
    	 for(String s:myvalue)
    	 {
    		 if(s.contains("profileImageUrlHttps"))
    		 {
    			 String[] pp1=s.split("=");
    	    	 pp=pp1[1].replaceAll("['']", ""); 
    		 }
    	 }
    	 screenname=sname[1];
    	 return name[1]+","+sname[1]+","+pp;
	}
     
     public void acceptrequest(String screenname) throws TwitterException, ClassNotFoundException, SQLException
     {
    	 twitter.createFriendship(screenname);
    	
     }
     
     public String friendlist(String screenname) throws TwitterException
     {
    	 String lists="";
    	 IDs followerIDs = twitter.getFollowersIDs(screenname, -1);
			long[] ids = followerIDs.getIDs();
			for (long id : ids) {
				
			   twitter4j.User user = twitter.showUser(id);
			   lists=user.getName()+","+lists;
			   //here i am trying to fetch the followers of each id
			   /*System.out.println("screenName: " + user.getScreenName());
			   System.out.println("Name: " + user.getName());*/
			   
			}
    	 return lists;
     }
}



