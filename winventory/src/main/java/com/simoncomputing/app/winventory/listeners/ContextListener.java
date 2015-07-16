package com.simoncomputing.app.winventory.listeners;




import org.apache.commons.dbcp.BasicDataSourceFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;

import com.simoncomputing.app.winventory.dao.SessionFactory;

/**
 * Application Lifecycle Listener implementation class ContextListener
 *
 */
@WebListener
public class ContextListener implements ServletContextListener
{
	private static final Logger logger = Logger.getLogger(ContextListener.class);
	
	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
	
	 @Override
    public void contextInitialized(ServletContextEvent sce)  { 
		//SessionFactory.initializeForTest();
        logger.info("Context init");
        SessionFactory.initialize();
    }

	 
	 @Override
     public void contextDestroyed(ServletContextEvent arg0) {
	     logger.info("Context destroyed");
     }

}
