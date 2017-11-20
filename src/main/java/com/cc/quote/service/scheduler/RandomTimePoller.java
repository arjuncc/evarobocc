package com.cc.quote.service.scheduler;

import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.cc.quote.service.TweetManager;

public class RandomTimePoller {
	static Timer timer = new Timer();
	private static Date d1;
    static class Task extends TimerTask {
        @Override
        public void run() {
        	Date d2 = null;
            long delay = 10000 + (100 + new Random().nextInt(1000)) * 10;
            long delayInMins = 500*60 + delay*100;
            
            if( delayInMins/60/1000 < 10 ) {
            	delayInMins = delayInMins + 600000;
            } else if (delayInMins/60/1000 > 20 ) {
            	delayInMins = delayInMins - 600000;
            }
            
           // long delay = 1000*60 + (100 + new Random().nextInt(1000)) * 10;
            timer.schedule(new Task(), delay);
           // long seconds = (d2.getTime()-d1.getTime())/1000;
            d2 = new Date();
            System.out.println(d2);
            if(d1 != null) {
            	long seconds = (d2.getTime()-d1.getTime())/1000;
            	
            	System.out.println("actual delay : "+delay+ " - seconds : "+seconds + "\n - delay : " +delayInMins + "\n Delay In Mins "+(delayInMins/60/1000));
            }
            d1 = d2; 
            
           TweetManager tweetManager = new TweetManager();
           tweetManager.tweet();	
            
            
        }

    }


  
    public static void main(String[] args) throws Exception {
        new Task().run();
    }
    
    public void start() {
    	new Task().run();
    }
    
}
