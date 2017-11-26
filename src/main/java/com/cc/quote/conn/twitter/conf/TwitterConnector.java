package com.cc.quote.conn.twitter.conf;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.cc.quote.common.propery.ProperyReader;
import com.google.gdata.data.dublincore.Date;

import twitter4j.DirectMessage;
import twitter4j.IDs;
import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Trend;
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;


public class TwitterConnector {
	private String TWITTER_CONSUMER_KEY = null;
    private String TWITTER_CONSUMER_SECRET = null;
    private String TWITTER_ACCESS_TOKEN = null;
    private String TWITTER_ACCESS_SECRET = null;   
    private String auther = "arjuncc";   
    private Twitter twitter = null; 
    private static MessgaeFromAuther lastMessgaeFromAuther = null;
    public void init() {
    	ProperyReader properyReader = new ProperyReader();
    	this.TWITTER_CONSUMER_KEY = properyReader.getValue("TWITTER_CONSUMER_KEY");           
    	this.TWITTER_CONSUMER_SECRET = properyReader.getValue("TWITTER_CONSUMER_SECRET"); 
    	this.TWITTER_ACCESS_TOKEN = properyReader.getValue("TWITTER_ACCESS_TOKEN"); 
    	this.TWITTER_ACCESS_SECRET = properyReader.getValue("TWITTER_ACCESS_SECRET");   
    	
    	
    	ConfigurationBuilder cb = new ConfigurationBuilder();
    	cb.setDebugEnabled(true)
    	  .setOAuthConsumerKey(TWITTER_CONSUMER_KEY)
    	  .setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET)
    	  .setOAuthAccessToken(TWITTER_ACCESS_TOKEN)
    	  .setOAuthAccessTokenSecret(TWITTER_ACCESS_SECRET);
    	TwitterFactory tf = new TwitterFactory(cb.build());
    	twitter = tf.getInstance();
    }
    
    
    public String tweet(String tweetContent) throws TwitterException {
        Status status = twitter.updateStatus(tweetContent);
        return status.getText();
    }
    
    public String tweet(String tweetContent, String fileUrl) throws TwitterException, IOException {
    	URL url = new URL(fileUrl);
    	StatusUpdate statusUpdate = new StatusUpdate(tweetContent);
    	URLConnection urlConnection = url.openConnection();
    	InputStream in = new BufferedInputStream(urlConnection.getInputStream());
    	statusUpdate.setMedia(getImageName()+".jpeg", in);
    	Status status = twitter.updateStatus(statusUpdate);
        return status.getText();
    }
    
    private String getImageName() {
    	return new Date().getValue();
    }
    
    public String retweet(String tweetId) throws TwitterException {
    	Status status =  twitter.showStatus(Long.parseLong(tweetId));
        twitter.retweetStatus(Long.parseLong(tweetId));
        return status.getUser().getScreenName();
    }
    
     /*  

    long cursor = -1;
PagableResponseList<User> followers;
do {
     followers = twitter.getFollowersList("screenName", cursor);
    for (User follower : followers) {
        // TODO: Collect top 10 followers here
        System.out.println(follower.getName() + " has " + follower.getFollowersCount() + " follower(s)");
    }
} while ((cursor = followers.getNextCursor()) != 0);
    
    */
    public String getRandomTrendingUser() throws TwitterException {
    	List<String> trendingTopicList = getTrendingGlobal(10);
    	String trendTag = trendingTopicList.get(getRandomInt(trendingTopicList.size()));
    	List<String> userList = getTweetOwner(10, trendTag);
    	return userList.get(getRandomInt(userList.size()));
    }
    
    public String getRandomTrendingUserIndia() throws TwitterException {
    	List<String> trendingTopicList = getTrendingLocal(10, 23424848);
    	String trendTag = trendingTopicList.get(getRandomInt(trendingTopicList.size()));
    	List<String> userList = getTweetOwner(10, trendTag);
    	return userList.get(getRandomInt(userList.size()));
    }
    
    public String getRandomTrendingQuote() throws TwitterException {
    	return getRandomTrendingQuote(false);
    }
    public String getRandomTrendingQuoteWithoutReTweet() throws TwitterException {
    	return getRandomTrendingQuote(true);
    }
    
    public String getRandomTrendingQuote(boolean avoidReTweet) throws TwitterException {
    	List<String> trendingTopicList = getTrendingGlobal(10);
    	String trendTag = trendingTopicList.get(getRandomInt(trendingTopicList.size()));
    	List<String> tweetList = getTweets(10, trendTag, avoidReTweet);
    	while (tweetList.isEmpty()) {
    		//System.out.println("inide while");
    		tweetList = getTweets(10, trendTag, avoidReTweet);
    	}
    	//System.out.println("----------------------------------List : "+tweetList);
    	return tweetList.get(getRandomInt(tweetList.size()));
    }
    
    public String getRandomTrendingQuoteIndia() throws TwitterException {
    	return getRandomTrendingQuoteIndia(false);
    }
    
    public String getRandomTrendingQuoteIndiaWithoutReTweet() throws TwitterException {
    	return getRandomTrendingQuoteIndia(true);
    }
    
    public String getRandomTrendingQuoteIndia(boolean avoidReTweet) throws TwitterException {
    	List<String> trendingTopicList = getTrendingLocal(10, 23424848);
    	String trendTag = trendingTopicList.get(getRandomInt(trendingTopicList.size()));
    	List<String> tweetList = getTweets(10, trendTag, avoidReTweet);
    	while (tweetList.isEmpty()) {
    		//System.out.println("inide while");
    		tweetList = getTweets(10, trendTag, avoidReTweet);
    	}
    	//System.out.println("----------------------------------List ind : "+tweetList);
    	return tweetList.get(getRandomInt(tweetList.size()));
    }
    
    public List<String> getTrendingGlobal(int number) throws TwitterException {
    	Trends trends = twitter.getPlaceTrends(1);
    	List<String> trendingTopicList = new ArrayList<String>();
        int count = 0;
        for (Trend trend : trends.getTrends()) {
            if (count < number) {
                //System.out.println(trend.getName());
                trendingTopicList.add(trend.getName());
                count++;
            }
        }
    	return trendingTopicList;
    }
    
    
    public List<String> getTrendingLocal(int number, int location) throws TwitterException {
    	Trends trends = twitter.getPlaceTrends(location);
    	List<String> trendingTopicList = new ArrayList<String>();
        int count = 0;
        for (Trend trend : trends.getTrends()) {
            if (count < number) {
             //   System.out.println(trend.getName());
                trendingTopicList.add(trend.getName());
                count++;
            }
        }
    	return trendingTopicList;
    }
    
    public List<String> getTweets(int count, String topic, boolean avoidReTweets) throws TwitterException {
        Query query = new Query(topic);
        List<String> tweetList = new ArrayList<String>();
        //query.s
        query.setCount(count);
        QueryResult result = twitter.search(query);
        for (Status status : result.getTweets()) {
            //System.out.println("Status@\t" + status.getUser().getScreenName() + "\t:\t" + status.getText());
        	// System.out.println("\t" + status.getId() + "\t:\t" + status.getText());
        	 //to avoid retweets
        	if(avoidReTweets) {
	           	 if(!status.getText().startsWith("RT @")) {
	        		// System.out.println( "Not ReTweet : " + status.getId() + " :" + status.getText());
	        		 tweetList.add(status.getId()+"");
	        	 } else {
	        		// System.out.println( " ReTweet  : " + status.getId() + " :" + status.getText());
	        	 }
        	} else {
	       		 System.out.println( status.getId() + " :" + status.getText());
	       		 tweetList.add(status.getId()+"");
        	}

        }
        
    	return tweetList;
    }
    
    
    public List<String> getTweetOwner(int count, String topic) throws TwitterException {
        Query query = new Query(topic);
        List<String> tweetList = new ArrayList<String>();
        //query.s
        query.setCount(count);
        QueryResult result = twitter.search(query);
        for (Status status : result.getTweets()) {
            //System.out.println("Status@\t" + status.getUser().getScreenName() + "\t:\t" + status.getText());
        	// System.out.println("getScreenName @" + status.getUser().getScreenName() +", topic: "+topic);
        	 tweetList.add(status.getUser().getScreenName());
        }
    	return tweetList;
    }
    
    public List<Long> getFollowersList(String userName) throws TwitterException {
        long cursor = -1;
        IDs ids;
        List<Long> followerList;
        System.out.println("Listing followers's ids.");
        do {
        		followerList = new ArrayList<Long>();
                ids = twitter.getFollowersIDs(userName, cursor);
                
            for (long id : ids.getIDs()) {
                System.out.println("id : "+id);
                followerList.add(id);
               // User user = twitter.showUser(id);
              //  System.out.println(user.getName());
            }
        } while ((cursor = ids.getNextCursor()) != 0);
        return followerList;
    }
    
    /***
     * 
     * @param tweetId : tweet Id
     * @param messgae : Reply message
     * @return
     * @throws TwitterException
     */
    public String reply(String tweetId, String messgae ) throws TwitterException {
    		Status status = twitter.showStatus(Long.parseLong(tweetId));
    		Status reply = twitter.updateStatus(new StatusUpdate(" @" + status.getUser().getScreenName() + " "+ messgae).inReplyToStatusId(status.getId()));
    	return status.getUser().getScreenName();
    }
    
    
    public String follow(long userId) throws TwitterException {
    	return twitter.createFriendship(userId).getBiggerProfileImageURL();
    }
    
    public String follow(String userName) throws TwitterException {
    	return twitter.createFriendship(userName).getBiggerProfileImageURL();
    }
    
    public String unFollow(long userId) throws TwitterException {
    	return twitter.destroyFriendship(userId).getBiggerProfileImageURL();
    }
    
    public String unFollow(String userName) throws TwitterException {
    	return twitter.destroyFriendship(userName).getBiggerProfileImageURL();
    }
    
    public String makeFavorite(String tweetId) throws TwitterException {
    	return makeFavorite(Long.parseLong(tweetId));
    }
    
    private String makeFavorite(long tweetId) throws TwitterException {
    	Status status  = twitter.createFavorite(tweetId);
    	return status.getUser().getScreenName();
    }
    
    /*
    private List<DirectMessage> getDirectMessgae(String auther) {
    	List<DirectMessage> messages;
    	
    }*/
    
    public List<MessgaeFromAuther> readMessgaes(String auther) throws TwitterException {
    	auther = auther == null ? this.auther : auther;
    	List<MessgaeFromAuther> messageList = new ArrayList<MessgaeFromAuther>();
    	 try {
             Paging paging = new Paging(1);
             List<DirectMessage> messages;
             do {
                 messages = twitter.getDirectMessages(paging);
                 for (DirectMessage message : messages) {/*
                     System.out.println("From: @" + message.getSenderScreenName() + " id:" + message.getId() + " - "
                             + message.getText());*/
                     if(message.getSenderScreenName().equalsIgnoreCase(auther)) {
                    	 MessgaeFromAuther msg = new MessgaeFromAuther();
                    	 msg.setId(message.getId()+"");
                    	 msg.setText(message.getText());
                    	 messageList.add(msg);
                     }
                 }
                 paging.setPage(paging.getPage() + 1);
                
             } while (messages.size() > 0 && paging.getPage() < 10);
            // System.out.println("List : "+messageList);
         } catch (TwitterException te) {
             te.printStackTrace();
             System.out.println("Failed to get messages: " + te.getMessage());
             throw te;
         }
    	return messageList;
    }
    
    
    public String getNewMessageFromAuther() throws TwitterException {
    	String message = null;
    	List<MessgaeFromAuther> messageList = readMessgaes(null);
    	// System.out.println("List : "+messageList);
    	MessgaeFromAuther messgaeFromAuther = messageList.get(0);
    	
    	if(TwitterConnector.lastMessgaeFromAuther == null) {
    		TwitterConnector.lastMessgaeFromAuther = messgaeFromAuther;
    		System.out.println("Init messaing ");
    	}
    	
    	if(messgaeFromAuther.getId().equalsIgnoreCase(TwitterConnector.lastMessgaeFromAuther.getId())) {
    		message = null;
    		 System.out.println("No New Messgae ");
    	} else {
    		TwitterConnector.lastMessgaeFromAuther = messgaeFromAuther;
    		message = messgaeFromAuther.getText();
    		System.out.println("New Messgae : "+message);
    	}
    	
    	return message;
    }
    
        
	private int getRandomInt(int maxVal) {
		Random rand = new Random();
		return rand.nextInt(maxVal);
	}
    
    public static void main(String[] args){
    	TwitterConnector twitterConnector = new TwitterConnector();
    	try {
    		twitterConnector.init();
       
           
    	//	twitterConnector.tweet("Test", "https://tctechcrunch2011.files.wordpress.com/2017/11/20-somethings.png");
    		twitterConnector.getFollowersList(twitterConnector.auther);
    		
    	//	
			//String tweetresp = twitterConnector.getRandomTrendingQuote();
			//23424848
			//923608938943201280
    	//	twitterConnector.reply("930497966443454464", "Reply from Eva : Hello ");
    	//	twitterConnector.getRandomTrendingUser();
    		//twitterConnector.follow("bryanjoiner");
    	//twitterConnector.getNewMessageFromAuther();
    		
    		
    		
			//twitterConnector.getTrendingLocal(10, 23424848); 
			//System.out.println(tweetresp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private class MessgaeFromAuther {
    	private String id;
    	private String Text;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getText() {
			return Text;
		}
		public void setText(String text) {
			Text = text;
		}
		@Override
		public String toString() {
			return "MessgaeFromAuther [id=" + id + ", Text=" + Text + "]";
		}
    	
    }
    
}
