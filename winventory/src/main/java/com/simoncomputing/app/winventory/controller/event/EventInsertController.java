package com.simoncomputing.app.winventory.controller.event;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.mail.EmailException;
import org.apache.log4j.Logger;

import com.simoncomputing.app.winventory.authentication.EmailService;
import com.simoncomputing.app.winventory.bo.HardwareBo;
import com.simoncomputing.app.winventory.bo.RefConditionBo;
import com.simoncomputing.app.winventory.bo.RoleBo;
import com.simoncomputing.app.winventory.bo.UserBo;
import com.simoncomputing.app.winventory.domain.Event;
import com.simoncomputing.app.winventory.domain.Hardware;
import com.simoncomputing.app.winventory.domain.RefCondition;
import com.simoncomputing.app.winventory.domain.Role;
import com.simoncomputing.app.winventory.domain.User;
import com.simoncomputing.app.winventory.util.BoException;
import com.simoncomputing.app.winventory.controller.BaseController;

/**
 * Servlet to handle the add user functionality, or insert user page.
 * 
 */


@WebServlet("/event/insert")
public class EventInsertController extends BaseController {
    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(EventInsertController.class);


    protected void doGet( HttpServletRequest request, HttpServletResponse response ) 
        throws ServletException, IOException {
        
        // the preset role ids to display in dropdown

        // forward to jsp
        request.getRequestDispatcher("/WEB-INF/flows/events/insert.jsp").forward(request, response);  
    }


    protected void doPost( HttpServletRequest request, HttpServletResponse response ) 
        throws ServletException, IOException {
        
        
        // the new user to be added
        Event event = new Event();
        
        // form errors
        ArrayList<String> errors = new ArrayList<String>();
        
        // bind the form values to the user
        errors = event.bindAndStore(request);
        
        // Create the new user in the db, if no errors exist
        
        
        // if errors, return to add user page and display errors
        if (errors.size() > 0) {
            // attach errors to the request
            request.setAttribute("errors", errors);
            
            // attach the entered values to the request
            request.setAttribute("description", request.getAttribute("description"));
            request.setAttribute("hardware", request.getAttribute("hardware"));
            request.setAttribute("software", request.getAttribute("software"));
            
            // forward to jsp and return from method
            request.getRequestDispatcher("/WEB-INF/flows/events/insert.jsp").forward(request, response);
            return;
        }
        
        // if no errors, redirect to results 
        response.sendRedirect(request.getContextPath() + "/event/view?key=" + event.getKey());
    }

}
