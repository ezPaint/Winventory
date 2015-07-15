package com.simoncomputing.app.winventory.controller.hardware;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.simoncomputing.app.winventory.bo.EventBo;
import com.simoncomputing.app.winventory.bo.HardwareBo;
import com.simoncomputing.app.winventory.bo.RefConditionBo;
import com.simoncomputing.app.winventory.controller.BaseController;
import com.simoncomputing.app.winventory.domain.EventType;
import com.simoncomputing.app.winventory.domain.Hardware;
import com.simoncomputing.app.winventory.domain.RefCondition;
import com.simoncomputing.app.winventory.util.BoException;

/**
 * Controller for the Insert Hardware page
 */
@WebServlet("/hardware/insert")
public class HardwareInsertController extends BaseController {

    private static final long serialVersionUID = 1L;

    private Logger log = Logger.getLogger(HardwareInsertController.class);

    /**
     * Runs when the "hardware/insert" page is accessed by a link or through the
     * url
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // permission check
        if (!this.userHasPermission(request, "createHardware")) {
            this.denyPermission(request, response);
            return;
        }

        // Create an ArrayList of all the valid Ref_Conditions, which will be
        // used to prevent the user from entering
        // or choosing a non-valid condition
        ArrayList<RefCondition> conditions = null;
        try {
            // Use a BO to attempt to grab all valid conditions from the
            // database
            conditions = new ArrayList<RefCondition>(RefConditionBo.getInstance().getAll());
        } catch (BoException e) {
            String error = logError(log, e);
            request.setAttribute("error", "Error code: " + error);
        }

        // Set the conditions as an attribute for the request
        if (conditions != null) {
            request.setAttribute("conditions", conditions);
        }

        // Forward the request to the "hardware/insert" page
        request.getRequestDispatcher("/WEB-INF/flows/hardware/insert.jsp").forward(request,
                response);
    }

    /**
     * Runs when a form is submitted from the "hardware/insert" page (occurs
     * when a Hardware is inserted)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // permission check
        if (!this.userHasPermission(request, "createHardware")) {
            this.denyPermission(request, response);
            return;
        }

        // Create a new Hardware and set its parameters based on the values from
        // the request
        // userId and locationId are set to null in order to allow associations
        // to be made later
        Hardware h = new Hardware();
        ArrayList<String> errors = h.bind(request);

        if (errors.size() == 0) {
            request.setAttribute("success", true);

            // Use a BO to attempt to create this hardware item and place it in
            // the database
            HardwareBo bo = HardwareBo.getInstance();
            try {
                bo.create(h);
            } catch (BoException e) {
                String error = logError(log, e);
                request.setAttribute("error", "Error code: " + error);
                request.setAttribute("success", false);
            }

            // Create an ArrayList of all the valid Ref_Conditions, which will
            // be used to prevent the user from entering
            // or choosing a non-valid condition
            ArrayList<RefCondition> conditions = null;
            try {
                // Use a BO to attempt to grab all valid conditions from the
                // database
                conditions = new ArrayList<RefCondition>(RefConditionBo.getInstance().getAll());
            } catch (BoException e) {
                String error = logError(log, e);
                request.setAttribute("error", "Error code: " + error);
                request.setAttribute("success", false);
            }

            // Reset the conditions as an attribute for the request
            if (conditions != null) {
                request.setAttribute("conditions", conditions);
            }
        } else {
            ArrayList<RefCondition> conditions = null;
            try {
                // Use a BO to attempt to grab all valid conditions from the
                // database
                conditions = new ArrayList<RefCondition>(RefConditionBo.getInstance().getAll());
            } catch (BoException e) {
                String error = logError(log, e);
                request.setAttribute("error", "Error code: " + error);
                request.setAttribute("success", false);
            }

            // Reset the conditions as an attribute for the request
            if (conditions != null) {
                request.setAttribute("conditions", conditions);
            }

            request.setAttribute("success", false);
            request.setAttribute("errors", errors);
        }

        // Add an event to this hardware that describing its creation.
        if (request.getAttribute("success").equals(true)) {
            try {

                if (h.getUserId() != null) {
                    String strang = "Inserted into DB and assigned to " + h.getUser().getUsername();
                    EventBo.getInstance().createSystemEvent(strang,
                            getUserInfo(request), EventType.ADMIN, h);
                } else if (h.getLocationId() != null) {
                    String strang = "Inserted into DB and stored at " + h.getLocation().getDescription();
                    EventBo.getInstance().createSystemEvent(strang,
                            getUserInfo(request), EventType.ADMIN, h);
                } else {
                    log.error("Hardware was somehow almost inserted into DB with no user or location ID");
                    errors.add("Hardware was somehow almost inserted into DB with no user or location ID.");
                    request.setAttribute("success", false);
                }

            } catch (BoException e) {
                log.error(e.getMessage());
                errors.add(e.getMessage());
                request.setAttribute("success", false);
            }
        }
        if (request.getAttribute("success") != null) {
            if ((boolean) request.getAttribute("success")) {
                request.setAttribute("success", "true");
            }
        }
        // Forward the request to the "hardware/insert" page to allow the user
        // to enter more items
        request.getRequestDispatcher("/WEB-INF/flows/hardware/insert.jsp").forward(request,
                response);
    }

}