package com.simoncomputing.app.winventory.controller.location;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.simoncomputing.app.winventory.bo.LocationBo;
import com.simoncomputing.app.winventory.controller.BaseController;
import com.simoncomputing.app.winventory.domain.Location;
import com.simoncomputing.app.winventory.util.BoException;

/**
 * Controller for the Results ("All Hardware") page
 */
@WebServlet("/location/results-location")
public class LocationResultsController extends BaseController {

    private static final long serialVersionUID = 1L;

    private Logger log = Logger.getLogger(LocationResultsController.class);

    /**
     * Runs when the "hardware/results" page is accessed by a general link or
     * through the url
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
                    throws ServletException, IOException {

        // Attempt to get all hardware from database using a BO
        ArrayList<Location> results = null;
        try {
            results = new ArrayList<Location>(LocationBo.getInstance().getAll());
        } catch (BoException e) {
            request.setAttribute("error", e.getMessage());
            log.error(e.getMessage());
        }

        // Set the results as an attribute for the page
        if (results != null) {
            request.setAttribute("results", results);
        }

        // Sets the page header to "All Hardware"
        request.setAttribute("page_header", "All Locations");

        // Forward the request to the "hardware/results" page
        request.getRequestDispatcher("/WEB-INF/flows/locations/results-location.jsp").forward(request,
                        response);

    }

    /**
     * Not currently used, for future use any page could forward an ArrayList of
     * Hardware to this page and it will be displayed in table format
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                    throws ServletException, IOException {

        @SuppressWarnings("unchecked")
        ArrayList<Location> results = (ArrayList<Location>) request.getAttribute("results");

        if (results != null) {
            request.setAttribute("results", results);
        }

        request.setAttribute("page_header", "Results");

        request.getRequestDispatcher("/WEB-INF/flows/locations/results-location.jsp").forward(request,
                        response);
    }

}