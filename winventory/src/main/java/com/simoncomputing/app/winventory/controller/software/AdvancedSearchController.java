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
public class AdvancedSearchController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static Logger log = Logger.getLogger(AdvancedSearchController.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/flows/software/advanced-search.jsp").forward(
                request, response);
    }

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
        ArrayList<String> costs = new ArrayList<String>(Arrays.asList(request
                .getParameterValues("cost")));
        ArrayList<String> keys = new ArrayList<String>(Arrays.asList(request
                .getParameterValues("licenseKey")));
        // ----------------------------------------------------------------------------------------
        //Grab purchased date range from Date Range Picker (in format YYYY-MM-DD - YYYY-MM-DD)
        ArrayList<String> purchasedDates = new ArrayList<String>(Arrays.asList(request
            .getParameterValues("purchasedDate")));
        String[] purchasedRange = purchasedDates.get(0).split(" - "); //split start and end dates
        String purchasedStart = purchasedRange[0]; //Starting date (in form YYYY-MM-DD)
        String purchasedEnd = purchasedRange[1];   //Ending date (in form YYYY-MM-DD)
        //Get all software objects between purchase range from database
        List<Software> softwares = null;
        try {
            softwares = SoftwareBo.getInstance().getListByPurchaseRange(Date.valueOf(purchasedStart), Date.valueOf(purchasedEnd));
        } catch (BoException e) {
            log.error(e.getMessage(), e);
        }
        
        //Grab expiration date range from Date Range Picker (in format YYYY-MM-DD - YYYY-MM-DD)
        ArrayList<String> expirationDates = new ArrayList<String>(Arrays.asList(request
            .getParameterValues("expirationDate")));
        String[] expirationRange = expirationDates.get(0).split(" - "); //split start and end dates
        String expirationStart = expirationRange[0];   //Starting date (in form YYYY-MM-DD)
        String expirationEnd = expirationRange[1];     //Ending date (in form YYYY-MM-DD)
      //Get all software objects between expiration range from database
        List<Software> softwares2 = null;
        try {
            softwares2 = SoftwareBo.getInstance().getListByExpirationRange(Date.valueOf(expirationStart), Date.valueOf(expirationEnd));
        } catch (BoException e) {
            log.error(e.getMessage(), e);
        }
        // ----------------------------------------------------------------------------------------
        
        // remove blank fields
        cleanFields(names);
        cleanFields(serials);
        cleanFields(versions);
        cleanFields(costs);
        cleanFields(keys);

        // passed as parameters to the dao
        // columns contains the list of columns in the database to search
        // searchs contains the actual search terms to match to when selecting
        // records
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

        if (costs != null && costs.size() > 0) {
            columns.add("cost");
            searches.add(costs);
        }

        if (keys != null && keys.size() > 0) {
            columns.add("license_Key");
            searches.add(names);
        }

        // get date search terms
        ArrayList<String> dateInfo = getDateInfo(request);

        // do sql stuff
        ArrayList<Software> results = null;
        ArrayList<Software> resultsInRange = null;

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
                } else {
                    results = new ArrayList<Software>(SoftwareBo.getInstance().searchAdvanced(
                            columns, searches));
                }

                if (dateInfo.size() > 0) {
                    resultsInRange = new ArrayList<Software>(SoftwareBo.getInstance()
                            .searchDateRange(results, dateInfo));
                }
            } catch (BoException e) {
                error = e.getLocalizedMessage();
                log.error(e.getMessage());
            }

        }

        if (resultsInRange != null) {
            request.setAttribute("results", resultsInRange);
        } else if (results != null) {
            request.setAttribute("results", results);
        }

        request.setAttribute("page_header", "Search Results");
        request.setAttribute("error", error);

        // only redirect to results page if no error occurred
        if (error != null) {
            request.getRequestDispatcher("/WEB-INF/flows/software/advanced-search.jsp").forward(
                    request, response);
        } else {
            request.getRequestDispatcher("/WEB-INF/flows/software/results.jsp").forward(request,
                    response);
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

        String startPDate = request.getParameter("purchasedDateStart");
        String endPDate = request.getParameter("purchasedDateEnd");
        String startExDate = request.getParameter("expirationDateStart");
        String endExDate = request.getParameter("expirationDateEnd");

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
