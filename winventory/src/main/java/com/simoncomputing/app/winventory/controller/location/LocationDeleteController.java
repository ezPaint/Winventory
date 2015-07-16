package com.simoncomputing.app.winventory.controller.location;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.simoncomputing.app.winventory.bo.EventBo;
import com.simoncomputing.app.winventory.bo.LocationBo;
import com.simoncomputing.app.winventory.controller.BaseController;
import com.simoncomputing.app.winventory.domain.EventType;
import com.simoncomputing.app.winventory.domain.Location;
import com.simoncomputing.app.winventory.util.BoException;

/**
 * Controller for the delete user function
 */
@WebServlet("/location/delete-location")
public class LocationDeleteController extends BaseController {

    private static final long serialVersionUID = 1L;
    private Logger logger = Logger.getLogger(LocationDeleteController.class);
    private String key = "";

    /**
     * Runs when the "delete" button is selected on the "location/view-location"
     * page url: <contextPath>/location/delete-address?key=<key>
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // make sure the user has delete permission
        if (!this.userHasPermission(request, "deleteLocation")) {
            this.denyPermission(request, response);
            return;
        }

        try {
            // get the key
            key = request.getParameter("key");

            Location location = LocationBo.getInstance().read(Long.valueOf(key));
            Long key = location.getKey();

            // delete selected software from database
            LocationBo.getInstance().delete(Long.valueOf(key));

            // Record event
            String description = "Deleted Location with id " + key + " and all of its "
                    + "associated events. Location: " + location.toString();
            EventBo.getInstance().createSystemEvent(description, getUserInfo(request),
                    EventType.SYSTEM, null, null, null, null);

        } catch (BoException e) {
            logError(logger, e);
            String error = "There was an error processing your delete request.";
            sendRedirect(request, response, "/winventory/location/results-location?error=" + error);
            return;
        }

        // forward(request, response, "/WEB-INF/flows/software/results.jsp");
        sendRedirect(request, response, "/winventory/location/results-location?success=" + true);

    }

}