package com.xgg.epd.listeners;

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang3.time.DateUtils;

public class TaskManager implements ServletContextListener {

	public static final long PERIOD_DAY=24*3600*1000;
	private Timer timer; 
	public void contextDestroyed(ServletContextEvent arg0) {		
		timer.cancel();
	}

	public void contextInitialized(ServletContextEvent arg0) {	
		 timer = new Timer(true);
		 timer.schedule(new SendEmailTask(),0,24*3600*1000);
	}

}
