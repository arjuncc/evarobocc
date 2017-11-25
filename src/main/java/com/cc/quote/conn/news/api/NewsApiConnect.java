package com.cc.quote.conn.news.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.cc.quote.common.model.Article;
import com.cc.quote.common.propery.ProperyReader;
import com.cc.quote.conn.extern.resourse.HttpConnector;
import org.json.*;

public class NewsApiConnect {
	private String apiUrl;
	private String apiKey;
	//private static int previousNews = 0;

	public NewsApiConnect() {
		ProperyReader properyReader = new ProperyReader();
		this.apiUrl = properyReader.getValue("newsapi.url");
		this.apiKey = properyReader.getValue("newsapi.key");
	}

	
	public List<Article> getNews(String newsSourse) {
		List<Article> artList = null;
		try {
			System.out.println("newsSourse : "+newsSourse);
			String newsString = getDataFromServer(newsSourse);
			System.out.println("newsString : "+newsString);
			artList = newsToArticleList(newsString);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return artList;
	}
	
	private String initParam(String newsSourse) {
		return "apiKey="+this.apiKey+"&source="+newsSourse;
	}
	
	private String getDataFromServer(String newsSourse) throws IllegalStateException, IOException {
		HttpConnector httpConnector = new HttpConnector();
		httpConnector.initGet(apiUrl,initParam(newsSourse));
		return httpConnector.getContent();
	}
	
	private List<Article> newsToArticleList(String responseString) {
		JSONObject jSONObject = new JSONObject(responseString);
		List<Article> articleList = new ArrayList<Article>();
		//String pageName = obj.getJSONObject("pageInfo").getString("pageName");

		JSONArray jSONArray = jSONObject.getJSONArray("articles");
		for (int i = 0; i < jSONArray.length(); i++) {
			JSONObject tempJSONObject = jSONArray.getJSONObject(i);
			Article article = new Article();
			article.setHeading(tempJSONObject.getString("title"));
			article.setImage(tempJSONObject.getString("urlToImage"));
			article.setLink(tempJSONObject.getString("url"));
			articleList.add(article);
		}
		return articleList;
	}
	
	

	
	
	
}
