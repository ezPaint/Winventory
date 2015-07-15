package com.simoncomputing.app.winventory.controller.software;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import com.simoncomputing.app.winventory.bo.EventBo;
import com.simoncomputing.app.winventory.bo.SoftwareBo;
import com.simoncomputing.app.winventory.controller.BaseController;
import com.simoncomputing.app.winventory.domain.Event;
import com.simoncomputing.app.winventory.domain.EventType;
import com.simoncomputing.app.winventory.domain.Software;
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
            EventBo.getInstance().unlink(new Event(), sw);
            EventBo.getInstance().createSystemEvent(name + " was deleted.", 
                getUserInfo(request), EventType.SYSTEM, null, null, sw, null);
        } catch (Exception e) {
            logError(logger, e);
        }
        
        // Reload results page with new software object deleted or updated
        ArrayList<Software> results = null;
        try {
            results = new ArrayList<Software>(SoftwareBo.getInstance().getAll());
        } catch (BoException e) {
            logError(logger, e);
        }
        
        if (results != null) {
            request.setAttribute("results", results);
        }
        
        sendRedirect(request, response, "/winventory/software?key=" + key + "&success=" + true);

    }

}

