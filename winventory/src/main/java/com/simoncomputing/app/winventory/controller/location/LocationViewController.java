package com.simoncomputing.app.winventory.controller.location;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.simoncomputing.app.winventory.bo.AddressBo;
import com.simoncomputing.app.winventory.bo.EventBo;
import com.simoncomputing.app.winventory.bo.LocationBo;
import com.simoncomputing.app.winventory.controller.BaseController;
import com.simoncomputing.app.winventory.domain.Address;
import com.simoncomputing.app.winventory.domain.Event;
import com.simoncomputing.app.winventory.domain.Location;
import com.simoncomputing.app.winventory.util.Barcoder;
import com.simoncomputing.app.winventory.util.BoException;

/**
 * Controller for the singular Location View page
 */
@WebServlet("/location/view-location")
public class LocationViewController extends BaseController {

    private static final long serialVersionUID = 1L;

    private Logger log = Logger.getLogger(LocationViewController.class);

    /**
     * Runs when the "location/view-location" page is accessed by a link or
     * through the url
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
                    throws ServletException, IOException {
        
        // Check to see if the current User has the permissions to update
        // Locations, and returns if they do not
        if (!this.userHasPermission(request, "readLocation")) {
            this.denyPermission(request, response);
            return;
        }

        // Retrieve the key parameter from the request
        String key = request.getParameter("key");

        // Create a Location and attempt to retrieve the Location associated
        // with the key value (assuming there is a key parameter in the request)
        Location location = null;
        Long long_key = null;

        if (key != null) {
            // Cast the key to the correct type
            long_key = Long.parseLong(key);
        }
        // This will fail if there is no key (i.e. the url ends at
        // "location/view-location")
        try {
            // Retrieve the Location associated with it using a BO instance
            location = LocationBo.getInstance().read(long_key);
        } catch (BoException e) {
            String error = logError(log, e);
            request.setAttribute("error", "Error code: " + error);
        }

        // If the Location is not found, this will throw an error (i.e. the url
        // contains a key that does not exist at "location/view-location?key=").
        // Otherwise, continue
        if (location == null) {
            String error = logError(log, new NullPointerException());
            request.setAttribute("error", "Error code: " + error);
        } else if (location != null) {
        	// get and assign barcode using Barcoder class
            request.setAttribute("barcode", Barcoder.getBarcode(location));
            
            // Get the Address information to display, if it exists
            if (location.getAddressId() != null) {
                try {
                    // Retrieve the Address associated with it using a BO
                    // instance
                    Address address = AddressBo.getInstance().read((long) location.getAddressId());
                    request.setAttribute("address", address);
                } catch (BoException e) {
                    request.setAttribute("error", e.getMessage());
                    log.error(e.getMessage());
                }
            }
            
            // set the barcode attribute
            request.setAttribute("barcode", Barcoder.getBarcode(location));
        }

        // Set the Location as an attribute for the request
        request.setAttribute("location", location);

        EventBo eb = EventBo.getInstance();
        List<Event> events = null;
        try {
            events = eb.getEventsOf(location);
        } catch (BoException e) {
            request.setAttribute("error", e.getMessage());
            log.error(e.getMessage());
        }
        
        request.setAttribute("events", events);
        
        // Forward the request to the "location/view-location" page
        request.getRequestDispatcher("/WEB-INF/flows/locations/view-location.jsp").forward(request,
                        response);

    }

    /**
     * Runs when the "delete" button is selected on the "location/view-location"
     * page
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                    throws ServletException, IOException {

        // Check to see if the current User has the permissions to delete
        // Locations, and returns if they do not
        if (!this.userHasPermission(request, "deleteLocation")) {
            this.denyPermission(request, response);
            return;
        }

        // Retrieve the key parameter from the request
        String key = request.getParameter("key");

        // Retrieve the key for the Location to be deleted from the database
        LocationBo bo = LocationBo.getInstance();
        Location location = null;
        Long long_key = null;
        if (key != null) {
            long_key = Long.parseLong(key);
        }
        try {
            // Retrieve the Location associated with it using a BO instance
            location = bo.read(long_key);
        } catch (BoException e) {
            String error = logError(log, e);
            request.setAttribute("error", "Error code: " + error);
        }
        
        // If the Location is not found, log the error and report the problem to
        // the user
        if (location == null) {
            String error = logError(log, new NullPointerException());
            request.setAttribute("error", "Error code: " + error);
        }
        
        // Used to display a deletion error to the User
        ArrayList<String> errors = new ArrayList<String>();

        // Attempt to delete the Location from the database using a BO. This will fail if there is any Hardware linking to this Location
        try {
            bo.delete(long_key);
        } catch (BoException e) {
            errors.add("There is currently hardware existing at this location.");
            log.error(e.getMessage());
        }
        
        // If the deletion did not occur
        if (errors.size() > 0) {
            // Attach errors to the request
            request.setAttribute("errors", errors);

            // Set the Location as an attribute for the request
            request.setAttribute("location", location);

            // Forward back to the View Location page
            request.getRequestDispatcher("/WEB-INF/flows/locations/view-location.jsp").forward(
                            request, response);
            return;
        }

        // Redirect to the results page, as the item no longer exists and does
        // not have a page
        this.sendRedirect(request, response, "results-location");

    }

}