package com.simoncomputing.app.winventory.controller.location;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.simoncomputing.app.winventory.bo.AddressBo;
import com.simoncomputing.app.winventory.controller.BaseController;
import com.simoncomputing.app.winventory.domain.Address;
import com.simoncomputing.app.winventory.util.BoException;

/**
 * Controller for the Results ("All Addresses") page
 */
@WebServlet("/location/results-address")
public class AddressResultsController extends BaseController {

    private static final long serialVersionUID = 1L;

    private Logger log = Logger.getLogger(AddressResultsController.class);

    /**
     * Runs when the "location/results-address" page is accessed by a general
     * link or through the url
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
                    throws ServletException, IOException {

        if(!userHasPermission(request, "readAddress")){
            denyPermission(request, response);
            return;
        }
        
        // Attempt to get all Addresses from database using a BO
        ArrayList<Address> results = null;
        try {
            results = new ArrayList<Address>(AddressBo.getInstance().getAll());
        } catch (BoException e) {
            request.setAttribute("error", "Unable to retrieve addresses from the database");
            log.error("Unable to retrieve addresses from the database. " + e.getMessage());
        }

        // Set the results as an attribute for the page
        if (results != null) {
            request.setAttribute("results", results);
        }

        // Sets the page header to "All Addresses"
        request.setAttribute("page_header", "All Addresses");
        
        request.setAttribute("success", request.getParameter("success"));
        request.setAttribute("error", request.getParameter("error"));

        // Forward the request to the "location/results-address" page
        request.getRequestDispatcher("/WEB-INF/flows/locations/results-address.jsp").forward(
                        request, response);

    }

    /**
     * Not currently used
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                    throws ServletException, IOException {

        @SuppressWarnings("unchecked")
        ArrayList<Address> results = (ArrayList<Address>) request.getAttribute("results");

        if (results != null) {
            request.setAttribute("results", results);
        }

        request.setAttribute("page_header", "Results");

        request.getRequestDispatcher("/WEB-INF/flows/locations/results-address.jsp").forward(
                        request, response);
    }

}