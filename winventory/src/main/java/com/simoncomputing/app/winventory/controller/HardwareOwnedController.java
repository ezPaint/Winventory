package com.simoncomputing.app.winventory.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.simoncomputing.app.winventory.bo.HardwareBo;
import com.simoncomputing.app.winventory.domain.Hardware;
import com.simoncomputing.app.winventory.util.BoException;

@WebServlet("/hardware/owned")
public class HardwareOwnedController extends BaseController {
    private static final long serialVersionUID = 1L;

    private Logger log = Logger.getLogger(HardwareOwnedController.class); 
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ArrayList<Hardware> results = null;
        
        try {
            results = new ArrayList<Hardware>(HardwareBo.getInstance().getInUse());
        } catch (BoException e) {
            request.setAttribute("error", e.getMessage());
            log.error(e.getMessage());
        }

        if (results != null) {
            request.setAttribute("results", results);
        }
        
        request.setAttribute("page_header", "Hardware in Use");

        request.getRequestDispatcher("/WEB-INF/flows/hardware/results.jsp").forward(request,
                response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}