package com.cc.quote.conn.google.spreadsheet.model;

public class QuoteModel {
	private String quote;
	private String auther;
	public String getQuote() {
		return quote;
	}
	public void setQuote(String quote) {
		this.quote = quote;
	}
	public String getAuther() {
		return auther;
	}
	public void setAuther(String auther) {
		this.auther = auther;
	}
	@Override
	public String toString() {
		return "{\"quote\":\""+quote+"\",\"auther\":\""+auther+"\"}";
	}
	
	public String getQuoteWithCharLimit(int charLimit) {
		if(quote != null && quote.length() >= charLimit ) {
			quote = quote.substring(0, charLimit);
		}
		return toString();
	}
	
	public String getQuoteWithCharLimitQuoteOnly(int charLimit) {
		if(quote != null && quote.length() >= charLimit ) {
			quote = quote.substring(0, charLimit);
		}
		return this.quote;
	}
}
