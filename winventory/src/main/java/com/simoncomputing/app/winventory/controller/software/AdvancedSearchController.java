package com.simoncomputing.app.winventory.controller.software;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ListIterator;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.simoncomputing.app.winventory.bo.SoftwareBo;
import com.simoncomputing.app.winventory.controller.BaseController;
import com.simoncomputing.app.winventory.domain.Software;
import com.simoncomputing.app.winventory.util.BoException;
import org.apache.log4j.Logger;

/**
 * Controller for Advanced Search functionality
 * 
 * @author jessica.ya
 *
 */
@WebServlet("/software/advancedsearch")
public class AdvancedSearchController extends BaseController {
    private static final long serialVersionUID = 1L;
    private static Logger log = Logger.getLogger(AdvancedSearchController.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        if (userHasPermission(request, "readSoftware")) {
            forward(request, response, "/WEB-INF/flows/software/advanced-search.jsp");
        } else {
            denyPermission(request, response);
            return;
        }
    }
    /**
     * Retrieves searches, removes blank fields, populate arraylists for
     * database search
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String error = null;

        // Get search terms and fields from form
        ArrayList<String> names = new ArrayList<String>(Arrays.asList(request
                .getParameterValues("name")));
        ArrayList<String> serials = new ArrayList<String>(Arrays.asList(request
                .getParameterValues("serialNo")));
        ArrayList<String> versions = new ArrayList<String>(Arrays.asList(request
                .getParameterValues("version")));

        // Add cost values to an arraylist 'costs'
        String minCost = request.getParameter("minCost");
        String maxCost = request.getParameter("maxCost");
        ArrayList<String> costs = new ArrayList<String>(); // holds min and max
                                                           // cost
        if (minCost != null && !minCost.equals(""))
            costs.add(minCost);
        if (maxCost != null && !maxCost.equals(""))
            costs.add(maxCost);

        ArrayList<String> keys = new ArrayList<String>(Arrays.asList(request
                .getParameterValues("licenseKey")));

        // Remove blank fields
        cleanFields(names);
        cleanFields(serials);
        cleanFields(versions);
        cleanFields(costs);
        cleanFields(keys);

        // Holds search results for RANGES (purchased date, expiration date,
        // cost)
        SoftwareBo bo = SoftwareBo.getInstance();
        ArrayList<Software> results = new ArrayList<Software>();
        try {
            results = (ArrayList<Software>) bo.getAll(); // Captures ALL
                                                         // software results
        } catch (BoException e) {
            logError(log, e);
        }
        // Narrow down by DATES
        // ---------------------------------------------------------------------
        String[] purchasedDate = (String[]) request.getParameter("purchasedDate").split("to");
        String[] expirationDate = (String[]) request.getParameter("expirationDate").split("to");
        ArrayList<String> dates = new ArrayList<String>();
        if (purchasedDate.length == 2) {
            dates.add(purchasedDate[0].trim()); // purchase date start
            dates.add(purchasedDate[1].trim()); // purchase date end
        } else {
            dates.add("");
            dates.add("");
        }
        if (expirationDate.length == 2) {
            dates.add(expirationDate[0].trim()); // purchase date start
            dates.add(expirationDate[1].trim()); // purchase date end
        } else {
            dates.add("");
            dates.add("");
        }
        try {
            // Narrow down list by DATES
            results = (ArrayList<Software>) bo.searchDateRange(results, dates);
        } catch (BoException e) {
            logError(log, e);
        }

        // Narrow down list by Cost
        // -----------------------------------------------------------------
        try {
            if (costs.size() > 1){
                // Reduce list by cost
                results = (ArrayList<Software>) bo.searchCostRange(results, costs.get(0), costs.get(1)); 
            }
        } catch (BoException e) {
            logError(log, e);
        }

        // Narrow down list by Name, Serial No, Version, License Key
        // --------------------------------

        // passed as parameters to the dao
        ArrayList<String> columns = new ArrayList<String>(); // corresponds to
                                                             // database columns
        ArrayList<ArrayList<String>> searches = new ArrayList<ArrayList<String>>(); // search
                                                                                    // terms
                                                                                    // from
                                                                                    // user

        // if the user specified search terms for "name", add "name" to columns
        // and all of the search terms to searches, otherwise ignore the
        // arraylist
        if (names != null && names.size() > 0) {
            columns.add("name");
            searches.add(names);
        }

        if (serials != null && serials.size() > 0) {
            columns.add("serial_No");
            searches.add(serials);
        }

        if (versions != null && versions.size() > 0) {
            columns.add("version");
            searches.add(versions);
        }

        if (keys != null && keys.size() > 0) {
            columns.add("license_Key");
            searches.add(keys);
        }

        // Get array of just the user's chosen software name, serial no,
        // version, license key
        ArrayList<Software> temp = new ArrayList<Software>();
        try {
            temp = (ArrayList<Software>) bo.searchAdvanced(columns, searches);

        } catch (BoException e) {
            error = e.getLocalizedMessage();
            logError(log, e);
        }

        // Combine date/cost (ranges) array with
        // name/serialno/version/licensekey array
        ArrayList<Software> searchResults = new ArrayList<Software>();
        for (Software a : results) {
            for (Software b : temp) {
                if (a.equals(b)) {
                    searchResults.add(a);
                    break;
                }
            }
        }

        if (results != null) {
            request.setAttribute("results", searchResults);
        }

        request.setAttribute("page_header", "Search Results");
        request.setAttribute("error", error);

        // only redirect to results page if no error occurred
        if (error != null) {
            forward(request, response, "/WEB-INF/flows/software/advanced-search.jsp");
        } else {
            forward(request, response, "/WEB-INF/flows/software/results.jsp");
        }

    }
    /**
     * Helper method to remove blank fields from form results.
     * 
     * @param fields
     */
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

    /**
     * Helper method that gets the appropriate information for a search by date
     * range
     * 
     * @param request
     * @return ArrayList of date range strings for search by date range
     */
    private ArrayList<String> getDateInfo(HttpServletRequest request) {
        // will contain the date search terms
        ArrayList<String> dates = new ArrayList<String>();
        String startPDate = "";
        String endPDate = "";
        String startExDate = "";
        String endExDate = "";

        // get search terms from form
        ArrayList<String> purchasedDates = new ArrayList<String>(Arrays.asList(request
                .getParameterValues("purchasedDate")));
        // split start and end dates
        String[] purchasedRange = purchasedDates.get(0).split("to");
        if (purchasedRange.length == 2) {
            startPDate = purchasedRange[0].trim();
            endPDate = purchasedRange[1].trim();
        } else if (purchasedRange.length == 1) {
            startPDate = purchasedRange[0].trim();
            endPDate = purchasedRange[0].trim();
        }

        ArrayList<String> expirationDates = new ArrayList<String>(Arrays.asList(request
                .getParameterValues("expirationDate")));
        // split start and end dates
        String[] expirationRange = expirationDates.get(0).split(" - ");
        if (expirationRange.length == 2) {
            startExDate = expirationRange[0];
            endExDate = expirationRange[1];
        } else if (expirationRange.length == 1) {
            startExDate = expirationRange[0];
            endExDate = expirationRange[0];
        }

        // determine if there are any date search terms,
        // if not return empty list
        if (startPDate.equals("") && endPDate.equals("") && startExDate.equals("")
                && endExDate.equals(""))
            return dates;

        // initially populated with empty Strings since each date field from the
        // form is linked to a specific index in the ArrayList, facilitates
        // logic/parsing done in SoftwareBo searchDateRange method
        dates.add("");
        dates.add("");
        dates.add("");
        dates.add("");

        // set date ranges
        if (!startPDate.equals("") && !endPDate.equals("")) {
            dates.set(0, startPDate);
            dates.add(1, endPDate);
        } else if (endPDate.equals("") && !startPDate.equals("")) {
            // if only one date is provided, search range
            // is just that date
            dates.add(0, startPDate);
            dates.add(1, startPDate);

        } else if (startPDate.equals("") && !endPDate.equals("")) {
            dates.add(0, endPDate);
            dates.add(1, endPDate);
        }

        if (!startExDate.equals("") && !endExDate.equals("")) {
            dates.add(2, startExDate);
            dates.add(3, endExDate);
        } else if (endExDate.equals("") && !startExDate.equals("")) {
            dates.add(2, startExDate);
            dates.add(3, startExDate);
        } else if (startExDate.equals("") && !endExDate.equals("")) {
            dates.add(2, endExDate);
            dates.add(3, endExDate);
        }

        return dates;
    }
}
