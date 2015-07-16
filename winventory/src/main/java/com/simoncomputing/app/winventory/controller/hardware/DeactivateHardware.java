package com.simoncomputing.app.winventory.controller.hardware;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.simoncomputing.app.winventory.bo.HardwareBo;
import com.simoncomputing.app.winventory.controller.BaseController;
import com.simoncomputing.app.winventory.domain.Event;
import com.simoncomputing.app.winventory.domain.Hardware;
import com.simoncomputing.app.winventory.util.BoException;

/**
 * Controller for the Results ("Hardware In Storage") page
 */
@WebServlet("/hardware/deactivate")
public class DeactivateHardware extends BaseController {
    private static final long serialVersionUID = 1L;

    private Logger log = Logger.getLogger(DeactivateHardware.class);

    /**
     * Runs when the "hardware/storage" page is accessed by a general link or
     * through the url
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // NOPE
    }

    /**
     * Not currently used
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // YUP

        if (!userHasPermission(request, "updateHardware")) {
            denyPermission(request, response);
            return;
        }

        // Retrieve the key parameter from the request
        String key = request.getParameter("key");

        // Create a Hardware and attempt to retrieve the Hardware associated
        // with the key value (assuming there is a key parameter in the request)
        boolean success = false;
        Hardware hardware = null;
        Long long_key = null;

        if (key != null) {
            // Cast the key to the correct type

            try {
                long_key = Long.parseLong(key);
            } catch (Exception e) {
                log.info("Invalid key for HTTP GET /hardware/view. The page will show no results."
                        + logError(log, e));

            }
        }
        // This will fail if there is no key (i.e. the url ends at
        // "hardware/edit")
        try {
            // Retrieve the Hardware associated with it using a BO instance
            hardware = HardwareBo.getInstance().read(long_key);
        } catch (BoException e) {
            log.info("Invalid key for HTTP GET /hardware/view. The page will show no results."
                    + logError(log, e));
        }

        hardware.setIsActive(false);

        try {
            HardwareBo.getInstance().update(hardware);
            success = true;
        } catch (BoException e) {
            log.error("Error deactivating hardware");
        }

        if (success) {
            this.sendRedirect(request, response, "view?key=" + key + "&success=true");
        }

    }
}
