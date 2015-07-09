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
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // forward to login.jsp
        request.getRequestDispatcher(loginJsp).forward(request, response);
        return;
    }

    /**
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
        
        
        if (password != null && user != null) {
            
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
                
            	request.getSession().invalidate();
            	logger.info("Logging in user " + username + " now.");
                
                // add the user info to the session
            	UserInfoBean userBean = new UserInfoBean(); 
            	userBean.bindUser(user);
            	this.setUserInfo(request, userBean);
            	
                // redirect as appropriate
                if (request.getParameter("next") != null) {
                    response.sendRedirect( request.getParameter("next") ); 
                }
                else {
                    response.sendRedirect("");
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
        else {
        	logger.info("User not found.");
            request.setAttribute("error", incorrectMsg);
            request.getRequestDispatcher(loginJsp).forward(request, response);
            return;
        }
    }

}
