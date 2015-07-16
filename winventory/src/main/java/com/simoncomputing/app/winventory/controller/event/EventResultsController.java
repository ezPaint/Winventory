package com.simoncomputing.app.winventory.controller.event;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.simoncomputing.app.winventory.bo.EventBo;
import com.simoncomputing.app.winventory.bo.HardwareBo;
import com.simoncomputing.app.winventory.controller.BaseController;
import com.simoncomputing.app.winventory.domain.Event;
import com.simoncomputing.app.winventory.domain.Hardware;
import com.simoncomputing.app.winventory.util.BoException;

/**
 * Controller for the Results ("All Events") page
 */
@WebServlet("/event/results")
public class EventResultsController extends BaseController {

    private static final long serialVersionUID = 1L;

    private Logger log = Logger.getLogger(EventResultsController.class);

    /**
     * Runs when the "event/results" page is accessed by a general link or
     * through the url
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
                    throws ServletException, IOException {
        if(!userHasPermission(request, "readEvent")){
            denyPermission(request, response);
            return;
        }
        
        // Attempt to get all hardware from database using a BO
        ArrayList<Event> results = null;
        try {
        	Calendar.getInstance().setTime(new Date());
        	Calendar.getInstance().add(Calendar.HOUR, -72);
        	Date start = Calendar.getInstance().getTime();
        	
        	Calendar.getInstance().setTime(new Date());
        	Calendar.getInstance().add(Calendar.HOUR, +72);
        	Date end = Calendar.getInstance().getTime();
        	
            results = new ArrayList<Event>(EventBo.getInstance().getListByDateRange(start, end ));
        } catch (BoException e) {
            request.setAttribute("error", e.getMessage());
            log.error(e.getMessage());
        }

        request.setAttribute("events", results);
        // Forward the request to the "hardware/results" page
        request.getRequestDispatcher("/WEB-INF/flows/events/results.jsp").forward(request,
                        response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                    throws ServletException, IOException {

    }

}