package com.simoncomputing.app.winventory.controller.location;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.simoncomputing.app.winventory.bo.AddressBo;
import com.simoncomputing.app.winventory.bo.LocationBo;
import com.simoncomputing.app.winventory.controller.BaseController;
import com.simoncomputing.app.winventory.domain.Address;
import com.simoncomputing.app.winventory.domain.Location;
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
        }

        // Set the Location as an attribute for the request
        request.setAttribute("location", location);

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
        if (this.requirePermission(request, response, "deleteLocation")) {
            return;
        }

        // Retrieve the key parameter from the request
        String key = request.getParameter("key");

        // Retrieve the key for the Location to be deleted from the database
        LocationBo bo = LocationBo.getInstance();
        Long long_key = null;
        if (key != null) {
            long_key = Long.parseLong(key);
        }

        // Attempt to delete the Location from the database using a BO
        try {
            bo.delete(long_key);
        } catch (BoException e) {
            request.setAttribute("error", e.getMessage());
            log.error(e.getMessage());
        }

        // Redirect to the results page, as the item no longer exists and does
        // not have a page
        this.sendRedirect(request, response, "results-location");

    }

}