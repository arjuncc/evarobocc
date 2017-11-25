package com.cc.quote.conn.news.api;

import java.util.ArrayList;
import java.util.List;

import com.cc.quote.common.model.Article;
import com.cc.quote.common.propery.ProperyReader;
import com.cc.quote.common.util.RandomNumberGenerator;

public class NewsManager {
	private static List<String> newsSourseList;
	public NewsManager() {
		ProperyReader properyReader = new ProperyReader();
		if (NewsManager.newsSourseList == null) {
			initNewsSourse(properyReader.getValue("newsapi.sourceList"));
		}
	}
	
	private void initNewsSourse(String sourceList) {
		NewsManager.newsSourseList = new ArrayList<String>();
		for(String sourse : sourceList.split(",")) {
			NewsManager.newsSourseList.add(sourse);
		}
	}
	
	public Article getRandomNews() {
		NewsApiConnect newsApiConnect = new NewsApiConnect();
		String newsSourse = newsSourseList.get(RandomNumberGenerator.getInt(newsSourseList.size()));
		List<Article> articleList = newsApiConnect.getNews(newsSourse);
		return articleList.get(RandomNumberGenerator.getInt(articleList.size()));
	}
	
	
	public static void main(String args[]) {
		NewsManager newsApiConnect = new NewsManager();
		newsApiConnect.getRandomNews();
	}
	

}
