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
 * Controller for the Edit Hardware page
 */
@WebServlet("/hardware/edit")
public class HardwareEditController extends BaseController {
    
    private static final long serialVersionUID = 1L;
    
    private Logger log = Logger.getLogger(HardwareEditController.class); 

    /**
     * Runs when the "hardware/edit" page is accessed by a link or through the url
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve the key parameter from the request
        String key = request.getParameter("key");

        // Create a Hardware and attempt to retrieve the Hardware associated with the key value
        // (assuming there is a key parameter in the request)
        Hardware hardware = null;
        if (key != null) {
            try {
                // Cast the key to the correct type and retrieve the Hardware associated with it using a BO instance
                Long long_key = Long.parseLong(key);
                hardware = HardwareBo.getInstance().read(long_key);
            } catch (BoException e) {
                e.printStackTrace();
            }
        }
        
        // If the hardware is not found, log the error and report the problem to the user
        if (hardware == null) {
            request.setAttribute("error", "The key is not valid or there is no hardware with that key");
            log.error("The key is not valid or there is no hardware with that key");
        }
        
        // Create an ArrayList of all the valid Ref_Conditions, which will be used to prevent the user from entering
        // or choosing a non-valid condition
        ArrayList<RefCondition> conditions = null;
        try {
            // Use a BO to attempt to grab all valid conditions from the database
            conditions = new ArrayList<RefCondition>(RefConditionBo.getInstance().getAll());
        } catch (BoException e) {
            e.printStackTrace();
        }
        
        // Set the conditions as an attribute for the request
        if (conditions != null) {
            request.setAttribute("conditions", conditions);
        }
        
        // Set the hardware as an attribute for the request
        request.setAttribute("hardware", hardware);
        
        // Forward the request to the "hardware/edit" page
        request.getRequestDispatcher("/WEB-INF/flows/hardware/edit.jsp").forward(request, response);

    }

    /**
     * Runs when a form is submitted from the "hardware/edit" page (occurs when the changes are submitted)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve the key parameter from the request
        String key = request.getParameter("key");
        
        // Create a Hardware and attempt to retrieve the Hardware associated with the key value
        // (assuming there is a key parameter in the request)
        HardwareBo bo = HardwareBo.getInstance();
        Hardware hardware = null;
        if (key != null) {
            try {
                Long long_key = Long.parseLong(key);
                hardware = bo.read(long_key);
            } catch (BoException e) {
                e.printStackTrace();
            }
        }
        
        // Get all of the potentially updated parameters from the request and set them for the Hardware
        hardware.setType(request.getParameter("type"));
        hardware.setCondition(request.getParameter("condition"));
        hardware.setCost(Double.parseDouble(request.getParameter("cost")));
        hardware.setDescription(request.getParameter("description"));
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            hardware.setPurchaseDate(format.parse(request.getParameter("date")));
        } catch (ParseException e) {
            System.out.println(e);
        }
        hardware.setSerialNo(request.getParameter("serialNo"));
        
        // Use the BO to attempt updating the Hardware in the database
        try {
            bo.update(hardware);
        } catch (BoException e) {
            e.printStackTrace();
        }

        // Set the key in the request
        request.setAttribute("key", key);
        
        // Redirect to the "hardware/view" page for this specific hardware
        this.sendRedirect(request, response, "view?key=" + key);
        
    }

}