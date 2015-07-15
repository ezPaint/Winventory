package com.simoncomputing.app.winventory.controller.user;

import java.io.IOException;
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

/**
 * Servlet for the page that shows the list of all users
 */
@WebServlet("/users/results")
public class UserResultsController extends BaseController {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(UserResultsController.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserResultsController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
	    
        // if the user is rejected, the redirect is sent in the require permission method.
        if (!this.userHasPermission(request, "readUser")) {
            this.denyPermission(request, response);
            return;
        }
        
	    
	    // list of users
	    ArrayList<User> results = null;

	    // fill the list of users
        try {
            results = new ArrayList<User>(UserBo.getInstance().getAll());
        } catch (BoException e) {
            logger.error("BoException in " + this.getClass().getName());
        }
        
        // set the results attribute of the request
        if (results != null) {
            request.setAttribute("results", results);
        }
        
        // check for success message (for redirects from edit/add/delete pages)
        if (request.getParameter("success") != null) {
            request.setAttribute("success", request.getParameter("success"));
        }
        
        // check for success message (for redirects from edit/add/delete pages)
        if (request.getParameter("error") != null) {
            request.setAttribute("error", request.getParameter("error"));
        }
        
	    
        //forward to users/results.jsp
		request.getRequestDispatcher("/WEB-INF/flows/users/results.jsp").forward(request, response);
		
	}

//	/**
//	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
//	 */
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//	    // require permission if/when this gets implemented
//	}

}
