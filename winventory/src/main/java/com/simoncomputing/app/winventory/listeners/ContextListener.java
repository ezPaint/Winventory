package com.simoncomputing.app.winventory.listeners;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.simoncomputing.app.winventory.dao.SessionFactory;

/**
 * Application Lifecycle Listener implementation class ContextListener
 *
 */
@WebListener
public class ContextListener implements ServletContextListener
{
	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
	
	 @Override
    public void contextInitialized(ServletContextEvent sce)  { 
        System.out.println("Context init");
    	SessionFactory.initializeForTest();
    }

	 
	 @Override
     public void contextDestroyed(ServletContextEvent arg0) {
	     System.out.println("Context destroyed");
     }

}
