package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import databaseConnection.ConnectionProvider;
import databaseConnection.DbOperation;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class BlockingProcess 
{
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
	        try 
	        {
				
            	Connection con=ConnectionProvider.getConnection();
        	    
            	System.out.println("\n\n\n");
	        	
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
	            
	            for (Status tweet : tweets) 
	            {
	            	int count=0;
	            	
	            	
	            		String sate=tweet.getText();
	            	
	            		
	            allRelatedTweets.add(sate);		
	            		
	            allRelatedTweets.size();
	            
		        for(int io=0;io<allRelatedTweets.size();io++)
	            {
	            	
	            	System.out.println(io+" Tweets is "+allRelatedTweets.get(io)+"\n");
	            	String s2=allRelatedTweets.get(io);
	            	s2=s2.replaceAll(",", " ");
	            	s2=s2.replaceAll("'", " ");
	            	System.out.println("S2 is "+s2);
	            	
	            //	DbOperation obj=new DbOperation();
	            	try 
	            	{
			    	PreparedStatement ps=con.prepareStatement("INSERT INTO `related_tweets`(`tweet_id`, `tweet_data`) VALUES (?,?)");
			    	ps.setInt(1, pid);
			    	ps.setString(2,allRelatedTweets.get(io) );
	            	int r=ps.executeUpdate();
	            	
	            	System.out.println("Result is "+r);
	            	
					} catch (Exception e) 
					{
						
						System.out.println("Exc "+e);
						// TODO: handle exception
					}
	            	
	            	
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
//System.out.println("News Count "+newCount);
  return newCount;
}
}
