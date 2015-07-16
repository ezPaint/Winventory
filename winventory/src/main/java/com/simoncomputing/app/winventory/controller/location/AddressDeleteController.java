package com.simoncomputing.app.winventory.controller.location;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.simoncomputing.app.winventory.bo.AddressBo;
import com.simoncomputing.app.winventory.controller.BaseController;
import com.simoncomputing.app.winventory.util.BoException;

/**
 * Controller for the delete user function
 */
@WebServlet("/location/delete-address")
public class AddressDeleteController extends BaseController {

    private static final long serialVersionUID = 1L;
    private Logger logger = Logger.getLogger(AddressDeleteController.class);
    private String key = "";
    
    /**
     * Runs when the "delete" button is selected on the "location/view-address" page
     * url:  <contextPath>/location/delete-address?key=<key>
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                    throws ServletException, IOException {
            
        // make sure the user has delete permission
        if (!this.userHasPermission(request, "deleteAddress")) {
            this.denyPermission(request, response);
            return;
        }
        
        try {
            // get the key
            key = request.getParameter("key");
            
            //delete selected software from database
            AddressBo.getInstance().delete(Long.valueOf(key)); 
            
            //Record event 
            //EventBo.getInstance().createSystemEvent(name + " was deleted.", 
                //getUserInfo(request), EventType.SYSTEM, null, address, null, null);
        } catch (BoException e) {
            logError(logger, e);
            String error = "The request could not be completed. There are locations which currently exist at this address.";
            sendRedirect(request, response, "/winventory/location/results-address?error=" + error);
            return;
        }
        
        //forward(request, response, "/WEB-INF/flows/software/results.jsp");
        sendRedirect(request, response, "/winventory/location/results-address?success=" + true);

    }

}