package com.simoncomputing.app.winventory.controller.location;

import java.io.IOException;
import java.util.ArrayList;

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
import com.simoncomputing.app.winventory.domain.EventType;
import com.simoncomputing.app.winventory.domain.Location;
import com.simoncomputing.app.winventory.util.BoException;

/**
 * Controller for the Edit Location page
 */
@WebServlet("/location/edit-location")
public class LocationEditController extends BaseController {

    private static final long serialVersionUID = 1L;

    private Logger log = Logger.getLogger(LocationEditController.class);

    /**
     * Runs when the "location/edit-location" page is accessed by a link or
     * through the url
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
                    throws ServletException, IOException {

        // Check to see if the current User has the permissions to update
        // Locations, and returns if they do not
        if (!this.userHasPermission(request, "updateLocation")) {
            this.denyPermission(request, response);
            return;
        }
        
        // Retrieve the key parameter from the request
        String key = request.getParameter("key");

        // Create a Location and attempt to retrieve the Location associated
        // with the key value (assuming there is a key parameter in the request)
        Location location = null;
        Long long_key = null;
        String error = null;
        
        if (key != null) {
            // Cast the key to the correct type
            long_key = Long.parseLong(key);
        }
        // This will fail if there is no key (i.e. the url ends at
        // "location/edit-location")
        try {
            // Retrieve the Location associated with it using a BO instance
            location = LocationBo.getInstance().read(long_key);
        } catch (BoException e) {
            log.info("Invalid key for HTTP GET /location/edit-location. The page will show no results." + logError(log, e));
            error = "No location key was entered";
        }

        // If the Location is not found, this will throw an error (i.e. the url
        // contains a key that does not exist at "location/edit-location?key=")
        if (location == null && error == null) {
            log.info("Invalid key for HTTP GET /location/edit-location. The page will show no results." + log, new NullPointerException());
            error = "No address exists with key " + key + ". The address may have been deleted or that may be the wrong key.";
        }

        // Set the Location as an attribute for the request
        request.setAttribute("location", location);
        
        // Get the Addresses in the database to be used as choices for the "addressId" field
        ArrayList<Address> addresses = new ArrayList<Address>();
        try {
            addresses = (ArrayList<Address>) AddressBo.getInstance().getAll();
        } catch (BoException e) {
            log.error("BoException in LocationEditController when trying to get locations");
            error = "Unable to retrieve a list of valid addresses";
        }
        if (addresses != null) {
            request.setAttribute("addresses", addresses);
        }
        
        request.setAttribute("error", error);

        // Forward the request to the "location/edit-location" page
        request.getRequestDispatcher("/WEB-INF/flows/locations/edit-location.jsp").forward(request,
                        response);

    }

    /**
     * Runs when a form is submitted from the "location/edit-location" page
     * (occurs when the changes are submitted)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                    throws ServletException, IOException {
        
        // Check to see if the current User has the permissions to update
        // Locations, and returns if they do not
        if (!this.userHasPermission(request, "updateLocation")) {
            this.denyPermission(request, response);
            return;
        }

        // Retrieve the key parameter from the request
        String key = request.getParameter("key");

        // Create a Location and attempt to retrieve the Location associated
        // with the key value (assuming there is a key parameter in the request)
        LocationBo bo = LocationBo.getInstance();
        Location location = null;
        Location original = null;
        Long long_key = null;
        String error = null;
        
        if (key != null) {
            // Cast the key to the correct type
            long_key = Long.parseLong(key);
        }
        try {
            // Retrieve the Location associated with it using a BO instance
            location = LocationBo.getInstance().read(long_key);
            original = LocationBo.getInstance().read(long_key);
        } catch (BoException e) {
            log.info("Invalid key for HTTP POST /location/edit-location. The page will show no results." + logError(log, e));
            error = "No location key was entered";
        }

        // If the Location is not found, log the error and report the problem to
        // the user
        if (location == null && error == null) {
            log.info("Invalid key for HTTP GET /location/edit-location. The page will show no results." + log, new NullPointerException());
            error = "No address exists with key " + key + ". The address may have been deleted or that may be the wrong key.";
        }

        // Get all of the potentially updated parameters from the request and
        // set them for the Location
        location.setDescription(request.getParameter("description"));
        location.setIsActive(Boolean.parseBoolean(request.getParameter("isActive")));
        location.setAddressId(Integer.parseInt(request.getParameter("addressId")));

        // Use the BO to attempt updating the Location in the database
        try {
            bo.update(location);
            
            if (!original.getDescription().equals(location.getDescription())) {
                String strang = "Description was changed from " + original.getDescription() + " to "
                        + location.getDescription();
                try {
                    EventBo.getInstance().createSystemEvent(strang, getUserInfo(request),
                            EventType.ADMIN, null, location, null, null);
                } catch (BoException e) {
                    log.info("Unable to record event for a change in description for the location");
                    error = "Unable to record event for a change in description for the location";
                }
            }
            
            if (original.getIsActive() != location.getIsActive()) {
                String strang;
                if (location.getIsActive()) {
                    strang = "Location was activated";
                } else {
                    strang = "Location was deactivated";
                }
                try {
                    EventBo.getInstance().createSystemEvent(strang, getUserInfo(request),
                            EventType.ADMIN, null, location, null, null);
                } catch (BoException e) {
                    log.info("Unable to record event for a change in active for the location");
                    error = "Unable to record event for a change in active for the location";
                }
            }
            
            if (!original.getAddressId().equals(location.getAddressId())) {
                String strang = "The address was changed from " + original.getAddress().getName() + " (address ID of " + original.getAddressId() + ") to "
                        + location.getAddress().getName() + " (address ID of " + location.getAddressId() + ")";
                try {
                    EventBo.getInstance().createSystemEvent(strang, getUserInfo(request),
                            EventType.ADMIN, null, location, null, null);
                } catch (BoException e) {
                    log.info("Unable to record event for a change in address ID for the location");
                    error = "Unable to record event for a change in address ID for the location";
                }
            }
            
            
        } catch (BoException e) {
            log.info("Invalid key for HTTP POST /location/edit-location. The page will show no results." + log, new NullPointerException());
            error = "An error occurred while trying to update location '" + location.getKey() + "'.";
        }

        // Set the key in the request
        request.setAttribute("key", key);

        // Redirect to the "location/view-location" page for this specific
        // Location
        this.sendRedirect(request, response, "view-location?key=" + key + "&success=true");

    }

}