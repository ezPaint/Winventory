package com.simoncomputing.app.winventory.controller.hardware;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

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
import com.simoncomputing.app.winventory.util.Barcoder;
import com.simoncomputing.app.winventory.util.BoException;

/**
 * Controller for the singular Hardware View page
 */
@WebServlet("/hardware/delete")
public class HardwareDeleteController extends BaseController {

    private static final long serialVersionUID = 1L;

    private Logger log = Logger.getLogger(HardwareViewController.class);

    /**
     * Deletes a hardware item from the db.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check to see if the current User has the permissions to delete
        // Hardware items, and returns if they do not
        if (!this.userHasPermission(request, "deleteHardware")) {
            this.denyPermission(request, response);
            return;
        }

        // Retrieve the key parameter from the request
        String key = request.getParameter("key");

        // Retrieve the key for the Hardware item to be deleted from the
        // database
        HardwareBo bo = HardwareBo.getInstance();
        Long long_key = null;
        if (key != null) {
            try {
                long_key = Long.parseLong(key);
            } catch (Exception e) {
                String error = logError(log, e);
                request.setAttribute("error", "Error code: " + error);
            }
        }
        
        

        // Attempt to get and delete the Hardware item from the database using a BO
        try {
            
            // Get hardware info
            Hardware hw = HardwareBo.getInstance().read(Long.valueOf(key));
            
            // delete the hardware (and its events, included in bo.delete() )
            bo.delete(long_key);
            log.info("Deleted hardware #" + long_key + ".");
            
            //Record event 
            String description = "Deleted Hardware with id " + key + " and all of its "
                    + "associated events. Hardware: " + hw.toString();
            EventBo.getInstance().createSystemEvent(description, getUserInfo(request), 
                    EventType.SYSTEM, null, null, null, null);
            
        } catch (BoException e) {
            String error = "Hardware item could not be deleted at this time.";
            log.error("Attempt to delete hardware item #" + long_key + " failed.");
            log.error(e.getMessage());
            response.sendRedirect(request.getContextPath() + "/hardware/view?key=" + key + "&error=" + error);
            return;
        }

        // Redirect to the results page, as the item no longer exists and does
        // not have a page
        this.sendRedirect(request, response, "results?success=true");

    }

}
