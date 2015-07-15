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
import com.simoncomputing.app.winventory.domain.Location;
import com.simoncomputing.app.winventory.util.BoException;

/**
 * Servlet to handle the add location functionality, or insert location page.
 * 
 */


@WebServlet("/location/insert-location")
public class LocationInsertController extends BaseController {
    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(LocationInsertController.class);

    // Occurs when the user tries to access the insert-location page
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) 
        throws ServletException, IOException {
        
    	// check for the create location permission
        if (!this.userHasPermission(request, "createLocation")) {
            this.denyPermission(request, response);
            return;
        }
        
    	// Set up the list of addresses to be used in the dropdown for
    	// the location insert form   	
    	 ArrayList<Address> addresses = new ArrayList<Address>();
         try {
        	 addresses = (ArrayList<Address>) AddressBo.getInstance().getAllActive();
         } catch (BoException e) {
             logger.error("BoException in LocationInsertController when trying to get locations");
         }
         if (addresses != null) {
             request.setAttribute("addresses", addresses);
         }
         
        request.getRequestDispatcher("/WEB-INF/flows/locations/insert-location.jsp").forward(request, response);  
    }

    // Occurs when the user submits the insert-location form
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) 
        throws ServletException, IOException {
        
        // check for the create location permission
        if (!this.userHasPermission(request, "createLocation")) {
            this.denyPermission(request, response);
            return;
        }
    	
    	Location location = new Location();
    	
    	// form errors
        ArrayList<String> errors = new ArrayList<String>();
        errors = location.bindInsertForm(request);
        
        // Create the new location in the db, if no errors exist
        if (errors.size() == 0) {
            location.create();
        }
    	
        // if errors, return to add location page and display errors
        if (errors.size() > 0) {
            // attach errors to the request
            request.setAttribute("errors", errors);
            // set the previously inserted attributes so user won't
            // have to re-enter them
            request.setAttribute("address", location.getAddressId());
            request.setAttribute("description", location.getDescription());
            request.setAttribute("isActive", location.getIsActive());
            
            // forward to jsp and return from method
            request.getRequestDispatcher("/WEB-INF/flows/locations/insert-location.jsp").forward(request, response);
            return;
        }
        
        // redirect back to location if the insert was successful
    	response.sendRedirect(request.getContextPath() + "/location/results-location");
 
    }

}
