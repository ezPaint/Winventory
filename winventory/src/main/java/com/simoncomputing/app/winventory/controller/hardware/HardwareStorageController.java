package com.simoncomputing.app.winventory.controller.hardware;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.simoncomputing.app.winventory.bo.HardwareBo;
import com.simoncomputing.app.winventory.controller.BaseController;
import com.simoncomputing.app.winventory.domain.Hardware;
import com.simoncomputing.app.winventory.util.BoException;

/**
 * Controller for the Results ("Hardware In Storage") page
 */
@WebServlet("/hardware/storage")
public class HardwareStorageController extends BaseController {
    private static final long serialVersionUID = 1L;

    private Logger log = Logger.getLogger(HardwareStorageController.class);

    /**
     * Runs when the "hardware/storage" page is accessed by a general link or
     * through the url
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
                    throws ServletException, IOException {

        // Attempt to get all hardware in use from database using a BO
        ArrayList<Hardware> results = null;
        try {
            results = new ArrayList<Hardware>(HardwareBo.getInstance().getStorage());
        } catch (BoException e) {
            request.setAttribute("error", e.getMessage());
            log.error(e.getMessage());
        }

        // Set the results as an attribute for the page
        if (results != null) {
            request.setAttribute("results", results);
        }

        // Sets the page header to "Hardware in Storage"
        request.setAttribute("page_header", "Hardware In Storage");

        // Forward the request to the "hardware/results" page
        request.getRequestDispatcher("/WEB-INF/flows/hardware/results.jsp").forward(request,
                        response);
    }

    /**
     * Not currently used
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                    throws ServletException, IOException {

    }
}
