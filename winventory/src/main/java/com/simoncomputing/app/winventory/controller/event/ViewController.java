package com.simoncomputing.app.winventory.controller.event;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.simoncomputing.app.winventory.bo.EventBo;
import com.simoncomputing.app.winventory.bo.SoftwareBo;
import com.simoncomputing.app.winventory.bo.UserBo;
import com.simoncomputing.app.winventory.controller.BaseController;
import com.simoncomputing.app.winventory.domain.Event;
import com.simoncomputing.app.winventory.domain.Hardware;
import com.simoncomputing.app.winventory.domain.Location;
import com.simoncomputing.app.winventory.domain.Software;
import com.simoncomputing.app.winventory.domain.User;
import com.simoncomputing.app.winventory.util.BoException;

/**
 * Single event item view controller.
 * @author megan.rigsbee
 *
 */
@WebServlet("/event/view")
public class ViewController extends BaseController {
    private static final long serialVersionUID = 1L;
    private static Logger log = Logger.getLogger(ViewController.class);
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // ensure the user can read events
        if(!userHasPermission(request, "readEvent")){
            denyPermission(request, response);
            return;
        }
        
        //Use the key the user clicked on to retrieve the corresponding software object from
        //the database
        String key = request.getParameter("key");

        //The events associated with software; 
        //initialized to a new list to avoid null pointer exception

        Event event = null;
        Hardware hardware = new Hardware();
        Software software = new Software();
        Location location = new Location();
        User user = new User();
        String username = "";
        
        if (key != null) {
            try {
                Long long_key = Long.parseLong(key);
                event = EventBo.getInstance().read(long_key); //get software obj from database
            } catch (BoException e) {
                logError(log, e);
            }
        }
        
        if (event != null) {
            if (event.getHardwareId() != null)
            {
            	try {
                    hardware = EventBo.getInstance().getHardwareOf(event); //get software obj from database
                } catch (BoException e) {
                    logError(log, e);
                }
            }

            
            if (event.getLocationId() != null)
            {
            	try {
                    location = EventBo.getInstance().getLocationOf(event); //get software obj from database
                } catch (BoException e) {
                    logError(log, e);
                }
            }
            
            
            if (event.getUserId() != null) {
	            try {
	                user = EventBo.getInstance().getUserOf(event); //get software obj from database
	            } catch (BoException e) {
	                logError(log, e);
	            }
            }
            
            if (event.getCreatorId() != null)
            {
                try {
                	username = UserBo.getInstance().read(event.getCreatorId()).getUsername();
        		} catch (BoException e) {
        			log.error("Error reading user of event id=" + event.getKey() + " with creator_id=" 
        					+ event.getCreatorId() + ". The user may not exist?");
        		}
            }

        
        
	       if (event.getSoftwareId() != null)
	       {
	    	   try {
	               software = EventBo.getInstance().getSoftwareOf(event); //get software obj from database
	           } catch (BoException e) {
	               logError(log, e);
	           }
	          
	       }
	       request.setAttribute("validKey", true);
        }
        else
        {
        	request.setAttribute("validKey", false);
        }
        request.setAttribute("key", key);

        request.setAttribute("event", event); 
        
        request.setAttribute("hardware", hardware);
        request.setAttribute("location", location);
        request.setAttribute("software", software);
        request.setAttribute("user", user);
        request.setAttribute("username", username);
        
        forward(request, response, "/WEB-INF/flows/events/view.jsp");

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}