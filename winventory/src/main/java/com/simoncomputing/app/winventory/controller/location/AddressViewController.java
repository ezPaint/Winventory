package com.simoncomputing.app.winventory.controller.location;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.simoncomputing.app.winventory.bo.AddressBo;
import com.simoncomputing.app.winventory.controller.BaseController;
import com.simoncomputing.app.winventory.domain.Address;
import com.simoncomputing.app.winventory.util.BoException;

/**
 * Controller for the singular Address View page
 */
@WebServlet("/location/view-address")
public class AddressViewController extends BaseController {

    private static final long serialVersionUID = 1L;

    private Logger log = Logger.getLogger(AddressViewController.class);

    /**
     * Runs when the "location/view-address" page is accessed by a link or
     * through the url
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
                    throws ServletException, IOException {

        // Check to see if the current User has the permissions to update
        // Addresses, and returns if they do not
        if (!this.userHasPermission(request, "readAddress")) {
            this.denyPermission(request, response);
            return;
        }

        // Retrieve the key parameter from the request
        String key = request.getParameter("key");

        // Create an Address and attempt to retrieve the Address associated
        // with the key value (assuming there is a key parameter in the request)
        Address address = null;
        Long long_key = null;
        String error = null;

        if (key != null) {
            // Cast the key to the correct type
            long_key = Long.parseLong(key);
        }
        // This will fail if there is no key (i.e. the url ends at
        // "location/view-address")
        try {
            // Retrieve the Address associated with it using a BO instance
            address = AddressBo.getInstance().read(long_key);
        } catch (BoException e) {
            log.info("Invalid key for HTTP GET /location/view-address. The page will show no results." + logError(log, e));
            error = "No address key was entered";
        }

        // If the Address is not found, this will throw an error (i.e. the url
        // contains a key that does not exist at "location/view-address?key=")
        if (address == null && error == null) {
            log.info("Invalid key for HTTP GET /location/view-address. The page will show no results." + log, new NullPointerException());
            error = "No address exists with key " + key + ". The address may have been deleted or that may be the wrong key.";
        }

        // Set the Address as an attribute for the request
        request.setAttribute("address", address);
        
        request.setAttribute("error", error);
        
        request.setAttribute("delete", request.getParameter("delete"));
        
        request.setAttribute("success", request.getParameter("success"));

        // Forward the request to the "location/view-address" page
        request.getRequestDispatcher("/WEB-INF/flows/locations/view-address.jsp").forward(request,
                        response);

    }

    /**
     * NO LONGER ACTIVE - used to be used for deletes
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                    throws ServletException, IOException {

        // Check to see if the current User has the permissions to delete
        // Addresses, and returns if they do not
        if (!this.userHasPermission(request, "deleteAddress")) {
            this.denyPermission(request, response);
            return;
        }

        // Retrieve the key parameter from the request
        String key = request.getParameter("key");

        // Retrieve the key for the Address to be deleted from the database
        AddressBo bo = AddressBo.getInstance();
        Address address = null;
        Long long_key = null;
        if (key != null) {
            long_key = Long.parseLong(key);
        }
        try {
            // Retrieve the Address associated with it using a BO instance
            address = bo.read(long_key);
        } catch (BoException e) {
            String error = logError(log, e);
            request.setAttribute("error", "Error code: " + error);
        }

        // If the Address is not found, log the error and report the problem to
        // the user
        if (address == null) {
            String error = logError(log, new NullPointerException());
            request.setAttribute("error", "Error code: " + error);
        }

        // Used to display a deletion error to the User
        ArrayList<String> errors = new ArrayList<String>();

        // Attempt to delete the Address from the database using a BO. It will
        // fail if there is any Location which uses this address
        try {
            bo.delete(long_key);
        } catch (BoException e) {
            errors.add("There are existing locations which use this address.");
            log.error(e.getMessage());
        }

        // If the deletion did not occur
        if (errors.size() > 0) {
            // Attach errors to the request
            request.setAttribute("errors", errors);

            // Set the Address as an attribute for the request
            request.setAttribute("address", address);

            // Forward back to the View Address page
            request.getRequestDispatcher("/WEB-INF/flows/locations/view-address.jsp").forward(
                            request, response);
            return;
        }

        // Redirect to the results page, as the item no longer exists and does
        // not have a page
        this.sendRedirect(request, response, "results-address");

    }

}