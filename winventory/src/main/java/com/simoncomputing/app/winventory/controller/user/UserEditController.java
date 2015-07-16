package com.simoncomputing.app.winventory.controller.user;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.simoncomputing.app.winventory.bo.EventBo;
import com.simoncomputing.app.winventory.bo.HardwareBo;
import com.simoncomputing.app.winventory.bo.RefConditionBo;
import com.simoncomputing.app.winventory.bo.RoleBo;
import com.simoncomputing.app.winventory.bo.UserBo;
import com.simoncomputing.app.winventory.controller.BaseController;
import com.simoncomputing.app.winventory.domain.EventType;
import com.simoncomputing.app.winventory.domain.Hardware;
import com.simoncomputing.app.winventory.domain.RefCondition;
import com.simoncomputing.app.winventory.domain.Role;
import com.simoncomputing.app.winventory.domain.User;
import com.simoncomputing.app.winventory.formbean.UserInfoBean;
import com.simoncomputing.app.winventory.util.BoException;

/**
 * Servlet implementation class UserEditController
 */
@WebServlet("/users/edit")
public class UserEditController extends BaseController {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(UserEditController.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserEditController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * displays the edit user page/form
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
	        throws ServletException, IOException {
	    
	    long key = -1; // the key of the requested user
	    User user; // the requested user 
	    UserInfoBean currentUser; // the currently logged in user
	    String roleTitle = null;
	    
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
	    } else {
            try {
                roleTitle = RoleBo.getInstance().read(user.getRoleId().longValue()).getTitle();
            } catch (BoException e) {
                logger.error("BoException when getting roleTitle for user " + user.getUsername());
            }
        }
	    
	    
	    
	    // if the user is rejected, the redirect is sent in the deny permission method.
        if (       ! this.userHasPermission(request, "updateUser") 
                && !(this.userHasPermission(request, "updateSelf") && key == currentUser.getKey()) 
                ) {
            this.denyPermission(request, response);
            return;
        }
	    
        
        // try to get the list of all roles
        ArrayList<Role> roles = null;
        try {
            roles = new ArrayList<Role>(RoleBo.getInstance().getAll());
        } catch (BoException e) {
            e.printStackTrace();
        }
        
        // add roles attribute to the request
        if (roles != null) {
            request.setAttribute("roles", roles);
        }
        
        //add the user to the attribute
        request.setAttribute("user", user);
        
        // forward to the edit jsp
        request.getRequestDispatcher("/WEB-INF/flows/users/edit.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * handle the edit-user form
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
	    long key = -1; // the key of the requested user
        User user; // the requested user 
        UserInfoBean currentUser; // the currently logged in user
        
        boolean reject = false;
        
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
        
        // if the user is rejected, the redirect is sent in the deny permission method.
        if (       ! this.userHasPermission(request, "updateUser") 
                && !(this.userHasPermission(request, "updateSelf") && key == currentUser.getKey()) 
                ) {
            this.denyPermission(request, response);
            return;
        }
        
        // form errors to be displayed on form page
        ArrayList<String> errors = new ArrayList<String>();
        
        // bind the form values to the user
        errors = user.bindEditForm(request);
        
        // try to update the user
        try {
            UserBo.getInstance().update(user);
            
         // Record event
            EventBo.getInstance().createSystemEvent(user.getUsername() + " was updated. ",
                getUserInfo(request), EventType.SYSTEM, null, null, null, user);
        } catch (BoException e) {
            logger.error("BoException in UserEditController. This is unexpected behavior. "
                    + "Username of the user which should have been edited:" + user.getUsername());
            errors.add("There was an error processing your request. Please check your values and try again. "
                    + "If the error persists, please contact and administrator.");
            response.sendRedirect(request.getContextPath() + "/users/edit?key=" + key );
            return;
        }
        
        response.sendRedirect(request.getContextPath() + "/users/view?key=" + key + "&success=true");

	}

}
