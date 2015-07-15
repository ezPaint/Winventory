package com.simoncomputing.app.winventory.controller.hardware;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ListIterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.simoncomputing.app.winventory.bo.HardwareBo;
import com.simoncomputing.app.winventory.bo.RefConditionBo;
import com.simoncomputing.app.winventory.controller.BaseController;
import com.simoncomputing.app.winventory.domain.Hardware;
import com.simoncomputing.app.winventory.domain.RefCondition;
import com.simoncomputing.app.winventory.util.BoException;

/**
 * Controller for the Advanced Search page
 */
@WebServlet("/hardware/advanced-search")
public class HardwareAdvancedSearchController extends BaseController {

    private static final long serialVersionUID = 1L;

    private Logger log = Logger.getLogger(HardwareAdvancedSearchController.class);

    /**
     * Runs when the "hardware/advanced-search" page is accessed by a link or
     * through the url
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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

        // Forward the request to the "hardware/advanced-search" page
        request.getRequestDispatcher("/WEB-INF/flows/hardware/advanced-search.jsp").forward(
                request, response);

    }

    /**
     * Runs when the Search buttton on the "hardware/advanced-search" page is
     * clicked
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String error = null;

        // Get all of the fields from the request (ArrayLists for everything
        // except the search option)
        ArrayList<String> types = new ArrayList<String>(Arrays.asList(request
                .getParameterValues("type")));
        ArrayList<String> descriptions = new ArrayList<String>(Arrays.asList(request
                .getParameterValues("description")));
        ArrayList<String> conditions = new ArrayList<String>(Arrays.asList(request
                .getParameterValues("condition")));
        ArrayList<String> serials = new ArrayList<String>(Arrays.asList(request
                .getParameterValues("serial")));

        String minCostString = request.getParameter("minCost");
        String maxCostString = request.getParameter("maxCost");

        double minCost = 0;
        double maxCost = Double.MAX_VALUE;

        boolean cost = false;
        if (minCostString != null && !minCostString.equals("")) {
            cost = true;

            try {
                minCost = Double.parseDouble(minCostString);
            } catch (Exception e) {
                error = logError(log, e);
                request.setAttribute("error", "Error code: " + error);
            }

        }

        if (maxCostString != null && !maxCostString.equals("")) {
            cost = true;

            try {
                maxCost = Double.parseDouble(maxCostString);
            } catch (Exception e) {
                error = logError(log, e);
                request.setAttribute("error", "Error code: " + error);
            }
        }

        String searchOption = request.getParameter("optionsSearch");

        // Check whether the User wants to search stored, owned, or all, and set
        // appropriate booleans
        boolean owned = false;
        boolean stored = false;
        if (searchOption.equals("stored")) {
            stored = true;
        }
        if (searchOption.equals("owned")) {
            owned = true;
        }

        boolean date = false;
        String dateString = request.getParameter("date");
        Date startDate = null;
        Date endDate = null;
        if (dateString != null && !dateString.equals("")) {
            date = true;

            String[] dates = dateString.split("to");
            try {
                startDate = Date.valueOf(dates[0].trim());
                endDate = Date.valueOf(dates[1].trim());
            } catch (Exception e) {
                error = logError(log, e);
                request.setAttribute("error", "Error code: " + error);
            }
        }

        // Remove blank fields from all of the ArrayLists
        cleanFields(types);
        cleanFields(descriptions);
        cleanFields(conditions);
        cleanFields(serials);

        // If a field isn't empty, add its type to the "columns" ArrayList and
        // the relevant list to the "searches" ArrayList
        ArrayList<String> columns = new ArrayList<String>();
        ArrayList<ArrayList<String>> searches = new ArrayList<ArrayList<String>>();
        if (types != null && types.size() > 0) {
            columns.add("type");
            searches.add(types);
        }
        if (descriptions != null && descriptions.size() > 0) {
            columns.add("description");
            searches.add(descriptions);
        }

        if (conditions != null && conditions.size() > 0) {
            columns.add("condition");
            searches.add(conditions);
        }
        if (serials != null && serials.size() > 0) {
            columns.add("serial_no");
            searches.add(serials);
        }

        // Attempt to get all relevant Hardware items from the advanced search
        // using a BO, so long as all of the fields are not empty
        ArrayList<Hardware> results = null;
        if (types.size() == 0 && descriptions.size() == 0 && !cost && !date
                && conditions.size() == 0 && serials.size() == 0) {
            error = "All fields were empty";
        } else {
            try {
                results = new ArrayList<Hardware>(HardwareBo.getInstance().searchAdvanced(columns,
                        searches, stored, owned, cost, minCost, maxCost, date, startDate, endDate));
            } catch (BoException e) {
                error = logError(log, e);
                request.setAttribute("error", "Error code: " + error);
            }

        }

        // Set the attributes for the request
        if (results != null) {
            request.setAttribute("results", results);
        }
        request.setAttribute("page_header", "Search Results");
        request.setAttribute("error", error);

        // If there was an error, stay on the advanced search page
        if (error != null) {
            request.getRequestDispatcher("/WEB-INF/flows/hardware/advanced-search.jsp").forward(
                    request, response);
            // Forward the request to the "hardware/results" page
        } else {
            request.getRequestDispatcher("/WEB-INF/flows/hardware/results.jsp").forward(request,
                    response);
        }
    }

    // Helper method which removes fields that are empty
    private void cleanFields(ArrayList<String> fields) {
        for (ListIterator<String> iterator = fields.listIterator(); iterator.hasNext();) {
            String field = iterator.next();
            if (field.equals("")) {
                iterator.remove();
            } else {
                iterator.set(field.toUpperCase());
            }
        }
    }
}
