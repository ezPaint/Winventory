package com.simoncomputing.app.winventory.listeners;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.ibatis.jdbc.ScriptRunner;
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
        logger.info("Context init");
    	SessionFactory.initializeForTest();
    	/*
    	try {
            Class.forName("org.h2.Driver");
			Connection connection = DriverManager.getConnection("jdbc:h2:tcp://localhost:9096/sample/testDB", "sa", "123");
			logger.info("OOOOOOMMMMMMMMGGGGGGG");
			ScriptRunner runner = new ScriptRunner(connection);
			File create = new File("sql/_CreateTables.sql");
			File alter = new File("sql/_AlterTable.sql");
			InputStreamReader creater = new InputStreamReader(new FileInputStream(create.getAbsolutePath()));
			InputStreamReader alterer = new InputStreamReader(new FileInputStream(alter.getAbsolutePath()));
			runner.runScript(creater);
			runner.runScript(alterer);
			creater.close();
			alterer.close();
			connection.close();
		} catch (SQLException e) {
			logger.error("Failed to connect to DB");
		} catch (ClassNotFoundException e) {
			logger.error("include org.h2.Driver");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
    }

	 
	 @Override
     public void contextDestroyed(ServletContextEvent arg0) {
	     logger.info("Context destroyed");
     }

}
