package com.simoncomputing.app.winventory.controller.software;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.simoncomputing.app.winventory.bo.AccessTokenBo;
import com.simoncomputing.app.winventory.bo.EventBo;
import com.simoncomputing.app.winventory.bo.HardwareBo;
import com.simoncomputing.app.winventory.bo.LocationBo;
import com.simoncomputing.app.winventory.bo.SoftwareBo;
import com.simoncomputing.app.winventory.bo.UserBo;
import com.simoncomputing.app.winventory.controller.BaseController;
import com.simoncomputing.app.winventory.domain.Event;
import com.simoncomputing.app.winventory.domain.EventType;
import com.simoncomputing.app.winventory.domain.Hardware;
import com.simoncomputing.app.winventory.domain.Location;
import com.simoncomputing.app.winventory.domain.Software;
import com.simoncomputing.app.winventory.domain.User;
import com.simoncomputing.app.winventory.formbean.UserInfoBean;
import com.simoncomputing.app.winventory.util.BoException;

/**
 * Controller for the delete user function
 */
@WebServlet("/software/delete")
public class SoftwareDeleteController extends BaseController {

    private static final long serialVersionUID = 1L;
    private String name = ""; // for redirecting
    private Logger logger = Logger.getLogger(SoftwareDeleteController.class);
    private String key = "";
    
    /**
     * Runs when the "delete" button is selected on the "users/view" page
     * url:  <contextPath>/users/delete?key=<key>
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                    throws ServletException, IOException {
            
        // make sure the user has delete permission
        if (!this.userHasPermission(request, "deleteSoftware")) {
            this.denyPermission(request, response);
            return;
        }
        
        try {
            // get the key
            key = request.getParameter("key");
            
           
            // Get software info
            Software sw = SoftwareBo.getInstance().read(Long.valueOf(key));
            String name = sw.getName();
            this.name = name; //for redirecting
            
            //delete selected software from database
            SoftwareBo.getInstance().delete(Long.valueOf(key)); 
            
            //Record event 
            String description = "Deleted Software with id " + key + " and all of its "
                    + "associated events. Software: " + sw.toString();
            EventBo.getInstance().createSystemEvent(description, getUserInfo(request), 
                    EventType.SYSTEM, null, null, null, null);
            
        } catch (Exception e) {
            logError(logger, e);
        }
        
        // Reload results page with new software object deleted or updated
        ArrayList<Software> results = null;
        try {
            results = new ArrayList<Software>(SoftwareBo.getInstance().getAll());
        } catch (BoException e) {
            logError(logger, e);
            String error = "There was an error processing your delete request.";
            sendRedirect(request, response, "/winventory/software/results?key=" + key + "&error=" + error);
            return;
        }
        
        //forward(request, response, "/WEB-INF/flows/software/results.jsp");
        sendRedirect(request, response, "/winventory/software/results?key=" + key + "&success=" + true);

    }

}

