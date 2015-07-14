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
 * Servlet to handle the add user functionality, or insert user page.
 * 
 */


@WebServlet("/location/insert-address")
public class AddressInsertController extends BaseController {
    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(AddressInsertController.class);


    protected void doGet( HttpServletRequest request, HttpServletResponse response ) 
        throws ServletException, IOException {
        
        // if the user is rejected, the redirect is sent in the require permission method.
       /* if (this.requirePermission(request, response, "createUser")) {
            return;
        }*/
        
    	
    	 ArrayList<Address> addresses = new ArrayList<Address>();
         try {
        	 addresses = (ArrayList<Address>) AddressBo.getInstance().getAll();
         } catch (BoException e) {
             logger.error("BoException in LocationInsertController when trying to get locations");
         }
         if (addresses != null) {
             request.setAttribute("addresses", addresses);
         }
         
        request.getRequestDispatcher("/WEB-INF/flows/locations/insert-address.jsp").forward(request, response);  
    }


    protected void doPost( HttpServletRequest request, HttpServletResponse response ) 
        throws ServletException, IOException {
        
        // check for the create user permission
        /*if (this.requirePermission(request, response, "createUser")) {
            return;
        }*/
    	
    	Address address = new Address();
    	
    	// form errors
        ArrayList<String> errors = new ArrayList<String>();
        errors = address.bindInsertForm(request);
        
        // Create the new location in the db, if no errors exist
        if (errors.size() == 0) {
            address.create();
        }
        
        // if errors, return to add address page and display errors
        if (errors.size() > 0) {
            // attach errors to the request
            request.setAttribute("errors", errors);
            
            // forward to jsp and return from method
            request.getRequestDispatcher("/WEB-INF/flows/locations/insert-address.jsp").forward(request, response);
            return;
        }
    	
    	response.sendRedirect(request.getContextPath() + "/location/results-address");
       
    }

}
