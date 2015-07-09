package com.simoncomputing.app.winventory.controller.software;

import java.io.IOException;
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

        request.getRequestDispatcher("/WEB-INF/flows/software/advanced-search.jsp").forward(request,
                response);
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
/*        ArrayList<String> startPDate = new ArrayList<String>(Arrays.asList(request
                .getParameterValues("purchasedDateStart")));
        ArrayList<String> endPDate = new ArrayList<String>(Arrays.asList(request
                .getParameterValues("purchasedDateEnd")));
        ArrayList<String> startExDate = new ArrayList<String>(Arrays.asList(request
                .getParameterValues("expirationDateStart")));
        ArrayList<String> endExDate = new ArrayList<String>(Arrays.asList(request
                .getParameterValues("expirationDateEnd")));*/

        // remove blank fields
        cleanFields(names);
        cleanFields(serials);
        cleanFields(versions);
        cleanFields(costs);
        cleanFields(keys);
       /* cleanFields(startPDate);
        cleanFields(endPDate);
        cleanFields(startExDate);
        cleanFields(endExDate);*/

        // passed as parameters to the dao 
        // columns contains the list of columns in the database to search
        // searchs contains the actual search terms to match to when selecting records
        ArrayList<String> columns = new ArrayList<String>();
        ArrayList<ArrayList<String>> searches = new ArrayList<ArrayList<String>>();

        // if the user specified search terms for "name", add "name" to columns
        // and all of the search terms to searches, otherwise ignore the arraylist
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

      /*  if (startPDate != null && startPDate.size() > 0) {
            columns.add("purchased_date");
            searches.add(startPDate);
        }

        if (endPDate != null && endPDate.size() > 0) {
            columns.add("purchased_date");
            searches.add(endPDate);
        }

        if (startExDate != null && startExDate.size() > 0) {
            columns.add("expiration_date");
            searches.add(startExDate);
        }

        if (endExDate != null && endExDate.size() > 0) {
            columns.add("expiration_date");
            searches.add(endExDate);
        }*/

        // do sql stuff
        ArrayList<Software> results = null;
        
        //check that at least one search term was given
        if (names.size() == 0 && serials.size() == 0 && versions.size() == 0 && costs.size() == 0
                && keys.size() == 0 /*&& startPDate.size() == 0 && endPDate.size() == 0
                && startExDate.size() == 0 && endExDate.size() == 0*/) {
            error = "Nothing was entered.";
        } else {

            try {
                results = new ArrayList<Software>(SoftwareBo.getInstance().searchAdvanced(columns,
                        searches));
            } catch (BoException e) {
                error = e.getLocalizedMessage();
                log.error(e.getMessage());
            }

        }

        if (results != null) {
            request.setAttribute("results", results);
        }

        request.setAttribute("page_header", "Search Results");
        request.setAttribute("error", error);

        // only redirect to results page if no error occurred 
        if (error != null) {
            request.getRequestDispatcher("/WEB-INF/flows/software/advanced-search.jsp").forward(request,
                    response);
        } else {
            request.getRequestDispatcher("/WEB-INF/flows/software/results.jsp").forward(request,
                    response);
        }

    }

    /** 
     * Helper method to remove blank fields from form results.
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
