package com.simoncomputing.app.winventory.controller.location;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.simoncomputing.app.winventory.controller.BaseController;
import com.simoncomputing.app.winventory.domain.Address;

/**
 * Servlet to handle the add address functionality, or insert address page.
 * 
 */


@WebServlet("/location/insert-address")
public class AddressInsertController extends BaseController {
    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(AddressInsertController.class);

    // Occurs when the user tries to access the insert-address form
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) 
        throws ServletException, IOException {
        
    	// check for the create address permission
        if (!this.userHasPermission(request, "createAddress")) {
            this.denyPermission(request, response);
            return;
        }
         
    	// forward the user to the address insert form
        request.getRequestDispatcher("/WEB-INF/flows/locations/insert-address.jsp").forward(request, response);  
    }

    // Occurs when the user submits the insert-address form
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) 
        throws ServletException, IOException {
        
    	// check for the create address permission
        if (!this.userHasPermission(request, "createAddress")) {
            this.denyPermission(request, response);
            return;
        }
    	
    	Address address = new Address();
    	
    	// form errors
        ArrayList<String> errors = new ArrayList<String>();
        errors = address.bindInsertForm(request);
        
        // Create the new address in the db, if no errors exist
        if (errors.size() == 0) {
            address.create();
        }
        
        // if errors, return to add address page and display errors
        if (errors.size() > 0) {
            // attach errors to the request
            request.setAttribute("errors", errors);
            // set the previously inserted attributes so user won't
            // have to re-enter them
            request.setAttribute("name", address.getName());
            request.setAttribute("street1", address.getStreet1());
            request.setAttribute("street2", address.getStreet2());
            request.setAttribute("city", address.getCity());
            request.setAttribute("state", address.getState());
            request.setAttribute("zipcode", address.getZipcode());
            request.setAttribute("isActive", address.getIsActive());
            
            logger.error("An error occurred trying to insert a new address.");
            
            // forward to jsp and return from method
            request.getRequestDispatcher("/WEB-INF/flows/locations/insert-address.jsp").forward(request, response);
            return;
        }
    	
        // redirect back to results if the insert was successful
    	response.sendRedirect(request.getContextPath() + "/location/results-address?success=true");
       
    }

}
