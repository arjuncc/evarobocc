package com.cc.quote.conn.news.api;

import java.util.Random;

public class NewsApiConnect {

	public static void main(String[]  args) {
		 double delay = (new Random().nextInt(10000)) * 100;
		 System.out.println("delay : "+(delay)+"");
		 System.out.println("delay : "+(delay/1000/60)+"Mins");
	}
	
}
