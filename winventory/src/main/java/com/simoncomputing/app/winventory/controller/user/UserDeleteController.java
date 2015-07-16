package com.simoncomputing.app.winventory.controller.user;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.simoncomputing.app.winventory.bo.AccessTokenBo;
import com.simoncomputing.app.winventory.bo.EventBo;
import com.simoncomputing.app.winventory.bo.HardwareBo;
import com.simoncomputing.app.winventory.bo.LocationBo;
import com.simoncomputing.app.winventory.bo.UserBo;
import com.simoncomputing.app.winventory.controller.BaseController;
import com.simoncomputing.app.winventory.domain.Event;
import com.simoncomputing.app.winventory.domain.EventType;
import com.simoncomputing.app.winventory.domain.Hardware;
import com.simoncomputing.app.winventory.domain.Location;
import com.simoncomputing.app.winventory.domain.User;
import com.simoncomputing.app.winventory.formbean.UserInfoBean;
import com.simoncomputing.app.winventory.util.BoException;

/**
 * Controller for the delete user function
 */
@WebServlet("/users/delete")
public class UserDeleteController extends BaseController {

    private static final long serialVersionUID = 1L;
    private Logger logger = Logger.getLogger(UserDeleteController.class);
    
    /**
     * Runs when the "delete" button is selected on the "users/view" page
     * url:  <contextPath>/users/delete?key=<key>
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                    throws ServletException, IOException {

        long key = -1; // the key of the requested user
        User user; // the requested user 
        UserInfoBean currentUser; // the currently logged in user
        
        // if key not present/valid, redirect to users list
        try {
            key = Long.parseLong(request.getParameter("key"));
            user = UserBo.getInstance().read(key);
            currentUser = this.getUserInfo(request);
        } catch (Exception e) {
            logger.info("Invalid key for HTTP GET /users/edit. Redirecting to list users page.");
            response.sendRedirect(request.getContextPath() + "/users/results");
            return;
        }
        
        // if the user does not exist:
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/users/results");
            return;
        }
        
        // if the user is rejected, the redirect is sent in the require permission method.
        if (!this.userHasPermission(request, "deleteUser")) {
            this.denyPermission(request, response);
            return;
        }
        
        // do not allow users to delete themselves
        if (user.getKey().equals(currentUser.getKey())) {
            logger.info("User " + user.getUsername() + " tried to delete (him/her)self.");
            String error = "You cannot delete yourself.";
            request.setAttribute("error", error);
            // send redirect to results page, with success message
            response.sendRedirect(request.getContextPath() + "/users/view?key=" + user.getKey() + "&error=" + error);
            return;
        }
        
        // Must delete any access token(s) associated with the user before deleting the user,
        // or else sql will give exceptions.
        AccessTokenBo accessTokenBo = AccessTokenBo.getInstance();
        
        try {
            accessTokenBo.delete(((Long)(user.getKey())).intValue());
        } catch (BoException e) {
            logger.error("BoException when trying to delete access "
                    + "token for user: " + user.getUsername());
        }
        
        UserBo bo = UserBo.getInstance();

        // Attempt to delete the User from the database using a BO
        try {
            bo.delete(key);
            EventBo.getInstance().createSystemEvent("Delete Software " + key 
                    + ": Deleting software with key of " + key + ": " 
                    + user.toString(), 
                getUserInfo(request), EventType.SYSTEM, null, null, null, null);
            logger.info("Deleted user " + user.getUsername() + ".");
        } catch (BoException e) {
            String error = "User " + user.getUsername() + " could not be deleted at this time.";
            logger.error("Attempt to delete user " + user.getUsername() + " failed.");
            response.sendRedirect(request.getContextPath() + "/users/view?key=" + key + "&error=" + error);
            return;
        }
        
        // send redirect to results page, with success message
        response.sendRedirect(request.getContextPath() + "/users/results?success=true");

    }

}