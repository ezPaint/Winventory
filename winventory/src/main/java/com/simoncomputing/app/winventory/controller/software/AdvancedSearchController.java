package com.simoncomputing.app.winventory.controller.software;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
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

        forward(request, response, "/WEB-INF/flows/software/advanced-search.jsp");
    }
    /**
     * Retrieves searches, removes blank fields, populate arraylists for database search
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String error = null;

        // get search terms and fields from form
        ArrayList<String> names = new ArrayList<String>(Arrays.asList(request
                .getParameterValues("name")));
        ArrayList<String> serials = new ArrayList<String>(Arrays.asList(request
                .getParameterValues("serialNo")));
        ArrayList<String> versions = new ArrayList<String>(Arrays.asList(request
                .getParameterValues("version")));
        
        
//        ArrayList<String> costs = new ArrayList<String>(Arrays.asList(request
//                .getParameterValues("cost")));
        String minCost = request.getParameter("minCost");
        String maxCost = request.getParameter("maxCost");
        ArrayList<String> costs = new ArrayList<String>();
        if (minCost != null)
        costs.add(minCost);
        if (maxCost != null)
        costs.add(maxCost);
        
        ArrayList<String> keys = new ArrayList<String>(Arrays.asList(request
                .getParameterValues("licenseKey")));
       
        // remove blank fields
        cleanFields(names);
        cleanFields(serials);
        cleanFields(versions);
        cleanFields(costs);
        cleanFields(keys);

        // passed as parameters to the dao
        // columns contains the list of columns in the database to search
        // searches contains the actual search terms to match to when selecting records

        ArrayList<String> columns = new ArrayList<String>();
        ArrayList<ArrayList<String>> searches = new ArrayList<ArrayList<String>>();

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

        if ( costs.size() == 1) {
        	costs.add(costs.get(0));
        } 

        if (keys != null && keys.size() > 0) {
            columns.add("license_Key");
            searches.add(keys);
        }

        // get date search terms
        ArrayList<String> dateInfo = getDateInfo(request);

        // do sql stuff
        ArrayList<Software> results = null;
        ArrayList<Software> resultsBase = null;
        ArrayList<Software> resultsInDateRange = null;

        // check that at least one search term was given
        if (names.size() == 0 && serials.size() == 0 && versions.size() == 0 && costs.size() == 0
                && keys.size() == 0 && dateInfo.size() == 0) {
            error = "Nothing was entered.";
        } else {

            try {
                if (columns.size() == 0 || searches.size() == 0) {
                    // if only date search terms were provided, search all
                    // software by date
                    results = new ArrayList<Software>(SoftwareBo.getInstance().getAll());
                    resultsBase = new ArrayList<Software>(SoftwareBo.getInstance().getAll()); //TODO: could just copy the arraylist over; which is faster?
                } else {
                    results = new ArrayList<Software>(SoftwareBo.getInstance().searchAdvanced(
                            columns, searches));
                    resultsBase = new ArrayList<Software>(SoftwareBo.getInstance().searchAdvanced(
                            columns, searches));
                }
                // From previous results (either getAll or searchAdvanced, narrow down searches to cost range
                if(costs.size() > 0) {
                	results.clear();
                	results = new ArrayList<Software>(SoftwareBo.getInstance().searchCostRange(resultsBase, costs.get(0), costs.get(1)));
                }
                // If date range was entered
                if (dateInfo.size() > 0) {
                    resultsInDateRange = new ArrayList<Software>(SoftwareBo.getInstance()
                            .searchDateRange(results, dateInfo));
                }
            } catch (BoException e) {
                error = e.getLocalizedMessage();
                logError(log, e);
            }

        }
        
        if (resultsInDateRange != null) {
        	request.setAttribute("results", resultsInDateRange);
        } else if (results != null) {
            request.setAttribute("results", results);
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
     * @return
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
        String[] purchasedRange = purchasedDates.get(0).split(" - ");
        if (purchasedRange.length == 2) {
            startPDate = purchasedRange[0];
            endPDate = purchasedRange[1];
        } else if (purchasedRange.length == 1) {
            startPDate = purchasedRange[0];
            endPDate = purchasedRange[0];
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
