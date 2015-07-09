package com.simoncomputing.app.winventory.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.simoncomputing.app.winventory.formbean.UserInfoBean;

/**
 * Servlet implementation class LogoutController
 */
@WebServlet("/logout")
public class LogoutController extends BaseController {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(LogoutController.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoutController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// get current user
		UserInfoBean userBean = this.getUserInfo(request);
		
		// Check if user is logged in
		if (userBean == null) {
			// log who is logging out
			logger.info("Invalid user accessed Logout");
		}
		else {
			// log who is logging out
			logger.info("Logged out user: " + userBean.getUsername());
			
		    // logout the user
			request.getSession(false).invalidate();
		}
		
		// redirect to login page
		response.sendRedirect(request.getContextPath() + "/login");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
	    // redirect to doGet()
		response.sendRedirect(request.getRequestURI());
	}

}
