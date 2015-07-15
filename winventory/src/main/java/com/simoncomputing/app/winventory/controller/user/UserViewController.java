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
import com.simoncomputing.app.winventory.bo.RoleBo;
import com.simoncomputing.app.winventory.bo.UserBo;
import com.simoncomputing.app.winventory.domain.Hardware;
import com.simoncomputing.app.winventory.domain.User;
import com.simoncomputing.app.winventory.formbean.UserInfoBean;
import com.simoncomputing.app.winventory.util.Barcoder;
import com.simoncomputing.app.winventory.util.BoException;
import com.simoncomputing.app.winventory.controller.BaseController;

@WebServlet("/users/view")
public class UserViewController extends BaseController {
    private static final long serialVersionUID = 1L;
    
    private Logger logger = Logger.getLogger(UserViewController.class); 

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        long key = -1; // the key of the requested user
        User user; // the requested user 
        UserInfoBean currentUser; // the currently logged in user
        String roleTitle = null; // the user's current role
        
        // if key not present/valid, redirect to users list
        try {
            key = Long.parseLong(request.getParameter("key"));
            user = UserBo.getInstance().read(key);
            request.setAttribute("barcode", Barcoder.getBarcode(user));
            currentUser = this.getUserInfo(request);
                    
        } catch (Exception e) {
            logger.info("Invalid key for HTTP GET /users/edit. Redirecting to list users page.");
            String error = "No user exists with key " + key +". The user may have been deleted or "
                    + "that may be the wrong key.";
            if (key == -1) {
                error = "Invalid user key.";
            }
            response.sendRedirect(request.getContextPath() + "/users/results?error=" + error);
            return;
        }
        
        // if the user does not exist:
        if (user == null) {
            logger.info("Invalid key for HTTP GET /users/edit. Redirecting to list users page.");
            String error = "No user exists with key " + key +". The user may have been deleted or "
                    + "that may be the wrong key.";
            response.sendRedirect(request.getContextPath() + "/users/results?error=" + error);
            return;
        }
        
        
        
        // if the user is rejected, the redirect is sent in the require permission method.
        if (  !(
                this.userHasPermission(request, "readUser")
            || (this.userHasPermission(request, "readSelf") && key == currentUser.getKey()) )) {
            this.denyPermission(request, response);
            return;
        }
        
        // set as request attribute
        request.setAttribute("user", user);
        
        
        // get the hardware the user owns
        ArrayList<Hardware> results = null;
        
        // try to get the hardware list
        try {
            results = new ArrayList<Hardware>(HardwareBo.getInstance().getListByUserId(key));
        } catch (BoException e) {
            request.setAttribute("error", e.getMessage());
            logger.error("BoException when getting hardware for view user, user: " + user.getUsername());
        }
        
        // set the results attribute
        if (results != null) {
            request.setAttribute("results", results);
        }
        
        // check for success message (for redirects from edit/add/delete pages)
        if (request.getParameter("success") != null) {
            request.setAttribute("success", request.getParameter("success"));
        }
        
        // check for delete request, if so then the delete confirmation message will appear
        if (request.getParameter("delete") != null) {
            request.setAttribute("delete", request.getParameter("delete"));
        }
        
        // check for delete request, if so then the delete confirmation message will appear
        if (request.getParameter("error") != null) {
            request.setAttribute("error", request.getParameter("error"));
        }
        
        // forward to the users/view jsp
        request.getRequestDispatcher("/WEB-INF/flows/users/view.jsp").forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

                
    }

}