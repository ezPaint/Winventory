package com.simoncomputing.app.winventory.controller.user;

import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.simoncomputing.app.winventory.bo.HardwareBo;
import com.simoncomputing.app.winventory.bo.UserBo;
import com.simoncomputing.app.winventory.domain.Hardware;
import com.simoncomputing.app.winventory.domain.User;
import com.simoncomputing.app.winventory.util.BoException;
import com.simoncomputing.app.winventory.controller.BaseController;

@WebServlet("/users/view")
public class UserViewController extends BaseController {
    private static final long serialVersionUID = 1L;
    
    private Logger log = Logger.getLogger(UserViewController.class); 

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // require that the current user have readUser permission
        if (requirePermission(request, response, "readUser")) {
            return;
        }
        
        // the requested user key
        String key = request.getParameter("key");

        User user = null;
        
        // if the key parameter exists, find the user with that key
        if (key != null) {
            try {
                Long long_key = Long.parseLong(key);
                user = UserBo.getInstance().read(long_key);
            } catch (BoException e) {
                request.setAttribute("error", "Unable to get user.");
                log.error("BoException in UserViewController for user with key = " + key);
            }
        }
        
        // if there is no user with that key
        if (user == null) {
            request.setAttribute("error", "The key is not valid or there is no user with that key");
            log.error("The key is not valid or there is no hardware with that key");
        } 
        
        // if the user is valid, set it as request attribute
        else {
            request.setAttribute("user", user);
        }
        
        // get the hardware the user owns
        ArrayList<Hardware> results = null;
        
        // try to get the hardware list
        try {
            results = new ArrayList<Hardware>(HardwareBo.getInstance().getListByUserId(Integer.parseInt(key)));
        } catch (BoException e) {
            request.setAttribute("error", e.getMessage());
            log.error("BoException when getting hardware for view user, user: " + user.getUsername());
        }
        
        // set the results attribute
        if (results != null) {
            request.setAttribute("results", results);
        }
        
        // check for success message (for redirects from edit/add/delete pages)
        if (request.getParameter("success") != null) {
            request.setAttribute("success", request.getParameter("success"));
        }
        
        // forward to the users/view jsp
        request.getRequestDispatcher("/WEB-INF/flows/users/view.jsp").forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

                
    }

}