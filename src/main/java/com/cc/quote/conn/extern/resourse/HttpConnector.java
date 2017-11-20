package com.cc.quote.conn.extern.resourse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.cc.quote.common.propery.ProperyReader;

public class HttpConnector {
	private HttpClient client;
	private HttpGet request;
	private String url;
	private String queryString;
	
	@SuppressWarnings("deprecation")
	public HttpConnector() {
		client = new DefaultHttpClient();
	}
	
	public HttpConnector initGet(String url, String queryString) {
		this.url = url;
		this.queryString = queryString;
		this.getSetRequest();
		return this;
	}
	
	public void updateQueryString(String queryString) {
		this.queryString = queryString;
		this.getSetRequest();
	}
	
	public String getContent() throws IllegalStateException, IOException {
		String content = "";
		HttpResponse response = client.execute(request);

		// Get the response
		BufferedReader rd = new BufferedReader
		    (new InputStreamReader(
		    response.getEntity().getContent()));

		String line = "";
		while ((line = rd.readLine()) != null) {
		  //  textView.append(line);
			content = content + line;
		}
		    
		return content;
	}
	
		
	private HttpGet getSetRequest() {
		String generatedURL = this.url + "?" + this.queryString;
		this.request = new HttpGet(generatedURL);
		return this.request;
	}
	
	public static void main(String args[]) {
		HttpConnector httpConnector = new HttpConnector();
		
		ProperyReader properyReader = new ProperyReader();
		String apiUrl = properyReader.getValue("newsapi.url");
		String apiKey = properyReader.getValue("newsapi.key");
		
		httpConnector.initGet(apiUrl, 
				"source=techcrunch&apiKey="+apiKey);
		
		try {
			System.out.println("-----------------> "+httpConnector.getContent());
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
		
		
	}
	
	
}
