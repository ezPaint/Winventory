package com.simoncomputing.app.winventory.controller.hardware;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

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
 * Controller for the Insert Hardware page
 */
@WebServlet("/hardware/insert")
public class HardwareInsertController extends BaseController {
    
    private static final long serialVersionUID = 1L;
    
    private Logger log = Logger.getLogger(HardwareInsertController.class); 

    /**
     * Runs when the "hardware/insert" page is accessed by a link or through the url
     */
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) 
        throws ServletException, IOException {
        
        // Create an ArrayList of all the valid Ref_Conditions, which will be used to prevent the user from entering
        // or choosing a non-valid condition
        ArrayList<RefCondition> conditions = null;
        try {
            // Use a BO to attempt to grab all valid conditions from the database
            conditions = new ArrayList<RefCondition>(RefConditionBo.getInstance().getAll());
        } catch (BoException e) {
            String error = logError(log, e);
            request.setAttribute("error", "Error code: " + error);
        }
        
        // Set the conditions as an attribute for the request
        if (conditions != null) {
            request.setAttribute("conditions", conditions);
        }
        
        // Forward the request to the "hardware/insert" page
        request.getRequestDispatcher("/WEB-INF/flows/hardware/insert.jsp").forward(request, response);  
    }

    /**
     * Runs when a form is submitted from the "hardware/insert" page (occurs when a Hardware is inserted)
     */
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) 
        throws ServletException, IOException {

        // Create a new Hardware and set its parameters based on the values from the request
        // userId and locationId are set to null in order to allow associations to be made later
        Hardware h = new Hardware();
        h.setType(request.getParameter("type"));
        h.setCondition(request.getParameter("condition"));
        h.setCost(Double.parseDouble(request.getParameter("cost")));
        h.setDescription(request.getParameter("description"));
        h.setLocationId(null);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            h.setPurchaseDate(format.parse(request.getParameter("date")));
        } catch (ParseException e) {
            String error = logError(log, e);
            request.setAttribute("error", "Error code: " + error);
        }
        h.setSerialNo(request.getParameter("serialNo"));
        h.setUserId(null);
        
        // Use a BO to attempt to create this hardware item and place it in the database
        HardwareBo bo = HardwareBo.getInstance();
        try {
            bo.create(h);
        } catch (BoException e) { 
            String error = logError(log, e);
            request.setAttribute("error", "Error code: " + error);
        }
        
        // Create an ArrayList of all the valid Ref_Conditions, which will be used to prevent the user from entering
        // or choosing a non-valid condition
        ArrayList<RefCondition> conditions = null;
        try {
            // Use a BO to attempt to grab all valid conditions from the database
            conditions = new ArrayList<RefCondition>(RefConditionBo.getInstance().getAll());
        } catch (BoException e) {
            String error = logError(log, e);
            request.setAttribute("error", "Error code: " + error);
        }
        
        // Reset the conditions as an attribute for the request
        if (conditions != null) {
            request.setAttribute("conditions", conditions);
        }
        
        // Forward the request to the "hardware/insert" page to allow the user to enter more items
        request.getRequestDispatcher("/WEB-INF/flows/hardware/insert.jsp").forward(request, response);
    }

}