package com.cc.quote.service.bootstrap;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.cc.quote.service.scheduler.RandomTimePoller;

public class BootStrapListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		RandomTimePoller randomTimePoller = new RandomTimePoller();
		randomTimePoller.start();
	}

}
