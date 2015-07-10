package com.simoncomputing.app.winventory.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.h2.engine.Session;
import org.apache.log4j.Logger;

import com.simoncomputing.app.winventory.authentication.PasswordHasher;
import com.simoncomputing.app.winventory.bo.UserBo;
import com.simoncomputing.app.winventory.domain.User;
import com.simoncomputing.app.winventory.formbean.UserInfoBean;

/**
 * Servlet implementation class LoginServlet
 * 
 * Handles the local user login process for the Webapp. 
 * 
 */
@WebServlet("/login")
public class LoginController extends BaseController {
	
	private static final long serialVersionUID = 1L;
	private static final String incorrectMsg = "Username or Password was incorrect.";
    private static final String loginJsp = "/WEB-INF/flows/authentication/login.jsp";
    private static Logger logger = Logger.getLogger( LoginController.class );
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
    	super(); 
    }

    /**
     * forwards the user to the "login.jsp"
     * 
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // forward to login.jsp
        request.getRequestDispatcher(loginJsp).forward(request, response);
        return;
    }

    /**
     * This method handles logins for users logging in with a password,
     * as opposed to using the Google login
     * 
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // get the username and password from the POST
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = new User();
        
        logger.info("Attempting to login user:  " + username);
        
        // fetch the user from the db
        try {
           UserBo userBo = UserBo.getInstance();
           user = userBo.getUserByUsername(username);
        } catch (Exception e) {
            // forward with errors to login.jsp
        	logger.error("Something Excpetional has gone wrong!!!!!!");
            request.setAttribute("error", incorrectMsg);
            request.getRequestDispatcher(loginJsp).forward(request, response);
            return;
        }
        
        // if the user exists and a password was typed in
        if (password != null && user != null) {
            
            // if the user is inactive, reject
            if (!user.getIsActive()) {
                logger.info("Inactive user " + user.getUsername() + " attempted to login.");
                request.setAttribute("error", incorrectMsg);
                request.getRequestDispatcher(loginJsp).forward(request, response);
                return;
            }
            
            // Hash the given password which also checks if it is the required length
            try {
                // try to encode the password using the password hasher
                PasswordHasher.encodePassword(password);
            } 
            catch (IllegalArgumentException e) {
            	logger.info("password was not between 8 and 32 characters in length");
                request.setAttribute("error", incorrectMsg);
                request.getRequestDispatcher(loginJsp).forward(request, response);
                return;
            }
            
            // check the password, login user if password check returns true
            if (PasswordHasher.checkPassword(password, user.getPassword())) {
                
                // logout any user which may have already been logged in
            	request.getSession().invalidate();
            	
            	// log the successful login
            	logger.info("Logging in user " + username + " now.");
                
                // add the user info to the session
            	UserInfoBean userBean = new UserInfoBean(); 
            	userBean.bindUser(user);
            	this.setUserInfo(request, userBean);
            	
                // redirect as appropriate
                if (request.getParameter("next") != null && !request.getParameter("next").equals("")) {
                    // if the user attempted to reach another page and was redirected to login,
                    // next is the location the user wanted to reach in the first place
                    response.sendRedirect( request.getParameter("next") ); 
                }
                else {
                    // default destination
                    response.sendRedirect(request.getContextPath() + "/hardware/results");
                    return;
                }
            }
            
            // username was valid, but password was incorrect
            else {
            	logger.info("password doesn't match");

                request.setAttribute("error", incorrectMsg);
                request.getRequestDispatcher(loginJsp).forward(request, response);
                return;
            }
        } 
        // the username is not in the db
        else {
        	logger.info("User not found.");
            request.setAttribute("error", incorrectMsg);
            request.getRequestDispatcher(loginJsp).forward(request, response);
            return;
        }
    }

}
