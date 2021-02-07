package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import databaseConnection.ConnectionProvider;
import databaseConnection.DbOperation;

public class GlobalFunction 
{

	public String getMaxPostID()
	{
		String max_id="";
		
		try {
			DbOperation dbOperation=new DbOperation();
			ResultSet rs=DbOperation.selectOperation("SELECT MAX(srno) FROM postdatabase");
			if(rs.next())
			{
				max_id=rs.getString(1);
			}
			System.out.println("Max ID Is "+max_id);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return max_id;
	}

	

	public String getPost(String p_id)
	{
		String result="";
		
		try {
			DbOperation dbOperation=new DbOperation();
			ResultSet rs=DbOperation.selectOperation("SELECT * FROM postdatabase where srno='"+p_id+"'");
			if(rs.next())
			{
				result=rs.getString("post");
			}
			System.out.println("Result Is "+result);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}

	
	public void CheckPost(String p_id)
	{
		try 
		{
			
			DbOperation dbOperation=new DbOperation();
			
			ResultSet rs=DbOperation.selectOperation("SELECT * FROM postdatabase where srno='"+p_id+"'");

			while(rs.next())
			{
				Integer pid1=rs.getInt(1);
				int pid_r=rs.getInt(1);
				Integer uid1=rs.getInt(2);
				String post=rs.getString(3);
				post=post.trim();
				String pid=pid1.toString();
				String uid=uid1.toString();
				
				System.out.println("Post is "+post);
				
				int newCount=BlockingProcess.CheckPostOnTwitterNetwork(pid_r,post);
				
				System.out.println("New Count at admin side "+newCount);
				System.out.println("user id "+uid);
				if(newCount<2)
				{
					
					BlockPostAuto.AutoBlock(pid1, uid1, post);
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	public static int CheckPostOnTwitterNetwork ( int pid ,String msg) throws ClassNotFoundException, SQLException {
		int newCount=0;
		String post=msg;
		//System.out.println("Post for Checking "+post);
		
		String []tokens=post.split(" ");
	    ConfigurationBuilder cb = new ConfigurationBuilder();
	    cb.setDebugEnabled(true)
	          .setOAuthConsumerKey("vbiiUvX3QuP9FQeaTMBuMuHit")
	          .setOAuthConsumerSecret("sxjyTZ1Ge4cALe9pXIp4mvhxoDPG8mmRlCFONIhtLsPVWUdzOk")
	          .setOAuthAccessToken("1001685556605145088-6UEW1B7x5fv91dLUyhuKHlN3Xye9TM")
	          .setOAuthAccessTokenSecret("vC3nEG1l1ZRWARIahvVfrMb4qpbFVoJpkvsMGfb8EBAto");
	    
	    TwitterFactory tf = new TwitterFactory(cb.build());
	    Twitter twitter = tf.getInstance();
	        try {
	        	
	        	System.out.println("Ritesh Surange \n\n\n");
	        	
	            Query query = new Query(post);
	            QueryResult result;
	            result = twitter.search(query);
	            List<Status> tweets = result.getTweets();
	            
	            
	          /*  for(int io=0;io<tweets.size();io++)
	            {
	            	
	            	System.out.println("Tweets is "+tweets.get(io));
	            	
	            }
	            */
	            
	            ArrayList<String> allRelatedTweets=new ArrayList<String>();
	            
	            
	            
	            System.out.println(tweets.size());
	            
	            System.out.println("before"+newCount);
	            
	    Connection con=ConnectionProvider.getConnection();
	    
	            
	            for (Status tweet : tweets) 
	            {
	            	int count=0;
	            	
	            	
	            		String sate=tweet.getText();
	            	
	            		
	            allRelatedTweets.add(sate);		
	            		
	            allRelatedTweets.size();
	            
	            
		           for(int io=0;io<allRelatedTweets.size();io++)
	            {
	            	
	            	System.out.println(io+" Tweets is "+allRelatedTweets.get(io)+"\n");
	            	
	            	//DbOperation obj=new DbOperation();
	            	PreparedStatement ps=con.prepareStatement("INSERT INTO `related_tweets`(`tweet_id`, `tweet_data`) VALUES ('"+pid+"','"+allRelatedTweets.get(io)+"')");
	            	int r=ps.executeUpdate();
	            	System.out.println("I is "+r);
	            	
	            }


	            		
	            	System.out.println("State is "+sate);
	            	
	            	
	            	
	            String sate1=tweet.getUser().getScreenName();
	            
	            System.out.println("Sate 1 "+sate1);
	            //DbOperation dbOperation=new DbOperation();
	            String query1="SELECT * FROM `userrecords` WHERE `screenname` = '"+sate1+"'";
	            
	            System.out.println(query1);
	            
	        	ResultSet rs=DbOperation.selectOperation(query1);
	        	int counter=0;
	        	while(rs.next())
	        	{
	        		//System.out.println(rs.getString("name"));
	        		counter++;
	        	}
	        	
	        	System.out.println("Counter is "+counter);
                /*rs.afterLast();
                
                counter=rs.getRow();
                System.out.println("counter "+counter);
                
              */
                
	        	if(counter==0)
                {
	            //System.out.println(sate);
	            for(String token:tokens)
	            {
	           	
	            sate=sate.toUpperCase();
	            token=token.toUpperCase();
	            	if(sate.contains(token))
	            	{
	            		//System.out.println(token+" vs "+sate);
	            		count++;
	            	}
	            }
	            
	            if(tokens.length==count)
	            {
	               //System.out.println(tweet.getText());
	               //System.out.println("Count of token "+count);
	               //System.out.println("\n");
	               newCount++;
	            }
	          
	           // System.out.println("new Count "+newCount);
	            }


	        } 
	        }catch (TwitterException te) {
	            te.printStackTrace();
	            System.out.println("Failed to search tweets: " + te.getMessage());
	            System.exit(-1);
	        }
  return newCount;
}
	
	public String getMobileNumber(int srno) {
		
		String rsl="";
		try {
			Connection con=ConnectionProvider.getConnection();
			PreparedStatement ps=con.prepareStatement("Select * from userrecords where srno='"+srno+"'");
			ResultSet rs=ps.executeQuery();
			if(rs.next())
			{
				rsl=rs.getString("mobileNumber");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return rsl;
		
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
