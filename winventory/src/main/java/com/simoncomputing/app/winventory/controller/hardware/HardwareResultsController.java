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

@WebServlet("/hardware/results")
public class HardwareResultsController extends BaseController {
    private static final long serialVersionUID = 1L;

    private Logger log = Logger.getLogger(HardwareResultsController.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ArrayList<Hardware> results = null;

        // gets all harwdware and forwards 
        try {
            results = new ArrayList<Hardware>(HardwareBo.getInstance().getAll());
        } catch (BoException e) {
            request.setAttribute("error", e.getMessage());
            log.error(e.getMessage());
        }

        if (results != null) {
            request.setAttribute("results", results);
        }

        request.setAttribute("page_header", "All Hardware");

        request.getRequestDispatcher("/WEB-INF/flows/hardware/results.jsp").forward(request,
                response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // not currently used, for future use any page could forward an arraylist
        // of hardware to this page and it will be displayed in table format
        @SuppressWarnings("unchecked")
        ArrayList<Hardware> results = (ArrayList<Hardware>) request.getAttribute("results");

        if (results != null) {
            request.setAttribute("results", results);
        }

        request.setAttribute("page_header", "Results");

        request.getRequestDispatcher("/WEB-INF/flows/hardware/results.jsp").forward(request,
                response);
    }

}