package com.cc.quote.service;

import java.util.List;
import java.util.Random;

import com.cc.quote.common.model.Article;
import com.cc.quote.common.util.DataConfigReader;
import com.cc.quote.common.util.RandomNumberGenerator;
import com.cc.quote.conn.google.spreadsheet.model.QuoteModel;
import com.cc.quote.conn.news.api.NewsManager;
import com.cc.quote.conn.twitter.conf.TwitterConnector;
import com.cc.quote.service.rest.helper.QuoteServiceHelper;

public class TweetManager {
	public void tweet() {
		try {
			
			int randomInt = getRandomInt(12);
			TwitterConnector twitterConnector = new TwitterConnector();
			twitterConnector.init();
			
			boolean messageFromAuther = false;
			String newMessage = twitterConnector.getNewMessageFromAuther();
			if(newMessage != null) {
				messageFromAuther = true;
				twitterConnector.tweet(newMessage);
			}
			
			if(messageFromAuther) {
				
			} else {
				if(randomInt <= 2 ) {
					System.out.println(" QUOTE ");
					QuoteServiceHelper quoteServiceHelper = new QuoteServiceHelper();
					QuoteModel quote = quoteServiceHelper.getRandomQuote();
					String quoteString = quote.getQuoteWithCharLimitQuoteOnly(140);
					System.out.println(" quoteString : "+quoteString);
					twitterConnector.tweet(quoteString);
				} else if (randomInt > 2 && randomInt <= 4){
					System.out.println(" RETWEET - GLOBAL ");
					String tweetId = twitterConnector.getRandomTrendingQuote();
					String userName = twitterConnector.retweet(tweetId);
					twitterConnector.follow(userName);
				} else if (randomInt > 4 && randomInt <= 6){
					System.out.println(" RETWEET - IND");
					String tweetId = twitterConnector.getRandomTrendingQuoteIndia();
					String userName = twitterConnector.retweet(tweetId);
					twitterConnector.follow(userName);
				} else if (randomInt > 6 && randomInt <= 8){
					String tweetId = null;
					System.out.println(" LIKE TWEET - IND+GLOB");
					if (randomInt%2==0) {
						tweetId = twitterConnector.getRandomTrendingQuoteIndia();
					} else {
						tweetId = twitterConnector.getRandomTrendingQuote();
					}
					System.out.println("tweetId : "+tweetId);
					String userName = twitterConnector.makeFavorite(tweetId);
					twitterConnector.follow(userName);
				} else if (randomInt > 8 && randomInt <= 10){
					String tweetId = null;
					System.out.println(" REPLY TWEET - IND+GLOB");
					if (randomInt%2==0) {
						tweetId = twitterConnector.getRandomTrendingQuoteIndiaWithoutReTweet();
					} else {
						tweetId = twitterConnector.getRandomTrendingQuoteWithoutReTweet();
					}
					System.out.println("tweetId : "+tweetId);
					List<String> messgaelist = DataConfigReader.getInstance().getReplyList();
					String userName = twitterConnector.reply(tweetId, messgaelist.get(RandomNumberGenerator.getInt(messgaelist.size())));
					twitterConnector.follow(userName);
				} else if (randomInt > 10 && randomInt <= 12){
					System.out.println(" NEWS ");
					NewsManager newsManager = new NewsManager();
					Article article = newsManager.getRandomNews();
					System.out.println("article : "+article);
					twitterConnector.tweet(article.getHeading() + " " + article.getLink(), article.getImage()); 
				}
			}
			
			
			
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}
	
	private int getRandomInt(int maxVal) {
		Random rand = new Random();
		return rand.nextInt(maxVal);
	}
}
