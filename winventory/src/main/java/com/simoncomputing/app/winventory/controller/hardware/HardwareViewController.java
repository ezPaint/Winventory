package com.simoncomputing.app.winventory.controller.hardware;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.simoncomputing.app.winventory.bo.EventBo;
import com.simoncomputing.app.winventory.bo.HardwareBo;
import com.simoncomputing.app.winventory.bo.HardwareToSoftwareBo;
import com.simoncomputing.app.winventory.bo.LocationBo;
import com.simoncomputing.app.winventory.bo.UserBo;
import com.simoncomputing.app.winventory.controller.BaseController;
import com.simoncomputing.app.winventory.domain.Event;
import com.simoncomputing.app.winventory.domain.Hardware;
import com.simoncomputing.app.winventory.domain.Location;
import com.simoncomputing.app.winventory.domain.Software;
import com.simoncomputing.app.winventory.domain.User;
import com.simoncomputing.app.winventory.util.Barcoder;
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

        if(!userHasPermission(request, "readHardware")){
            denyPermission(request, response);
            return;
        }
        
        // Retrieve the key parameter from the request
        String key = request.getParameter("key");

        // Create a Hardware and attempt to retrieve the Hardware associated
        // with the key value (assuming there is a key parameter in the request)
        Hardware hardware = null;
        Long long_key = null;
        String error = null;

        List<Event> events = null;

        if (key != null) {
            // Cast the key to the correct type

            try {
                long_key = Long.parseLong(key);
            } catch (Exception e) {
                log.info("Invalid key for HTTP GET /hardware/view. The page will show no results." + logError(log, e));
                error = "An invalid key was entered";
                request.setAttribute("error", error);
            }
        }
        // This will fail if there is no key (i.e. the url ends at
        // "hardware/edit")
        try {
            // Retrieve the Hardware associated with it using a BO instance
            hardware = HardwareBo.getInstance().read(long_key);
        } catch (BoException e) {
            log.info("Invalid key for HTTP GET /hardware/view. The page will show no results." + logError(log, e));            
            error = "No hardware key was entered";
            request.setAttribute("error", error);
        }

        // If the hardware is not found, this will throw an error (i.e. the url
        // contains a key that does not exist at "hardware/edit?key=").
        // Otherwise, continue
        if (hardware == null && error == null) {
            log.info("Invalid key for HTTP GET /hardware/view. The page will show no results." + log, new NullPointerException());
            error = "No hardware exists with key " + key + ". The item may have been deleted or that may be the wrong key.";
            request.setAttribute("error", error);
        } else if (hardware != null) {
            // Get the User information to display, if it exists
            if (hardware.getUserId() != null) {
                try {
                    // Retrieve the User associated with it using a BO instance
                    User owner = UserBo.getInstance().read((long) hardware.getUserId());
                    request.setAttribute("owner", owner);
                } catch (BoException e) {
                    log.info("Unable to retrieve user information" + log, e);
                    error = "Unable to retrieve user information";
                    request.setAttribute("error", error);
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
                    log.info("Unable to retrieve location information" + log, e);
                    error = "Unable to retrieve location information";
                    request.setAttribute("error", error);
                }
            }
            
            ArrayList<Software> software = null;
            try {
                 software = new ArrayList<Software>(
                        HardwareToSoftwareBo.getInstance().getSoftwareByHardwareId(hardware.getKey()));
            } catch (BoException e1) {
                log.info("Unable to retrieve the software related to this hardware" + log, e1);
                error = "Unable to retrieve the software related to this hardware";
                request.setAttribute("error", error);
            }
            
            request.setAttribute("software", software);
                    

            // get and assign barcode using Barcoder class
            request.setAttribute("barcode", Barcoder.getBarcode(hardware));

            // Use Bo to get all events associated with this hardware
            EventBo eb = EventBo.getInstance();
            try {
            	if (hardware != null)
            	{
            		events = eb.getListByHardwareId(hardware.getKey());
            	}
                
            } catch (BoException e) {
                log.info("Unable to retrieve events related to this hardware" + log, e);
                error = "Unable to retrieve events related to this hardware";
                request.setAttribute("error", error);
            }
        }

        // Set the hardware as an attribute for the request
        request.setAttribute("hardware", hardware);

        // Set the list of events as an attribute
        request.setAttribute("events", events);
        //log.error(events.get(0).getCreatorId());
        
        // check for success message (for redirects from edit/add/delete pages)
        if (request.getParameter("success") != null) {
            request.setAttribute("success", request.getParameter("success"));
        }
        
        // check for delete request, if so then the delete confirmation message will appear
        if (request.getParameter("delete") != null) {
            request.setAttribute("delete", request.getParameter("delete"));
        }
        
        // check for error on request param, if so then the error confirmation message will appear
        if (request.getParameter("error") != null) {
            request.setAttribute("error", request.getParameter("error"));
        }
        
        request.setAttribute("delete", request.getParameter("delete"));

        // Forward the request to the "hardware/view" page
        request.getRequestDispatcher("/WEB-INF/flows/hardware/view.jsp").forward(request, response);

    }

    /**
     * No posts on this page currently.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // no posts on this page
    }
}