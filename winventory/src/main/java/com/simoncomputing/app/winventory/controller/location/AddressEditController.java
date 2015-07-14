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
        // "location/edit-address")
        try {
            // Retrieve the Address associated with it using a BO instance
            address = AddressBo.getInstance().read(long_key);
        } catch (BoException e) {
            String error = logError(log, e);
            request.setAttribute("error", "Error code: " + error);
        }

        // If the Address is not found, this will throw an error (i.e. the url
        // contains a key that does not exist at "location/edit-address?key=")
        if (address == null) {
            String error = logError(log, new NullPointerException());
            request.setAttribute("error", "Error code: " + error);
        }

        // Set the Address as an attribute for the request
        request.setAttribute("address", address);

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

        // Retrieve the key parameter from the request
        String key = request.getParameter("key");

        // Create an Address and attempt to retrieve the Address associated
        // with the key value (assuming there is a key parameter in the request)
        AddressBo bo = AddressBo.getInstance();
        Address address = null;
        Long long_key = null;
        if (key != null) {
            // Cast the key to the correct type
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

        // Get all of the potentially updated parameters from the request and
        // set them for the Address
        address.setName(request.getParameter("name"));
        address.setStreet1(request.getParameter("street1"));
        address.setStreet2(request.getParameter("street2"));
        address.setCity(request.getParameter("city"));
        address.setState(request.getParameter("state"));
        address.setZipcode(request.getParameter("zipcode"));

        // Use the BO to attempt updating the Address in the database
        try {
            bo.update(address);
        } catch (BoException e) {
            String error = logError(log, e);
            request.setAttribute("error", "Error code: " + error);
        }

        // Set the key in the request
        request.setAttribute("key", key);

        // Redirect to the "location/view-address" page for this specific
        // Address
        this.sendRedirect(request, response, "view-address?key=" + key);

    }

}