package com.cc.quote.service;

import java.util.Random;

import com.cc.quote.common.model.Article;
import com.cc.quote.conn.google.spreadsheet.model.QuoteModel;
import com.cc.quote.conn.news.api.NewsManager;
import com.cc.quote.conn.twitter.conf.TwitterConnector;
import com.cc.quote.service.rest.helper.QuoteServiceHelper;

public class TweetManager {
	public void tweet() {
		try {
			int randomInt = getRandomInt(14);
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
				if(randomInt <= 3 ) {
					System.out.println(" QUOTE ");
					QuoteServiceHelper quoteServiceHelper = new QuoteServiceHelper();
					QuoteModel quote = quoteServiceHelper.getRandomQuote();
					String quoteString = quote.getQuoteWithCharLimitQuoteOnly(140);
					System.out.println(" quoteString : "+quoteString);
					twitterConnector.tweet(quoteString);
				} else if (randomInt > 3 && randomInt <= 6){
					System.out.println(" RETWEET - GLOBAL ");
					String tweetId = twitterConnector.getRandomTrendingQuote();
					String userName = twitterConnector.retweet(tweetId);
					twitterConnector.follow(userName);
				} else if (randomInt > 6 && randomInt <= 10){
					System.out.println(" RETWEET - IND");
					String tweetId = twitterConnector.getRandomTrendingQuoteIndia();
					String userName = twitterConnector.retweet(tweetId);
					twitterConnector.follow(userName);
				} else if (randomInt > 10 && randomInt <= 14){
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
