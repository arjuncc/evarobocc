package com.cc.quote.service;

import java.util.Random;

import com.cc.quote.conn.google.spreadsheet.model.QuoteModel;
import com.cc.quote.conn.twitter.conf.TwitterConnector;
import com.cc.quote.service.rest.helper.QuoteServiceHelper;

public class TweetTester {
	public  static void main(String[] args) {
		try {
			
			int randomInt = 2;
			
			TwitterConnector twitterConnector = new TwitterConnector();
			twitterConnector.init();
			
			/*
			QuoteServiceHelper quoteServiceHelper = new QuoteServiceHelper();
			QuoteModel quote = quoteServiceHelper.getRandomQuote();
			String quoteString = quote.getQuoteWithCharLimitQuoteOnly(140);
			System.out.println(" quoteString : "+quoteString);*/
			
			
			
			if(randomInt <= 3) {
				System.out.println(" QUOTE ");
				QuoteServiceHelper quoteServiceHelper = new QuoteServiceHelper();
				QuoteModel quote = quoteServiceHelper.getRandomQuote();
				String quoteString = quote.getQuoteWithCharLimitQuoteOnly(140);
				System.out.println(" quoteString : "+quoteString);
				twitterConnector.tweet(quoteString);
			} else if (randomInt > 3 && randomInt <= 6){
				System.out.println(" RETWEET - GLOBAL ");
				String tweetId = twitterConnector.getRandomTrendingQuote();
				twitterConnector.retweet(tweetId);
			} else if (randomInt > 6 && randomInt <= 10){
				System.out.println(" RETWEET - IND");
				String tweetId = twitterConnector.getRandomTrendingQuoteIndia();
				twitterConnector.retweet(tweetId);
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
