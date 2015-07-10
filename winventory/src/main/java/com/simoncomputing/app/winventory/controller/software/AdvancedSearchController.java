package com.simoncomputing.app.winventory.controller.software;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
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

        
        String startPDate = request.getParameter("purchasedDateStart");
        String endPDate = request.getParameter("purchasedDateEnd");
        String startExDate = request.getParameter("expirationDateStart");
        String endExDate = request.getParameter("expirationDateEnd");
        
        ArrayList<String> labels = new ArrayList<String>();
        ArrayList<String> dates = new ArrayList<String>();

        if (startPDate != null && endPDate != null) {
            labels.add("purchased_date");
            dates.add(startPDate);
            dates.add(endPDate);
        } else if (endPDate == null) {
            labels.add("purchased_date");
            dates.add(startPDate);
            dates.add(startPDate);
        } else if (startPDate == null) {
            labels.add("purchased_date");
            dates.add("0000-00-00");
            dates.add(endPDate);
        }
        else{
            labels.add("purchased_date");
            dates.add(null);
            dates.add(null);
        }
        

        if (startExDate != null && endExDate != null) {
            labels.add("expiration_date");
            dates.add(startExDate);
            dates.add(endExDate);
        } else if (endExDate == null) {
            labels.add("purchased_date");
            dates.add(startExDate);
            dates.add(startExDate);
        } else if (startExDate == null) {
            labels.add("purchased_date");
            dates.add("0000-00-00");
            dates.add(endExDate);
        }
        else{
            labels.add("expired_date");
            dates.add(null);
            dates.add(null);
        }

        // do sql stuff
        ArrayList<Software> results = null;
        ArrayList<Software> resultsInRange = null;

        // check that at least one search term was given
        if (names.size() == 0 && serials.size() == 0 && versions.size() == 0 && costs.size() == 0
                && keys.size() == 0 && startPDate == null && endPDate == null
                && startExDate == null && endExDate == null) {
            error = "Nothing was entered.";
        } else {

            try {
//
                
                if (columns.size() == 0 || searches.size() == 0) {
                    results = new ArrayList<Software>(SoftwareBo.getInstance().getAll());
                } else {
                    results = new ArrayList<Software>(SoftwareBo.getInstance().searchAdvanced(
                            columns, searches));
                }

                if (!(startPDate == null && endPDate == null && startExDate == null && endExDate == null)) {
                    resultsInRange = new ArrayList<Software>(SoftwareBo.getInstance().searchRange(
                            results, dates));
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
}
