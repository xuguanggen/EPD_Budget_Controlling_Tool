package com.xgg.epd.autotasks;

import java.sql.Time;
import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ProjectServletContextListner implements ServletContextListener{

	private Timer timer=null;
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		timer=new Timer(true);
		timer.schedule(new Task(),  0, 86400000);
	}

	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		timer.cancel();
	}

}
