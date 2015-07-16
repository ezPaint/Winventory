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
 * Controller for the Edit Address page
 */
@WebServlet("/location/edit-address")
public class AddressEditController extends BaseController {

    private static final long serialVersionUID = 1L;

    private Logger log = Logger.getLogger(AddressEditController.class);

    /**
     * Runs when the "hardware/address" page is accessed by a link or through
     * the url
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
                    throws ServletException, IOException {

        // Check to see if the current User has the permissions to update
        // Addresses, and returns if they do not
        if (!this.userHasPermission(request, "updateAddress")) {
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
        // "location/edit-address")
        try {
            // Retrieve the Address associated with it using a BO instance
            address = AddressBo.getInstance().read(long_key);
        } catch (BoException e) {
            log.info("Invalid key for HTTP GET /location/edit-address. The page will show no results." + logError(log, e));
            error = "No location key was entered";
        }

        // If the Address is not found, this will throw an error (i.e. the url
        // contains a key that does not exist at "location/edit-address?key=")
        if (address == null && error == null) {
            log.info("Invalid key for HTTP GET /location/edit-address. The page will show no results." + log, new NullPointerException());
            error = "No address exists with key " + key + ". The address may have been deleted or that may be the wrong key.";
        }

        // Set the Address as an attribute for the request
        request.setAttribute("address", address);
        
        request.setAttribute("error", error);

        // Forward the request to the "location/edit-address" page
        request.getRequestDispatcher("/WEB-INF/flows/locations/edit-address.jsp").forward(request,
                        response);

    }

    /**
     * Runs when a form is submitted from the "location/edit-address" page
     * (occurs when the changes are submitted)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                    throws ServletException, IOException {
        
        // Check to see if the current User has the permissions to update
        // Addresses, and redirects to permissionDenied if they do not
        if (!this.userHasPermission(request, "updateAddress")) {
            this.denyPermission(request, response);
            return;
        }

        // Retrieve the key parameter from the request
        String key = request.getParameter("key");

        // Create an Address and attempt to retrieve the Address associated
        // with the key value (assuming there is a key parameter in the request)
        AddressBo bo = AddressBo.getInstance();
        Address address = null;
        Long long_key = null;
        String error = null;
        if (key != null) {
            // Cast the key to the correct type
            long_key = Long.parseLong(key);
        }
        try {
            // Retrieve the Address associated with it using a BO instance
            address = bo.read(long_key);
        } catch (BoException e) {
            log.info("Invalid key for HTTP POST /location/edit-address. The page will show no results." + logError(log, e));
            error = "No location key was entered";
        }

        // If the Address is not found, log the error and report the problem to
        // the user
        if (address == null && error == null) {
            log.info("Invalid key for HTTP POST /location/edit-address. The page will show no results." + log, new NullPointerException());
            error = "No address exists with key " + key + ". The address may have been deleted or that may be the wrong key.";
        }

        // check if there are any errors that exist
        ArrayList<String> errors = new ArrayList<String>();
        errors = address.bindInsertFormForEdit(request);
        
        // Update if no errors, otherwise reload the page and inform the User
        if (errors.size() == 0) {
            
            // Get all of the potentially updated parameters from the request and
            // set them for the Address
            address.setName(request.getParameter("name"));
            address.setStreet1(request.getParameter("street1"));
            address.setStreet2(request.getParameter("street2"));
            address.setCity(request.getParameter("city"));
            address.setState(request.getParameter("state"));
            address.setZipcode(request.getParameter("zipcode"));
            address.setIsActive(Boolean.parseBoolean(request.getParameter("isActive")));
            
            // Use the BO to attempt updating the Address in the database
            try {
                bo.update(address);
            } catch (BoException e) {
                log.info("Invalid key for HTTP POST /location/edit-address. The page will show no results." + log, new NullPointerException());
                error = "An error occurred while trying to update address '" + address.getName() + "'.";
            }
        } else {
            // Attach errors to the request
            request.setAttribute("errors", errors);
            
            // Set the Address as an attribute for the request
            request.setAttribute("address", address);
            
            // forward to jsp and return from method
            request.getRequestDispatcher("/WEB-INF/flows/locations/edit-address.jsp").forward(request, response);
            return;
        }

        // Set the key in the request
        request.setAttribute("key", key);
        
        request.setAttribute("error", error);

        // Redirect to the "location/view-address" page for this specific
        // Address
        this.sendRedirect(request, response, "view-address?key=" + key + "&success=true");

    }

}