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
import com.simoncomputing.app.winventory.bo.RoleBo;
import com.simoncomputing.app.winventory.controller.BaseController;
import com.simoncomputing.app.winventory.domain.Address;
import com.simoncomputing.app.winventory.domain.Location;
import com.simoncomputing.app.winventory.domain.Role;
import com.simoncomputing.app.winventory.util.BoException;

/**
 * Servlet to handle the add user functionality, or insert user page.
 * 
 */


@WebServlet("/locations/insert")
public class LocationInsertController extends BaseController {
    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(LocationInsertController.class);


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
         
        request.getRequestDispatcher("/WEB-INF/flows/locations/insert-location.jsp").forward(request, response);  
    }


    protected void doPost( HttpServletRequest request, HttpServletResponse response ) 
        throws ServletException, IOException {
        
        // check for the create user permission
        /*if (this.requirePermission(request, response, "createUser")) {
            return;
        }*/
       
    }

}
