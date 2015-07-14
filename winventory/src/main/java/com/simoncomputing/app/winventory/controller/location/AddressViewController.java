package com.simoncomputing.app.winventory.controller.location;

import java.io.IOException;

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

        // Retrieve the key parameter from the request
        String key = request.getParameter("key");

        // Create an Address and attempt to retrieve the Address associated
        // with the key value (assuming there is a key parameter in the request)
        Address address = null;
        Long long_key = null;

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
            String error = logError(log, e);
            request.setAttribute("error", "Error code: " + error);
        }

        // If the Address is not found, this will throw an error (i.e. the url
        // contains a key that does not exist at "location/view-address?key=")
        if (address == null) {
            String error = logError(log, new NullPointerException());
            request.setAttribute("error", "Error code: " + error);
        }

        // Set the Address as an attribute for the request
        request.setAttribute("address", address);

        // Forward the request to the "location/view-address" page
        request.getRequestDispatcher("/WEB-INF/flows/locations/view-address.jsp").forward(request,
                        response);

    }

    /**
     * Runs when the "delete" button is selected on the "location/view-address"
     * page
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                    throws ServletException, IOException {

        // Check to see if the current User has the permissions to delete
        // Addresses, and returns if they do not
        if (this.requirePermission(request, response, "deleteAddress")) {
            return;
        }

        // Retrieve the key parameter from the request
        String key = request.getParameter("key");

        // Retrieve the key for the Address to be deleted from the database
        AddressBo bo = AddressBo.getInstance();
        Long long_key = null;
        if (key != null) {
            long_key = Long.parseLong(key);
        }

        // Attempt to delete the Address from the database using a BO
        try {
            bo.delete(long_key);
        } catch (BoException e) {
            request.setAttribute("error", e.getMessage());
            log.error(e.getMessage());
        }

        // Redirect to the results page, as the item no longer exists and does
        // not have a page
        this.sendRedirect(request, response, "results-address");

    }

}