package com.simoncomputing.app.winventory.controller.location;

import java.io.IOException;
import java.util.ArrayList;

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
        // "location/edit-location")
        try {
            // Retrieve the Location associated with it using a BO instance
            location = LocationBo.getInstance().read(long_key);
        } catch (BoException e) {
            String error = logError(log, e);
            request.setAttribute("error", "Error code: " + error);
        }

        // If the Location is not found, this will throw an error (i.e. the url
        // contains a key that does not exist at "location/edit-location?key=")
        if (location == null) {
            String error = logError(log, new NullPointerException());
            request.setAttribute("error", "Error code: " + error);
        }

        // Set the Location as an attribute for the request
        request.setAttribute("location", location);
        
        // Get the Addresses in the database to be used as choices for the "addressId" field
        ArrayList<Address> addresses = new ArrayList<Address>();
        try {
            addresses = (ArrayList<Address>) AddressBo.getInstance().getAll();
        } catch (BoException e) {
            log.error("BoException in LocationInsertController when trying to get locations");
        }
        if (addresses != null) {
            request.setAttribute("addresses", addresses);
        }

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

        // Retrieve the key parameter from the request
        String key = request.getParameter("key");

        // Create a Location and attempt to retrieve the Location associated
        // with the key value (assuming there is a key parameter in the request)
        LocationBo bo = LocationBo.getInstance();
        Location location = null;
        Long long_key = null;
        if (key != null) {
            // Cast the key to the correct type
            long_key = Long.parseLong(key);
        }
        try {
            // Retrieve the Location associated with it using a BO instance
            location = LocationBo.getInstance().read(long_key);
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

        // Get all of the potentially updated parameters from the request and
        // set them for the Location
        location.setDescription(request.getParameter("description"));
        location.setIsActive(Boolean.parseBoolean(request.getParameter("isActive")));
        location.setAddressId(Integer.parseInt(request.getParameter("addressId")));

        // Use the BO to attempt updating the Location in the database
        try {
            bo.update(location);
        } catch (BoException e) {
            String error = logError(log, e);
            request.setAttribute("error", "Error code: " + error);
        }

        // Set the key in the request
        request.setAttribute("key", key);

        // Redirect to the "location/view-location" page for this specific
        // Location
        this.sendRedirect(request, response, "view-location?key=" + key);

    }

}