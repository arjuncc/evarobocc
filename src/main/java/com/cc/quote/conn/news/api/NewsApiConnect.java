package com.cc.quote.conn.news.api;

import java.io.IOException;

import com.cc.quote.common.propery.ProperyReader;
import com.cc.quote.conn.extern.resourse.HttpConnector;

public class NewsApiConnect {

	public String getNews() {
		String newsList = null;
		HttpConnector httpConnector = new HttpConnector();
		
		ProperyReader properyReader = new ProperyReader();
		String apiUrl = properyReader.getValue("newsapi.url");
		String apiKey = properyReader.getValue("newsapi.key");
		
		httpConnector.initGet(apiUrl, 
				"source=techcrunch&apiKey="+apiKey);
		
		try {
			newsList = httpConnector.getContent();
			System.out.println("-----------------> "+newsList);
	//		JSONParser parser = new JSONParser();
		//	JSONParser parser = new JSONParser();
		//	JSONObject json = (JSONObject) parser.parse(stringToParse);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return newsList;
	}
	
	
	public static void main(String args[]) {
		NewsApiConnect newsApiConnect = new NewsApiConnect();
		newsApiConnect.getNews();
	}
	
}
