package com.simoncomputing.app.winventory.controller.hardware;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.simoncomputing.app.winventory.bo.EventBo;
import com.simoncomputing.app.winventory.bo.HardwareBo;
import com.simoncomputing.app.winventory.bo.LocationBo;
import com.simoncomputing.app.winventory.bo.UserBo;
import com.simoncomputing.app.winventory.controller.BaseController;
import com.simoncomputing.app.winventory.domain.Event;
import com.simoncomputing.app.winventory.domain.Hardware;
import com.simoncomputing.app.winventory.domain.Location;
import com.simoncomputing.app.winventory.domain.User;
import com.simoncomputing.app.winventory.util.BoException;

/**
 * Controller for the singular Hardware View page
 */
@WebServlet("/hardware/view")
public class HardwareViewController extends BaseController {

    private static final long serialVersionUID = 1L;

    private Logger log = Logger.getLogger(HardwareViewController.class);

    /**
     * Runs when the "hardware/view" page is accessed by a link or through the
     * url
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
                    throws ServletException, IOException {

        // Retrieve the key parameter from the request
        String key = request.getParameter("key");

        // Create a Hardware and attempt to retrieve the Hardware associated
        // with the key value (assuming there is a key parameter in the request)
        Hardware hardware = null;
        Long long_key = null;
        
        List<Event> events = null;
        
        if (key != null) {
            // Cast the key to the correct type
            long_key = Long.parseLong(key);
        }
        // This will fail if there is no key (i.e. the url ends at
        // "hardware/edit")
        try {
            // Retrieve the Hardware associated with it using a BO instance
            hardware = HardwareBo.getInstance().read(long_key);
        } catch (BoException e) {
            String error = logError(log, e);
            request.setAttribute("error", "Error code: " + error);
        }

        // If the hardware is not found, this will throw an error (i.e. the url
        // contains a key that does not exist at "hardware/edit?key=").
        // Otherwise, continue
        if (hardware == null) {
            String error = logError(log, new NullPointerException());
            request.setAttribute("error", "Error code: " + error);
        } else if (hardware != null) {
            // Get the User information to display, if it exists
            if (hardware.getUserId() != null) {
                try {
                    // Retrieve the User associated with it using a BO instance
                    User owner = UserBo.getInstance().read((long) hardware.getUserId());
                    request.setAttribute("owner", owner);
                } catch (BoException e) {
                    request.setAttribute("error", e.getMessage());
                    log.error(e.getMessage());
                }
                // If no User, get the Location information to display, if it
                // exists
            } else if (hardware.getLocationId() != null) {
                try {
                    // Retrieve the Location associated with it using a BO
                    // instance
                    Location location = LocationBo.getInstance().read(
                                    (long) hardware.getLocationId());
                    request.setAttribute("location", location);
                } catch (BoException e) {
                    request.setAttribute("error", e.getMessage());
                    log.error(e.getMessage());
                }
            }
            
            //Use Bo to get all events associated with this hardware
            EventBo eb = EventBo.getInstance();
        	try {
				events = eb.getEventsOf(hardware);
			} catch (BoException e) {
				// TODO Auto-generated catch block
				request.setAttribute("error", e.getMessage());
                log.error(e.getMessage());
			}
        }

        // Set the hardware as an attribute for the request
        request.setAttribute("hardware", hardware);
        
        //Set the list of events as an attribute
        request.setAttribute("events", events);
        
        // Forward the request to the "hardware/view" page
        request.getRequestDispatcher("/WEB-INF/flows/hardware/view.jsp").forward(request, response);

    }

    /**
     * Runs when the "delete" button is selected on the "hardware/view" page
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                    throws ServletException, IOException {

        // Check to see if the current User has the permissions to delete
        // Hardware items, and returns if they do not
        if (this.requirePermission(request, response, "deleteHardware")) {
            return;
        }

        // Retrieve the key parameter from the request
        String key = request.getParameter("key");

        // Retrieve the key for the Hardware item to be deleted from the
        // database
        HardwareBo bo = HardwareBo.getInstance();
        Long long_key = null;
        if (key != null) {
            long_key = Long.parseLong(key);
        }

        // Attempt to delete the Hardware item from the database using a BO
        try {
            bo.delete(long_key);
        } catch (BoException e) {
            request.setAttribute("error", e.getMessage());
            log.error(e.getMessage());
        }

        // Redirect to the results page, as the item no longer exists and does
        // not have a page
        this.sendRedirect(request, response, "results");

    }

}