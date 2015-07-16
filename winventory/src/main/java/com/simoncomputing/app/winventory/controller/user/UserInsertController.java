package com.simoncomputing.app.winventory.controller.user;

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
import com.simoncomputing.app.winventory.bo.EventBo;
import com.simoncomputing.app.winventory.bo.HardwareBo;
import com.simoncomputing.app.winventory.bo.RefConditionBo;
import com.simoncomputing.app.winventory.bo.RoleBo;
import com.simoncomputing.app.winventory.bo.UserBo;
import com.simoncomputing.app.winventory.domain.Event;
import com.simoncomputing.app.winventory.domain.EventType;
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


@WebServlet("/users/insert")
public class UserInsertController extends BaseController {
    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(UserInsertController.class);


    protected void doGet( HttpServletRequest request, HttpServletResponse response ) 
        throws ServletException, IOException {
        
        // if the user is rejected, the redirect is sent in the require permission method.
        if (!this.userHasPermission(request, "createUser")) {
            this.denyPermission(request, response);
            return;
        }
        
        
        // the preset role ids to display in dropdown
        ArrayList<Role> roles = new ArrayList<Role>();
        try {
            roles = (ArrayList<Role>) RoleBo.getInstance().getAll();
        } catch (BoException e) {
            logger.error("BoException in UserInsertController when trying to get roles");
        }
        
        if (roles != null) {
            request.setAttribute("roles", roles);
        }

        // forward to jsp
        request.getRequestDispatcher("/WEB-INF/flows/users/insert.jsp").forward(request, response);  
    }


    protected void doPost( HttpServletRequest request, HttpServletResponse response ) 
        throws ServletException, IOException {
        
        // check for the create user permission
        if (!this.userHasPermission(request, "createUser")) {
            this.denyPermission(request, response);
            return;
        }
        
        // the new user to be added
        User user = new User();
        
        // form errors
        ArrayList<String> errors = new ArrayList<String>();
        
        // bind the form values to the user
        errors = user.bind(request);
        
        // Create the new user in the db, if no errors exist
        if (errors.size() == 0) {
            // save the user, collect the errors if any
            ArrayList<String> saveErrors = user.create();
            errors.addAll(saveErrors);
            
            // if there are no save errors, then the user is saved into db
            if (saveErrors.size() == 0) {
                try {
                    user.sendEmailInvite(request);
                    Event event = EventBo.getInstance().createSystemEvent(
                            user.getUsername() + " was created.", getUserInfo(request), EventType.SYSTEM,
                            null, null, null, user);
                }
                catch (Exception e) {
                    errors.add("Must be logged in to send this email invite.");
                    logger.error("Anonymous user (no user logged in) tried to send email invite."); 
                    this.logError(logger, e);
                }
            }
        }
        
        
        // if errors, return to add user page and display errors
        if (errors.size() > 0) {
            // attach errors to the request
            request.setAttribute("errors", errors);
            
            // attach the entered values to the request
            request.setAttribute("username", request.getAttribute("username"));
            request.setAttribute("email", request.getAttribute("email"));
            request.setAttribute("isGoogle", request.getAttribute("isGoogle"));
            request.setAttribute("firstName", request.getAttribute("firstName"));
            request.setAttribute("lastName", request.getAttribute("lastName"));
            request.setAttribute("cellPhone", request.getAttribute("cellPhone"));
            request.setAttribute("workPhone", request.getAttribute("workPhone"));
            request.setAttribute("isActive", request.getAttribute("isActive"));
            
            // attach the role dropdown values to the request
            ArrayList<Role> roles = new ArrayList<Role>();
            try {
                roles = (ArrayList<Role>) RoleBo.getInstance().getAll();
            } catch (BoException e) {
                logger.error("BoException in UserInsertController when trying to get roles");
            }
            if (roles != null) {
                request.setAttribute("roles", roles);
            }
            
            // forward to jsp and return from method
            request.getRequestDispatcher("/WEB-INF/flows/users/insert.jsp").forward(request, response);
            return;
        }
        
        // if no errors, redirect to results 
        response.sendRedirect(request.getContextPath() + "/users/results?success=true");
    }

}
