package com.simoncomputing.app.winventory.controller.hardware;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.simoncomputing.app.winventory.bo.HardwareBo;
import com.simoncomputing.app.winventory.bo.RefConditionBo;
import com.simoncomputing.app.winventory.bo.UserBo;
import com.simoncomputing.app.winventory.controller.BaseController;
import com.simoncomputing.app.winventory.domain.Hardware;
import com.simoncomputing.app.winventory.domain.RefCondition;
import com.simoncomputing.app.winventory.domain.User;
import com.simoncomputing.app.winventory.util.BoException;

/**
 * Controller for the Edit Hardware page
 */
@WebServlet("/hardware/edit")
public class HardwareEditController extends BaseController {

    private static final long serialVersionUID = 1L;

    private Logger log = Logger.getLogger(HardwareEditController.class);

    /**
     * Runs when the "hardware/edit" page is accessed by a link or through the
     * url
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // permission check
        if (!this.userHasPermission(request, "updateHardware")) {
            this.denyPermission(request, response);
            return;
        }

        // Retrieve the key parameter from the request
        String key = request.getParameter("key");

        // Create a Hardware and attempt to retrieve the Hardware associated
        // with the key value (assuming there is a key parameter in the request)
        Hardware hardware = null;
        Long long_key = null;
        if (key != null && !key.equals("")) {
            // Cast the key to the correct type
            try {
                long_key = Long.parseLong(key);
            } catch (Exception e) {
                String error = logError(log, e);
                request.setAttribute("error", "Error code: " + error);
            }
        }
        // This will fail if there is no key (i.e. the url ends at
        // "hardware/edit")
        try {
            // Retrieve the Hardware associated with it using a BO instance
            hardware = HardwareBo.getInstance().read(long_key);
        } catch (BoException e) {
            String error = logError(log, e);
            request.setAttribute("error", "Error code: " + error);
        }

        // If the hardware is not found, this will throw an error (i.e. the url
        // contains a key that does not exist at "hardware/edit?key=")
        if (hardware == null) {
            String error = logError(log, new NullPointerException());
            request.setAttribute("error", "Error code: " + error);
        } else {
            try {
                if (hardware.getUserId() != null) {
                    User u = UserBo.getInstance().read(hardware.getUserId());

                    if (u != null) {
                        request.setAttribute("username", u.getUsername());
                    } else {
                        String error = logError(log, new NullPointerException());
                        request.setAttribute("error", "Error code: " + error);
                    }
                } else {
                    request.setAttribute("locationID", hardware.getLocationId());
                }
            } catch (BoException e) {
                String error = logError(log, e);
                request.setAttribute("error", "Error code: " + error);
            }
        }

        // Create an ArrayList of all the valid Ref_Conditions, which will be
        // used to prevent the user from entering or choosing a non-valid
        // condition
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

        // Set the hardware as an attribute for the request
        request.setAttribute("hardware", hardware);

        // Forward the request to the "hardware/edit" page
        request.getRequestDispatcher("/WEB-INF/flows/hardware/edit.jsp").forward(request, response);

    }

    /**
     * Runs when a form is submitted from the "hardware/edit" page (occurs when
     * the changes are submitted)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // permission check
        if (!this.userHasPermission(request, "updateHardware")) {
            this.denyPermission(request, response);
            return;
        }

        // Retrieve the key parameter from the request
        String key = request.getParameter("key");
        Long long_key = null;
        if (key != null && !key.equals("")) {
            // Cast the key to the correct type
            try {
                long_key = Long.parseLong(key);
            } catch (Exception e) {
                String error = logError(log, e);
                request.setAttribute("error", "Error code: " + error);
            }
        }
        Hardware h = new Hardware();
        ArrayList<String> errors = h.bind(request);
        h.setKey(long_key);

        if (errors.size() == 0) {
            request.setAttribute("success", true);

            // Use a BO to attempt to create this hardware item and place it in
            // the database
            HardwareBo bo = HardwareBo.getInstance();
            try {
                bo.update(h);
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
            
            // Redirect to the "hardware/view" page for this specific hardware
            if ((boolean)request.getAttribute("success")) {
                this.sendRedirect(request, response, "view?key=" + key + "&success=true");
            }
            else {
                this.sendRedirect(request, response, "view?key=" + key);
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

            Hardware hardware = null;
            try {
                hardware = HardwareBo.getInstance().read(long_key);
            } catch (BoException e) {
                String error = logError(log, e);
                request.setAttribute("error", "Error code: " + error);
                request.setAttribute("success", false);
            }

            try {
                if (hardware.getUserId() != null) {
                    User u = UserBo.getInstance().read(hardware.getUserId());

                    if (u != null) {
                        request.setAttribute("username", u.getUsername());
                    } else {
                        String error = logError(log, new NullPointerException());
                        request.setAttribute("error", "Error code: " + error);
                    }
                } else {
                    request.setAttribute("locationID", hardware.getLocationId());
                }
            } catch (Exception e) {
                String error = logError(log, e);
                request.setAttribute("error", "Error code: " + error);
                request.setAttribute("success", false);
            }

            request.setAttribute("hardware", hardware);
            request.setAttribute("success", false);
            request.setAttribute("errors", errors);
            // Forward the request to the "hardware/insert" page to allow the user
            // to enter more items
            request.getRequestDispatcher("/WEB-INF/flows/hardware/edit.jsp").forward(request,
                    response);

        }
    }
}