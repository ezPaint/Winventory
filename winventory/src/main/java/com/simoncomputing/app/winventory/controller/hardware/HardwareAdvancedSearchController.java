package com.simoncomputing.app.winventory.controller.hardware;

import java.io.IOException;
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

@WebServlet("/hardware/advanced-search")
public class HardwareAdvancedSearchController extends BaseController {
    private static final long serialVersionUID = 1L;

    private Logger log = Logger.getLogger(HardwareAdvancedSearchController.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ArrayList<RefCondition> conditions = null;
        try {
            conditions = new ArrayList<RefCondition>(RefConditionBo.getInstance().getAll());
        } catch (BoException e) {
            request.setAttribute("error", e.getMessage());
            log.error(e.getMessage());
        }

        if (conditions != null) {
            request.setAttribute("conditions", conditions);
        }

        request.getRequestDispatcher("/WEB-INF/flows/hardware/advanced-search.jsp").forward(
                request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String error = null;

        // get fields
        ArrayList<String> types = new ArrayList<String>(Arrays.asList(request
                .getParameterValues("type")));
        ArrayList<String> descriptions = new ArrayList<String>(Arrays.asList(request
                .getParameterValues("description")));
        ArrayList<String> costs = new ArrayList<String>(Arrays.asList(request
                .getParameterValues("cost")));
        ArrayList<String> dates = new ArrayList<String>(Arrays.asList(request
                .getParameterValues("date")));
        ArrayList<String> conditions = new ArrayList<String>(Arrays.asList(request
                .getParameterValues("condition")));
        ArrayList<String> serials = new ArrayList<String>(Arrays.asList(request
                .getParameterValues("serial")));

        // remove blank fields
        cleanFields(types);
        cleanFields(descriptions);
        cleanFields(costs);
        cleanFields(dates);
        cleanFields(conditions);
        cleanFields(serials);

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

        if (costs != null && costs.size() > 0) {
            columns.add("cost");
            searches.add(costs);
        }

        if (dates != null && dates.size() > 0) {
            columns.add("purchase_date");
            searches.add(dates);
        }

        if (conditions != null && conditions.size() > 0) {
            columns.add("condition");
            searches.add(conditions);
        }

        if (serials != null && serials.size() > 0) {
            columns.add("serial_no");
            searches.add(serials);
        }

        // do sql stuff
        ArrayList<Hardware> results = null;
        if (types.size() == 0 && descriptions.size() == 0 && costs.size() == 0 && dates.size() == 0
                && conditions.size() == 0 && serials.size() == 0) {
            error = "Nothing was entered.";
        } else {

            try {
                results = new ArrayList<Hardware>(HardwareBo.getInstance().searchAdvanced(columns,
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

        if (error != null) {
            request.getRequestDispatcher("/WEB-INF/flows/hardware/advanced-search.jsp").forward(request,
                    response);
        } else {
            request.getRequestDispatcher("/WEB-INF/flows/hardware/results.jsp").forward(request,
                    response);
        }
    }

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
