package com.cc.quote.service.rest.helper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Random;

import com.cc.quote.conn.google.spreadsheet.GoogleSheetConnector;
import com.cc.quote.conn.google.spreadsheet.model.QuoteModel;
import com.google.gdata.util.ServiceException;

public class QuoteServiceHelper {
	private static GoogleSheetConnector googleSheetConnector;
	private String sheetName;
	private List<QuoteModel> getQuoteList(String sheetName, String key) throws IOException, ServiceException, MalformedURLException {
		
		if(googleSheetConnector == null) {
			googleSheetConnector = new GoogleSheetConnector(key);
			//googleSheetConnector.sheetName = sheetName
			this.setSheetName(sheetName); 
		} else if(!sheetName.equalsIgnoreCase(this.getSheetName())) {
			googleSheetConnector = new GoogleSheetConnector(key);
			this.setSheetName(sheetName); 
		}
		List<QuoteModel> quoteList = googleSheetConnector.getQuoteList(sheetName);
		return quoteList;
	}
	
	public QuoteModel getQuote(String sheetName, int index ) throws IOException, ServiceException, MalformedURLException {
		List<QuoteModel> quoteList = getQuoteList(sheetName,"1");
		return quoteList.get(index);
	}
	
	public QuoteModel getRandomQuote(String sheetName) throws MalformedURLException, IOException, ServiceException {
		return getQuote(sheetName, getRandomInt(getListMax(sheetName)) );
	}
	
	public QuoteModel getRandomQuote() throws MalformedURLException, IOException, ServiceException {
		String sheetName = "QuoteSheet";
		return getQuote(sheetName, getRandomInt(getListMax(sheetName)) );
	}
	
	private int getListMax(String sheetName) throws MalformedURLException, IOException, ServiceException {
		return getQuoteList(sheetName,"1").size();
	}
	
	private int getRandomInt(int maxVal) {
		Random rand = new Random();
		return rand.nextInt(maxVal);
	}
	
	public static void main(String[] args) {
		try {
			QuoteServiceHelper cc = new QuoteServiceHelper();
			QuoteModel q = cc.getRandomQuote("QuoteSheet");
		System.out.println(q.getQuoteWithCharLimit(140));
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	
	
	
}
