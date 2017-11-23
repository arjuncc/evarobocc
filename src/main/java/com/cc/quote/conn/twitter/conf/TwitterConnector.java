package com.cc.quote.conn.twitter.conf;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.cc.quote.common.propery.ProperyReader;

import twitter4j.DirectMessage;
import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
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
    private Twitter twitter = null; 
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
    	List<String> trendingTopicList = getTrendingGlobal(10);
    	String trendTag = trendingTopicList.get(getRandomInt(trendingTopicList.size()));
    	List<String> tweetList = getTweets(10, trendTag);
    	return tweetList.get(getRandomInt(tweetList.size()));
    }
    
    public String getRandomTrendingQuoteIndia() throws TwitterException {
    	List<String> trendingTopicList = getTrendingLocal(10, 23424848);
    	String trendTag = trendingTopicList.get(getRandomInt(trendingTopicList.size()));
    	List<String> tweetList = getTweets(10, trendTag);
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
    
    public List<String> getTweets(int count, String topic) throws TwitterException {
        Query query = new Query(topic);
        List<String> tweetList = new ArrayList<String>();
        //query.s
        query.setCount(count);
        QueryResult result = twitter.search(query);
        for (Status status : result.getTweets()) {
            //System.out.println("Status@\t" + status.getUser().getScreenName() + "\t:\t" + status.getText());
        	// System.out.println("Status@\t" + status.getId() + "\t:\t" + status.getText());
        	 tweetList.add(status.getId()+"");
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
    	return Long.toString(reply.getId());
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
    
    
    public List<String> readMessgaes(String auther) throws TwitterException {
    	List<String> messageList = new ArrayList<String>();
    	 try {
             Paging paging = new Paging(1);
             List<DirectMessage> messages;
             do {
                 messages = twitter.getDirectMessages(paging);
                 for (DirectMessage message : messages) {
                     System.out.println("From: @" + message.getSenderScreenName() + " id:" + message.getId() + " - "
                             + message.getText());
                 }
                 paging.setPage(paging.getPage() + 1);
             } while (messages.size() > 0 && paging.getPage() < 10);
             System.out.println("done.");
             System.exit(0);
         } catch (TwitterException te) {
             te.printStackTrace();
             System.out.println("Failed to get messages: " + te.getMessage());
             System.exit(-1);
         }
    	
    	return messageList;
    }
    
        
	private int getRandomInt(int maxVal) {
		Random rand = new Random();
		return rand.nextInt(maxVal);
	}
    
    public static void main(String[] args){
    	TwitterConnector twitterConnector = new TwitterConnector();
    	try {
    		
          //  Twitter twitter = new TwitterFactory().getInstance();
           
    		
    		
    		twitterConnector.init();
			//String tweetresp = twitterConnector.getRandomTrendingQuote();
			//23424848
			//923608938943201280
    	//	twitterConnector.reply("930497966443454464", "Reply from Eva : Hello ");
    	//	twitterConnector.getRandomTrendingUser();
    		//twitterConnector.follow("bryanjoiner");
    		twitterConnector.readMessgaes(null);
    		
    		
    		
			//twitterConnector.getTrendingLocal(10, 23424848); 
			//System.out.println(tweetresp);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
}
